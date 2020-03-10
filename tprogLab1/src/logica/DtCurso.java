package logica;


import java.util.Calendar;
import java.util.List;

public class DtCurso {
	
	private String nombre;
	private String descripcion;
	private String duracion;
	private int cantidadHoras;
	private Calendar fechaDeRegistro;
	private String url;
	private int creditos;
	private List<String> previas;
	private List<String> ediciones;
	private List<String> programas;
	private List<String> categorias;
	private String src;
	private String instituto;
	private int[] rating;
	private int visitas;
	
	public DtCurso(String nombre, String descripcion, String duracion, int cantidadHoras, Calendar fechaDeRegistro,
			String url, int creditos, List<String> previas, List<String> ediciones,
			List<String> programas, List<String> categorias, String source, String insti, int[] rat,int v) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.duracion = duracion;
		this.cantidadHoras = cantidadHoras;
		this.fechaDeRegistro = fechaDeRegistro;
		this.url = url;
		this.creditos = creditos;
		this.previas = previas;
		this.ediciones = ediciones;
		this.programas = programas;
		this.categorias = categorias;
		this.src = source;
		this.instituto = insti;
		this.rating = rat;
		this.visitas=v;
	}

	public String getNombre() {
		return nombre;
	}
	public String getInstituto() {
		return this.instituto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getDuracion() {
		return duracion;
	}

	public int getCantidadHoras() {
		return cantidadHoras;
	}

	public Calendar getFechaDeRegistro() {
		return fechaDeRegistro;
	}

	public String getUrl() {
		return url;
	}

	public int getCreditos() {
		return creditos;
	}

	public List<String> getPrevias() {
		return previas;
	}

	public List<String> getEdiciones() {
		return ediciones;
	}

	public List<String> getProgramas() {
		return programas;
	}
	
	public List<String> getCategorias() {
		return categorias;
	}
	
	public String getSource() {
		return src;
	}
	
	public int getVisitas() {
		return visitas;
	}

	



}
