package logica;


import java.util.Calendar;
import java.util.List;

public class DtProgramaFormacionEstudiante extends DtProgramaFormacion {
	private Calendar inscripcion;
	
	public Calendar getInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(Calendar inscripcion) {
		this.inscripcion = inscripcion;
	}

	public DtProgramaFormacionEstudiante(String nombre, String descripcion, Calendar fechaInicio, Calendar fechaFin, Calendar fechaAlta, List<String> cursos, List<String> categorias, Calendar inscripcion, String source) {
		super(nombre, descripcion, fechaInicio, fechaFin, fechaAlta, cursos, categorias, source);
		this.inscripcion = inscripcion;
	}
}
