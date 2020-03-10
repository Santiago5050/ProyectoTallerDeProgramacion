package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import excepciones.CursoNoExisteException;

public class ProgramaFormacion {
	private String nombre;
	private String descripcion;
	private Calendar fechaInicio;
	private Calendar fechaFin;
	private Calendar fechaAlta;
	private String source;
	private Map<String, Curso> cursos;
	
	public ProgramaFormacion(String nombre, String descripcion, Calendar fechaInicio, Calendar fechaFin, String source) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fechaAlta = Calendar.getInstance();
		this.cursos = new HashMap<String, Curso>();
		this.source = source;
	}
	
	public DtProgramaFormacion getDtProgramaFormacion() {
		ArrayList<String> nombreCursos = new ArrayList<String>();
		ArrayList<String> categorias = new ArrayList<String>();
		if (!this.cursos.isEmpty()) {
			Set<String> tempSet = new HashSet<String>();
			for (Map.Entry<String, Curso> entry : this.cursos.entrySet()) {
				nombreCursos.add(entry.getKey());
				tempSet.addAll(entry.getValue().getCategorias());
			}
			categorias.addAll(tempSet);
		}
		
		return new DtProgramaFormacion(this.nombre, this.descripcion, this.fechaInicio, this.fechaFin, this.fechaAlta, nombreCursos, categorias, this.source);
	}
	
	public String getNombre() {
		return nombre;
	}
	public boolean tieneCurso(String nomCurso) {
		return cursos.containsKey(nomCurso);
	}
	
	public boolean addCurso(String curso) throws CursoNoExisteException {
		if (!this.cursos.containsKey(curso)) {
			ManejadorCurso mCurso = ManejadorCurso.getInstance();
			Curso auxCurso = mCurso.getCurso(curso);
			this.cursos.put(curso, auxCurso);
			return true;
		} else {
			return false;
		}
	}
}
