package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import java.lang.reflect.Field;

import excepciones.CursoNoExisteException;
import excepciones.EdicionNoExisteException;
import excepciones.InstitutoNoExisteException;
import excepciones.InstitutoYaExisteException;
import excepciones.UsuarioNoExisteException;

class TestControladorCurso {
	
	ControladorCurso controladorCurso;
	@BeforeEach
	void setup() {
		controladorCurso = new ControladorCurso();

	}
	
	
	@AfterEach 
	void tearDown() throws Exception {
		controladorCurso = null;
		Field field = ManejadorInstituto.class.getDeclaredField("instance");
		field.setAccessible(true);
		field.set(ManejadorInstituto.class, null);
		Field field2 = ManejadorCurso.class.getDeclaredField("instance");
		field2.setAccessible(true);
		field2.set(ManejadorCurso.class, null);
		Field field3 = ManejadorUsuario.class.getDeclaredField("instance");
		field3.setAccessible(true);
		field3.set(ManejadorUsuario.class, null);
	}
	
	@Test
	void altaInstituto(){
		assertAll(()->{
			controladorCurso.ingresarInstituto("IMERL");
			ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
			Instituto instituto= mInstituto.getInstituto("IMERL");
			assertEquals(instituto.getNombre(), "IMERL");
		});
		
	}
	
	@Test
	void altaInstitutos(){
		assertAll(()->{
			
			controladorCurso.ingresarInstituto("INCO");
			controladorCurso.ingresarInstituto("INCO2");
			controladorCurso.ingresarInstituto("INCO3");
			controladorCurso.ingresarInstituto("INCO4");
			ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
			Instituto instituto= mInstituto.getInstituto("INCO");
			assertEquals(instituto.getNombre(), "INCO");
			List<String> arrayList = controladorCurso.listarInstitutos();
			assertEquals("INCO", arrayList.get(0));
			assertEquals("INCO2", arrayList.get(1));
			assertEquals("INCO4", arrayList.get(2));
			assertEquals("INCO3", arrayList.get(3));
			assertThrows(InstitutoYaExisteException.class, ()->controladorCurso.ingresarInstituto("INCO2"));
			
		});
		
	}
	
	@Test
	void testListarCursos() {
		assertAll(()->{
			controladorCurso.ingresarInstituto("INCO");
			controladorCurso.ingresarInstituto("INCO2");
			controladorCurso.ingresarInstituto("INCO3");
			controladorCurso.ingresarInstituto("INCO4");
			controladorCurso.altaCurso("INCO", "Dalavuelta", "1", "10 semanas", 60, 4, "1", new ArrayList<String>());
			controladorCurso.altaCurso("INCO", "Dalavuelta2", "2", "10 semanas", 60, 4, "2", new ArrayList<String>());
			controladorCurso.altaCurso("INCO", "Dalavuelta3", "3", "10 semanas", 60, 4, "3", new ArrayList<String>());
			controladorCurso.altaCurso("INCO3", "Dalavuelta4", "1", "10 semanas", 60, 4, "4", new ArrayList<String>());
			Map<String,DtCurso> strings = controladorCurso.listarCursos();
			assertTrue(strings.containsKey("Dalavuelta"));
			assertTrue(strings.containsKey("Dalavuelta2"));
			assertTrue(strings.containsKey("Dalavuelta3"));
			assertTrue(strings.containsKey("Dalavuelta4"));
			List<String> cursoStrings = controladorCurso.mostrarCursosEnInstituto("INCO");
			assertTrue(cursoStrings.contains("Dalavuelta"));
			assertTrue(cursoStrings.contains("Dalavuelta2"));
			assertTrue(cursoStrings.contains("Dalavuelta3"));
			assertFalse(cursoStrings.contains("Dalavuelta4"));
			List<String> cursoStrings2 = controladorCurso.mostrarCursosEnInstituto("INCO3");
			assertFalse(cursoStrings2.contains("Dalavuelta"));
			assertFalse(cursoStrings2.contains("Dalavuelta2"));
			assertFalse(cursoStrings2.contains("Dalavuelta3"));
			assertTrue(cursoStrings2.contains("Dalavuelta4"));
			assertThrows(InstitutoNoExisteException.class, ()-> controladorCurso.mostrarCursosEnInstituto("IMPII"));
			assertThrows(CursoNoExisteException.class, ()-> controladorCurso.mostrarCurso("INCO2"));
			
			
		});
	}
	
