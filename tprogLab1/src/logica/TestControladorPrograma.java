package logica;

import static org.junit.jupiter.api.Assertions.*;
import excepciones.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import excepciones.ProgramaNoExisteException;

class TestControladorPrograma {
	private ControladorProgramaFormacion cProgramaFormacion;
	
	@BeforeEach
	void setUp() {
		cProgramaFormacion = new ControladorProgramaFormacion();
	}
	
	@AfterEach
	void tearDown() throws Exception {
		cProgramaFormacion = null;
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
	void crearPrograma() {
		assertAll(()-> {
			//Ingreso de datos necesarios
			ManejadorProgramaFormacion mpf = ManejadorProgramaFormacion.getInstance();
			Calendar fechaIni = Calendar.getInstance();
			Calendar fechaFin = Calendar.getInstance();
			fechaFin.set(5, 6, 2020);
			//Pruebas
			assertTrue(cProgramaFormacion.crearPrograma("programa1", "esto es un programa de prueba", fechaIni, fechaFin));
			ProgramaFormacion cargadoPf = mpf.getProgramaFormacion("programa1");
			assertEquals("programa1", cargadoPf.getNombre());
			DtProgramaFormacion dtPf = cProgramaFormacion.mostrarProgramaFormacion("programa1");
			assertEquals("esto es un programa de prueba", dtPf.getDescripcion());
			assertEquals(fechaIni.toString(), dtPf.getFechaInicio().toString());
			assertEquals(fechaFin.toString(), dtPf.getFechaFin().toString());
			assertNotNull(dtPf.getFechaAlta());
			assertEquals("programa1", dtPf.getNombre());
			assertFalse(cProgramaFormacion.crearPrograma("programa1", "esto es un programa de prueba", fechaIni, fechaFin));
			assertEquals("", dtPf.getSource());
		});
	}
	
	@Test
	void crearProgramaSource() {
		assertAll(()-> {
			//Ingreso de datos necesarios
			ManejadorProgramaFormacion mpf = ManejadorProgramaFormacion.getInstance();
			Calendar fechaIni = Calendar.getInstance();
			Calendar fechaFin = Calendar.getInstance();
			fechaFin.set(5, 6, 2020);
			//Pruebas
			assertTrue(cProgramaFormacion.crearProgramaSource("programa1", "esto es un programa de prueba", fechaIni, fechaFin, "source1"));
			ProgramaFormacion cargadoPf = mpf.getProgramaFormacion("programa1");
			assertEquals("programa1", cargadoPf.getNombre());
			DtProgramaFormacion dtPf = cProgramaFormacion.mostrarProgramaFormacion("programa1");
			assertEquals("esto es un programa de prueba", dtPf.getDescripcion());
			assertEquals(fechaIni.toString(), dtPf.getFechaInicio().toString());
			assertEquals(fechaFin.toString(), dtPf.getFechaFin().toString());
			assertNotNull(dtPf.getFechaAlta());
			assertEquals("programa1", dtPf.getNombre());
			assertFalse(cProgramaFormacion.crearPrograma("programa1", "esto es un programa de prueba", fechaIni, fechaFin));
			assertEquals("source1", dtPf.getSource());
		});
	}
	
	@Test
	void listarProgramasFormacion() {
		assertAll(()->{
			//Ingreso de Datos necesarios
			ManejadorProgramaFormacion mpf = ManejadorProgramaFormacion.getInstance();
			Calendar fechaIni = Calendar.getInstance();
			Calendar fechaFin = Calendar.getInstance();
			fechaFin.set(5, 6, 2020);
			cProgramaFormacion.crearPrograma("programa1", "esto es un programa de prueba 1", fechaIni, fechaFin);
			cProgramaFormacion.crearPrograma("programa2", "esto es un programa de prueba 2", fechaIni, fechaFin);
			cProgramaFormacion.crearPrograma("programa3", "esto es un programa de prueba 3", fechaIni, fechaFin);
			cProgramaFormacion.crearPrograma("programa4", "esto es un programa de prueba 4", fechaIni, fechaFin);
			//Pruebas
			List<String> programas = cProgramaFormacion.listarProgramasFormacion();
			assertTrue(programas.contains("programa1"));
			assertTrue(programas.contains("programa2"));
			assertTrue(programas.contains("programa3"));
			assertTrue(programas.contains("programa4"));
		});
	}
	
	@Test
	void listarDtProgramasFormacion() {
		assertAll(()->{
			//Ingreso de Datos necesarios
			Calendar fechaIni = Calendar.getInstance();
			Calendar fechaFin = Calendar.getInstance();
			fechaFin.set(5, 6, 2020);
			cProgramaFormacion.crearPrograma("programa1", "esto es un programa de prueba 1", fechaIni, fechaFin);
			cProgramaFormacion.crearPrograma("programa2", "esto es un programa de prueba 2", fechaIni, fechaFin);
			cProgramaFormacion.crearPrograma("programa3", "esto es un programa de prueba 3", fechaIni, fechaFin);
			cProgramaFormacion.crearPrograma("programa4", "esto es un programa de prueba 4", fechaIni, fechaFin);
			//Pruebas
			try {
				Map<String, DtProgramaFormacion> programas = cProgramaFormacion.listarDtProgramasFormacion();
				assertTrue(programas.containsKey("programa1"));
				assertTrue(programas.containsKey("programa2"));
				assertTrue(programas.containsKey("programa3"));
				assertTrue(programas.containsKey("programa4"));
			} catch (ProgramaNoExisteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		});
	}
	
	
	@Test
	void agregarCurso() {
		assertAll(()->{
			//Ingreso de datos necesarios
			ManejadorProgramaFormacion mpf = ManejadorProgramaFormacion.getInstance();
			Calendar fechaIni = Calendar.getInstance();
			Calendar fechaFin = Calendar.getInstance();
			fechaFin.set(5, 6, 2020);
			cProgramaFormacion.crearPrograma("programa1", "esto es un programa de prueba 1", fechaIni, fechaFin);
			ControladorCurso cCurso = new ControladorCurso();
			ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
			mInstituto.crearInstituto("INCO");
			cCurso.altaCurso("INCO", "programacion1", "Introduccion a la programacion", "1 semestre", 15, 10, "eva.fing.com.uy/p1", new ArrayList<String>());
			//Pruebas
			assertTrue(cProgramaFormacion.agregarCursoAPrograma("programa1", "programacion1"));
			Map<String, DtCurso> cursosMap = cProgramaFormacion.listarCursos();
			assertEquals("programacion1", cursosMap.get("programacion1").getNombre());
			DtProgramaFormacion dtpf = mpf.getDtProgramaFormacion("programa1");
			List<String> cursosObtenidos = dtpf.getCursos();
			assertEquals("programacion1", cursosObtenidos.get(0));
		});
	}
	
	@Test
	void agregarCursoErrores() throws Exception{
		//Cargo datos necesarios
		ManejadorProgramaFormacion mpf = ManejadorProgramaFormacion.getInstance();
		Calendar fechaIni = Calendar.getInstance();
		Calendar fechaFin = Calendar.getInstance();
		fechaFin.set(5, 6, 2020);
		ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
		mInstituto.crearInstituto("INCO");
		cProgramaFormacion.crearPrograma("programa1", "esto es un programa de prueba 1", fechaIni, fechaFin);
		ControladorCurso cCurso = new ControladorCurso();
		cCurso.altaCurso("INCO", "programacion1", "Introduccion a la programacion", "1 semestre", 15, 10, "eva.fing.com.uy/p1", new ArrayList<String>());
		//Agregar curso no existente en el sistema
		assertThrows(CursoNoExisteException.class, ()->{cProgramaFormacion.agregarCursoAPrograma("programa1", "cursoNoExistente");});
		//Agregar curso a programa no Existente
		assertThrows(ProgramaNoExisteException.class, ()->{cProgramaFormacion.agregarCursoAPrograma("programaNoExistente", "programacion1");});
		//Pedir Dt de un programa no existente
		assertThrows(ProgramaNoExisteException.class, ()->{mpf.getDtProgramaFormacion("programaNoExistente");});
	}
	
	@Test
	public void existePrograma() {
		assertAll(() -> {
			ManejadorProgramaFormacion mpf = ManejadorProgramaFormacion.getInstance();
			Calendar fechaIni = Calendar.getInstance();
			Calendar fechaFin = Calendar.getInstance();
			fechaFin.set(2030, 6, 1);
			
			assertTrue(cProgramaFormacion.crearPrograma("programa1", "esto es un programa de prueba", fechaIni, fechaFin));
			
			assertTrue(cProgramaFormacion.existePrograma("programa1"));
			assertFalse(cProgramaFormacion.existePrograma("asd"));
		});
	}

}
