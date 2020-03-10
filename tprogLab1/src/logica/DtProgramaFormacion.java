package logica;


import java.util.Calendar;
import java.util.List;

public class DtProgramaFormacion {
	private String nombre;
	private String descripcion;
	private Calendar fechaInicio;
	private Calendar fechaFin;
	private Calendar fechaAlta;
	private String src;
	private List<String> cursos;
	private List<String> categorias;
	
	public DtProgramaFormacion(String nombre, String descripcion, Calendar fechaInicio, Calendar fechaFin, Calendar fechaAlta, List<String> cursos, List<String> categorias, String source) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fechaAlta = fechaAlta;
		this.src = source;
		this.cursos = cursos;
		this.categorias= categorias;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public Calendar getFechaInicio() {
		return this.fechaInicio;
	}
	
	public Calendar getFechaFin() {
		return this.fechaFin;
	}
	
	public Calendar getFechaAlta() {
		return this.fechaAlta;
	}
	
	public List<String> getCursos() {
		return this.cursos;
	}
	
	public List<String> getCategorias() {
		return categorias;
	}
	
	public String getSource() {
		return this.src;
	}
}
