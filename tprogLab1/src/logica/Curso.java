package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import excepciones.CategoriaNoExisteException;
import excepciones.CursoNoExisteException;
import excepciones.EdicionNoExisteException;
import excepciones.EstudianteYaInscripto;
import excepciones.UsuarioNoExisteException;

public class Curso {
	private String nombre;
	private String descripcion;
	private String duracion;
	private int cantidadHoras;
	private int creditos;
	private Calendar fechaDeRegistro;
	private String url;
	private Map<String, Edicion> mapEdicion;
	private Map<String, Curso> mapPrevias;
	private Map<String, Categoria> mapCategorias;
	private String source;
	private int [] rating;
	private Map<String, Integer> valoraciones;
	private int cantidadVisitas;
	
	public Curso(String nom, String des, String duracion, int cantHoras, int creditos, String url, Map<String, Curso> previas, String source) {
		// TODO Auto-generated constructor stub
		this.fechaDeRegistro= Calendar.getInstance();
		this.mapEdicion = new HashMap<String, Edicion>();
		this.mapCategorias = new HashMap<String, Categoria>();
		this.mapPrevias = previas;
		this.nombre = nom;
		this.descripcion = des;
		this.duracion = duracion;
		this.cantidadHoras= cantHoras;
		this.url = url;
		this.creditos = creditos;
		this.source = source;
		this.rating = new int[5];
		for (int i = 0; i < rating.length; i++) {
			rating[i]=0;
		}
		this.valoraciones= new HashMap<String, Integer>();
		this.cantidadVisitas=0;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	
	public DtCurso getDtCurso() {
		ArrayList<String> arrayEdicion = new ArrayList<String>();
		ArrayList<String> arrayPrevias = new ArrayList<String>();
		List<String> arrayFormacion = ManejadorProgramaFormacion.getInstance().getProgramasConCurso(this.nombre);
		ArrayList<String> arrayCategorias = new ArrayList<String>();
		
		mapEdicion.forEach((k, v)-> arrayEdicion.add(v.getNombre()));
		mapPrevias.forEach((k, v)-> arrayPrevias.add(v.getNombre()));
		mapCategorias.forEach((k, v)-> arrayCategorias.add(v.getNombre()));
		
		ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
		String instituto = mInstituto.getInstitutoCurso(this.nombre);
		
		return new DtCurso(this.nombre, this.descripcion, this.duracion, this.cantidadHoras, this.fechaDeRegistro, this.url, this.creditos, arrayPrevias, arrayEdicion, arrayFormacion, arrayCategorias, this.source, instituto,this.rating, this.cantidadVisitas);
		
	}
	
	public List<String> getListaEdiciones() throws EdicionNoExisteException {
		ArrayList<String> ediciones = new ArrayList<String>();
		if (!this.mapEdicion.isEmpty()) {
			for (Map.Entry<String, Edicion> entry : this.mapEdicion.entrySet()) {
				ediciones.add(entry.getValue().getNombre());
			}
		} else {
			throw new EdicionNoExisteException("No hay ediciones vinculadas a este curso");
		}
		return ediciones;
	}
	public boolean tieneEdicion(String nombre) {
		return this.mapEdicion.containsKey(nombre);
	}
	
	public DtEdicion darDatosEdicion(String edicion) throws EdicionNoExisteException {
		Edicion edi = mapEdicion.get(edicion);
		if (edi != null) {
			DtEdicion dte = edi.getDtEdicion();
			return dte;
		} else {
			throw new EdicionNoExisteException("La edición no existe");
		}
	}
	public Edicion agregarEdicion(String nombre, Calendar fechaIni, Calendar fechaFin, int cupo, String source) {
		Edicion auxEdicion;
		boolean esVigente;
		if (!this.mapEdicion.isEmpty()) {
			Iterator<Map.Entry<String, Edicion>> iter = mapEdicion.entrySet().iterator();
			esVigente = true;
			while (iter.hasNext() && esVigente) {
				Map.Entry<String, Edicion> aux = iter.next();
				esVigente = fechaIni.after(aux.getValue().getFechaInicio()) && fechaFin.after(Calendar.getInstance());
			}
			if (esVigente) {
				for (Map.Entry<String, Edicion> entry : this.mapEdicion.entrySet()) {
					entry.getValue().setVigencia(false);
				}
			}
		} else {
			esVigente = fechaFin.after(Calendar.getInstance());
		}
		auxEdicion = new Edicion(nombre, fechaIni, fechaFin, cupo, esVigente, source);
		this.mapEdicion.put(nombre, auxEdicion);
		return auxEdicion;
	}
	
	public DtEdicion mostrarEdicionVigente() throws EdicionNoExisteException {
		if (!this.mapEdicion.isEmpty()) {
			Iterator<Map.Entry<String, Edicion>> iter = mapEdicion.entrySet().iterator();
			boolean verificador = false;
			while (iter.hasNext() && !verificador) {
				Map.Entry<String, Edicion> aux = iter.next();
				verificador = aux.getValue().getVigencia();
				if (verificador) {
					DtEdicion dte = aux.getValue().getDtEdicion();
					return dte;
				}
			}
			throw new EdicionNoExisteException("No hay una edición vigente de este curso");
		} else {
			throw new EdicionNoExisteException("No hay ediciones vinculadas a este curso");
		}
	}
	
	public void inscribirEstudiante(String nickname, String edicion, Calendar fecha, String video) throws EdicionNoExisteException, UsuarioNoExisteException, EstudianteYaInscripto {
		Edicion edi = mapEdicion.get(edicion);
		if (edi != null) {
			ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
			if (edi.getCupo()==0)
				mUsuario.inscribirEstudianteAEdicion(nickname, edi, fecha, video, EnumEstado.ACEPTADA);
			else
				mUsuario.inscribirEstudianteAEdicion(nickname, edi, fecha, video, EnumEstado.INSCRIPTO);
		} else {
			throw new EdicionNoExisteException("La edición no existe");
		}
	}

	public void setFechaRegistro(Calendar cal3) {
		// TODO Auto-generated method stub
		fechaDeRegistro=cal3;
	}
	
	public void agregarCategorias(List<String> categorias) throws CategoriaNoExisteException {
		ManejadorCategoria mCategoria = ManejadorCategoria.getInstance();
		for (int i = 0; i < categorias.size(); i++) {
			String nombre = categorias.get(i);
			Categoria auxCategoria = mCategoria.getCategoria(nombre);
			this.mapCategorias.put(nombre, auxCategoria);
		}
	}
	
	public Set<String> getCategorias() {
		Set<String> categoriaSet = new HashSet<String>();
		
		mapCategorias.forEach((k, v)->categoriaSet.add(v.getNombre()));
		return categoriaSet;
	}
	
	public Map<String, DtEdicion> getDtEdicion(){
		Map<String, DtEdicion> resMap = new HashMap<String, DtEdicion>();
		mapEdicion.forEach((k, v)->resMap.put(k, v.getDtEdicion()));
		return resMap;
	}
	
	public Edicion getEdicion(String nombre) {
		return this.mapEdicion.get(nombre);
	}
	
	public void cerrarEdicion(String edicion) throws EdicionNoExisteException {
		Edicion edicionACerrar = this.mapEdicion.get(edicion);
		if (edicionACerrar != null) {
			edicionACerrar.cerrarEdicion();
		} else {
			throw new EdicionNoExisteException("No existe la edicion");
		}
	}
	
	public void valorarCurso(int valoracion, Map<String, DtEdicion> ediciones,String nick) throws CursoNoExisteException {
		
		boolean candidato=false;
		for (Map.Entry<String, Edicion> ed : this.mapEdicion.entrySet() ) {
			if(ediciones.containsKey(ed.getKey())) {
				candidato = true;
			}
		}
		if(candidato==true && !this.valoraciones.containsKey(nick)) {
			this.rating[valoracion-1]++;
			this.valoraciones.put(nick, valoracion);
		} else if(candidato==true && this.valoraciones.containsKey(nick)) {
			int  i = valoraciones.get(nick);
			this.rating[i-1]--;
			this.rating[valoracion-1]++;
			this.valoraciones.put(nick, valoracion);
		} else {
			throw new CursoNoExisteException("Debe haber cursado para valorar.");
		}
	}
	
	public int visitado() {
		this.cantidadVisitas++;
		return this.cantidadVisitas;
	}
	
	public Map<String, Integer> getValoracionesCurso() {
		return this.valoraciones;
	}
}