	@Test
	void testMostrarCurso() {
		assertAll(()-> {
			controladorCurso.ingresarInstituto("INCO");
			controladorCurso.ingresarInstituto("INCO2");
			controladorCurso.ingresarInstituto("INCO3");
			controladorCurso.ingresarInstituto("INCO4");
			controladorCurso.altaCurso("INCO", "Dalavuelta", "1", "10 semanas", 60, 4, "1", new ArrayList<String>());
			controladorCurso.altaCurso("INCO", "Dalavuelta2", "2", "10 semanas", 60, 4, "2", new ArrayList<String>());
			controladorCurso.altaCurso("INCO", "Dalavuelta3", "3", "10 semanas", 60, 4, "3", new ArrayList<String>());
			controladorCurso.altaCurso("INCO3", "Dalavuelta4", "1", "10 semanas", 60, 4, "4", new ArrayList<String>());
			assertThrows(CursoNoExisteException.class, ()->controladorCurso.mostrarCurso("asfdsf"));
			assertEquals(controladorCurso.mostrarCurso("Dalavuelta").getNombre(), "Dalavuelta");
			assertEquals(controladorCurso.mostrarCurso("Dalavuelta").getCreditos(), 4);
			Map<String,DtCurso> strings = controladorCurso.listarCursosAsociados("INCO");
			assertTrue(strings.containsKey("Dalavuelta"));
			assertTrue(strings.containsKey("Dalavuelta2"));
			assertTrue(strings.containsKey("Dalavuelta3"));
			assertFalse(strings.containsKey("Dalavuelta4"));
			Map<String,DtCurso> strings2 = controladorCurso.listarCursosAsociados("INCO3");
			assertFalse(strings2.containsKey("Dalavuelta"));
			assertFalse(strings2.containsKey("Dalavuelta2"));
			assertFalse(strings2.containsKey("Dalavuelta3"));
			assertTrue(strings2.containsKey("Dalavuelta4"));
			assertTrue(controladorCurso.listarCursosAsociados("INCO2").isEmpty());
			
			
		});
	}
	
