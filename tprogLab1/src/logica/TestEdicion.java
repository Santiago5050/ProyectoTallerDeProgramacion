package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestEdicion {
	private Edicion test1;
	private Calendar inicio;
	private Calendar fin;
	private Calendar cambio;
	private Calendar resultado;
	private DtEdicion dtPrueba;
	private DtEdicion dtResultado;

	@BeforeEach
	void setUp() throws Exception {
		inicio = Calendar.getInstance();
		inicio.set(2000, Calendar.JULY, 12);
		fin = Calendar.getInstance();
		fin.set(2019, Calendar.MARCH, 5);
		test1 = new Edicion("Edición de test 1", inicio, fin, 10, false, "sourceEd");
	}

	@AfterEach
	void tearDown() throws Exception {
		test1 = null;
		inicio = null;
		fin = null;
		cambio = null;
		resultado = null;
		dtPrueba = null;
		dtResultado = null;
	}

	@Test
	void testGetSetNombre() {
		test1.setNombre("cambie nombre");
		assertEquals("cambie nombre", test1.getNombre());
	}
	
	@Test
	void testGetSetFechaInicio() {
		// Hago cambio.
		cambio = Calendar.getInstance();
		cambio.set(1993, Calendar.JANUARY, 5);
		test1.setFechaInicio(cambio);
		
		// Resultado esperado.
		resultado = Calendar.getInstance();
		resultado.set(1993, Calendar.JANUARY, 5);
		
		assertEquals(resultado.get(Calendar.DAY_OF_MONTH), test1.getFechaInicio().get(Calendar.DAY_OF_MONTH));
		assertEquals(resultado.get(Calendar.MONTH), test1.getFechaInicio().get(Calendar.MONTH));
		assertEquals(resultado.get(Calendar.YEAR), test1.getFechaInicio().get(Calendar.YEAR));
	}
	
	@Test
	void testGetSetFechaFin() {
		// Hago cambio.
		cambio = Calendar.getInstance();
		cambio.set(1995, Calendar.NOVEMBER, 11);
		test1.setFechaFin(cambio);
				
		// Resultado esperado.
		resultado = Calendar.getInstance();
		resultado.set(1995, Calendar.NOVEMBER, 11);
				
		assertEquals(resultado.get(Calendar.DAY_OF_MONTH), test1.getFechaFin().get(Calendar.DAY_OF_MONTH));
		assertEquals(resultado.get(Calendar.MONTH), test1.getFechaFin().get(Calendar.MONTH));
		assertEquals(resultado.get(Calendar.YEAR), test1.getFechaFin().get(Calendar.YEAR));
	}
	
	@Test
	void testGetSetCupo() {
		test1.setCupo(5);
		assertEquals(5, test1.getCupo());
	}
	
	@Test
	void testSetFechaPublicacion() {
		cambio = Calendar.getInstance();
		cambio.set(2010, Calendar.OCTOBER, 15);
		test1.setFechaPublicacion(cambio);
		assertEquals(cambio.get(Calendar.DAY_OF_MONTH), test1.getFechaPublicacion().get(Calendar.DAY_OF_MONTH));
		assertEquals(cambio.get(Calendar.MONTH), test1.getFechaPublicacion().get(Calendar.MONTH));
		assertEquals(cambio.get(Calendar.YEAR), test1.getFechaPublicacion().get(Calendar.YEAR));
	}
	
	@Test
	void testVigencia1() {
		test1.setVigencia(false);
		assertEquals(false, test1.getVigencia());
	}
	
	@Test
	void testGetSetSource() {
		test1.setSource("nuevoSource");
		assertEquals("nuevoSource", test1.getSource());
	}
	
	@Test
	void testGetDtEdicion() {
		dtPrueba = test1.getDtEdicion();
		dtResultado = new DtEdicion(test1.getNombre(), test1.getFechaInicio(), test1.getFechaFin(), test1.getCupo(), test1.getFechaPublicacion(), test1.getVigencia(), test1.getSource(), test1.getCerrada());
		assertEquals(dtPrueba.getNombre(), dtResultado.getNombre());
		assertEquals(dtPrueba.getCupo(), dtResultado.getCupo());
		assertEquals(dtPrueba.getFechaInicio(), dtResultado.getFechaInicio());
		assertEquals(dtPrueba.getFechaFin(), dtResultado.getFechaFin());
		assertEquals(dtPrueba.getFechaPublicacion(), dtResultado.getFechaPublicacion());
		assertEquals(dtPrueba.getVigencia(), dtResultado.getVigencia());
		assertEquals(dtPrueba.getSource(), dtResultado.getSource());
	}

}
