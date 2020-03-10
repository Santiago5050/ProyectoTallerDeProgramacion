package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.util.List;

import excepciones.CategoriaNoExisteException;
import excepciones.CursoNoExisteException;
import excepciones.EdicionNoExisteException;
import excepciones.EstudianteYaInscripto;
import excepciones.UsuarioNoExisteException;

public class ManejadorCurso {
	private Map<String, Curso> cursos;
	private static ManejadorCurso instance = null;
	
	private ManejadorCurso() {
		this.cursos = new HashMap<String, Curso>();
	}
	
	public static ManejadorCurso getInstance() {
		if (instance == null) {
			instance = new ManejadorCurso();
		}
		return instance;
	}
	
	public DtCurso getDtCurso(String nomCruso) throws CursoNoExisteException {
			
		if (cursos.containsKey(nomCruso)) {
				Curso curso = cursos.get(nomCruso);
				return curso.getDtCurso();
		}else {
			throw new CursoNoExisteException("El curso no existe!");
		}
	}
	
	public DtEdicion darDatosEdicion(String nomCurso, String nomEdicion) throws CursoNoExisteException, EdicionNoExisteException {
		
		if (cursos.containsKey(nomCurso)) {
			Curso curso = cursos.get(nomCurso);
			return curso.darDatosEdicion(nomEdicion);
		}else {
			throw new CursoNoExisteException("El curso no existe!");
		}
	}
	
	public List<String> mostrarEdicionesDeCurso(String curso) throws CursoNoExisteException, EdicionNoExisteException {
		List<String> ediciones = new ArrayList<String>();
		if (this.cursos.containsKey(curso)) {
			Curso cur = cursos.get(curso);
			ediciones = cur.getListaEdiciones();
		} else {
			throw new CursoNoExisteException("No hay cursos en el sistema");
		}
		return ediciones;
	}
	
	public DtEdicion mostrarEdicionVigenteDeCurso(String curso) throws CursoNoExisteException, EdicionNoExisteException {
		if (this.cursos.containsKey(curso)) {
			Curso cur = cursos.get(curso);
			DtEdicion dte = cur.mostrarEdicionVigente(); 
			return dte;
		} else {
			throw new CursoNoExisteException("No hay cursos en el sistema");
		}
	}
	
	public Map<String, DtCurso> getColDtCurso() {
		Map<String, DtCurso> listaDtCursos = new HashMap<String, DtCurso>();
		this.cursos.forEach((k, v)-> {
			listaDtCursos.put(k, v.getDtCurso());
		});
		return listaDtCursos;
	}
	
	public Curso getCurso(String nombre) throws CursoNoExisteException {
		Curso auxCurso = this.cursos.get(nombre);
		if (auxCurso == null)
			throw new CursoNoExisteException("El curso ingresado no existe");
		return auxCurso;
	}
	
	public Curso crearCurso(String nombre, String descripcion, String duracion, int cantHoras, int creditos, String url, List<String> previas, String source) throws CursoNoExisteException {
		boolean existeCurso = this.cursos.containsKey(nombre);
		if (!existeCurso) {
			Map<String, Curso> auxPrevias = new HashMap<String, Curso>();
			for (int i = 0; i < previas.size(); i++) {
				String nombrePrevia = previas.get(i);
				if (this.cursos.get(nombrePrevia) == null)
					throw new CursoNoExisteException("La previa ingresada no existe");
				auxPrevias.put(nombrePrevia, this.cursos.get(nombrePrevia));
			}
			Curso nuevoCurso = new Curso(nombre, descripcion, duracion, cantHoras, creditos, url, auxPrevias, source);
			this.cursos.put(nombre, nuevoCurso);
			return nuevoCurso;
		} else {
			return null;
		}
	}
	
	public boolean existeEdicion(String nombre) {
		boolean existe = false;
		for (Map.Entry<String, Curso> entry : this.cursos.entrySet()) {
			existe = existe || entry.getValue().tieneEdicion(nombre);
		}
		return existe;
	}
	
	public void inscribirEstudianteACurso(String nickname, String curso, String edicion, Calendar fecha, String video) throws CursoNoExisteException, EdicionNoExisteException, UsuarioNoExisteException, EstudianteYaInscripto {
		Curso cur = cursos.get(curso);
		
		if (cur != null) {
			cur.inscribirEstudiante(nickname, edicion, fecha, video);
		} else {
			throw new CursoNoExisteException("El curso no existe");
		}
	}
	
