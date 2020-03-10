package logica;

import java.util.Calendar;

public class DtEdicion {
	private String nombre;
	private Calendar fechaInicio;
	private Calendar fechaFin;
	private int cupo;
	private Calendar fechaPublicacion;
	private boolean vigencia;
	private String src;
	private boolean cerrada;
	
	public DtEdicion(String nombre, Calendar fechaInicio, Calendar fechaFin, int cupo, Calendar fechaPublicacion, boolean vigencia, String source, boolean cerrada) {
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.cupo = cupo;
		this.fechaPublicacion = fechaPublicacion;
		this.vigencia = vigencia;
		this.src = source;
		this.cerrada = cerrada;
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
		return src;
	}

	public void setSource(String source) {
		this.src = source;
	}
	
	public boolean getCerrada() {
		return this.cerrada;
	}
	
	public void setCerrada(boolean cerrada) {
		this.cerrada = cerrada;
	}
}
