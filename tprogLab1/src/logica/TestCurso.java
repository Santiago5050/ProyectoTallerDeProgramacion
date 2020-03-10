package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.EdicionNoExisteException;
import excepciones.UsuarioNoExisteException;

class TestCurso {
	private Curso cur;
	private Edicion edicion;
	private Calendar cal;
	private Calendar cal2;
	
	@BeforeEach
	void setup() {
		cur = new Curso("Talleres plenarios", "*Talleres plenarios*: presentados por cuatro reconocidos\n" + 
					"matemáticos uruguayos, plantearán diversos tópicos de matemática\n" + 
					"en el marco de los cuales se realizarán actividades fomentando la\n" + 
					"integración entre estudiantes, docentes e investigadores.\n" + 
					"", "3 semanas", 15, 1, " www.tmu.edu.uy", new HashMap<String, Curso>(), "source1");
		cal= Calendar.getInstance();
		cur.setFechaRegistro(cal);
		cal2= Calendar.getInstance();
		cal2.add(Calendar.DATE, 5);
		edicion = new Edicion("Talleres-2019", cal, cal2, 20, true, "sourceEd");
		Calendar calUsuario = Calendar.getInstance();
		calUsuario.set(1971, Calendar.DECEMBER, 31);
		Factory factory = new Factory();
		InterfazControladorUsuario icu = factory.getInterfazControladorUsuario();
		icu.altaEstudiante("eleven11", "Eleven", "", "eleven11@gmail.com", calUsuario, "nwoncw83");
		
	}

	@AfterEach
	void tearDown() throws Exception {
		cur=null;
		edicion=null;
		Field field = ManejadorInstituto.class.getDeclaredField("instance");
		field.setAccessible(true);
		field.set(ManejadorInstituto.class, null);
		Field field2 = ManejadorCurso.class.getDeclaredField("instance");
		field2.setAccessible(true);
		field2.set(ManejadorCurso.class, null);
		Field field3 = ManejadorUsuario.class.getDeclaredField("instance");
		field3.setAccessible(true);
		field3.set(ManejadorUsuario.class, null);
		Field field4 = ManejadorProgramaFormacion.class.getDeclaredField("instance");
		field4.setAccessible(true);
		field4.set(ManejadorProgramaFormacion.class, null);
	}
	
	@Test
	void testgetNombre() {
		assertEquals(cur.getNombre(), "Talleres plenarios");
	}
	