	@Test 
	void testAltaEdicion() {
		assertAll(()->{
			controladorCurso.ingresarInstituto("INCO");
			controladorCurso.ingresarInstituto("INCO2");
			controladorCurso.ingresarInstituto("INCO3");
			controladorCurso.ingresarInstituto("INCO4");
			controladorCurso.altaCurso("INCO", "Dalavuelta", "1", "10 semanas", 60, 4, "1", new ArrayList<String>());
			controladorCurso.altaCurso("INCO", "Dalavuelta2", "2", "10 semanas", 60, 4, "2", new ArrayList<String>());
			controladorCurso.altaCurso("INCO", "Dalavuelta3", "3", "10 semanas", 60, 4, "3", new ArrayList<String>());
			controladorCurso.altaCurso("INCO3", "Dalavuelta4", "1", "10 semanas", 60, 4, "4", new ArrayList<String>());
			Calendar cal1 = Calendar.getInstance();
			
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 5);
			assertTrue(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
			assertFalse(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
			assertFalse(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta4", new ArrayList<String>(), "INCO3"));
			Calendar cal3 = Calendar.getInstance();
			Calendar cal4 = Calendar.getInstance();
			cal4.add(Calendar.DATE, 5);
			cal3.add(Calendar.YEAR, -1);
			cal4.add(Calendar.YEAR, -1);
			assertTrue(controladorCurso.altaEdicionCurso("Dalavuelta-2018", cal3, cal4, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
			DtEdicion datos = controladorCurso.darDatosEdicion("Dalavuelta", "Dalavuelta-2018");
			assertEquals("", datos.getSource());
			
			assertThrows(CursoNoExisteException.class, ()->controladorCurso.mostrarEdicionesDeCurso("sfasdas"));
			assertThrows(EdicionNoExisteException.class, ()->controladorCurso.mostrarEdicionesDeCurso("Dalavuelta2"));
			assertTrue(controladorCurso.mostrarEdicionesDeCurso("Dalavuelta").contains("Dalavuelta-2019"));
			assertTrue(controladorCurso.mostrarEdicionesDeCurso("Dalavuelta").contains("Dalavuelta-2018"));
			assertEquals(controladorCurso.mostrarEdicionVigenteDeCurso("Dalavuelta").getNombre(), "Dalavuelta-2019");
			assertTrue(controladorCurso.altaEdicionCurso("Dalavuelta2-2018", cal3, cal4, 20, "Dalavuelta2", new ArrayList<String>(), "INCO"));
			assertThrows(EdicionNoExisteException.class,()->controladorCurso.mostrarEdicionVigenteDeCurso("Dalavuelta2"));
			assertThrows(EdicionNoExisteException.class,()->controladorCurso.mostrarEdicionVigenteDeCurso("Dalavuelta3") );
			assertThrows(CursoNoExisteException.class,()->controladorCurso.mostrarEdicionVigenteDeCurso("dasdasdas") );
			assertThrows(EdicionNoExisteException.class, ()-> controladorCurso.darDatosEdicion("Dalavuelta", "Dalavuelta-2017"));
			assertThrows(CursoNoExisteException.class, ()-> controladorCurso.darDatosEdicion("Dalavueltaasdasd", "Dalavuelta-2017"));
			assertEquals(controladorCurso.darDatosEdicion("Dalavuelta", "Dalavuelta-2018").getNombre(),"Dalavuelta-2018");
		});
	}
	
	@Test 
	void testAltaEdicionSrc() {
		assertAll(()->{
			controladorCurso.ingresarInstituto("INCO");
			controladorCurso.ingresarInstituto("INCO2");
			controladorCurso.ingresarInstituto("INCO3");
			controladorCurso.ingresarInstituto("INCO4");
			controladorCurso.altaCursoSrc("INCO", "Dalavuelta", "1", "10 semanas", 60, 4, "1", new ArrayList<String>(), "sourceCurso");
			controladorCurso.altaCursoSrc("INCO", "Dalavuelta2", "2", "10 semanas", 60, 4, "2", new ArrayList<String>(), "sourceCurso");
			controladorCurso.altaCursoSrc("INCO", "Dalavuelta3", "3", "10 semanas", 60, 4, "3", new ArrayList<String>(), "sourceCurso");
			controladorCurso.altaCursoSrc("INCO3", "Dalavuelta4", "1", "10 semanas", 60, 4, "4", new ArrayList<String>(), "sourceCurso");
			Calendar cal1 = Calendar.getInstance();
			
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 5);
			assertTrue(controladorCurso.altaEdicionCursoSrc("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO", "SourceEd"));
			assertFalse(controladorCurso.altaEdicionCursoSrc("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO", "SourceEd"));
			assertFalse(controladorCurso.altaEdicionCursoSrc("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta4", new ArrayList<String>(), "INCO3", "SourceEd"));
			Calendar cal3 = Calendar.getInstance();
			Calendar cal4 = Calendar.getInstance();
			cal4.add(Calendar.DATE, 5);
			cal3.add(Calendar.YEAR, -1);
			cal4.add(Calendar.YEAR, -1);
			assertTrue(controladorCurso.altaEdicionCursoSrc("Dalavuelta-2018", cal3, cal4, 20, "Dalavuelta", new ArrayList<String>(), "INCO", "SourceEd"));
			DtEdicion datos = controladorCurso.darDatosEdicion("Dalavuelta", "Dalavuelta-2018");
			assertEquals("SourceEd", datos.getSource());
			
			assertThrows(CursoNoExisteException.class, ()->controladorCurso.mostrarEdicionesDeCurso("sfasdas"));
			assertThrows(EdicionNoExisteException.class, ()->controladorCurso.mostrarEdicionesDeCurso("Dalavuelta2"));
			assertTrue(controladorCurso.mostrarEdicionesDeCurso("Dalavuelta").contains("Dalavuelta-2019"));
			assertTrue(controladorCurso.mostrarEdicionesDeCurso("Dalavuelta").contains("Dalavuelta-2018"));
			assertEquals(controladorCurso.mostrarEdicionVigenteDeCurso("Dalavuelta").getNombre(), "Dalavuelta-2019");
			assertTrue(controladorCurso.altaEdicionCursoSrc("Dalavuelta2-2018", cal3, cal4, 20, "Dalavuelta2", new ArrayList<String>(), "INCO", "SourceEd"));
			assertThrows(EdicionNoExisteException.class,()->controladorCurso.mostrarEdicionVigenteDeCurso("Dalavuelta2"));
			assertThrows(EdicionNoExisteException.class,()->controladorCurso.mostrarEdicionVigenteDeCurso("Dalavuelta3") );
			assertThrows(CursoNoExisteException.class,()->controladorCurso.mostrarEdicionVigenteDeCurso("dasdasdas") );
			assertThrows(EdicionNoExisteException.class, ()-> controladorCurso.darDatosEdicion("Dalavuelta", "Dalavuelta-2017"));
			assertThrows(CursoNoExisteException.class, ()-> controladorCurso.darDatosEdicion("Dalavueltaasdasd", "Dalavuelta-2017"));
			assertEquals(controladorCurso.darDatosEdicion("Dalavuelta", "Dalavuelta-2018").getNombre(),"Dalavuelta-2018");
		});
	}
	
	@Test
	void testgetListaDeEstudiantes() {
		assertThrows(UsuarioNoExisteException.class, ()-> controladorCurso.getListaDeEstudiantes());
		assertAll(()->{
			Calendar calUsuario = Calendar.getInstance();
			calUsuario.set(1959, Calendar.MAY, 15);
			controladorCurso.ingresarInstituto("INCO");

			ControladorUsuario cUsuario = new ControladorUsuario();
			cUsuario.altaDocente("house", "Gregory", "House", "greghouse@gmail.com", calUsuario.getInstance(), "INCO", "nja98");
			assertTrue(controladorCurso.getListaDeEstudiantes().isEmpty());
			
			calUsuario = Calendar.getInstance();
			calUsuario.set(1975, Calendar.AUGUST, 2);
			cUsuario.altaEstudiante("roro", "Rodrigo", "Cotelo", "rcotelo@yahoo.com", calUsuario, "n9bwo0");
			
			calUsuario = Calendar.getInstance();
			calUsuario.set(1987, Calendar.SEPTEMBER, 12);
			cUsuario.altaEstudiante("chechi", "Cecilia", "Garrido", "cgarrido@hotmail.com", calUsuario, "n7qobd9");
			assertTrue(controladorCurso.getListaDeEstudiantes().contains("chechi"));
			assertTrue(controladorCurso.getListaDeEstudiantes().contains("roro"));
			
		});
	}
	
	@Test
	void testgetListaDeDocentes() {
		assertAll(()->{
			Calendar calUsuario = Calendar.getInstance();
			ControladorUsuario cUsuario = new ControladorUsuario();
			
			calUsuario.set(1975, Calendar.AUGUST, 2);
			cUsuario.altaEstudiante("roro", "Rodrigo", "Cotelo", "rcotelo@yahoo.com", calUsuario, "nwn98dh");
			
			calUsuario = Calendar.getInstance();
			calUsuario.set(1987, Calendar.SEPTEMBER, 12);
			cUsuario.altaEstudiante("chechi", "Cecilia", "Garrido", "cgarrido@hotmail.com", calUsuario, "n239wb");
			
			assertTrue(controladorCurso.listarDocentes().isEmpty());
			
			controladorCurso.ingresarInstituto("INCO");

			calUsuario = Calendar.getInstance();
			calUsuario.set(1959, Calendar.MAY, 15);
			cUsuario.altaDocente("house", "Gregory", "House", "greghouse@gmail.com", calUsuario, "INCO", "oweh8923r");
			
			assertTrue(controladorCurso.listarDocentes().containsKey("house"));
			
			
		
			
		});
	}
	
	@Test
	void testgetinscribir() {
		assertAll(()->{
			
			controladorCurso.ingresarInstituto("INCO");
			controladorCurso.ingresarInstituto("INCO2");
			controladorCurso.ingresarInstituto("INCO3");
			controladorCurso.ingresarInstituto("INCO4");
			controladorCurso.altaCurso("INCO", "Dalavuelta", "1", "10 semanas", 60, 4, "1", new ArrayList<String>());
			controladorCurso.altaCurso("INCO", "Dalavuelta2", "2", "10 semanas", 60, 4, "2", new ArrayList<String>());
			controladorCurso.altaCurso("INCO", "Dalavuelta3", "3", "10 semanas", 60, 4, "3", new ArrayList<String>());
			controladorCurso.altaCurso("INCO3", "Dalavuelta4", "1", "10 semanas", 60, 4, "4", new ArrayList<String>());
			Calendar cal1 = Calendar.getInstance();
			
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 5);
			assertTrue(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
			assertFalse(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
			assertFalse(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta4", new ArrayList<String>(), "INCO3"));
			Calendar cal3 = Calendar.getInstance();
			Calendar cal4 = Calendar.getInstance();
			cal4.add(Calendar.DATE, 5);
			cal3.add(Calendar.YEAR, -1);
			cal4.add(Calendar.YEAR, -1);
			
			Calendar calUsuario = Calendar.getInstance();
			ControladorUsuario cUsuario = new ControladorUsuario();
			
			calUsuario.set(1975, Calendar.AUGUST, 2);
			cUsuario.altaEstudiante("roro", "Rodrigo", "Cotelo", "rcotelo@yahoo.com", calUsuario, "n09rh8ewu");
			
			calUsuario = Calendar.getInstance();
			calUsuario.set(1987, Calendar.SEPTEMBER, 12);
			cUsuario.altaEstudiante("chechi", "Cecilia", "Garrido", "cgarrido@hotmail.com", calUsuario, "n023irni");
			
			assertThrows(CursoNoExisteException.class, ()-> controladorCurso.inscribirEstudianteACurso("roro", "asdasd", "asdasd", Calendar.getInstance(), ""));
			assertThrows(EdicionNoExisteException.class, ()-> controladorCurso.inscribirEstudianteACurso("roro", "Dalavuelta", "fdfgfd", Calendar.getInstance(), ""));
			assertThrows(UsuarioNoExisteException.class, ()-> controladorCurso.inscribirEstudianteACurso("rfdgfdg", "Dalavuelta", "Dalavuelta-2019", Calendar.getInstance(), ""));
		
			controladorCurso.inscribirEstudianteACurso("roro", "Dalavuelta", "Dalavuelta-2019", Calendar.getInstance(), "");
			ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
			DtEstudiante dtEstudiante = (DtEstudiante) mUsuario.informacionDeUsuario("roro");
			assertTrue(dtEstudiante.getEdiciones().contains("Dalavuelta-2019"));
			assertTrue(controladorCurso.listarEstudianteEdicion("Dalavuelta-2019").containsKey("roro"));
			assertFalse(controladorCurso.listarEstudianteEdicion("Dalavuelta-2019").containsKey("roro2"));
		});
	}
	
	@Test
	void testDesistirAInscripcionDeCurso() {
		assertAll(()->{
			
			controladorCurso.ingresarInstituto("INCO");
			controladorCurso.ingresarInstituto("INCO2");
			controladorCurso.ingresarInstituto("INCO3");
			controladorCurso.ingresarInstituto("INCO4");
			controladorCurso.altaCurso("INCO", "Dalavuelta", "1", "10 semanas", 60, 4, "1", new ArrayList<String>());
			controladorCurso.altaCurso("INCO", "Dalavuelta2", "2", "10 semanas", 60, 4, "2", new ArrayList<String>());
			controladorCurso.altaCurso("INCO", "Dalavuelta3", "3", "10 semanas", 60, 4, "3", new ArrayList<String>());
			controladorCurso.altaCurso("INCO3", "Dalavuelta4", "1", "10 semanas", 60, 4, "4", new ArrayList<String>());
			Calendar cal1 = Calendar.getInstance();
			
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 5);
			assertTrue(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
			assertFalse(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
			assertFalse(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta4", new ArrayList<String>(), "INCO3"));
			Calendar cal3 = Calendar.getInstance();
			Calendar cal4 = Calendar.getInstance();
			cal4.add(Calendar.DATE, 5);
			cal3.add(Calendar.YEAR, -1);
			cal4.add(Calendar.YEAR, -1);
			
			Calendar calUsuario = Calendar.getInstance();
			ControladorUsuario cUsuario = new ControladorUsuario();
			
			calUsuario.set(1975, Calendar.AUGUST, 2);
			cUsuario.altaEstudiante("roro", "Rodrigo", "Cotelo", "rcotelo@yahoo.com", calUsuario, "n09rh8ewu");
			
			calUsuario = Calendar.getInstance();
			calUsuario.set(1987, Calendar.SEPTEMBER, 12);
			cUsuario.altaEstudiante("chechi", "Cecilia", "Garrido", "cgarrido@hotmail.com", calUsuario, "n023irni");
			
			assertThrows(CursoNoExisteException.class, ()-> controladorCurso.inscribirEstudianteACurso("roro", "asdasd", "asdasd", Calendar.getInstance(), ""));
			assertThrows(EdicionNoExisteException.class, ()-> controladorCurso.inscribirEstudianteACurso("roro", "Dalavuelta", "fdfgfd", Calendar.getInstance(), ""));
			assertThrows(UsuarioNoExisteException.class, ()-> controladorCurso.inscribirEstudianteACurso("rfdgfdg", "Dalavuelta", "Dalavuelta-2019", Calendar.getInstance(), ""));
		
			controladorCurso.inscribirEstudianteACurso("roro", "Dalavuelta", "Dalavuelta-2019", Calendar.getInstance(), "");
			ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
			DtEstudiante dtEstudiante = (DtEstudiante) mUsuario.informacionDeUsuario("roro");
			assertTrue(dtEstudiante.getEdiciones().contains("Dalavuelta-2019"));
			assertTrue(controladorCurso.listarEstudianteEdicion("Dalavuelta-2019").containsKey("roro"));
			assertFalse(controladorCurso.listarEstudianteEdicion("Dalavuelta-2019").containsKey("roro2"));
			
			controladorCurso.desistirAInscripcionDeCurso("roro", "Dalavuelta-2019");
			mUsuario = ManejadorUsuario.getInstance();
			dtEstudiante = (DtEstudiante) mUsuario.informacionDeUsuario("roro");
			assertFalse(dtEstudiante.getEdiciones().contains("Dalavuelta-2019"));
			assertFalse(controladorCurso.listarEstudianteEdicion("Dalavuelta-2019").containsKey("roro"));
		});
	}
	
	 @Test
	 void listarEdiciones() {
		 assertAll(()->{
				controladorCurso.ingresarInstituto("INCO");
				controladorCurso.ingresarInstituto("INCO2");
				controladorCurso.ingresarInstituto("INCO3");
				controladorCurso.ingresarInstituto("INCO4");
				controladorCurso.altaCurso("INCO", "Dalavuelta", "1", "10 semanas", 60, 4, "1", new ArrayList<String>());
				controladorCurso.altaCurso("INCO", "Dalavuelta2", "2", "10 semanas", 60, 4, "2", new ArrayList<String>());
				controladorCurso.altaCurso("INCO", "Dalavuelta3", "3", "10 semanas", 60, 4, "3", new ArrayList<String>());
				controladorCurso.altaCurso("INCO3", "Dalavuelta4", "1", "10 semanas", 60, 4, "4", new ArrayList<String>());
				Calendar cal1 = Calendar.getInstance();
				
				Calendar cal2 = Calendar.getInstance();
				cal2.add(Calendar.DATE, 5);
				assertTrue(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
				assertTrue(controladorCurso.altaEdicionCurso("Dalavuelta-2018", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
				assertTrue(controladorCurso.listarDtEdiciones("Dalavuelta").containsKey("Dalavuelta-2019"));
				assertTrue(controladorCurso.listarDtEdiciones("Dalavuelta").containsKey("Dalavuelta-2018"));
				assertFalse(controladorCurso.listarDtEdiciones("Dalavuelta").containsKey("Dalavuelta-2017"));	
		 });
	 }
	 
	 @Test
		void testCerrarEdicion() {
			assertAll(()->{
				
				controladorCurso.ingresarInstituto("INCO");
				controladorCurso.ingresarInstituto("INCO2");
				controladorCurso.ingresarInstituto("INCO3");
				controladorCurso.ingresarInstituto("INCO4");
				controladorCurso.altaCurso("INCO", "Dalavuelta", "1", "10 semanas", 60, 4, "1", new ArrayList<String>());
				controladorCurso.altaCurso("INCO", "Dalavuelta2", "2", "10 semanas", 60, 4, "2", new ArrayList<String>());
				controladorCurso.altaCurso("INCO", "Dalavuelta3", "3", "10 semanas", 60, 4, "3", new ArrayList<String>());
				controladorCurso.altaCurso("INCO3", "Dalavuelta4", "1", "10 semanas", 60, 4, "4", new ArrayList<String>());
				Calendar cal1 = Calendar.getInstance();
				
				Calendar cal2 = Calendar.getInstance();
				cal2.add(Calendar.DATE, 5);
				assertTrue(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
				assertFalse(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
				assertFalse(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta4", new ArrayList<String>(), "INCO3"));
				Calendar cal3 = Calendar.getInstance();
				Calendar cal4 = Calendar.getInstance();
				cal4.add(Calendar.DATE, 5);
				cal3.add(Calendar.YEAR, -1);
				cal4.add(Calendar.YEAR, -1);
				
				Calendar calUsuario = Calendar.getInstance();
				ControladorUsuario controladorUsuario = new ControladorUsuario();
				
				calUsuario.set(1959, Calendar.MAY, 15);
				controladorUsuario.altaDocente("house", "Gregory", "House", "greghouse@gmail.com", calUsuario, "INCO", "oweh8923r");
				
				List<String> docentes = new ArrayList<String>();
				docentes.add("house");
				
				ManejadorCurso manejadorCurso = ManejadorCurso.getInstance();
				controladorUsuario.asignarEdicionDocentes(docentes, manejadorCurso.getCurso("Dalavuelta").getEdicion("Dalavuelta-2019"));
				
				DtEdicion infoEdicion = controladorCurso.darDatosEdicion("Dalavuelta", "Dalavuelta-2019");
				assertFalse(infoEdicion.getCerrada());
				
				try {
					controladorCurso.cerrarEdicion("Dalavuelta-2019", "house");
				} catch (CursoNoExisteException | EdicionNoExisteException | UsuarioNoExisteException e) {
					System.out.println(e.getMessage());
				}
				infoEdicion = controladorCurso.darDatosEdicion("Dalavuelta", "Dalavuelta-2019");
				assertTrue(infoEdicion.getCerrada());
				
				assertThrows(UsuarioNoExisteException.class, () -> controladorCurso.cerrarEdicion("Dalavuelta-2019", "asdasd"));
				assertThrows(UsuarioNoExisteException.class, () -> controladorCurso.cerrarEdicion("asdasd", "house"));
			});
		}
	 
	 @Test
	 public void testValidacionDatos() {
		 assertAll(() -> {
			 controladorCurso.ingresarInstituto("INCO");
			 controladorCurso.ingresarInstituto("INCO2");
			 controladorCurso.altaCurso("INCO", "Dalavuelta", "1", "10 semanas", 60, 4, "1", new ArrayList<String>());
			
			 Calendar cal1 = Calendar.getInstance();
			 cal1.set(2019, 6, 1);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.set(2019, 12, 15);
			 assertTrue(controladorCurso.altaEdicionCurso("Dalavuelta-2019", cal1, cal2, 20, "Dalavuelta", new ArrayList<String>(), "INCO"));
			 
			 assertTrue(controladorCurso.existeCurso("Dalavuelta"));
			 assertFalse(controladorCurso.existeCurso("asd"));
			 
			 assertTrue(controladorCurso.existeEdicion("Dalavuelta-2019"));
			 assertFalse(controladorCurso.existeEdicion("asd"));
		 });
	 }

}
