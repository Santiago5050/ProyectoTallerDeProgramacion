package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.List;





public class DtEstudiante extends DtUsuario {
	private Map<String, DtFechaEstadoNota> estadoEdiciones;
	private List<String> programas;
//	private List<String> sigue;

	
	public DtEstudiante(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, Map<String, DtFechaEstadoNota> ediciones, List<String> programas, List<String> sigue, String source) {
		super(nickname, nombre, apellido, mail, fechaNacimiento, sigue, source);
//		this.sigue = sigue;
		this.programas = programas;
		this.estadoEdiciones =ediciones;
		
	}


	public Map<String, DtFechaEstadoNota> getEstadoEdiciones() {
		return estadoEdiciones;
	}
	
	public List<String> getEdiciones(){
		ArrayList<String> strings = new ArrayList<String>();
		estadoEdiciones.forEach((k, v)->{
			strings.add(k);
		});
		return strings;
	}

//	public List<String> getSigue() {
//		return sigue;
//	}

	public List<String> getProgramas() {
		return this.programas;
	}
	
	public void test() {
		
	}
}
