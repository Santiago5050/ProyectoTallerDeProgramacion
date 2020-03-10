package logica;

import java.util.Calendar;

public class DtEdicionEstudiante extends DtEdicion {
	
	private Calendar inscripcion;
	private EnumEstado estado;
	private String video;
	
	public DtEdicionEstudiante(String nombre, Calendar fechaInicio, Calendar fechaFin, int cupo, Calendar fechaPublicacion, boolean vigencia, String source, Calendar inscripcion, EnumEstado estado, boolean cerrada, String video) {
		super(nombre, fechaInicio, fechaFin, cupo, fechaPublicacion, vigencia, source, cerrada);
		this.inscripcion = inscripcion;
		this.estado = estado;
		this.video = video;
	}

	public Calendar getInscripcion() {
		return inscripcion;
	}
	
	public EnumEstado getEstado() {
		return this.estado;
	}

	public void setInscripcion(Calendar inscripcion) {
		this.inscripcion = inscripcion;
	}
	
	public String getVideo() {
		return this.video;
	}
}
