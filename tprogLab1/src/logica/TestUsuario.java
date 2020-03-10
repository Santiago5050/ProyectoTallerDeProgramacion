package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestUsuario {

	@BeforeEach
	void setUp() throws Exception {
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
	void testPassword() {
		Calendar fechaNacimiento1 = Calendar.getInstance();
		fechaNacimiento1.set(2000, 6, 1);
		Usuario user = new Estudiante("nick1", "nombre", "apellido", "mail", fechaNacimiento1, "nsow8b11", "");
		assertEquals("nsow8b11", user.getPassword());
		
		Usuario user2 = new Docente("nick1", "nombre", "apellido", "mail", fechaNacimiento1, "INCO", " w9 23(&$ e#@@", "");
		assertEquals(" w9 23(&$ e#@@", user2.getPassword());

		Usuario user3 = new Estudiante("nick1", "nombre", "apellido", "mail", fechaNacimiento1, "w92%/. ,24 ", "");
		assertEquals("w92%/. ,24 ", user3.getPassword());
	}

}
