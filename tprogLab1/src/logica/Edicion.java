package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import excepciones.EstudianteNoInscriptoException;

public class Edicion {
	private String nombre;
	private Calendar fechaInicio;
	private Calendar fechaFin;
	private int cupo;
	private Calendar fechaPublicacion;
	private boolean vigencia;
	private String source;
	private boolean cerrada;
	private List<DtComentario> comentarios;
	 
	public Edicion(String nombre, Calendar fechaInicio, Calendar fechaFin, int cupo, boolean vigencia, String source) {
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.cupo = cupo;
		this.fechaPublicacion = Calendar.getInstance();
		this.vigencia = vigencia;
		this.source = source;
		this.cerrada = false;
		this.comentarios = new ArrayList<DtComentario>();
	}
	
	public DtEdicion getDtEdicion() {
		return new DtEdicion(this.nombre, this.fechaInicio, this.fechaFin, this.cupo, this.fechaPublicacion, this.vigencia, this.source, this.cerrada);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Calendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Calendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Calendar getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Calendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getCupo() {
		return cupo;
	}

	public void setCupo(int cupo) {
		this.cupo = cupo;
	}

	public Calendar getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Calendar fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	
	public boolean getVigencia() {
		return vigencia;
	}
	
	public void setVigencia(boolean vigencia) {
		this.vigencia = vigencia;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public void setCerrada(boolean cerrada) {
		this.cerrada = cerrada;
	}
	
	public boolean getCerrada() {
		return this.cerrada;
	}
	
	public void cerrarEdicion() {
		this.cerrada = true;
	}
	
	public void agregarComentario(DtComentario nuevo) {
		this.comentarios.add(nuevo);
	}
	
	public List<DtComentario> getComentarios() {
		return this.comentarios;
	}
	
	public void agregarComentario(String nickname, String nombre, String apellido, String comentario, Calendar fechaComentario) {
		String index = "" + this.comentarios.size();
		DtComentario nuevo = new DtComentario(index, nickname, nombre, apellido, comentario, fechaComentario);
		this.comentarios.add(nuevo);
	}
	
	public void responderComentario(String id, String nickname, String nombre, String apellido, String comentario, Calendar fechaComentario) throws EstudianteNoInscriptoException {
		List<DtComentario> actuaList = this.comentarios;
		int iter = 0;
		String nuevoIndex = "";
		while(iter < id.length()) {
			int index = Integer.parseInt(id.substring(iter, iter + 1));
			DtComentario auxDtComentario = actuaList.get(index);
			if (auxDtComentario != null) {
				actuaList = auxDtComentario.getComentariosRespuesta();
			} else {
				throw new EstudianteNoInscriptoException("El id del comentario es incorrecto");
			}
			nuevoIndex = nuevoIndex + index;
			iter++;
		}
		nuevoIndex = nuevoIndex + actuaList.size();
		DtComentario auxDtComentario = new DtComentario(nuevoIndex, nickname, nombre, apellido, comentario, fechaComentario);
		actuaList.add(auxDtComentario);
	}
	
}
