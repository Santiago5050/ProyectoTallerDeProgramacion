package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.CursoNoExisteException;
import excepciones.CursoYaExisteException;
import excepciones.DocenteNoPerteneceMismoInstitutoException;
import excepciones.EdicionNoExisteException;
import excepciones.EstudianteComoDocenteException;
import excepciones.EstudianteYaInscripto;
import excepciones.InstitutoNoBrindaCursoException;
import excepciones.InstitutoNoExisteException;
import excepciones.InstitutoYaExisteException;
import excepciones.ProgramaNoExisteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;

class TestControladorUsuario {
	private ControladorUsuario cUsuario;

	@BeforeEach
	void setUp() throws Exception {
		cUsuario = new ControladorUsuario();
		
		// Reinicio los manejadores.
		Field field;
		field = ManejadorUsuario.class.getDeclaredField("instance");
		field.setAccessible(true);
		field.set(ManejadorUsuario.class, null);
				
		Field field2;
		field2 = ManejadorCurso.class.getDeclaredField("instance");
		field2.setAccessible(true);
		field2.set(ManejadorCurso.class, null);
				
		Field field3;
		field3 = ManejadorInstituto.class.getDeclaredField("instance");
		field3.setAccessible(true);
		field3.set(ManejadorInstituto.class, null);
				
		Field field4;
		field4 = ManejadorProgramaFormacion.class.getDeclaredField("instance");
		field4.setAccessible(true);
		field4.set(ManejadorProgramaFormacion.class, null);
	}

