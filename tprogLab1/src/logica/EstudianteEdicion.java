package logica;

import java.util.Calendar;

import excepciones.NotaErroneaException;

public class EstudianteEdicion {
	private Calendar inscripcion;
	private Edicion edicion;
	private Estado estado;
	private String video;
	
	public EstudianteEdicion(Calendar inscripcion, Edicion edicion, String video, EnumEstado estado) {
		// TODO Auto-generated constructor stub
		this.inscripcion= inscripcion;
		this.edicion= edicion;
		this.video = video;
		if (estado==EnumEstado.ACEPTADA)
			this.estado = new Aceptada();
		else if (estado==EnumEstado.INSCRIPTO)
			this.estado = new Inscripto();
	}
	
	public String getNombre() {
		return this.edicion.getNombre();
	}
	
	public DtEdicionEstudiante getDtEdicion() {
		DtEdicion dtEdicion = this.edicion.getDtEdicion();
		return new DtEdicionEstudiante(dtEdicion.getNombre(), dtEdicion.getFechaInicio(), dtEdicion.getFechaFin(), dtEdicion.getCupo(), dtEdicion.getFechaPublicacion(), dtEdicion.getVigencia(), dtEdicion.getSource(), this.inscripcion, this.estado.getEstado(), dtEdicion.getCerrada(), this.getVideo());
	}
	
	public void cambiarEstado(boolean aceptada) {
		Estado nuevoEstado = this.estado.cambiarEstado(aceptada);
		if (nuevoEstado != null) {
			this.estado = nuevoEstado;
		}
	}
	
	public EnumEstado getEstado() {
		return this.estado.getEstado();
	}

	public Calendar getInscripcion() {
		return inscripcion;
	}
	
	public void agregarNota(int nota, Calendar fechaEvaluacion) throws NotaErroneaException {
		if (!this.edicion.getCerrada()) {
			if (this.estado instanceof Aceptada) {
				((Aceptada) this.estado).agregarNota(nota, fechaEvaluacion);
			}
		} else {
			throw new NotaErroneaException("La edicion esta cerrada");
		}
	}
	
	public int getNota() {
		return ((Aceptada) this.estado).getNota();
	}
	
	public void setVideo(String video) {
		this.video = video;
	}
	
	public String getVideo() {
		return this.video;
	}
	
	public Calendar getFechaPublicacion() {
		return ((Aceptada) this.estado).getFechaEvaluacion();
	}
	
}
