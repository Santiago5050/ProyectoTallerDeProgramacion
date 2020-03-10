package logica;

import java.util.Calendar;

public class EstudiantePrograma {
	private Calendar inscripcion;
	private ProgramaFormacion programaFormacion;
	
	public EstudiantePrograma(Calendar inscripcion, ProgramaFormacion programaFormacion) {
		this.inscripcion = inscripcion;
		this.programaFormacion = programaFormacion;
	}
	
	public String getNombre() {
		return this.programaFormacion.getNombre();
	}
	
	public DtProgramaFormacionEstudiante getDtProgramaFormacion() {
		DtProgramaFormacion dtPrograma = this.programaFormacion.getDtProgramaFormacion();
		return new DtProgramaFormacionEstudiante(dtPrograma.getNombre(), dtPrograma.getDescripcion(), dtPrograma.getFechaInicio(), dtPrograma.getFechaFin(), dtPrograma.getFechaAlta(), dtPrograma.getCursos(), dtPrograma.getCategorias(),  this.inscripcion, dtPrograma.getSource());
	}
}
