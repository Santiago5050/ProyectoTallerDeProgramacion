package logica;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.List;



import excepciones.CursoYaExisteException;
import excepciones.DocenteNoPerteneceMismoInstitutoException;
import excepciones.EdicionNoExisteException;
import excepciones.EstudianteComoDocenteException;
import excepciones.EstudianteNoInscriptoException;
import excepciones.EstudianteYaInscripto;
import excepciones.InstitutoNoBrindaCursoException;
import excepciones.InstitutoNoExisteException;
import excepciones.InstitutoYaExisteException;
import excepciones.CategoriaNoExisteException;
import excepciones.CategoriaYaExisteException;
import excepciones.CursoNoExisteException;
import excepciones.UsuarioNoExisteException;

public class ControladorCurso implements InterfazControladorCurso {
	
	public DtCurso mostrarCurso(String nomCurso)  throws CursoNoExisteException{
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		return mCurso.getDtCurso(nomCurso);
	}
	
	public void ingresarInstituto(String instituto) throws InstitutoYaExisteException {
		ManejadorInstituto manejadorInstituto = ManejadorInstituto.getInstance();
		manejadorInstituto.crearInstituto(instituto);
	}
	
	public Map<String, DtCurso> listarCursos() {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		Map<String, DtCurso> listaDtCursos = mCurso.getColDtCurso();
		return listaDtCursos;
	}
	
	public void altaCurso(String instituto, String nombre, String descripcion, String duracion, int cantHoras, int creditos, String url, List<String> previas) throws CursoYaExisteException, InstitutoNoExisteException, CursoNoExisteException {
		ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
		mInstituto.agregarCurso(instituto,  nombre,  descripcion,  duracion,  cantHoras,  creditos,  url, previas, "");
	}
	
	public void altaCursoSrc(String instituto, String nombre, String descripcion, String duracion, int cantHoras, int creditos, String url, List<String> previas, String src) throws CursoYaExisteException, InstitutoNoExisteException, CursoNoExisteException {
		ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
		mInstituto.agregarCurso(instituto,  nombre,  descripcion,  duracion,  cantHoras,  creditos,  url, previas, src);
	}
	
	public List<String> listarInstitutos() {
		ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
		return mInstituto.getListaDeInstitutos();
	}

	public List<String> mostrarCursosEnInstituto(String instituto) throws InstitutoNoExisteException, CursoNoExisteException {
		ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
		return mInstituto.mostrarCursosEnInstituto(instituto);
	}
	
