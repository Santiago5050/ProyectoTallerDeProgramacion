package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import excepciones.InstitutoNoExisteException;
import excepciones.InstitutoYaExisteException;
import excepciones.CursoNoExisteException;
import excepciones.CursoYaExisteException;

public class ManejadorInstituto {
	private Map<String, Instituto> institutos;
	private static ManejadorInstituto instance = null;
	
	private ManejadorInstituto() {
		this.institutos = new HashMap<String, Instituto>();
	}
	
	public static ManejadorInstituto getInstance() {
		if (instance == null) {
			instance = new ManejadorInstituto();
		}
		return instance;
	}
	
	public void crearInstituto(String instituto) throws InstitutoYaExisteException {
		if (institutos.containsKey(instituto)) {
			throw new InstitutoYaExisteException("El instituto " + instituto + " ya existe.");
		}else {
			Instituto ins = new Instituto(instituto);
			institutos.put(instituto, ins);
		}
	}
	
	public List<String> mostrarCursosEnInstituto(String instituto) throws InstitutoNoExisteException, CursoNoExisteException {
		List<String> cursos = new ArrayList<String>();
		Instituto inst = institutos.get(instituto);
		if (inst != null) {
			cursos = inst.getListaCursos();
		} else {
			throw new InstitutoNoExisteException("El instituto no existe");
		}
		return cursos;
	}
	
	public List<String> getListaDeInstitutos() {
		ArrayList<String> listaInstitutos = new ArrayList<String>();
		if (!this.institutos.isEmpty()) {
			this.institutos.forEach((k, v) -> {
				listaInstitutos.add(v.getNombre());
			});
		}
		return listaInstitutos;
	}
	
	public void agregarCurso(String instituto, String nombre, String descripcion, String duracion, int cantHoras, int creditos, String url, List<String> previas, String source) throws CursoYaExisteException, InstitutoNoExisteException, CursoNoExisteException {
		if (this.institutos.get(instituto) == null)
			throw new InstitutoNoExisteException("El instituto ingresado no existe");
		Instituto auxInstituto = this.institutos.get(instituto);
		auxInstituto.agregarCurso(nombre, descripcion, duracion, cantHoras, creditos, url, previas, source);
	}
	
	public Instituto getInstituto(String nombre) {
		return this.institutos.get(nombre);
	}
	
	public boolean tieneCurso(String instituto, String curso) throws InstitutoNoExisteException {
		if (this.institutos.get(instituto) == null)
			throw new InstitutoNoExisteException("El instituto ingresado no existe");
		return this.getInstituto(instituto).tieneCurso(curso);
	}
	
	public String getInstitutoCurso(String curso) {
		for (Map.Entry<String, Instituto> ins : institutos.entrySet()) {
			if (ins.getValue().tieneCurso(curso))
				return ins.getValue().getNombre();
		}
		return null;
	}

}
