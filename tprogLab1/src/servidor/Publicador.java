package servidor;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import excepciones.CategoriaNoExisteException;
import excepciones.CursoNoExisteException;
import excepciones.CursoYaExisteException;
import excepciones.DocenteNoAsignadoAEdicionException;
import excepciones.DocenteNoPerteneceMismoInstitutoException;
import excepciones.EdicionNoExisteException;
import excepciones.EstudianteComoDocenteException;
import excepciones.EstudianteNoInscriptoException;
import excepciones.EstudianteYaInscripto;
import excepciones.InstitutoNoBrindaCursoException;
import excepciones.InstitutoNoExisteException;
import excepciones.NotaErroneaException;
import excepciones.ProgramaNoExisteException;
import excepciones.UsuarioNoExisteException;
import logica.ControladorCurso;
import logica.ControladorProgramaFormacion;
import logica.ControladorUsuario;
import logica.DtCertificado;
import logica.DtComentario;
import logica.DtCurso;
import logica.DtDocente;
import logica.DtEdicion;
import logica.DtEdicionEstudiante;
import logica.DtEstudiante;
import logica.DtProgramaFormacion;
import logica.DtUsuario;
import logica.Factory;
import logica.InterfazControladorCurso;
import logica.InterfazControladorProgramaFormacion;
import logica.InterfazControladorUsuario;
import presentacion.Main;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class Publicador {

    private Endpoint endpoint = null;
    // Constructor
    public Publicador(){}

    // Operaciones que quiero publicar

    @WebMethod(exclude = true)
    public void publicar(){
    	//String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    	String rootPath = Main.class.getResource("Main.class").getPath();
    	// Matchea la ruta al directorio raíz del proyecto.
    	//Pattern patt = Pattern.compile("(.*/tprogLab1/).*");
        Pattern patt = Pattern.compile(".*?(/.*/).*?/presentacion/.*"); 
        Matcher mat = patt.matcher(rootPath);
        String configPath = null;
        if (mat.matches()) {
        	configPath = mat.group(1) + "config.properties";
        }
    	// Carga las propiedades desde el archivo config.properties.
    	Properties appProps = new Properties();
    	try {
			appProps.load(new FileInputStream(configPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	// Asigna al endpoint la URL indicada en config.properties.
    	String endpointURL = appProps.getProperty("endpointURL");
    	// Publica el endpoint.
        endpoint = Endpoint.publish(endpointURL, this);
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
            return endpoint;
    }

    @WebMethod
    public String edicionDoGet(String json) {
    	Factory f = new Factory();
		InterfazControladorCurso icc = f.getInterfazControladorCurso();
		InterfazControladorUsuario icu = f.getInterfazControladorUsuario();
    	
		Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
		JsonObject request = gsonBuilder.fromJson(json, JsonObject.class);
    	if (request.has("Edicion") && request.has("Curso")) {
			String curso = request.get("Curso").getAsJsonArray().get(0).getAsString();
			String edicion = request.get("Edicion").getAsJsonArray().get(0).getAsString();
			DtEdicion dtEdicion = null;
			try {
				dtEdicion = icc.darDatosEdicion(curso, edicion);
				Gson gson = new Gson();
				String edicionJson = gson.toJson(dtEdicion);
				return edicionJson;
			} catch (CursoNoExisteException | EdicionNoExisteException e) {
				JsonObject jsonRespuesta = new JsonObject();
				jsonRespuesta.addProperty("ERROR", e.getMessage());
				Gson gson = new Gson();
				return gson.toJson(jsonRespuesta);
			}
		} else if (request.has("Curso")) {
			String curso = request.get("Curso").getAsJsonArray().get(0).getAsString();
			Map<String, DtEdicion> ediciones = new HashMap<String, DtEdicion>();
			try {
				ediciones = icc.listarDtEdiciones(curso);
				Gson gson = new Gson();
				JsonArray edicionesArray = new JsonArray();
				ediciones.forEach((k,v)->{
					edicionesArray.add(gson.toJsonTree(v));
				});
				
				String edicionesJson = gson.toJson(edicionesArray);
				return edicionesJson;
			} catch (CursoNoExisteException e) {
				JsonObject jsonRespuesta = new JsonObject();
				jsonRespuesta.addProperty("ERROR", e.getMessage());
				Gson gson = new Gson();
				return gson.toJson(jsonRespuesta);
			}
		} else if (request.has("Usuario")) {
			String usuario = request.get("Usuario").getAsJsonArray().get(0).getAsString();
			Map<String, DtEdicion> ediciones = new HashMap<String, DtEdicion>();
			try {
				ediciones = icu.getEdicionesEstudiante(usuario);
				Gson gson = new Gson();
				JsonArray array = new JsonArray();
				for (Map.Entry<String, DtEdicion> edicion : ediciones.entrySet()) {
					if (edicion.getValue() instanceof DtEdicionEstudiante) {
						DtEdicionEstudiante ee = (DtEdicionEstudiante) edicion.getValue();
						array.add(gson.toJsonTree(ee));
					} else {
						array.add(gson.toJsonTree(edicion.getValue()));
					}
				}
				return gson.toJson(array);
				
			} catch (Exception e) {
				JsonObject jsonRespuesta = new JsonObject();
				jsonRespuesta.addProperty("ERROR", e.getMessage());
				Gson gson = new Gson();
				return gson.toJson(jsonRespuesta);
			}
		} else {
			Map<String, DtEdicion> ediciones = new HashMap<String, DtEdicion>();
			Map<String, DtCurso> cursos = icc.listarCursos();
			for (Map.Entry<String, DtCurso> curso : cursos.entrySet()) {
				Map<String, DtEdicion> edicionesCurso;
				try {
					edicionesCurso = icc.listarDtEdiciones(curso.getValue().getNombre());
					for (Map.Entry<String, DtEdicion> edicionCurso : edicionesCurso.entrySet()) {
						ediciones.put(edicionCurso.getKey(), edicionCurso.getValue());
					}
				} catch (CursoNoExisteException e) {
					JsonObject jsonRespuesta = new JsonObject();
					jsonRespuesta.addProperty("ERROR", e.getMessage());
					Gson gson = new Gson();
					return gson.toJson(jsonRespuesta);
				}
			}
			Gson gson = new Gson();
			String edicionesJson = gson.toJson(ediciones);
			return edicionesJson;
		}
    }
    
    /*
     * @param (params - Arreglo de parametros de una request http en formato JSON)
     */
    
    @WebMethod
    public String cursoGet(String params) {
    	Gson gson = new Gson();
    	JsonObject jParams = gson.fromJson(params, JsonObject.class);
    	Factory fab = new Factory();
    	InterfazControladorCurso iCurso = fab.getInterfazControladorCurso();
		if(jParams.has("Curso")) {
			try {
				DtCurso curso = iCurso.mostrarCurso(jParams.get("Curso").getAsJsonArray().get(0).getAsString());
				String cursoJSON = gson.toJson(curso);
				return cursoJSON;
			} catch (CursoNoExisteException e) {
				return "ERROR: El curso no existe.";
			}
		}else if (jParams.has("Instituto")) {
			Map<String, DtCurso> cursoMap = iCurso.listarCursosAsociados(jParams.get("Instituto").getAsJsonArray().get(0).getAsString());
			JsonArray jArray = new JsonArray();
			cursoMap.forEach((k,v)->{
				jArray.add(gson.toJsonTree(v));
			});
			String cursosJSON = gson.toJson(jArray);
			return cursosJSON;
			
		}else if(jParams.has("Previas")) {
			
			try {
				DtCurso dtCurso = iCurso.mostrarCurso(jParams.get("Previas").getAsJsonArray().get(0).getAsString());
				List<String> catArrayList = dtCurso.getPrevias();
				Map<String, DtCurso> res = iCurso.listarDtCursos(catArrayList);
				JsonArray jArray2 = new JsonArray();
				res.forEach((k,v)->{
					jArray2.add(gson.toJsonTree(v));
				});
				String catJSON = gson.toJson(jArray2);
				return catJSON;
			} catch (CursoNoExisteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}else if(jParams.has("Programa")) {
			
			try {
				InterfazControladorProgramaFormacion iFormacion = fab.getInterfazControladorProgramaFormacion();
				DtProgramaFormacion dtF = iFormacion.mostrarProgramaFormacion(jParams.get("Programa").getAsJsonArray().get(0).getAsString());
				List<String> catArrayList = dtF.getCursos();
				Map<String, DtCurso> res = iCurso.listarDtCursos(catArrayList);
				JsonArray jArray2 = new JsonArray();
				res.forEach((k,v)->{
					jArray2.add(gson.toJsonTree(v));
				});
				String catJSON = gson.toJson(jArray2);
				return catJSON;
			} catch (ProgramaNoExisteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}else if (jParams.has("Edicion")) {
			DtCurso curso = iCurso.getDtCursoDeEdicion(jParams.get("Edicion").getAsJsonArray().get(0).getAsString());
			return gson.toJson(curso);
			
		}else {
			Map<String, DtCurso> cursoMap = iCurso.listarCursos();
			JsonArray jArray = new JsonArray();
			cursoMap.forEach((k,v)->{
				jArray.add(gson.toJsonTree(v));
				
			});
			String cursosJSON = gson.toJson(jArray);
			return cursosJSON;
		}
		return "";
    }
    
    /*
     * @param (params - Body de un metodo post en formato JSON)
     */
    
    @WebMethod
    public String cursoPost(String body) {
    	Gson gson = new Gson();
    	JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
		Factory fab = new Factory();
		InterfazControladorCurso iCurso = fab.getInterfazControladorCurso();
    	
    	ArrayList<String> strings = new ArrayList<String>();
		if(jsonObject.get("Previas")!=null) {
			
			JsonArray array = jsonObject.get("Previas").getAsJsonArray();
			for (JsonElement string : array) {
				strings.add(string.getAsString());
			}
		}
		
		
		
		try {
			iCurso.altaCursoSrc(jsonObject.get("Instituto").getAsString(), jsonObject.get("Nombre").getAsString(), jsonObject.get("Descripcion").getAsString(), jsonObject.get("Duracion").getAsString(),jsonObject.get("CantHoras").getAsInt(),jsonObject.get("Creditos").getAsInt() , jsonObject.get("url").getAsString(), strings, jsonObject.get("src").getAsString());
			ArrayList<String> cate = new ArrayList<String>();
			if(jsonObject.get("categorias")!=null) {
				JsonArray categorias = jsonObject.get("categorias").getAsJsonArray();
				for (JsonElement cat : categorias) {
					cate.add(((JsonObject) cat).get("nombre").getAsString());
				}
			}
			iCurso.agregarCategoriasACurso(jsonObject.get("Nombre").getAsString(), cate);
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("OK","Curso agregado con exito" );
			return jsonObject2.toString();
		} catch (CursoYaExisteException | InstitutoNoExisteException | CursoNoExisteException | CategoriaNoExisteException e) {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("ERROR", e.getMessage());
			return jsonObject2.toString();
			
		}
    }
    @WebMethod
    public String cursoPut(String body,String nickSession){
    	Gson gson = new Gson();
    	JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
    	Factory fab = new Factory();
    	InterfazControladorCurso iCurso =fab.getInterfazControladorCurso();
    	if(nickSession!="") {
    		try {
    			iCurso.valorarUnCurso(jsonObject.get("Curso").getAsString(), jsonObject.get("Rating").getAsInt(),nickSession);
    			JsonObject jsonObject2 = new JsonObject();
    			jsonObject2.addProperty("OK","Se ha recibido la valoración." );
    			return jsonObject2.toString();
    		} catch (CursoNoExisteException | UsuarioNoExisteException e) {
    			// TODO Auto-generated catch block
    			JsonObject jsonObject2 = new JsonObject();
    			jsonObject2.addProperty("ERROR", e.getMessage());
    			return jsonObject2.toString();
    		}
    	}else {
    		JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("ERROR", "Ingrese para valorar");
			return jsonObject2.toString();
    	}
    	
    }
    
    public String cursoVisita(String body) {
    	Gson gson = new Gson();
    	JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
    	Factory fab = new Factory();
    	InterfazControladorCurso iCurso =fab.getInterfazControladorCurso();
    	try {
			int i = iCurso.cursoVisitado(jsonObject.get("Curso").getAsString());
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("OK",i);
			return jsonObject2.toString();
		} catch (CursoNoExisteException e) {
			// TODO Auto-generated catch block
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("ERROR", e.getMessage());
			return jsonObject2.toString();
		}
    }
    /*
     * @param (params - Body de un metodo post en formato JSON)
     * @param (sessionNick - String con el nickname de la session)
     */
    
    
    @WebMethod
    public String inscricpcionEstudianteEdicionGet(String params, String sessionNick, String sessionTipo) {
    	Gson gson = new Gson();
    	JsonObject jParams = gson.fromJson(params, JsonObject.class);
    	Factory fab = new Factory();
    	InterfazControladorCurso iCurso = fab.getInterfazControladorCurso();
    	InterfazControladorUsuario iUsuario = fab.getInterfazControladorUsuario();
    	
		if(jParams.has("Edicion") && jParams.has("Curso")) {
			Map<String, DtEstudiante> estEd = iCurso.listarEstudianteEdicion(jParams.get("Edicion").getAsJsonArray().get(0).getAsString());
			
			try {
				JsonArray jArray = new JsonArray();
				for (Map.Entry<String, DtEstudiante> est : estEd.entrySet()) {
						int k = iUsuario.obtenerCantidadInscripcionesRechazadas(jParams.get("Curso").getAsJsonArray().get(0).getAsString(), est.getValue().getNickname());
						JsonObject jObject = (JsonObject) gson.toJsonTree(est.getValue());
						jObject.addProperty("Ranking", k*0.5);
						jArray.add(jObject);
				}
				String jsonString2 = gson.toJson(jArray);
				return jsonString2;
			} catch (EstudianteComoDocenteException | UsuarioNoExisteException | EdicionNoExisteException
					| CursoNoExisteException e) {
				JsonObject jsonObject2 = new JsonObject();
				jsonObject2.addProperty("ERROR", e.getMessage());
				return jsonObject2.toString();
				
			}
		}else {
			if(sessionNick!="" && sessionTipo=="estudiante") {
				try {
					DtEstudiante estudiante =(DtEstudiante) iUsuario.informacionDeUsuario((String) sessionNick);
					
					String jsonString = gson.toJson(estudiante);
					return jsonString;
				} catch (UsuarioNoExisteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JsonObject jsonObject2 = new JsonObject();
					jsonObject2.addProperty("ERROR", e.getMessage());
					return jsonObject2.toString();
				}
				
			}
			return "";
		}
    }
    
    @WebMethod
    public String inscripcionEstudianteEdicionPost(String body,String sessionNick,String sessionTipo) {
    	
    	Gson gson = new Gson();
    	JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
		Factory fab = new Factory();
		InterfazControladorCurso iCurso = fab.getInterfazControladorCurso();
    	
    	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d;
		try {
			d = sdf.parse(jsonObject.get("Fecha-Inscripcion").getAsString());
			Calendar inscripcion = Calendar.getInstance();
			inscripcion.setTime(d);
			if(sessionNick!="") {
				iCurso.inscribirEstudianteACurso((String) sessionNick, jsonObject.get("Curso").getAsString(), jsonObject.get("Edicion").getAsString(),inscripcion, jsonObject.get("URL").getAsString());
				JsonObject jsonObject2 = new JsonObject();
				jsonObject2.addProperty("OK", "Inscripto con exito");
				return jsonObject2.toString();
				
			}
		} catch (ParseException | CursoNoExisteException | EdicionNoExisteException | UsuarioNoExisteException
				| EstudianteYaInscripto e) {
			
			e.printStackTrace();
			
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("ERROR", e.getMessage());
			return jsonObject2.toString();
		}
		return "";
    }
    
    public String inscripcionEstudianteEdicionPut(String body, String nickname) {
    	Factory f = new Factory();
		InterfazControladorCurso icc = f.getInterfazControladorCurso();
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(body, JsonObject.class);
		JsonObject jsonRespuesta = new JsonObject();
		
		if (json.has("Edicion")) {
			try {
				icc.desistirAInscripcionDeCurso(nickname, json.get("Edicion").getAsString());
				jsonRespuesta.addProperty("OK", "Se ha desistido al curso");
				return gson.toJson(jsonRespuesta);
			} catch (EdicionNoExisteException | UsuarioNoExisteException e) {
				jsonRespuesta.addProperty("ERROR", e.getMessage());
				return gson.toJson(jsonRespuesta);
			}
		} else {
			jsonRespuesta.addProperty("ERROR", "Formato no valido");
			return gson.toJson(jsonRespuesta);
		}
    }
    
    @WebMethod
    public String inscripcionEstudianteProgramaPost(String body,String sessionNick,String sessionTipo) {
    	Gson gson = new Gson();
    	JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
		Factory fab = new Factory();
		InterfazControladorUsuario iUsuario = fab.getInterfazControladorUsuario();
    	
    	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d;
		try {
			d = sdf.parse(jsonObject.get("Fecha-Inscripcion").getAsString());
			Calendar inscripcion = Calendar.getInstance();
			inscripcion.setTime(d);
			if(sessionNick!="") {
				iUsuario.inscribirEstudiantePrograma((String) sessionNick,jsonObject.get("Programa").getAsString(),inscripcion);
				JsonObject jsonObject2 = new JsonObject();
				jsonObject2.addProperty("OK", "Inscripto con exito");
				return jsonObject2.toString();
				
			}
		} catch (ParseException | UsuarioNoExisteException
				| EstudianteYaInscripto | ProgramaNoExisteException | EstudianteComoDocenteException e) {
			
			e.printStackTrace();
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("ERROR", e.getMessage());
			return jsonObject2.toString();
		}
    	return "";
    }
    
    @WebMethod
    public String loginPost(String body) {
		Factory f = new Factory();
		InterfazControladorUsuario iUsuario = f.getInterfazControladorUsuario();
		Gson gson = new Gson();
    	JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
    	
		try {
			if(iUsuario.validarDatosInicioSesion(jsonObject.get("Nickname").getAsString(), jsonObject.get("Pass").getAsString())) {
				
				DtUsuario logeado = iUsuario.informacionDeUsuario(jsonObject.get("Nickname").getAsString());
				String tipoString; 
				if ( logeado instanceof DtEstudiante) {
					tipoString="estudiante";
				}else {
					tipoString="docente";
				}
				 JsonElement jsonElement = gson.toJsonTree(logeado);
				 JsonObject j = (JsonObject) jsonElement;
				 j.addProperty("tipo", tipoString);
				 
				 // Login móvil debe rechazar a docentes.
				 if ((tipoString == "docente") && (jsonObject.has("Movil"))) {
					 j.addProperty("ERROR", "No es estudiante");
					 return j.toString();
				 }
				 
				 j.addProperty("OK", "Sesion iniciada");
				 return j.toString();
				 
			}else {
				JsonObject jsonObject2 = new JsonObject();
				jsonObject2.addProperty("ERROR","Password o Nickname incorrectos");
				return jsonObject2.toString();
			}
			
		} catch (UsuarioNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("ERROR", e.getMessage());
			return jsonObject2.toString();
		}
	
    }
    
    @WebMethod
    public String inscripcionDocenteDoGet(String json) {
    	Factory f = new Factory();
		InterfazControladorCurso icc = f.getInterfazControladorCurso();
		InterfazControladorUsuario icu = f.getInterfazControladorUsuario();
		
		Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
		JsonObject request = gsonBuilder.fromJson(json, JsonObject.class);
		
		if ((request.has("Edicion")) && (request.has("Tipo"))) {
			String edicion = request.get("Edicion").getAsJsonArray().get(0).getAsString();
			String tipo = request.get("Tipo").getAsJsonArray().get(0).getAsString();
			if (tipo.equals("estudiante")) {
				JsonArray array = new JsonArray();
				Map<String, DtEstudiante> estudiantes = icc.listarEstudianteEdicion(edicion);
				Gson gson = new Gson();
				for (Map.Entry<String, DtEstudiante> dt : estudiantes.entrySet()) {
					array.add(gson.fromJson(gson.toJson(dt.getValue()), JsonObject.class));
				}
				String estudiantesJson = gson.toJson(array);
				return estudiantesJson;
			} else if (tipo.equals("docente")) {
				JsonArray array = new JsonArray();
				Map<String, DtDocente> docentes = icu.getDocentesDeEdicion(edicion);
				Gson gson = new Gson();
				for (Map.Entry<String, DtDocente> dt : docentes.entrySet()) {
					array.add(gson.fromJson(gson.toJson(dt.getValue()), JsonObject.class));
				}
				String docentesJson = gson.toJson(array);
				return docentesJson;
			}
		}
		return null;
    }
    
    @WebMethod
    public String edicionDoPost(String s) {
    	Factory f = new Factory();
		InterfazControladorCurso icc = f.getInterfazControladorCurso();
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(s, JsonObject.class);
    	
    	if (json.has("Docentes") && json.has("AnioInicio") && json.has("MesInicio") && json.has("DiaInicio") && json.has("AnioFin") && json.has("MesFin") && json.has("DiaFin") && json.has("Nombre") && json.has("Cupo") && json.has("Curso") && json.has("Instituto") && json.has("Source")) {
			JsonArray docentes = json.get("Docentes").getAsJsonArray();
			ArrayList<String> listaDocentes = new ArrayList<String>();
			for (JsonElement jsonElement: docentes) {
				String agregar = jsonElement.getAsString();
				listaDocentes.add(agregar);
			}
			
			Calendar fechaInicio = Calendar.getInstance();
			fechaInicio.set(json.get("AnioInicio").getAsInt(), (json.get("MesInicio").getAsInt() - 1), json.get("DiaInicio").getAsInt());
			
			
			Calendar fechaFin = Calendar.getInstance();
			fechaFin.set(json.get("AnioFin").getAsInt(), (json.get("MesFin").getAsInt() - 1), json.get("DiaFin").getAsInt());
			
			Boolean alta = false;
			JsonObject respuesta = new JsonObject();
			try {
				
				String nombre = json.get("Nombre").getAsString();
				Integer cupo = json.get("Cupo").getAsInt();
				String curso = json.get("Curso").getAsString();
				String instituto = json.get("Instituto").getAsString();
				String source = json.get("Source").getAsString();
				
				alta = icc.altaEdicionCursoSrc(nombre, fechaInicio, fechaFin, cupo, curso, listaDocentes, instituto, source);
				if (alta) {
					respuesta.addProperty("OK", "Edición de curso dada de alta");
					return gson.toJson(respuesta);
				} else {
					respuesta.addProperty("ERROR", "Ya existe una edición con ese nombre");
					return gson.toJson(respuesta);
				}
			} catch (DocenteNoPerteneceMismoInstitutoException | EstudianteComoDocenteException | CursoNoExisteException
					| UsuarioNoExisteException | InstitutoNoExisteException | InstitutoNoBrindaCursoException e) {
				JsonObject jsonRespuesta = new JsonObject();
				jsonRespuesta.addProperty("ERROR", e.getMessage());
				return gson.toJson(jsonRespuesta);
			}
		} else {
			JsonObject jsonRespuesta = new JsonObject();
			jsonRespuesta.addProperty("ERROR", "Invalid JSON");
			return gson.toJson(jsonRespuesta);
		}
    }
    
    @WebMethod
    public String edicionDoPut(String body, String nickname) {
    	Factory f = new Factory();
		InterfazControladorCurso icc = f.getInterfazControladorCurso();
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(body, JsonObject.class);
		
		JsonObject jsonRespuesta = new JsonObject();
		if (json.has("Edicion")) {
			String edicion = json.get("Edicion").getAsString();
			try {
				icc.cerrarEdicion(edicion, nickname);
				jsonRespuesta.addProperty("OK", "Edicion cerrada");
				return gson.toJson(jsonRespuesta);
			} catch (CursoNoExisteException | EdicionNoExisteException | UsuarioNoExisteException e) {
				jsonRespuesta.addProperty("ERROR", e.getMessage());
				return gson.toJson(jsonRespuesta);
			}
		} else {
			jsonRespuesta.addProperty("ERROR", "Invalid JSON");
			return gson.toJson(jsonRespuesta);
		}
    }
    
    @WebMethod
    public String inscripcionDocenteDoPost(String s, String nickname) {
    	Factory f = new Factory();
		InterfazControladorUsuario icu = f.getInterfazControladorUsuario();
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(s, JsonObject.class);
		
		if (json.has("Inscripciones") && json.has("Docente") && json.has("Edicion")) {
			JsonArray inscripciones = json.get("Inscripciones").getAsJsonArray();
			Map<String, Boolean> inscribir = new HashMap<String, Boolean>();
			for (JsonElement jsonElement: inscripciones) {
				inscribir.put(jsonElement.getAsJsonObject().get("Nombre").getAsString(), jsonElement.getAsJsonObject().get("Estado").getAsBoolean());
			}
			
			JsonObject respuesta = new JsonObject();
			try {
				icu.inscribirEstudiantesPorUnDocente(nickname, json.get("Edicion").getAsString(), inscribir);
				respuesta.addProperty("OK", "Estudiante inscripto");
				return gson.toJson(respuesta);
			} catch (UsuarioNoExisteException | DocenteNoAsignadoAEdicionException | EstudianteNoInscriptoException
					| EstudianteYaInscripto e) {
				respuesta.addProperty("ERROR", e.getMessage());
				return gson.toJson(respuesta);
			}
		} else {
			JsonObject jsonRespuesta = new JsonObject();
			jsonRespuesta.addProperty("ERROR", "Invalid JSON");
			return gson.toJson(jsonRespuesta);
		}
    }
    
    
    @WebMethod
    public String inscripcionDocenteDoPut(String body, String nick) {
    	Gson gson = new Gson();
    	Factory fab = new Factory();
    	InterfazControladorUsuario iUsuario = fab.getInterfazControladorUsuario();
    	JsonObject jObject = gson.fromJson(body, JsonObject.class);
    	try {
			iUsuario.ingresarResultadosDeEvaluaciones(nick, jObject.get("Edicion").getAsString(),jObject.get("Estudiante").getAsString() , jObject.get("Nota").getAsInt());
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("OK", "Nota registrada con exito");
			return jsonObject2.toString();
		} catch (UsuarioNoExisteException | DocenteNoAsignadoAEdicionException | EstudianteNoInscriptoException
				| NotaErroneaException e) {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("ERROR",e.getMessage());
			return jsonObject2.toString();
			
		}
    }
    
    @WebMethod
    public String usuarioDoGet(String json) {
    	try {
	    	ControladorUsuario cu = new ControladorUsuario();
	    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    	String jsonRetorno = "{}";
	    	JsonObject paramJson = gson.fromJson(json, JsonObject.class);
	    	String nickUsuario = "";
	    	String instituto = "";
	    	if (paramJson.has("nickname")) {
	    		nickUsuario = paramJson.get("nickname").getAsString();
	    	}
	    	if (paramJson.has("instituto")) {
	    		instituto = paramJson.get("instituto").getAsString();
	    	}
			if (nickUsuario != "") {
				DtUsuario auxDtUsuario = cu.informacionDeUsuario(nickUsuario);
				jsonRetorno = gson.toJson(auxDtUsuario);
			} else if (instituto != "") {
				JsonArray array = new JsonArray();
				List<DtDocente> listaDocentes = cu.listarDocentesInstituto(instituto);
				for (DtDocente dtDocente : listaDocentes) {
					array.add(gson.fromJson(gson.toJson(dtDocente), JsonObject.class));
				}
				jsonRetorno = array.toString();
			} else {
				JsonArray array = new JsonArray();
				String tipoUsuario = "";
				if (paramJson.has("type")) {
					tipoUsuario = paramJson.get("type").getAsString();
				}
				List<DtUsuario> listaUsuarios = cu.listarDtUsuarios();
				for (DtUsuario dtUsuario : listaUsuarios) {
					if (tipoUsuario != "") {
						if (tipoUsuario.equals("docente") && dtUsuario instanceof DtDocente) {
							array.add(gson.fromJson(gson.toJson(dtUsuario), JsonObject.class));						
						} else if (tipoUsuario.equals("estudiante") && dtUsuario instanceof DtEstudiante) {
							array.add(gson.fromJson(gson.toJson(dtUsuario), JsonObject.class));			
						} 						
					} else {
						array.add(gson.fromJson(gson.toJson(dtUsuario), JsonObject.class));									
					}
				}
				jsonRetorno = array.toString();
			}
			return jsonRetorno;
    	} catch (Exception e) {
			JsonObject jsonRespuesta = new JsonObject();
			jsonRespuesta.addProperty("ERROR", e.getMessage());
			return jsonRespuesta.toString();
		}
		
    }
    
    @WebMethod
    public String usuarioDoPost(String jsonRequest) {
    	try {
			JsonObject jsonRespuesta = new JsonObject();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject json = gson.fromJson(jsonRequest, JsonObject.class);
	    	ControladorUsuario cu = new ControladorUsuario();
			boolean resultado = false;
			if(json.has("nickname") && json.has("nombre") && json.has("apellido") && json.has("mail") && json.has("fechaNacimiento") && json.has("password") && json.has("src")) {
				if(json.has("instituto")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar fechaNacimiento = Calendar.getInstance();
					fechaNacimiento.setTime(sdf.parse(json.get("fechaNacimiento").getAsString()));
					resultado = cu.altaDocenteSrc(json.get("nickname").getAsString(), json.get("nombre").getAsString(), json.get("apellido").getAsString(), json.get("mail").getAsString(), fechaNacimiento, json.get("instituto").getAsString(), json.get("password").getAsString(), json.get("src").getAsString());
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar fechaNacimiento = Calendar.getInstance();
					fechaNacimiento.setTime(sdf.parse(json.get("fechaNacimiento").getAsString()));
					resultado = cu.altaEstudianteSrc(json.get("nickname").getAsString(), json.get("nombre").getAsString(), json.get("apellido").getAsString(), json.get("mail").getAsString(), fechaNacimiento, json.get("password").getAsString(), json.get("src").getAsString());
				}
				if (resultado) {
					jsonRespuesta.addProperty("OK", "Se registro correctamente al usuario");
				} else {
					jsonRespuesta.addProperty("ERROR", "El usuario o email ya existe");
				}
			} else {
				jsonRespuesta.addProperty("ERROR", "El formato del archivo JSON no es valido");
			}
			return jsonRespuesta.toString();
    	} catch (Exception e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			return jsonError.toString();
		}
    }
    
    @WebMethod
    public String usuarioDoPut(String nickSession, String jsonRequest) {
    	try {
	    	JsonObject jsonRespuesta = new JsonObject();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject json = gson.fromJson(jsonRequest, JsonObject.class);
			ControladorUsuario cu = new ControladorUsuario();
			if(json.has("nombre") && json.has("apellido") && json.has("mail") && json.has("fechaNacimiento") && json.has("src")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar fechaNacimiento = Calendar.getInstance();
				fechaNacimiento.setTime(sdf.parse(json.get("fechaNacimiento").getAsString()));
				cu.modificarDatosUsuarioSource(nickSession, json.get("nombre").getAsString(), json.get("apellido").getAsString(), fechaNacimiento, json.get("src").getAsString());
				jsonRespuesta.addProperty("OK", "Se modifico correctamente el usuario");
			} else {
				jsonRespuesta.addProperty("ERROR", "El formato del archivo JSON no es valido");
			}
			return jsonRespuesta.toString();
    	} catch (Exception e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			return jsonError.toString();
		}
    }
    
    @WebMethod
    public String programaDoGet(String jsonParams) {
    	try {
    		ControladorProgramaFormacion cpf = new ControladorProgramaFormacion();
    		Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject json = gson.fromJson(jsonParams, JsonObject.class);
			String jsonRetorno = "{}";
			String nombrePrograma = "";
			String nombreCurso = "";
			String nombreUsuario = "";
			if (json.has("nombre")) {
				nombrePrograma = json.get("nombre").getAsString();
			}
			if (json.has("curso")) {
				nombreCurso = json.get("curso").getAsString();
			}
			if (json.has("user")) {
				nombreUsuario = json.get("user").getAsString();
			}
			if (nombrePrograma != "") {
				DtProgramaFormacion auxDtPf = cpf.mostrarProgramaFormacion(nombrePrograma);
				jsonRetorno = gson.toJson(auxDtPf);
			} else if (nombreCurso != "") {
				JsonArray array = new JsonArray();
				List<DtProgramaFormacion> listaProgramas = cpf.obtenerProgramasDeCurso(nombreCurso);
				for (DtProgramaFormacion dtProgramaFormacion : listaProgramas) {
					array.add(gson.fromJson(gson.toJson(dtProgramaFormacion), JsonObject.class));
				}
				jsonRetorno = array.toString();
			} else if (nombreUsuario != "") {
				ControladorUsuario cu = new ControladorUsuario();
				DtUsuario auxUsuario = cu.informacionDeUsuario(nombreUsuario);
				if (auxUsuario instanceof DtEstudiante) {
					DtEstudiante auxEstudiante = (DtEstudiante) auxUsuario;
					JsonArray array = new JsonArray();
					List<DtProgramaFormacion> listaProgramas = cpf.obtenerDtProgramas(auxEstudiante.getProgramas());
					for (DtProgramaFormacion dtProgramaFormacion : listaProgramas) {
						array.add(gson.fromJson(gson.toJson(dtProgramaFormacion), JsonObject.class));
					}
					jsonRetorno = array.toString();
				} else {
					JsonObject jsonRespuesta = new JsonObject();
					jsonRespuesta.addProperty("ERROR", "El usuario ingresado no es un estudiante");
					jsonRetorno = jsonRespuesta.toString();
				}
			} else {
				JsonArray array = new JsonArray();
				Map<String, DtProgramaFormacion> listaPf = cpf.listarDtProgramasFormacion();
				for (Map.Entry<String, DtProgramaFormacion> dt : listaPf.entrySet()) {
					array.add(gson.fromJson(gson.toJson(dt.getValue()), JsonObject.class));
				}
				jsonRetorno = array.toString();
			}
			return jsonRetorno.toString();
    	} catch (Exception e) {
			JsonObject jsonRespuesta = new JsonObject();
			jsonRespuesta.addProperty("ERROR", e.getMessage());
			return jsonRespuesta.toString();
		}
    }
    
    @WebMethod
    public String programaDoPost(String jsonRequest) {
    	try {
			JsonObject jsonRespuesta = new JsonObject();
    		Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject json = gson.fromJson(jsonRequest, JsonObject.class);
			ControladorProgramaFormacion cpf = new ControladorProgramaFormacion();
			boolean resultado = false;
			if(json.has("nombre") && json.has("descripcion") && json.has("fechaInicio") && json.has("fechaFin") && json.has("src")) {
				//Se crean calendar con las fechas del JSON
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar fechaInicio = Calendar.getInstance();
				Calendar fechaFin = Calendar.getInstance();
				fechaInicio.setTime(sdf.parse(json.get("fechaInicio").getAsString()));
				fechaFin.setTime(sdf.parse(json.get("fechaFin").getAsString()));
				//Se crea el programa de formacion
				resultado = cpf.crearProgramaSource(json.get("nombre").getAsString(), json.get("descripcion").getAsString(), fechaInicio, fechaFin, json.get("src").getAsString());
				
				if (resultado) {
					jsonRespuesta.addProperty("OK", "Se creo correctamente el programa de formacion");
				} else {
					jsonRespuesta.addProperty("ERROR", "Se produjo un error al crear el programa de formacion");
				}
			} else {
				jsonRespuesta.addProperty("ERROR", "El formato del archivo JSON no es valido");
			}
			return jsonRespuesta.toString();
    	} catch (Exception e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			return jsonError.toString();
		}
    }
    
    @WebMethod
    public String programaDoPut(String jsonRequest) {
    	try {
			JsonObject jsonRespuesta = new JsonObject();
    		Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject json = gson.fromJson(jsonRequest, JsonObject.class);
			ControladorProgramaFormacion cpf = new ControladorProgramaFormacion();
			boolean resultado = true;
			if(json.has("programa") && json.has("cursos")) {
				//Obtengo el nombre del programa
				String nomPrograma = json.get("programa").getAsString();
				//Para cada curso en el json lo agrego a su respectivo programa
				JsonArray listaCursosJson = json.get("cursos").getAsJsonArray();
				ArrayList<String> listaCurso = new ArrayList<String>();
				for (JsonElement curso : listaCursosJson) {
					listaCurso.add(curso.getAsString());
				}
				resultado = cpf.agregarCursosAPrograma(nomPrograma, listaCurso);
				//Se devuelve msj informando si la operacion fue exitosa o no
				if (resultado) {
					jsonRespuesta.addProperty("OK", "Se agregaron correctamente los cursos al programa de formacion");
				} else {
					jsonRespuesta.addProperty("ERROR", "Se produjo un error al agregar los cursos al programa de formacion");
				}
			} else {
				jsonRespuesta.addProperty("ERROR", "El formato del archivo JSON no es valido");
			}
			return jsonRespuesta.toString();
    	} catch (Exception e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			return jsonError.toString();
		}
    }
    
    @WebMethod
    public String seguirDoPost(String nickSession, String jsonRequest) {
    	try {
			JsonObject jsonRespuesta = new JsonObject();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject json = gson.fromJson(jsonRequest, JsonObject.class);
			ControladorUsuario cu = new ControladorUsuario();
			if(json.has("accion") && json.has("nickDest")) {
				if (json.get("accion").getAsString().equals("follow")) {
					cu.seguirUsuario(nickSession, json.get("nickDest").getAsString());
					jsonRespuesta.addProperty("OK", "Ahora sigues al usuario " + json.get("nickDest").getAsString());
				} else if (json.get("accion").getAsString().equals("unfollow")) {
					cu.dejarDeSeguirUsuario(nickSession, json.get("nickDest").getAsString());
					jsonRespuesta.addProperty("OK", "Dejaste de seguir al usuario " + json.get("nickDest").getAsString());
				} else {
					jsonRespuesta.addProperty("ERROR", "El formato del campo 'accion' no es valido");
				}
			} else {
				jsonRespuesta.addProperty("ERROR", "El formato del JSON no es valido");
			}
			return jsonRespuesta.toString();
    	} catch (Exception e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			return jsonError.toString();
		}
    }
    
    @WebMethod
    public String validacionDoGet(String json) {
    	Factory factory = new Factory();
		InterfazControladorCurso interfazControladorCurso = factory.getInterfazControladorCurso();
		InterfazControladorUsuario interfazControladorUsuario = factory.getInterfazControladorUsuario();
		InterfazControladorProgramaFormacion interfazControladorProgramaFormacion = factory.getInterfazControladorProgramaFormacion();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject request = gson.fromJson(json, JsonObject.class);
		JsonObject response = new JsonObject();
		if (request.has("Nickname")) {
			if (interfazControladorUsuario.existeNickname(request.get("Nickname").getAsString())) {
				response.addProperty("OK", "true");
			} else {
				response.addProperty("OK", "false");
			}
		} else if (request.has("Mail")) {
			if (interfazControladorUsuario.existeMail(request.get("Mail").getAsString())) {
				response.addProperty("OK", "true");
			} else {
				response.addProperty("OK", "false");
			}
		} else if (request.has("Curso")) {
			if (interfazControladorCurso.existeCurso(request.get("Curso").getAsString())) {
				response.addProperty("OK", "true");
			} else {
				response.addProperty("OK", "false");
			}
		} else if (request.has("Edicion")) {
			if (interfazControladorCurso.existeEdicion(request.get("Edicion").getAsString())) {
				response.addProperty("OK", "true");
			} else {
				response.addProperty("OK", "false");
			}
		} else if (request.has("Programa")) {
			if (interfazControladorProgramaFormacion.existePrograma(request.get("Programa").getAsString())) {
				response.addProperty("OK", "true");
			} else {
				response.addProperty("OK", "false");
			}
		}
		return gson.toJson(response);
    }
    
    @WebMethod
    public String institutosCategoriasGet(String json) {
    	Factory factory = new Factory();
    	InterfazControladorUsuario interfazControladorUsuario = factory.getInterfazControladorUsuario();
    	InterfazControladorCurso interfazControladorCurso = factory.getInterfazControladorCurso();
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject request = gson.fromJson(json, JsonObject.class);
    	
    	if (request.has("Institutos")) {
			List<String> institutos = interfazControladorUsuario.listarInstitutos();
			JsonArray institutosJson = new JsonArray();
			for (int i = 0; i < institutos.size(); i++) {
				JsonObject instituto = new JsonObject();
				instituto.addProperty("nombre", institutos.get(i));
				institutosJson.add(gson.toJsonTree(instituto));
			}
			return gson.toJson(institutosJson);
		} else if (request.has("Categorias")) {
			List<String> categorias = interfazControladorCurso.getListaCategorias();
			JsonArray categoriasJson = new JsonArray();
			for (int i = 0; i < categorias.size(); i++) {
				JsonObject categoria = new JsonObject();
				categoria.addProperty("nombre", categorias.get(i));
				categoriasJson.add(gson.toJsonTree(categoria));
			}
			return gson.toJson(categoriasJson);
		} else {
			System.out.println("Invalid JSON");
			return null;
		}
    }
    
    @WebMethod
    public String comentarioDoGet(String jsonParams) {
    	try {
	    	ControladorCurso cCurso = new ControladorCurso();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject json = gson.fromJson(jsonParams, JsonObject.class);
			if (json.has("edicion") && json.has("curso")) {
				String jsonRespuesta = "";
				String edicion = json.get("edicion").getAsString();
				String curso = json.get("curso").getAsString();
				JsonArray array = new JsonArray();
				List<DtComentario> listaComentarios = cCurso.getComentariosEdicion(curso, edicion);
				for (DtComentario dtComentario : listaComentarios) {
					array.add(gson.fromJson(gson.toJson(dtComentario), JsonObject.class));
				}
				jsonRespuesta = array.toString();
				return jsonRespuesta;
			} else {
				JsonObject jsonRespuesta = new JsonObject();
				jsonRespuesta.addProperty("ERROR", "El formato del JSON no es valido");
				return jsonRespuesta.toString();
			}
    	}catch (Exception e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			return jsonError.toString();
		}
    }
    
    @WebMethod
    public String comentarioDoPost(String jsonRequest, String nickname) {
    	try {
			JsonObject jsonRespuesta = new JsonObject();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject json = gson.fromJson(jsonRequest, JsonObject.class);
			ControladorCurso cCurso = new ControladorCurso();
			if(nickname != "" && json.has("curso") && json.has("edicion") && json.has("nombre") && json.has("apellido") && json.has("comentario") && json.has("fechaComentario")) {
				String curso = json.get("curso").getAsString();
				String edicion = json.get("edicion").getAsString();
				String nombre = json.get("nombre").getAsString();
				String apellido = json.get("apellido").getAsString();
				String comentario = json.get("comentario").getAsString();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar fecha = Calendar.getInstance();
				fecha.setTime(sdf.parse(json.get("fechaComentario").getAsString()));
				cCurso.agregarComentario(curso, edicion, nickname, nombre, apellido, comentario, fecha);
				jsonRespuesta.addProperty("OK", "Comentario agregado");
			} else {
				jsonRespuesta.addProperty("ERROR", "El formato del JSON no es valido");
			}
			return jsonRespuesta.toString();
    	} catch (Exception e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			return jsonError.toString();
		}
    }
    
    @WebMethod
    public String comentarioDoPut(String jsonRequest, String nickname) {
    	try {
			JsonObject jsonRespuesta = new JsonObject();
    		Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject json = gson.fromJson(jsonRequest, JsonObject.class);
			ControladorCurso cCurso = new ControladorCurso();
			if(nickname != "" && json.has("id") && json.has("curso") && json.has("edicion") && json.has("nombre") && json.has("apellido") && json.has("comentario") && json.has("fechaComentario")) {
				String curso = json.get("curso").getAsString();
				String edicion = json.get("edicion").getAsString();
				String nombre = json.get("nombre").getAsString();
				String apellido = json.get("apellido").getAsString();
				String comentario = json.get("comentario").getAsString();
				String id = json.get("id").getAsString();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar fecha = Calendar.getInstance();
				fecha.setTime(sdf.parse(json.get("fechaComentario").getAsString()));
				cCurso.responderComentario(curso, edicion, id, nickname, nombre, apellido, comentario, fecha);
				jsonRespuesta.addProperty("OK", "Comentario agregado");
			} else {
				jsonRespuesta.addProperty("ERROR", "El formato del JSON no es valido");
			}
			return jsonRespuesta.toString();
    	} catch (Exception e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			return jsonError.toString();
		}
    }
    
    
    
    public String aproboPrograma(String params,String nick) {
    	Gson gson=new Gson();
    	JsonObject pObject = gson.fromJson(params, JsonObject.class);
    	Factory fab = new Factory();
    	InterfazControladorUsuario icu = fab.getInterfazControladorUsuario();
    	try {
			DtCertificado dtCertificado = icu.getCertificado(nick, pObject.get("Programa").getAsString());
			if(dtCertificado!=null) {
				String res = gson.toJson(dtCertificado);
				JsonObject jObject =gson.fromJson(res, JsonObject.class);
				jObject.addProperty("OK", "certificado generado");
				res = gson.toJson(jObject);
				return res;
			}else {
				JsonObject jsonError = new JsonObject();
				jsonError.addProperty("ERROR", "Aun no ha aprobado el programa");
				return jsonError.toString();
			}
		
		} catch (ProgramaNoExisteException | CursoNoExisteException | EdicionNoExisteException
				| UsuarioNoExisteException e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			return jsonError.toString();
		}
    	
    }

}