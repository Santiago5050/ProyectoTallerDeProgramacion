package logica;


import java.util.Calendar;
import java.util.Map;
import java.util.List;

import excepciones.CategoriaNoExisteException;
import excepciones.CategoriaYaExisteException;
import excepciones.CursoNoExisteException;
import excepciones.CursoYaExisteException;
import excepciones.DocenteNoPerteneceMismoInstitutoException;
import excepciones.EdicionNoExisteException;
import excepciones.EstudianteComoDocenteException;
import excepciones.EstudianteYaInscripto;
import excepciones.InstitutoNoBrindaCursoException;
import excepciones.InstitutoNoExisteException;
import excepciones.InstitutoYaExisteException;
import excepciones.ProgramaNoExisteException;
import excepciones.UsuarioNoExisteException;

public interface InterfazControladorCurso {
	public DtCurso mostrarCurso(String nomCurso) throws CursoNoExisteException;
	public void ingresarInstituto(String instituto) throws InstitutoYaExisteException;
	public Map<String, DtCurso> listarCursos();
	public void altaCurso(String instituto, String nombre, String descripcion, String duracion, int cantHoras, int creditos, String url, List<String> previas) throws CursoYaExisteException, InstitutoNoExisteException, CursoNoExisteException;
	public void altaCursoSrc(String instituto, String nombre, String descripcion, String duracion, int cantHoras, int creditos, String url, List<String> previas, String src) throws CursoYaExisteException, InstitutoNoExisteException, CursoNoExisteException;
	public List<String> listarInstitutos();
	public List<String> mostrarCursosEnInstituto(String instituto) throws InstitutoNoExisteException, CursoNoExisteException;
	public List<String> mostrarEdicionesDeCurso(String curso) throws CursoNoExisteException, EdicionNoExisteException;
	public DtEdicion darDatosEdicion(String curso, String edicion) throws CursoNoExisteException, EdicionNoExisteException;
	public DtEdicion mostrarEdicionVigenteDeCurso(String curso) throws CursoNoExisteException, EdicionNoExisteException;
	public Map<String, DtCurso> listarCursosAsociados(String nomInstituto);
	public Map<String, DtDocente> listarDocentes();
	public boolean altaEdicionCurso(String nombre, Calendar fechaIni, Calendar fechaFin, int cupo, String curso, List<String> docentes, String instituto) throws DocenteNoPerteneceMismoInstitutoException, EstudianteComoDocenteException, CursoNoExisteException, UsuarioNoExisteException, InstitutoNoExisteException, InstitutoNoBrindaCursoException;
	public List<String> getListaDeEstudiantes() throws UsuarioNoExisteException;
	public void inscribirEstudianteACurso(String nickname, String curso, String edicion, Calendar fecha, String video) throws CursoNoExisteException, EdicionNoExisteException, UsuarioNoExisteException, EstudianteYaInscripto;
	public void altaCategoria(String nom) throws CategoriaYaExisteException;
	public List<String> getListaCategorias();
	public void agregarCategoriasACurso(String curso, List<String> categorias) throws CursoNoExisteException, CategoriaNoExisteException;
	public Map<String, DtEdicion> listarDtEdiciones(String nomCurso) throws CursoNoExisteException;
	public Map<String, DtEstudiante> listarEstudianteEdicion(String nomEdicion);
	public boolean altaEdicionCursoSrc(String nombre, Calendar fechaIni, Calendar fechaFin, int cupo, String curso, List<String> docentes, String instituto, String source) throws DocenteNoPerteneceMismoInstitutoException, EstudianteComoDocenteException, CursoNoExisteException, UsuarioNoExisteException, InstitutoNoExisteException, InstitutoNoBrindaCursoException;
	public Map<String, DtCurso> listarDtCursos(List<String> nomCursos);
	public DtCurso getDtCursoDeEdicion(String nomEdicion);
	public void desistirAInscripcionDeCurso(String nickname, String edicion) throws EdicionNoExisteException, UsuarioNoExisteException;
	public void cerrarEdicion(String edicion, String docente) throws CursoNoExisteException, EdicionNoExisteException, UsuarioNoExisteException;
	public void valorarUnCurso(String nomCurso,int rat,String nick) throws CursoNoExisteException, UsuarioNoExisteException;
	public boolean existeCurso(String curso);
	public boolean existeEdicion(String edicion);
	public int cursoVisitado(String nomCurso) throws CursoNoExisteException;
	
}