	public void agregarCategoriasACurso(String curso, List<String> categorias) throws CursoNoExisteException, CategoriaNoExisteException {
		if (this.cursos.containsKey(curso)) {
			Curso auxCurso = this.cursos.get(curso);
			auxCurso.agregarCategorias(categorias);
		} else {
			throw new CursoNoExisteException("El curso no existe");
		}
	}
	
	public Map<String, DtEdicion> listarDtEdicion(String nomCurso) throws CursoNoExisteException{
		if (cursos.containsKey(nomCurso)) {
			Curso curso = cursos.get(nomCurso);
			return curso.getDtEdicion();
		}
		throw new CursoNoExisteException(nomCurso + "no se encuentra en el sistema.");
	}

	public int getCantidadInscripcionesRechazadas(String curso, Estudiante est) throws EdicionNoExisteException, CursoNoExisteException {
		int cantidadRechazadas = 0;
		Curso auxCurso = this.cursos.get(curso);
		if (curso != null) {
			List<String> auxEdiciones = auxCurso.getListaEdiciones();
			for (int i = 0; i < auxEdiciones.size(); i++) {
				EstudianteEdicion auxEstudianteEdicion = est.getEstudianteEdicion(auxEdiciones.get(i));
				if (auxEstudianteEdicion != null && auxEstudianteEdicion.getEstado() == EnumEstado.RECHAZADA) {
					cantidadRechazadas++;
				}
			}
		} else {
			throw new CursoNoExisteException("El curso ingresado no existe");
		}
		return cantidadRechazadas;
	}
	
	public Map<String, DtCurso> getlistaDtCurso(List<String> nomCursos){
		Map<String, DtCurso> map = new HashMap<String, DtCurso>();
		for (int i = 0; i < nomCursos.size(); i++) {
			map.put(nomCursos.get(i), cursos.get(nomCursos.get(i)).getDtCurso());
		}
		return map;
		
	}
	
	public boolean cupoLleno(String nomEdicion, int cantAceptados) {
		for (Map.Entry<String, Curso> curso : this.cursos.entrySet()) {
			Map<String, DtEdicion> listaEdiciones = curso.getValue().getDtEdicion();
			for (Map.Entry<String, DtEdicion> edicion : listaEdiciones.entrySet()) {
				if (edicion.getKey().equals(nomEdicion)) {
					return cantAceptados >= edicion.getValue().getCupo();
				}
			}
			
		}
		return false;
	}
	
	public void cerrarEdicion(String curso, String edicion) throws CursoNoExisteException, EdicionNoExisteException {
		Curso cursoEdicion = this.cursos.get(curso);
		if (curso != null) {
			cursoEdicion.cerrarEdicion(edicion);
		} else {
			throw new CursoNoExisteException("No existe el curso");
		}
	}
	
	public void valorarUnCurso(String nomCurso, int rat,String nick) throws CursoNoExisteException, UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		if(cursos.containsKey(nomCurso)) {
			cursos.get(nomCurso).valorarCurso(rat, mUsuario.getEdicionesAceptadasEstudiante(nick),nick);
		}else {
			throw new CursoNoExisteException("El curso " + nomCurso + "no exise. ");
		}
	}
	
	public boolean existeCurso(String curso) {
		return (this.cursos.get(curso) != null);
	}
	
	public int cursoVisitado(String nomCurso) throws CursoNoExisteException {
		if(cursos.containsKey(nomCurso)) {
			return cursos.get(nomCurso).visitado();
		}else {
			throw new CursoNoExisteException("El Curso no existe");
		}
	}
	
	public boolean edicionCerrada(String nomCurso, String nomEdicion) {
		if(cursos.containsKey(nomCurso)) {
			Curso curso=cursos.get(nomCurso);
			Edicion eds=curso.getEdicion(nomEdicion);
			return eds.getCerrada();
		}else {
			return false;
		}
	}
	
	public Map<String, Integer> getValoracionesCurso(String curso) throws CursoNoExisteException {
		if (this.cursos.containsKey(curso)) {
			Curso auxCurso = this.cursos.get(curso);
			return auxCurso.getValoracionesCurso();
		} else {
			throw new CursoNoExisteException("El curso no existe");
		}
	}
	
}
