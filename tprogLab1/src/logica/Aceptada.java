package logica;

import java.util.Calendar;

public class Aceptada extends Estado {
	private int nota;
	private Calendar fechaEvaluacion;
	
	public Aceptada() {
		this.nota = -1;
	}
	
	@Override
	public Estado cambiarEstado(boolean aceptada) {
		return null;
	}
	
	public EnumEstado getEstado() {
		return EnumEstado.ACEPTADA;
	}
	
	void agregarNota(int nota, Calendar fechaEvaluacion) {
		this.nota = nota;
		this.fechaEvaluacion = fechaEvaluacion;
	}
	
	public int getNota() {
		return nota;
	}
	
	public Calendar getFechaEvaluacion() {
		return fechaEvaluacion;
	}
}
