package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CargaDatosTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
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
	void test() {
		String error = "";
		CargaDatos cargaDatos = new CargaDatos();
		
		
			try {
				cargaDatos.cargar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		assertEquals("", error);
	}

}
