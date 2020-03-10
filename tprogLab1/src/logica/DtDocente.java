package logica;

import java.util.Calendar;
import java.util.List;

public class DtDocente extends DtUsuario{
	private String instituto;
	private List<String> ediciones;
	
	public DtDocente(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String instituto, List<String> ediciones, List<String> sigue, String source) {
		super(nickname, nombre, apellido, mail, fechaNacimiento, sigue, source);
		this.instituto = instituto;
		this.ediciones = ediciones;
	}
	
	public String getInstituto() {
		return this.instituto;
	}
	
	public List<String> getEdiciones() {
		return this.ediciones;
	}
	
	public void test() {
		
	}
}