	@AfterEach
	void tearDown() throws Exception {
		cUsuario = null;
		
		// Reinicio los manejadores.
		Field field;
		field = ManejadorUsuario.class.getDeclaredField("instance");
		field.setAccessible(true);
		field.set(ManejadorUsuario.class, null);
		
		Field field2;
		field2 = ManejadorCurso.class.getDeclaredField("instance");
		field2.setAccessible(true);
		field2.set(ManejadorCurso.class, null);
		
		Field field3;
		field3 = ManejadorInstituto.class.getDeclaredField("instance");
		field3.setAccessible(true);
		field3.set(ManejadorInstituto.class, null);
		
		Field field4;
		field4 = ManejadorProgramaFormacion.class.getDeclaredField("instance");
		field4.setAccessible(true);
		field4.set(ManejadorProgramaFormacion.class, null);
	}
	
	
	@Test
	void testAltas() {
		ControladorCurso cCurso = new ControladorCurso();
		try {
			cCurso.ingresarInstituto("Instituto1");
		} catch (InstitutoYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cCurso = null;
		
		// Usuario 1
		Calendar fechaNacimiento1 = Calendar.getInstance();
		fechaNacimiento1.set(2000, 6, 1);
		boolean creo = cUsuario.altaEstudiante("nick1", "nombre1", "apellido1", "mail1@mail.com", fechaNacimiento1, "os92veo");
		assertEquals(true, creo);
		
		// Usuario 2
		Calendar fechaNacimiento2 = Calendar.getInstance();
		fechaNacimiento2.set(2000, 6, 2);
		creo = cUsuario.altaEstudiante("nick1", "nombre2", "apellido2", "mail2@mail.com", fechaNacimiento2, "aas90qb");
		assertEquals(false, creo);
		
		// Usuario 3
		Calendar fechaNacimiento3 = Calendar.getInstance();
		fechaNacimiento3.set(2000, 6, 3);
		creo = cUsuario.altaDocente("nick3", "nombre3", "apellido3", "mail1@mail.com", fechaNacimiento3, "Instituto1", "bxpo7");
		assertEquals(false, creo);
		
		// Usuario 4
		Calendar fechaNacimiento4 = Calendar.getInstance();
		fechaNacimiento4.set(2000, 6, 4);
		creo = cUsuario.altaEstudiante("nick1", "nombre4", "apellido4", "mail1@mail.com", fechaNacimiento4, "oas8g");
		assertEquals(false, creo);
		
		// Usuario 5
		Calendar fechaNacimiento5 = Calendar.getInstance();
		fechaNacimiento5.set(2000, 6, 5);
		creo = cUsuario.altaDocente("nick5", "nombre5", "apellido5", "mail5@mail.com", fechaNacimiento5, "Instituto1", "bao28");
		assertEquals(true, creo);
	}
	
	@Test
	void testInformacionDeUsuario1() {
		DtUsuario info;
		String error = "";
		try {
			info = cUsuario.informacionDeUsuario("nick0");
		} catch (UsuarioNoExisteException e) {
			error = e.getMessage();
		}
		assertEquals("El usuario no existe", error);
	}
	
	@Test
	void testInformacionDeUsuario2() {
		// Cargo usuario.
		Calendar fechaNacimiento1 = Calendar.getInstance();
		fechaNacimiento1.set(2000, 6, 1);
		boolean creo = cUsuario.altaEstudiante("nick1", "nombre1", "apellido1", "mail1@mail.com", fechaNacimiento1, "sdmqs8");
		assertEquals(true, creo);
		
		// Testeo.
		DtUsuario info;
		String error2 = "";
		try {
			info = cUsuario.informacionDeUsuario("nick1");
			assertEquals("nick1", info.getNickname());
			assertEquals("nombre1", info.getNombre());
			assertEquals("apellido1", info.getApellido());
			assertEquals("mail1@mail.com", info.getMail());
			Calendar resultado = Calendar.getInstance();
			assertEquals(1, info.getFechaNacimiento().get(Calendar.DAY_OF_MONTH));
			assertEquals(6, info.getFechaNacimiento().get(Calendar.MONTH));
			assertEquals(2000, info.getFechaNacimiento().get(Calendar.YEAR));
		} catch (Exception e2) {
			error2 = e2.getMessage();
		}
		assertEquals("", error2);
	}
	
	@Test
	public void testListarUsuarios1() {
		// Cargo usuarios.
		Calendar fechaNacimiento1 = Calendar.getInstance();
		fechaNacimiento1.set(2000, 6, 1);
		boolean creo = cUsuario.altaEstudiante("nick1", "nombre1", "apellido1", "mail1@mail.com", fechaNacimiento1, "nioasn89b");
		assertEquals(true, creo);
						
		Calendar fechaNacimiento5 = Calendar.getInstance();
		fechaNacimiento5.set(2000, 6, 5);
		creo = cUsuario.altaDocente("nick5", "nombre5", "apellido5", "mail5@mail.com", fechaNacimiento5, "Instituto1", "buasb2p");
		assertEquals(true, creo);
		
		// Testeo
		String error = "";
		List<String> resultado = new ArrayList<String>();
		try {
			resultado = cUsuario.listarUsuarios();
		} catch (Exception e) {
			error = e.getMessage();
		}
		List<String> objetivo = new ArrayList<String>();
		objetivo.add("nick1");
		objetivo.add("nick5");
		assertEquals(objetivo, resultado);
	}
	
	@Test
	void testListarUsuarios2() {
		String error = "";
		List<String> resultado;
		try {
			resultado = cUsuario.listarUsuarios();
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertEquals("No hay usuarios en el sistema", error);
	}
	
	@Test
	void testModificarDatosDeUsuario() {
		String error1 = "";
		// Testeo sin usuario.
		try {
			Calendar fechaModificada = Calendar.getInstance();
			fechaModificada.set(1111, 1, 1);
			cUsuario.modificarDatosUsuario("nick1", "modificadoNombre", "modificadoApellido", fechaModificada);
		} catch (UsuarioNoExisteException noExiste) {
			// TODO: handle exception
			error1 = noExiste.getMessage();
		}
		assertEquals("El usuario no existe", error1);
		
		// Cargo usuarios.
		Calendar fechaNacimiento1 = Calendar.getInstance();
		fechaNacimiento1.set(2000, 6, 1);
		boolean creo = cUsuario.altaEstudiante("nick1", "nombre1", "apellido1", "mail1@mail.com", fechaNacimiento1, "ac09asb89");
		assertEquals(true, creo);
		
		String error2 = "";
		// Testeo con usuario.
		try {
			Calendar fechaModificada = Calendar.getInstance();
			fechaModificada.set(1111, 1, 1);
			cUsuario.modificarDatosUsuario("nick1", "modificadoNombre", "modificadoApellido", fechaModificada);
			DtUsuario info = cUsuario.informacionDeUsuario("nick1");
			assertEquals("modificadoNombre", info.getNombre());
			assertEquals("modificadoApellido", info.getApellido());
			assertEquals(1, info.getFechaNacimiento().get(Calendar.DAY_OF_MONTH));
			assertEquals(1, info.getFechaNacimiento().get(Calendar.MONTH));
			assertEquals(1111, info.getFechaNacimiento().get(Calendar.YEAR));
			assertEquals("", info.getSource());
			cUsuario.modificarDatosUsuarioSource("nick1", "modificadoNombre", "modificadoApellido", fechaModificada, "source1");
			info = cUsuario.informacionDeUsuario("nick1");
			assertEquals("source1", info.getSource());
		} catch (UsuarioNoExisteException siExiste) {
			// TODO: handle exception
			error2 = siExiste.getMessage();
		}
		assertEquals("", error2);
	}
	
	
	@Test
	void testListarInstitutos() {
		List<String> institutos = new ArrayList<String>();
		ControladorCurso cCurso = new ControladorCurso();
		
		institutos = cUsuario.listarInstitutos();
		assertEquals(true, institutos.isEmpty());
		
		try {
			cCurso.ingresarInstituto("institutoTest");
		} catch (InstitutoYaExisteException e) {
			
		}
		institutos = new ArrayList<String>();
		institutos = cUsuario.listarInstitutos();
		assertEquals(false, institutos.isEmpty());
		assertEquals("institutoTest", institutos.get(0));
	}
	
	@Test
	void testHayDocentesDeOtroInstituto() {
		boolean creo;
		ControladorCurso cCurso = new ControladorCurso();
		Calendar fechaNacimiento3;
		Calendar fechaNacimiento4;
		Calendar fechaNacimiento5;
		List<String> docentes;
		boolean hayDocenteDeOtroInstituto;
		String error = "";
		
		// Creo institutos.
		try {
			cCurso.ingresarInstituto("Instituto1");
			cCurso.ingresarInstituto("Instituto2");
		} catch (InstitutoYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cCurso = null;
		
		// Agrego usuarios.
		
		// Usuario 3
		fechaNacimiento3 = Calendar.getInstance();
		fechaNacimiento3.set(2000, 6, 3);
		creo = cUsuario.altaDocente("nick3", "nombre3", "apellido3", "mail3@mail.com", fechaNacimiento3, "Instituto1", "pasdb7");
		assertEquals(true, creo);
		
		// Usuario 4
		fechaNacimiento4 = Calendar.getInstance();
		fechaNacimiento4.set(2000, 6, 4);
		creo = cUsuario.altaDocente("nick4", "nombre4", "apellido4", "mail4@mail.com", fechaNacimiento4, "Instituto2", "spcnas8");
		assertEquals(true, creo);
		
		// Usuario 5
		fechaNacimiento5 = Calendar.getInstance();
		fechaNacimiento5.set(2000, 6, 5);
		creo = cUsuario.altaEstudiante("nick5", "nombre5", "apellido5", "mail5@mail.com", fechaNacimiento5, "wqin0q");
		assertEquals(true, creo);
		
		// Testeo 1 (false)
		docentes = new ArrayList<String>();
		docentes.add("nick3");
		try {
			hayDocenteDeOtroInstituto = cUsuario.hayDocenteDeOtroInstituto(docentes, "Instituto1");
			assertEquals(false, hayDocenteDeOtroInstituto);
		} catch (EstudianteComoDocenteException | UsuarioNoExisteException e) {
			error = e.getMessage();
		}
		assertEquals("", error);
		
		// Testeo 2 (true)
		docentes = new ArrayList<String>();
		docentes.add("nick3");
		docentes.add("nick4");
		try {
			hayDocenteDeOtroInstituto = cUsuario.hayDocenteDeOtroInstituto(docentes, "Instituto1");
			assertEquals(true, hayDocenteDeOtroInstituto);
		} catch (EstudianteComoDocenteException | UsuarioNoExisteException e) {
			error = e.getMessage();
		}
		assertEquals("", error);
		
		// Testeo 3 (no existe)
		docentes = new ArrayList<String>();
		docentes.add("nick1");
		try {
			hayDocenteDeOtroInstituto = cUsuario.hayDocenteDeOtroInstituto(docentes, "Instituto1");
		} catch (EstudianteComoDocenteException | UsuarioNoExisteException e) {
			error = e.getMessage();
		}
		assertEquals("El docente ingresado no existe", error);
		
		// Testeo 4 (estudiante como docente).
		docentes = new ArrayList<String>();
		docentes.add("nick5");
		try {
			hayDocenteDeOtroInstituto = cUsuario.hayDocenteDeOtroInstituto(docentes, "Instituto1");
		} catch (EstudianteComoDocenteException | UsuarioNoExisteException e) {
			error = e.getMessage();
		}
		assertEquals("Se ha seleccionado a un usuario estudiante en lugar de un docente", error);
		
	}
	
	@Test
	void testConsultaEdicion() {
		Calendar fechaNacimiento1;
		Calendar fechaInicio;
		Calendar fechaFin;
		Calendar fechaInscripcionCurso;
		ControladorCurso cCurso = new ControladorCurso();
		List<String> previas = new ArrayList<String>();
		List<String> docentes = new ArrayList<String>();
		String error = "";
		DtEdicion edicion = null;
		
		// Creo institutos.
		try {
			cCurso.ingresarInstituto("Instituto1");
		} catch (InstitutoYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Agrego estudiante
		fechaNacimiento1 = Calendar.getInstance();
		fechaNacimiento1.set(2000, 6, 1);
		boolean creo = cUsuario.altaEstudiante("nick1", "nombre1", "apellido1", "mail1@mail.com", fechaNacimiento1, "siuweq872");
		assertEquals(true, creo);
		
		// Agrego docente
		Calendar fechaNacimiento5 = Calendar.getInstance();
		fechaNacimiento5.set(2000, 6, 5);
		creo = cUsuario.altaDocente("nick5", "nombre5", "apellido5", "mail5@mail.com", fechaNacimiento5, "Instituto1", "dkcuwv692");
		assertEquals(true, creo);
		
		// Agrego curso.
		try {
			cCurso.altaCurso("Instituto1", "Curso1", "Curso de prueba 1", "1 semana", 1, 1, "www.curso1.com", previas);
		} catch (CursoYaExisteException | InstitutoNoExisteException | CursoNoExisteException e) {
			error = e.getMessage();
		}
		assertEquals("", error);
		
		// Agrego edición curso.
		docentes.add("nick5");
		fechaInicio = Calendar.getInstance();
		fechaInicio.set(2000, Calendar.JANUARY, 1);
		fechaFin = Calendar.getInstance();
		fechaFin.set(2000, Calendar.DECEMBER, 12);
		try {
			cCurso.altaEdicionCurso("Curso1 edición1", fechaInicio, fechaFin, 1, "Curso1", docentes, "Instituto1");
		} catch (DocenteNoPerteneceMismoInstitutoException | EstudianteComoDocenteException | CursoNoExisteException
				| UsuarioNoExisteException | InstitutoNoExisteException | InstitutoNoBrindaCursoException e) {
			error = e.getMessage();
		}
		assertEquals("", error);
		
		// Inscribo el estudiante al curso.
		fechaInscripcionCurso = Calendar.getInstance();
		try {
			cCurso.inscribirEstudianteACurso("nick1", "Curso1", "Curso1 edición1", fechaInscripcionCurso, "");
		} catch (CursoNoExisteException | EdicionNoExisteException | UsuarioNoExisteException | EstudianteYaInscripto e) {
			error = e.getMessage();
		}
		assertEquals("", error);
		
		// Testeo.
		try {
			edicion = cUsuario.consultaEdicion("nick1", "Curso1 edición1");
		} catch (UsuarioNoExisteException | EdicionNoExisteException e) {
			error = e.getMessage();
		}
		assertEquals("", error);
		assertEquals("Curso1 edición1", edicion.getNombre());
		assertEquals(1, edicion.getCupo());
		assertEquals(2000, edicion.getFechaFin().get(Calendar.YEAR));
		assertEquals(Calendar.DECEMBER, edicion.getFechaFin().get(Calendar.MONTH));
		assertEquals(12, edicion.getFechaFin().get(Calendar.DAY_OF_MONTH));
		assertEquals(2000, edicion.getFechaInicio().get(Calendar.YEAR));
		assertEquals(Calendar.JANUARY, edicion.getFechaInicio().get(Calendar.MONTH));
		assertEquals(1, edicion.getFechaInicio().get(Calendar.DAY_OF_MONTH));
		assertEquals(EnumEstado.INSCRIPTO, ((DtEdicionEstudiante) edicion).getEstado());
	}
	
	@Test
	void testSeguirUsuario() {
		assertAll(()-> {
			// Usuario 1
			Calendar fechaNacimiento1 = Calendar.getInstance();
			fechaNacimiento1.set(2000, 6, 1);
			boolean creo = cUsuario.altaEstudiante("nick1", "nombre1", "apellido1", "mail1@mail.com", fechaNacimiento1, "os91veo");
			assertEquals(true, creo);
			
			// Usuario 2
			Calendar fechaNacimiento2 = Calendar.getInstance();
			fechaNacimiento2.set(2000, 6, 2);
			creo = cUsuario.altaEstudiante("nick2", "nombre2", "apellido2", "mail2@mail.com", fechaNacimiento2, "os92veo");
			assertEquals(true, creo);
			
			//sin seguir a nadie
			DtUsuario info;
			try {
				info = cUsuario.informacionDeUsuario("nick1");
				assertEquals(true, info.getSigue().isEmpty());
			} catch (UsuarioNoExisteException e) {
				e.printStackTrace();
			}
			
			//nick1 sigue a nick2
			List<String> sigue, resultado = new ArrayList<String>();
			try {
				cUsuario.seguirUsuario("nick1", "nick2");
				info = cUsuario.informacionDeUsuario("nick1");
				sigue = info.getSigue();
				resultado.add("nick2");
				assertEquals(resultado, sigue);
			} catch (UsuarioNoExisteException e) {
				e.printStackTrace();
			} catch (UsuarioYaExisteException e2) {
				e2.printStackTrace();
			}
			
			// nick1 deja de seguir a nick2
			try {
				cUsuario.dejarDeSeguirUsuario("nick1", "nick2");
				info = cUsuario.informacionDeUsuario("nick1");
				assertTrue(info.getSigue().isEmpty());
			} catch (UsuarioNoExisteException e) {
				e.printStackTrace();
			}
			
			assertThrows(UsuarioNoExisteException.class, ()->{cUsuario.dejarDeSeguirUsuario("nick1", "nick2");});
			assertThrows(UsuarioNoExisteException.class, ()->{cUsuario.dejarDeSeguirUsuario("nick3", "nick4");});
		});
	}
	
	@Test
	void testAltaUserSrc() {
		//Estudiante de Prueba
		Calendar fechaNac = Calendar.getInstance();
		boolean creado = cUsuario.altaEstudianteSrc("estoEsUnNick", "estoEsUnNombre", "estoEsUnApellido", "estoEsUnMail", fechaNac, "estoEsUnaPass", "/estoEsUnSrc.jpg");
		assertTrue(creado);
		
		//Agrego intituto para asignar al docente
		ControladorCurso cCurso = new ControladorCurso();
		try {
			cCurso.ingresarInstituto("InstitutoAux");
		} catch (InstitutoYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Docente de Prueba
		Calendar fechaNac2 = Calendar.getInstance();
		boolean creado2 = cUsuario.altaDocenteSrc("estoEsUnNick2", "estoEsUnNombre", "estoEsUnApellido", "estoEsUnMail2", fechaNac2, "InstitutoAux", "estoEsUnaPass", "/estoEsUnSrc.jpg");
		assertTrue(creado2);		
	}
	
	@Test
	void testListarDtUsuarios() {
		//Estudiante de Prueba
		Calendar fechaNac = Calendar.getInstance();
		boolean creado = cUsuario.altaEstudianteSrc("estoEsUnNick", "estoEsUnNombre", "estoEsUnApellido", "estoEsUnMail", fechaNac, "estoEsUnaPass", "/estoEsUnSrc.jpg");
		assertTrue(creado);
		
		//Agrego intituto para asignar al docente
		ControladorCurso cCurso = new ControladorCurso();
		try {
			cCurso.ingresarInstituto("InstitutoAux");
		} catch (InstitutoYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Docente de Prueba
		Calendar fechaNac2 = Calendar.getInstance();
		boolean creado2 = cUsuario.altaDocenteSrc("estoEsUnNick2", "estoEsUnNombre", "estoEsUnApellido", "estoEsUnMail2", fechaNac2, "InstitutoAux", "estoEsUnaPass", "/estoEsUnSrc.jpg");
		assertTrue(creado2);
		
		//Prueba operacion listarDtUsuarios
		try {
			List<DtUsuario> auxList = cUsuario.listarDtUsuarios();
			assertEquals("estoEsUnNick", auxList.get(0).getNickname());
			assertEquals("estoEsUnNick2", auxList.get(1).getNickname());
		} catch (UsuarioNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Test
	void testInscribirEstudiantePrograma() {
		//Creo un programa
		ControladorProgramaFormacion cpf = new ControladorProgramaFormacion();
		Calendar fechaInicio = Calendar.getInstance(), fechaFin = Calendar.getInstance();
		fechaInicio.set(2010, 6, 1);
		fechaFin.set(2010, 6, 30);
		cpf.crearPrograma("programa1", "esto es un programa", fechaInicio, fechaFin);
		
		//Estudiante de Prueba
		Calendar fechaNac = Calendar.getInstance();
		boolean creado = cUsuario.altaEstudianteSrc("nick1", "estoEsUnNombre", "estoEsUnApellido", "estoEsUnMail", fechaNac, "estoEsUnaPass", "/estoEsUnSrc.jpg");
		
		//Inscribo al estudiante al programa
		Calendar fechaIns = Calendar.getInstance();
		
		//pruebas
		try {
			cUsuario.inscribirEstudiantePrograma("nick1", "programa1", fechaIns);
			
			DtProgramaFormacionEstudiante aux = cUsuario.consultaProgramaEstudiante("nick1", "programa1");
			assertNotNull(aux);
		} catch (ProgramaNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsuarioNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EstudianteComoDocenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EstudianteYaInscripto e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testValidar() {
		assertAll(()->{
			Calendar fechaNacimiento1 = Calendar.getInstance();
			boolean creo = cUsuario.altaEstudiante("nick1", "nombre1", "apellido1", "mail1@mail.com", fechaNacimiento1, "siuweq872");
			assertEquals(true, creo);
			assertTrue(cUsuario.validarDatosInicioSesion("nick1", "siuweq872"));
			assertFalse(cUsuario.validarDatosInicioSesion("nick1", "siuweq873"));
		});
	}
	
	
	@Test
	void testInscribirEstudiantesPorUnDocente() {
		assertAll(()-> {
			ControladorCurso cCurso = new ControladorCurso();
			
			//creo instituto
			cCurso.ingresarInstituto("Instituto1");
			
			// Estudiante 1
			Calendar fechaNacimiento1 = Calendar.getInstance();
			fechaNacimiento1.set(2000, 6, 1);
			boolean creo = cUsuario.altaEstudiante("nick1", "nombre1", "apellido1", "mail1@mail.com", fechaNacimiento1, "os92veo");
			assertEquals(true, creo);
			
			// Estudiante 2
			Calendar fechaNacimiento2 = Calendar.getInstance();
			fechaNacimiento2.set(2000, 6, 2);
			creo = cUsuario.altaEstudiante("nick2", "nombre2", "apellido2", "mail2@mail.com", fechaNacimiento2, "aas90qb");
			assertEquals(true, creo);
			
			// Docente
			Calendar fechaNacimiento3 = Calendar.getInstance();
			fechaNacimiento3.set(2000, 6, 3);
			creo = cUsuario.altaDocente("nick3", "nombre3", "apellido3", "mail3@mail.com", fechaNacimiento3, "Instituto1", "bxpo7");
			assertEquals(true, creo);
				
			//creo curso
			cCurso.altaCursoSrc("Instituto1", "Dalavuelta", "1", "10 semanas", 60, 4, "1", new ArrayList<String>(), "sourceCurso");
				
			//creo edicion
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 5);
			assertTrue(cCurso.altaEdicionCursoSrc("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "Instituto1", "SourceEd"));
			
			//obtengo la instancia de Edicion
			ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
			Instituto insti = mInstituto.getInstituto("Instituto1");
			Curso cur = insti.getCurso("Dalavuelta");
			Edicion edic = cur.getEdicion("Dalavuelta-2019");
			
			//asignar docente a edicion
			List<String> docentes = new ArrayList<String>();
			docentes.add("nick3");
			cUsuario.asignarEdicionDocentes(docentes, edic);
			
			//inscribo estudiantes a edicion
			cCurso.inscribirEstudianteACurso("nick1", "Dalavuelta", "Dalavuelta-2019", cal1, "");
			cCurso.inscribirEstudianteACurso("nick2", "Dalavuelta", "Dalavuelta-2019", cal1, "");
			
			//creo map
			Map<String, Boolean> eleccion = new HashMap<String, Boolean>();
			eleccion.put("nick1", true);
			eleccion.put("nick2", false);
			
			//test
			cUsuario.inscribirEstudiantesPorUnDocente("nick3", "Dalavuelta-2019", eleccion);
			DtEdicion dte1 = cUsuario.consultaEdicion("nick1", "Dalavuelta-2019");
			EnumEstado resultado = ((DtEdicionEstudiante) dte1).getEstado();
			assertEquals(EnumEstado.ACEPTADA, resultado);
			
			DtEdicion dte2 = cUsuario.consultaEdicion("nick2", "Dalavuelta-2019");
			resultado = ((DtEdicionEstudiante) dte2).getEstado();
			assertEquals(EnumEstado.RECHAZADA, resultado);
		});
	}
	
	@Test
	void testIngresarResultadosDeEvaluaciones() {
		assertAll(()-> {
			ControladorCurso cCurso = new ControladorCurso();
			ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
			
			//creo instituto
			cCurso.ingresarInstituto("Instituto1");
			
			// Estudiante 1
			Calendar fechaNacimiento1 = Calendar.getInstance();
			fechaNacimiento1.set(2000, 6, 1);
			boolean creo = mUsuario.crearEstudiante("nick1", "nombre1", "apellido1", "mail1@mail.com", fechaNacimiento1, "os92veo", "source1");
			assertEquals(true, creo);
			
			// Docente
			Calendar fechaNacimiento2 = Calendar.getInstance();
			fechaNacimiento2.set(2000, 6, 3);
			creo = mUsuario.crearDocente("nick2", "nombre2", "apellido2", "mail2@mail.com", fechaNacimiento2, "Instituto1", "bxpo7", "source2");
			assertEquals(true, creo);
				
			//creo curso
			cCurso.altaCursoSrc("Instituto1", "Dalavuelta", "1", "10 semanas", 60, 4, "1", new ArrayList<String>(), "sourceCurso");
				
			//creo edicion
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 5);
			assertTrue(cCurso.altaEdicionCursoSrc("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "Instituto1", "SourceEd"));
			
			//obtengo la instancia de Edicion
			ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
			Instituto insti = mInstituto.getInstituto("Instituto1");
			Curso cur = insti.getCurso("Dalavuelta");
			Edicion edic = cur.getEdicion("Dalavuelta-2019");
			
			//asigno docente a edicion
			Docente docente1 = mUsuario.getDocente("nick2");
			docente1.asignarEdicion(edic);
			
			//inscribo estudiante a edicion
			cCurso.inscribirEstudianteACurso("nick1", "Dalavuelta", "Dalavuelta-2019", cal1, "");
			
			//creo map
			Map<String, Boolean> eleccion = new HashMap<String, Boolean>();
			eleccion.put("nick1", true);
			
			//acepto estudiante
			mUsuario.inscribirEstudiantesPorUnDocente("nick2", "Dalavuelta-2019", eleccion);
			DtEdicion dtEdicion1 = cUsuario.consultaEdicion("nick1", "Dalavuelta-2019");
			EnumEstado aceptar = ((DtEdicionEstudiante) dtEdicion1).getEstado();
			assertEquals(EnumEstado.ACEPTADA, aceptar);
			
			//califico estudiante
			Calendar fechaCalendar = Calendar.getInstance();
			mUsuario.ingresarResultadosDeEvaluaciones("nick2", "Dalavuelta-2019", "nick1", 10, fechaCalendar);
			
			//test
			Estudiante estudiante1 = mUsuario.getEstudiante("nick1");
			EstudianteEdicion estEdicion = estudiante1.getEstudianteEdicion("Dalavuelta-2019");
			int resultado = 10;
			assertEquals(estEdicion.getNota(), resultado);
			
		});
	}
	
	@Test
	void testObtenerCantidadInscripcionesRechazadas() {
		assertAll(()-> {
			//Estudiante de Prueba
			ControladorUsuario cUsuario = new ControladorUsuario();
			Calendar fechaNac = Calendar.getInstance();
			boolean creado = cUsuario.altaEstudianteSrc("nick1", "estoEsUnNombre", "estoEsUnApellido", "estoEsUnMail", fechaNac, "estoEsUnaPass", "/estoEsUnSrc.jpg");
			assertTrue(creado);
			
			//Obtenego al estudiante para hacer pruebas
			ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
			Estudiante auxEstudiante = mUsuario.getEstudiante("nick1");
			
			//Alta instituto para crear un curso
			ControladorCurso cCurso = new ControladorCurso();
			cCurso.ingresarInstituto("ins1");
			
			//Curso de prueba
			cCurso.altaCursoSrc("ins1", "curso1", "1", "10 semanas", 60, 4, "1", new ArrayList<String>(), "sourceCurso");

			//Edicion1
			Calendar fechaInicio = Calendar.getInstance(), fechaFin = Calendar.getInstance();
			fechaInicio.set(2010, 6, 1);
			fechaFin.set(2020, 6, 30);
			cCurso.altaEdicionCursoSrc("ed1", fechaInicio, fechaFin, 50, "curso1", new ArrayList<String>(), "ins1", "");
			
			//Edicion2
			Calendar fechaI = Calendar.getInstance(), fechaF = Calendar.getInstance();
			fechaInicio.set(2013, 6, 1);
			fechaFin.set(2023, 6, 30);
			cCurso.altaEdicionCursoSrc("ed2", fechaI, fechaF, 50, "curso1", new ArrayList<String>(), "ins1", "");
			
			//Prueba 1, no hay ediciones, resultado esperado 0
			int cant = cUsuario.obtenerCantidadInscripcionesRechazadas("curso1", "nick1");
			assertEquals(0, cant);
			
			//Prueba 2, inscripto a una edicion, resultado esperado 1
			Calendar fecha = Calendar.getInstance();
			cCurso.inscribirEstudianteACurso("nick1", "curso1", "ed1", fecha, "");
			EstudianteEdicion auxEE = auxEstudiante.getEstudianteEdicion("ed1");
			auxEE.cambiarEstado(false);
			cant = cUsuario.obtenerCantidadInscripcionesRechazadas("curso1", "nick1");
			assertEquals(1, cant);
			
			//Prueba 3, inscripto a 2 ediciones 1 rechazada, resultado esperado 1
			cCurso.inscribirEstudianteACurso("nick1", "curso1", "ed2", fecha, "");
			cant = cUsuario.obtenerCantidadInscripcionesRechazadas("curso1", "nick1");
			assertEquals(1, cant);
			
			//Prueba 4, inscripto a 2 ediciones 2 rechazadas, resultado esperado 2
			auxEE = auxEstudiante.getEstudianteEdicion("ed2");
			auxEE.cambiarEstado(false);
			cant = cUsuario.obtenerCantidadInscripcionesRechazadas("curso1", "nick1");
			assertEquals(2, cant);
		});
	}
	
	@Test
	public void testExistenciaDeDatos() {
		assertAll(() -> {
			// Usuario 1
			Calendar fechaNacimiento1 = Calendar.getInstance();
			fechaNacimiento1.set(2000, 6, 1);
			boolean creo = cUsuario.altaEstudiante("nick1", "nombre1", "apellido1", "mail1@mail.com", fechaNacimiento1, "os92veo");
			assertEquals(true, creo);
			
			// Usuario 2
			Calendar fechaNacimiento2 = Calendar.getInstance();
			fechaNacimiento2.set(2000, 6, 2);
			creo = cUsuario.altaEstudiante("nick2", "nombre2", "apellido2", "mail2@mail.com", fechaNacimiento2, "aas90qb");
			assertEquals(true, creo);
			
			assertTrue(cUsuario.existeNickname("nick1"));
			assertFalse(cUsuario.existeNickname("asd"));
			
			assertTrue(cUsuario.existeMail("mail1@mail.com"));
			assertFalse(cUsuario.existeMail("asd"));
		});
	}

}
