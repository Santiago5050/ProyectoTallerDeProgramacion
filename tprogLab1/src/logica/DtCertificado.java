package logica;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DtCertificado {
	private Map<String, Calendar> edicionesAprobadas;
	private String nomPrograma;
	private List<String> cursos;
	private Calendar fechaAprobacion;
	private String nombre;
	private String apellido;
	
	public DtCertificado(String nomPrograma,Map<String,Calendar> eds,List<String> cursos,Calendar f, String nom, String ape) {
		this.edicionesAprobadas=eds;
		this.nomPrograma=nomPrograma;
		this.cursos=cursos;
		this.fechaAprobacion=f;
		this.nombre = nom;
		this.apellido = ape;
	}

	public Map<String, Calendar> getEdicionesAprobadas() {
		return edicionesAprobadas;
	}

	public String getNomPrograma() {
		return nomPrograma;
	}

	public List<String> getCursos() {
		return cursos;
	}

	public Calendar getFechaAprobacion() {
		return fechaAprobacion;
	}
	
	

}