	public List<String> mostrarEdicionesDeCurso(String curso) throws CursoNoExisteException, EdicionNoExisteException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		return mCurso.mostrarEdicionesDeCurso(curso);
	}
	
	public DtEdicion darDatosEdicion(String curso, String edicion) throws CursoNoExisteException, EdicionNoExisteException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		return mCurso.darDatosEdicion(curso, edicion);
	}
	
	public DtEdicion mostrarEdicionVigenteDeCurso(String curso) throws CursoNoExisteException, EdicionNoExisteException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		return mCurso.mostrarEdicionVigenteDeCurso(curso);
	}
	public Map<String, DtCurso> listarCursosAsociados(String nomInstituto) {
		ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
		Instituto auxInstituto = mInstituto.getInstituto(nomInstituto);
		Map<String, DtCurso> listaDtCursos = auxInstituto.getDtCursos();
		return listaDtCursos;
	}
	
	public Map<String, DtDocente> listarDocentes() {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		Map<String, DtDocente> listaDtDocentesMap = mUsuario.getColDtDocente();
		return listaDtDocentesMap;
	}
	
	public boolean altaEdicionCurso(String nombre, Calendar fechaIni, Calendar fechaFin, int cupo, String curso, List<String> docentes, String instituto) throws DocenteNoPerteneceMismoInstitutoException, EstudianteComoDocenteException, CursoNoExisteException, UsuarioNoExisteException, InstitutoNoExisteException, InstitutoNoBrindaCursoException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		Curso auxCurso = mCurso.getCurso(curso);
		if (curso != null) {
			//Controlo que el instituto ingresado contenga al curso
			ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
			boolean controlInstituto = mInstituto.tieneCurso(instituto, curso);
			if (controlInstituto) {
				//Controlo que los docentes ingresados sean del mismo instituto que el curso
				ControladorUsuario cUsuario = new ControladorUsuario();
				boolean control =  cUsuario.hayDocenteDeOtroInstituto(docentes, instituto);
				if (control) {
					throw new DocenteNoPerteneceMismoInstitutoException("Uno de los docentes ingresados no pertenece al instituto que brinda el curso " + curso);
				}
			
				//Controlo que no exista la edicion en el sistema
				boolean existeEdicion = mCurso.existeEdicion(nombre);
				if (!existeEdicion) {
					Edicion auxEdicion = auxCurso.agregarEdicion(nombre, fechaIni, fechaFin, cupo, "");
					//De ocurrir algun error en el alta de edicion, no entra al if
					if (auxEdicion != null) {
						cUsuario.asignarEdicionDocentes(docentes, auxEdicion);
						return true;
					}
				}
			} else {
				throw new InstitutoNoBrindaCursoException("El curso ingresado no es brindado por el instituto seleccionado");
			}
		} else {
			throw new CursoNoExisteException("El curso ingresado no existe");
		}
		return false;
	}
	
	public boolean altaEdicionCursoSrc(String nombre, Calendar fechaIni, Calendar fechaFin, int cupo, String curso, List<String> docentes, String instituto, String source) throws DocenteNoPerteneceMismoInstitutoException, EstudianteComoDocenteException, CursoNoExisteException, UsuarioNoExisteException, InstitutoNoExisteException, InstitutoNoBrindaCursoException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		Curso auxCurso = mCurso.getCurso(curso);
		if (curso != null) {
			//Controlo que el instituto ingresado contenga al curso
			ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
			boolean controlInstituto = mInstituto.tieneCurso(instituto, curso);
			if (controlInstituto) {
				//Controlo que los docentes ingresados sean del mismo instituto que el curso
				ControladorUsuario cUsuario = new ControladorUsuario();
				boolean control =  cUsuario.hayDocenteDeOtroInstituto(docentes, instituto);
				if (control) {
					throw new DocenteNoPerteneceMismoInstitutoException("Uno de los docentes ingresados no pertenece al instituto que brinda el curso " + curso);
				}
			
				//Controlo que no exista la edicion en el sistema
				boolean existeEdicion = mCurso.existeEdicion(nombre);
				if (!existeEdicion) {
					Edicion auxEdicion = auxCurso.agregarEdicion(nombre, fechaIni, fechaFin, cupo, source);
					//De ocurrir algun error en el alta de edicion, no entra al if
					if (auxEdicion != null) {
						cUsuario.asignarEdicionDocentes(docentes, auxEdicion);
						return true;
					}
				}
			} else {
				throw new InstitutoNoBrindaCursoException("El curso ingresado no es brindado por el instituto seleccionado");
			}
		} else {
			throw new CursoNoExisteException("El curso ingresado no existe");
		}
		return false;
	}
	
	public List<String> getListaDeEstudiantes() throws UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.getListaDeEstudiantes();
	}
	
	public void inscribirEstudianteACurso(String nickname, String curso, String edicion, Calendar fecha, String video) throws CursoNoExisteException, EdicionNoExisteException, UsuarioNoExisteException, EstudianteYaInscripto {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		mCurso.inscribirEstudianteACurso(nickname, curso, edicion, fecha, video);
	}
	
	public void desistirAInscripcionDeCurso(String nickname, String edicion) throws EdicionNoExisteException, UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		mUsuario.desistirAInscripcionDeCurso(nickname, edicion);
	}
	
	public void altaCategoria(String nom) throws CategoriaYaExisteException {
		ManejadorCategoria mCurso = ManejadorCategoria.getInstance();
		mCurso.agregarCategoria(nom);
	}
	
	public List<String> getListaCategorias() {
		ManejadorCategoria mCurso = ManejadorCategoria.getInstance();
		return mCurso.getListaCategorias();
	}
	
	public void agregarCategoriasACurso(String curso, List<String> categorias) throws CursoNoExisteException, CategoriaNoExisteException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		mCurso.agregarCategoriasACurso(curso, categorias);
	}
	
	public Map<String, DtEdicion> listarDtEdiciones(String nomCurso) throws CursoNoExisteException{
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		return mCurso.listarDtEdicion(nomCurso);
	}
	
	public Map<String, DtEstudiante> listarEstudianteEdicion(String nomEdicion){
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.getEstudiantes(nomEdicion);
	}
	
	public Map<String, DtCurso> listarDtCursos(List<String> nomCursos){
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		return mCurso.getlistaDtCurso(nomCursos);
	}
	
	public DtCurso getDtCursoDeEdicion(String nomEdicion) {
		Map<String, DtCurso> listaCursos = this.listarCursos();
		for (Map.Entry<String, DtCurso> dt : listaCursos.entrySet()) {
			List<String> ediciones = dt.getValue().getEdiciones();
			if (ediciones.contains(nomEdicion)) {
				return dt.getValue();
			}
		}
		return null;
	}
	
	public void cerrarEdicion(String edicion, String docente) throws CursoNoExisteException, EdicionNoExisteException, UsuarioNoExisteException {
		// Verificar que el docente dicta la edición.
		ControladorUsuario controladorUsuario = new ControladorUsuario();
		Map<String, DtDocente> docentesDeEdicion = controladorUsuario.getDocentesDeEdicion(edicion);
		DtDocente docenteEdicion = docentesDeEdicion.get(docente);
		if (docenteEdicion != null) {
			// Buscar el curso asociado a la edición.
			ControladorCurso controladorCurso = new ControladorCurso();
			DtCurso cursoDeEdicion = controladorCurso.getDtCursoDeEdicion(edicion);
			if (cursoDeEdicion != null) {
				ManejadorCurso manejadorCurso = ManejadorCurso.getInstance();
				manejadorCurso.cerrarEdicion(cursoDeEdicion.getNombre(), edicion);
			}
		} else {
			throw new UsuarioNoExisteException("El docente no dicta la edicion");
		}
	}
	
	public void valorarUnCurso(String nomCurso,int rat,String nick) throws CursoNoExisteException, UsuarioNoExisteException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		mCurso.valorarUnCurso(nomCurso, rat,nick);
	}
	
	public boolean existeCurso(String curso) {
		ManejadorCurso manejadorCurso = ManejadorCurso.getInstance();
		return manejadorCurso.existeCurso(curso);
	}
	
	public boolean existeEdicion(String edicion) {
		ControladorCurso controladorCurso = new ControladorCurso();
		DtCurso cursoDeEdicion = controladorCurso.getDtCursoDeEdicion(edicion);
		return (cursoDeEdicion != null);
	}
	
	public int cursoVisitado(String nomCurso) throws CursoNoExisteException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		return mCurso.cursoVisitado(nomCurso);
	}
	
	public List<DtComentario> getComentariosEdicion(String curso, String edicion) throws CursoNoExisteException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		Curso auxCurso = mCurso.getCurso(curso);
		Edicion auxEdicionEdicion = auxCurso.getEdicion(edicion);
		List<DtComentario> auxComentarios = auxEdicionEdicion.getComentarios();
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		Map<String, DtEstudiante> auxColEstudiantes = mUsuario.getEstudiantes(edicion);
		Map<String, Integer> auxColValoraciones = mCurso.getValoracionesCurso(curso);
		recurcionSrcDtComentario(auxColValoraciones, auxColEstudiantes, auxComentarios);
		return auxComentarios;
	}
	
	//Funcion auxiliar para ingresar src	
	private void recurcionSrcDtComentario(Map<String, Integer> listaValoraciones, Map<String, DtEstudiante> listaEstudiantes, List<DtComentario> comentariosRespuesta) {
		for (DtComentario dtComentario : comentariosRespuesta) {
			if(dtComentario.getComentariosRespuesta().size() != 0) {
				recurcionSrcDtComentario(listaValoraciones, listaEstudiantes, dtComentario.getComentariosRespuesta());
			}
			dtComentario.setSrc(listaEstudiantes.get(dtComentario.getNickname()).getSource());
			Integer valoracion = listaValoraciones.get(dtComentario.getNickname());
			if (valoracion != null) {
				dtComentario.setValoracion(valoracion);
			} else {
				dtComentario.setValoracion(-1);
			}
		}
	}
	
	public void agregarComentario(String curso, String edicion, String nickname, String nombre, String apellido, String comentario, Calendar fechaComentario) throws CursoNoExisteException, EstudianteNoInscriptoException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		Curso auxCurso = mCurso.getCurso(curso);
		Edicion auxEdicion = auxCurso.getEdicion(edicion);
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		Map<String, DtEstudiante> colEstudiantes = mUsuario.getEstudiantes(edicion);
		if (colEstudiantes.containsKey(nickname)) {
			DtEstudiante auxDtEstudiante = colEstudiantes.get(nickname);
			DtFechaEstadoNota auxEstadoNota = auxDtEstudiante.getEstadoEdiciones().get(edicion);
			if (auxEstadoNota.getEstado() == EnumEstado.ACEPTADA) {
				auxEdicion.agregarComentario(nickname, nombre, apellido, comentario, fechaComentario);
			} else {
				throw new EstudianteNoInscriptoException("El estudiante no ha sido aceptado en la edicion");
			}
		} else {
			throw new EstudianteNoInscriptoException("El estudiante no esta en la edicion");
		}
	}
	
	public void responderComentario(String curso, String edicion, String id, String nickname, String nombre, String apellido, String comentario, Calendar fechaComentario) throws CursoNoExisteException, EstudianteNoInscriptoException {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		Curso auxCurso = mCurso.getCurso(curso);
		Edicion auxEdicion = auxCurso.getEdicion(edicion);
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		Map<String, DtEstudiante> colEstudiantes = mUsuario.getEstudiantes(edicion);
		if (colEstudiantes.containsKey(nickname)) {
			DtEstudiante auxDtEstudiante = colEstudiantes.get(nickname);
			DtFechaEstadoNota auxEstadoNota = auxDtEstudiante.getEstadoEdiciones().get(edicion);
			if (auxEstadoNota.getEstado() == EnumEstado.ACEPTADA) {
				auxEdicion.responderComentario(id, nickname, nombre, apellido, comentario, fechaComentario);
			} else {
				throw new EstudianteNoInscriptoException("El estudiante no ha sido aceptado en la edicion");
			}
		} else {
			throw new EstudianteNoInscriptoException("El estudiante no esta en la edicion");
		}
	}
}
