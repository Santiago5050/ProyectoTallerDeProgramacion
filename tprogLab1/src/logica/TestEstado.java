package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestEstado {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		assertAll(()-> {
			Estado nuevoEstado = new Inscripto();
			assertEquals(EnumEstado.INSCRIPTO, nuevoEstado.getEstado());
			nuevoEstado = nuevoEstado.cambiarEstado(true);
			assertEquals(EnumEstado.ACEPTADA, nuevoEstado.getEstado());
			nuevoEstado = nuevoEstado.cambiarEstado(true);
			assertEquals(null, nuevoEstado);
			
			Estado nuevoEstado2 = new Inscripto();
			assertEquals(EnumEstado.INSCRIPTO, nuevoEstado2.getEstado());
			nuevoEstado2 = nuevoEstado2.cambiarEstado(false);
			assertEquals(EnumEstado.RECHAZADA, nuevoEstado2.getEstado());
			nuevoEstado2 = nuevoEstado2.cambiarEstado(true);
			assertEquals(null, nuevoEstado2);
		});		
	}
	
	@Test
	void test2() {
		assertAll(()-> {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			Edicion edic = new Edicion("edicion1", cal1, cal2, 0, true, "asdasd");
			
			EstudianteEdicion eEdic = new EstudianteEdicion(cal1, edic, "", EnumEstado.INSCRIPTO);
			assertEquals(EnumEstado.INSCRIPTO, eEdic.getEstado());
			eEdic.cambiarEstado(true);
			assertEquals(EnumEstado.ACEPTADA, eEdic.getEstado());
			
			EstudianteEdicion ee2 = new EstudianteEdicion(cal1, edic, "", EnumEstado.INSCRIPTO);
			assertEquals(EnumEstado.INSCRIPTO, ee2.getEstado());
			ee2.cambiarEstado(false);
			assertEquals(EnumEstado.RECHAZADA, ee2.getEstado());
		});
	}

}
