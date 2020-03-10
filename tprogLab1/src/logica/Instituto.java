package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import excepciones.CursoNoExisteException;
import excepciones.CursoYaExisteException;

public class Instituto {
	private String nombre;
	private Map<String, Curso> cursos;
	
	public Instituto(String nombre) {
		this.nombre = nombre;
		this.cursos = new HashMap<String, Curso>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<String> getListaCursos() throws CursoNoExisteException {
		ArrayList<String> cursosArrayList = new ArrayList<String>();
		if (!this.cursos.isEmpty()) {
			cursos.forEach((k, v)-> cursosArrayList.add(v.getNombre()));
		} else {
			throw new CursoNoExisteException("No hay cursos");
		}
		return cursosArrayList;

	}
	
	public void agregarCurso(String nombre, String descripcion, String duracion, int cantHoras, int creditos, String url, List<String> previas, String source) throws CursoYaExisteException, CursoNoExisteException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		Curso auxCurso = mCurso.crearCurso(nombre, descripcion, duracion, cantHoras, creditos, url, previas, source);
		if (auxCurso != null)
			this.cursos.put(nombre, auxCurso);
		else {
			throw new CursoYaExisteException("Ya existe un curso con el nombre " + nombre);
		}
	}
	
	public Map<String, DtCurso> getDtCursos() {
		Map<String, DtCurso> listaDtCursos = new HashMap<String, DtCurso>();
		this.cursos.forEach((k, v)-> {
			listaDtCursos.put(k, v.getDtCurso());
		});
		return listaDtCursos;
	}
	
	public boolean tieneCurso(String curso) {
		return this.cursos.get(curso) != null;
	}
	
	public Curso getCurso(String nombre) {
		return this.cursos.get(nombre);
	}
}
