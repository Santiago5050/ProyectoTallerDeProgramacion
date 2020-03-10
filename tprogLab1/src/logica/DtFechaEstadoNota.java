package logica;

import java.util.Calendar;

public class DtFechaEstadoNota {
	private EnumEstado estado;
	private Calendar fecha;
	private int nota;
	private String video;
	
	public DtFechaEstadoNota(EnumEstado estado, Calendar fecha, String video) {
		super();
		this.estado = estado;
		this.fecha = fecha;
		this.video = video;		
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public Calendar getFecha() {
		return fecha;
	}
	
	public int getNota() {
		return nota;
	}
	
	public String getVideo() {
		return this.video;
	}
	
	public void agregarNota(int nota) {
		if (this.estado == EnumEstado.ACEPTADA) {
			this.nota = nota;
		}
	}
	
}