	@Test
	void testgetDtCurso() {
		DtCurso curso1 = cur.getDtCurso();
		DtCurso curso2 = new DtCurso("Talleres plenarios", "*Talleres plenarios*: presentados por cuatro reconocidos\n" + 
					"matemáticos uruguayos, plantearán diversos tópicos de matemática\n" + 
					"en el marco de los cuales se realizarán actividades fomentando la\n" + 
					"integración entre estudiantes, docentes e investigadores.\n" + 
					"", "3 semanas", 15, cal, " www.tmu.edu.uy", 1, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), "source1","IMERL",new int[5],0);
		assertEquals(curso1.getNombre(), curso2.getNombre());
		assertEquals(curso1.getDescripcion(), curso2.getDescripcion());
		assertEquals(curso1.getCantidadHoras(), curso2.getCantidadHoras());
		assertEquals(curso1.getDuracion(), curso2.getDuracion());
		assertEquals(curso1.getUrl(), curso2.getUrl());
		assertEquals(curso1.getCreditos(), curso2.getCreditos());
		assertEquals(curso1.getEdiciones(), curso2.getEdiciones());
		assertEquals(curso1.getPrevias(), curso2.getPrevias());
		assertEquals(curso1.getProgramas(), curso2.getProgramas());
		assertEquals(curso1.getSource(), curso2.getSource());
		assertTrue(curso1.getFechaDeRegistro().equals(curso2.getFechaDeRegistro()));
	}
	
	@Test
	void testAgregarEdicion() throws EdicionNoExisteException {
		cur.agregarEdicion("Talleres-2019", cal, cal2, 20, "sourceEd");
		DtEdicion dtEdicion = cur.darDatosEdicion("Talleres-2019");
		DtEdicion dtEdicion2 = edicion.getDtEdicion();
		assertEquals(dtEdicion.getNombre(),dtEdicion2.getNombre());
		assertEquals(dtEdicion.getCupo(), dtEdicion2.getCupo());
		assertEquals(dtEdicion.getVigencia(), dtEdicion2.getVigencia());
		assertTrue(dtEdicion.getFechaFin().equals(dtEdicion2.getFechaFin()));
		assertTrue(dtEdicion.getFechaInicio().equals(dtEdicion2.getFechaInicio()));
		assertEquals(dtEdicion.getFechaPublicacion().get(Calendar.DAY_OF_MONTH), dtEdicion2.getFechaPublicacion().get(Calendar.DAY_OF_MONTH));
		assertEquals(dtEdicion.getFechaPublicacion().get(Calendar.MONTH), dtEdicion2.getFechaPublicacion().get(Calendar.MONTH));
		assertEquals(dtEdicion.getFechaPublicacion().get(Calendar.YEAR), dtEdicion2.getFechaPublicacion().get(Calendar.YEAR));
		assertEquals(dtEdicion.getSource(),dtEdicion2.getSource());
	}
	
	@Test
	void testTieneEdicionCorrecto() {
		cur.agregarEdicion("Talleres-2019", cal, cal2, 20, "sourceEd");
		assertTrue(cur.tieneEdicion("Talleres-2019"));
	}
	
	@Test
	void testTieneEdicionIncorrecto() {
		
		assertFalse(cur.tieneEdicion("Talleres-2019"));
	}
	
	@Test
	void testMostrarEdiciones() {
		assertAll(()->{
			EdicionNoExisteException edicionNoExisteException = assertThrows(EdicionNoExisteException.class, ()-> cur.getListaEdiciones());
			assertEquals(edicionNoExisteException.getMessage(), "No hay ediciones vinculadas a este curso");
			cur.agregarEdicion("Talleres-2019", cal, cal2, 20, "sourceEd");
			assertTrue(cur.getListaEdiciones().contains("Talleres-2019"));
			assertTrue(cur.getListaEdiciones().size()==1);
		});		
	}
	
	@Test
	void testMostrarEdicioneVigente() {
		assertAll(()->{
			EdicionNoExisteException edicionNoExisteException = assertThrows(EdicionNoExisteException.class, ()-> cur.mostrarEdicionVigente());
			assertEquals(edicionNoExisteException.getMessage(), "No hay ediciones vinculadas a este curso");
			cur.agregarEdicion("Talleres-2019", cal, cal2, 20, "sourceEd");
			assertTrue(cur.mostrarEdicionVigente().getNombre()=="Talleres-2019");
			assertTrue(cur.mostrarEdicionVigente().getFechaInicio()==cal);
			assertTrue(cur.mostrarEdicionVigente().getFechaFin()==cal2);
			assertTrue(cur.mostrarEdicionVigente().getCupo()==20);
			assertTrue(cur.mostrarEdicionVigente().getSource()=="sourceEd");
			Calendar cal3= Calendar.getInstance();
			cal3.add(Calendar.YEAR, -1);
			Calendar cal4= Calendar.getInstance();
			cal4.add(Calendar.YEAR, -1);
			cal4.add(Calendar.DATE, 5);
			cur.agregarEdicion("Talleres-2018", cal3, cal4, 20, "sourceEd");
			assertTrue(cur.mostrarEdicionVigente().getNombre()=="Talleres-2019");
			assertTrue(cur.mostrarEdicionVigente().getFechaInicio()==cal);
			assertTrue(cur.mostrarEdicionVigente().getFechaFin()==cal2);
			assertTrue(cur.mostrarEdicionVigente().getCupo()==20);
			assertTrue(cur.mostrarEdicionVigente().getSource()=="sourceEd");
		});	
	}
	
	@Test
	void testMostrarEdicioneVigente2() {
		assertAll(()->{
			EdicionNoExisteException edicionNoExisteException = assertThrows(EdicionNoExisteException.class, ()-> cur.mostrarEdicionVigente());
			assertEquals(edicionNoExisteException.getMessage(), "No hay ediciones vinculadas a este curso");
			Calendar cal3= Calendar.getInstance();
			cal3.add(Calendar.YEAR, -1);
			Calendar cal4= Calendar.getInstance();
			cal4.add(Calendar.YEAR, -1);
			cal4.add(Calendar.DATE, 5);
			System.out.println(cal4.toString());
			System.out.println(cal3.toString());
			cur.agregarEdicion("Talleres-2018", cal3, cal4, 20, "sourceEd");
			assertThrows(EdicionNoExisteException.class,()->cur.mostrarEdicionVigente());
			
			cur.agregarEdicion("Talleres-2019", cal, cal2, 20, "sourceEd");
			DtEdicion edic = cur.mostrarEdicionVigente();
			assertEquals(edic.getNombre(),"Talleres-2019");
			assertTrue(edic.getFechaInicio()==cal);
			assertTrue(edic.getFechaFin()==cal2);
			assertEquals(edic.getCupo(),20);
			assertTrue(edic.getSource()=="sourceEd");
		
		});	
	}
	
	@Test
	void testMostrarEdicioneVigente3() {
		assertAll(()->{
			EdicionNoExisteException edicionNoExisteException = assertThrows(EdicionNoExisteException.class, ()-> cur.mostrarEdicionVigente());
			assertEquals(edicionNoExisteException.getMessage(), "No hay ediciones vinculadas a este curso");
			cur.agregarEdicion("Talleres-2019", cal, cal2, 20, "sourceEd");
			assertTrue(cur.mostrarEdicionVigente().getNombre()=="Talleres-2019");
			assertTrue(cur.mostrarEdicionVigente().getFechaInicio()==cal);
			assertTrue(cur.mostrarEdicionVigente().getFechaFin()==cal2);
			assertTrue(cur.mostrarEdicionVigente().getCupo()==20);
			assertTrue(cur.mostrarEdicionVigente().getSource()=="sourceEd");
			Calendar cal3= Calendar.getInstance();
			cal3.add(Calendar.YEAR, -1);
			Calendar cal4= Calendar.getInstance();
			cal4.add(Calendar.YEAR, -1);
			cal4.add(Calendar.DATE, 5);
			cur.agregarEdicion("Talleres-2018", cal3, cal4, 20, "sourceEd");
			assertTrue(cur.mostrarEdicionVigente().getNombre()=="Talleres-2019");
			assertTrue(cur.mostrarEdicionVigente().getFechaInicio()==cal);
			assertTrue(cur.mostrarEdicionVigente().getFechaFin()==cal2);
			assertTrue(cur.mostrarEdicionVigente().getCupo()==20);
			assertTrue(cur.mostrarEdicionVigente().getSource()=="sourceEd");
			Calendar cal5= Calendar.getInstance();
			cal5.add(Calendar.YEAR, 1);
			Calendar cal6= Calendar.getInstance();
			cal6.add(Calendar.YEAR, 1);
			cal6.add(Calendar.DATE, 5);
			cur.agregarEdicion("Talleres-2020", cal5, cal6, 20, "sourceEd");
			assertTrue(cur.mostrarEdicionVigente().getNombre()=="Talleres-2020");
			assertTrue(cur.mostrarEdicionVigente().getFechaInicio()==cal5);
			assertTrue(cur.mostrarEdicionVigente().getFechaFin()==cal6);
			assertTrue(cur.mostrarEdicionVigente().getCupo()==20);
			assertTrue(cur.mostrarEdicionVigente().getSource()=="sourceEd");
		});	
	}
	
	@Test 
	void darDatos() {
		assertThrows(EdicionNoExisteException.class, ()->cur.darDatosEdicion("sadasfasdfds"));
		assertAll(()->{
			cur.agregarEdicion("Talleres-2019", cal, cal2, 20, "sourceEd");
			Calendar cal3= Calendar.getInstance();
			cal3.add(Calendar.YEAR, -1);
			Calendar cal4= Calendar.getInstance();
			cal4.add(Calendar.YEAR, -1);
			cal4.add(Calendar.DATE, 5);
			cur.agregarEdicion("Talleres-2018", cal3, cal4, 20, "sourceEd");
			assertThrows(EdicionNoExisteException.class, ()->cur.darDatosEdicion("sadasfasdfds"));
			assertEquals("Talleres-2019", cur.darDatosEdicion("Talleres-2019").getNombre());
			assertEquals("Talleres-2018", cur.darDatosEdicion("Talleres-2018").getNombre());
		});
	}
	
	@Test
	void inscribirEstudiante() {
		assertThrows(EdicionNoExisteException.class, ()->cur.inscribirEstudiante("Robertito", "fsdfsdf", Calendar.getInstance(), ""));
		cur.agregarEdicion("Talleres-2019", cal, cal2, 20, "sourceEd");
		assertThrows(UsuarioNoExisteException.class,()->cur.inscribirEstudiante("Robertito", "Talleres-2019", Calendar.getInstance(), ""));
		assertAll(()->{
			cur.inscribirEstudiante("eleven11", "Talleres-2019", Calendar.getInstance(), "");
			ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
			DtEstudiante dtEstudiante = (DtEstudiante) mUsuario.informacionDeUsuario("eleven11");
			assertTrue(dtEstudiante.getEdiciones().contains("Talleres-2019"));
		});
		
	}
	
	
	@Test
	void testVigencia() {
		assertAll(()->{
			ArrayList<String> docentes = new ArrayList<String>();
			ArrayList<String> previas = new ArrayList<String>();
			Factory factory = new Factory();
			InterfazControladorCurso icc = factory.getInterfazControladorCurso();
			icc.ingresarInstituto("Instituto1");
			icc.altaCurso("Instituto1", "curso1", "asdasd", "8 horas", 4, 2, "sdfsdfsa", previas);
			
			// Edición 1
			Calendar inicio = Calendar.getInstance();
			inicio.set(2010, Calendar.OCTOBER, 15);
			Calendar fin = Calendar.getInstance();
			fin.set(2030, Calendar.OCTOBER, 15);
			try {
				boolean alta = icc.altaEdicionCurso("edicion1", inicio, fin, 0, "curso1", docentes, "Instituto1");
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals("edicion1", icc.mostrarEdicionVigenteDeCurso("curso1").getNombre());
			
			// Edición 2
			inicio = Calendar.getInstance();
			inicio.set(2010, Calendar.OCTOBER, 16);
			fin = Calendar.getInstance();
			fin.set(2030, Calendar.OCTOBER, 15);
			try {
				boolean alta = icc.altaEdicionCurso("edicion2", inicio, fin, 0, "curso1", docentes, "Instituto1");
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals("edicion2", icc.mostrarEdicionVigenteDeCurso("curso1").getNombre());
			
			// Edición 3
			inicio = Calendar.getInstance();
			inicio.set(2010, Calendar.OCTOBER, 17);
			fin = Calendar.getInstance();
			fin.set(2030, Calendar.OCTOBER, 15);
			try {
				boolean alta = icc.altaEdicionCurso("edicion3", inicio, fin, 0, "curso1", docentes, "Instituto1");
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals("edicion3", icc.mostrarEdicionVigenteDeCurso("curso1").getNombre());
			
			// Edición 4
			inicio = Calendar.getInstance();
			inicio.set(2010, Calendar.OCTOBER, 15);
			fin = Calendar.getInstance();
			fin.set(2030, Calendar.OCTOBER, 15);
			try {
				boolean alta = icc.altaEdicionCurso("edicion4", inicio, fin, 0, "curso1", docentes, "Instituto1");
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals("edicion3", icc.mostrarEdicionVigenteDeCurso("curso1").getNombre());
			
			// Edición 5
			inicio = Calendar.getInstance();
			inicio.set(2010, Calendar.OCTOBER, 19);
			fin = Calendar.getInstance();
			fin.set(2017, Calendar.OCTOBER, 15);
			try {
				boolean alta = icc.altaEdicionCurso("edicion5", inicio, fin, 0, "curso1", docentes, "Instituto1");
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals("edicion3", icc.mostrarEdicionVigenteDeCurso("curso1").getNombre());
			
		});
	}

}
