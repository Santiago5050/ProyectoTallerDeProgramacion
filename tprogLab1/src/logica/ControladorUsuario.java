package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import excepciones.EdicionNoExisteException;
import excepciones.EstudianteComoDocenteException;
import excepciones.EstudianteYaInscripto;
import excepciones.ProgramaNoExisteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.EstudianteNoInscriptoException;
import excepciones.CursoNoExisteException;
import excepciones.DocenteNoAsignadoAEdicionException;
import excepciones.NotaErroneaException;

public class ControladorUsuario implements InterfazControladorUsuario {
	
	public boolean altaEstudianteSrc(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String password, String src) {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.crearEstudiante(nickname, nombre, apellido, mail, fechaNacimiento, password, src);
	}
	
	public boolean altaDocenteSrc(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String instituto, String password, String src) {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.crearDocente(nickname, nombre, apellido, mail, fechaNacimiento, instituto, password, src);
	}
	
	public boolean altaEstudiante(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String password) {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.crearEstudiante(nickname, nombre, apellido, mail, fechaNacimiento, password, "");
	}
	
	public boolean altaDocente(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String instituto, String password) {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.crearDocente(nickname, nombre, apellido, mail, fechaNacimiento, instituto, password, "");
	}
	
	public DtUsuario informacionDeUsuario(String nickname) throws UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.informacionDeUsuario(nickname);
	}
	
	public DtProgramaFormacionEstudiante consultaProgramaEstudiante(String nickname, String programa) throws UsuarioNoExisteException, ProgramaNoExisteException{
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.consultaProgramaEstudiante(nickname, programa);
	}
	
	public DtEdicion consultaEdicion(String nickname, String edicion) throws UsuarioNoExisteException, EdicionNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.consultaEdicion(nickname, edicion);
	}
	
	public void asignarEdicionDocentes(List<String> docentes, Edicion edicion) throws EstudianteComoDocenteException, UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		for (int i = 0; i < docentes.size(); i++) {
			Docente auxDocente = mUsuario.getDocente(docentes.get(i));
			auxDocente.asignarEdicion(edicion);
		}
	}
	
	public List<String> listarUsuarios() throws UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.getListaDeUsuarios();
	}
	
	public List<DtUsuario> listarDtUsuarios() throws UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.getDtUsuarios();
	}
	
	public void modificarDatosUsuario(String nickname, String nombre, String apellido, Calendar fecha) throws UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		mUsuario.modificarDatosUsuario(nickname, nombre, apellido, fecha);
	}
	
	public void modificarDatosUsuarioSource(String nickname, String nombre, String apellido, Calendar fecha, String source) throws UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		mUsuario.modificarDatosUsuarioSource(nickname, nombre, apellido, fecha, source);
	}
	
	public List<String> listarInstitutos(){
		ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
		return mInstituto.getListaDeInstitutos();
	}
	
	public boolean hayDocenteDeOtroInstituto(List<String> docentes, String instituto) throws EstudianteComoDocenteException, UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		for (int i = 0; i < docentes.size(); i++) {
			if (!mUsuario.getDocente(docentes.get(i)).getInstituto().equals(instituto)) {
				return true;				
			}
		}
		return false;
	}
	
	public void seguirUsuario(String usuarioSigue, String usuarioASeguir) throws UsuarioNoExisteException, UsuarioYaExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		mUsuario.seguirUsuario(usuarioSigue, usuarioASeguir);
	}
	
	public void dejarDeSeguirUsuario(String usuarioSigue, String usuarioSeguido) throws UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		mUsuario.dejarDeSeguirUsuario(usuarioSigue, usuarioSeguido);
	}
	
	public void inscribirEstudiantePrograma(String nickname, String nomPrograma, Calendar fechaIns) throws ProgramaNoExisteException, UsuarioNoExisteException, EstudianteComoDocenteException, EstudianteYaInscripto {
		ManejadorProgramaFormacion mpf = ManejadorProgramaFormacion.getInstance();
		ProgramaFormacion auxPf = mpf.getProgramaFormacion(nomPrograma);
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		mUsuario.inscribirEstudiantePrograma(nickname, auxPf, fechaIns);
	}
	
	public boolean validarDatosInicioSesion(String nick, String pass) throws UsuarioNoExisteException {
		ManejadorUsuario mUsuario= ManejadorUsuario.getInstance();
		return mUsuario.validarPassword(nick, pass);
	}
	
	public void inscribirEstudiantesPorUnDocente(String nickDocente, String nomEdicion, Map<String, Boolean> eleccion) throws UsuarioNoExisteException, DocenteNoAsignadoAEdicionException, EstudianteNoInscriptoException, EstudianteYaInscripto {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		mUsuario.inscribirEstudiantesPorUnDocente(nickDocente, nomEdicion, eleccion);
	}
	
	public void ingresarResultadosDeEvaluaciones(String nickDocente, String nomEdicion, String nickEstudiante, int nota) throws UsuarioNoExisteException, DocenteNoAsignadoAEdicionException, EstudianteNoInscriptoException, NotaErroneaException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		Calendar fechaEvaluacion = Calendar.getInstance();
		mUsuario.ingresarResultadosDeEvaluaciones(nickDocente, nomEdicion, nickEstudiante, nota, fechaEvaluacion);
	}
	
	public void ingresarResultadosDeEvaluacionesFecha(String nickDocente, String nomEdicion, String nickEstudiante, int nota, Calendar fechaEvaluacion) throws UsuarioNoExisteException, DocenteNoAsignadoAEdicionException, EstudianteNoInscriptoException, NotaErroneaException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		mUsuario.ingresarResultadosDeEvaluaciones(nickDocente, nomEdicion, nickEstudiante, nota, fechaEvaluacion);
	}
	
	public int obtenerCantidadInscripcionesRechazadas(String curso, String nickEstudiante) throws EstudianteComoDocenteException, UsuarioNoExisteException, EdicionNoExisteException, CursoNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		Estudiante auxEstudiante = mUsuario.getEstudiante(nickEstudiante);
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		return mCurso.getCantidadInscripcionesRechazadas(curso, auxEstudiante);
	}
	
	public Map<String, DtEdicion> getEdicionesEstudiante(String nickname) throws UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.getEdicionesEstudiante(nickname);
	}
	
	public Map<String, DtDocente> getDocentesDeEdicion(String edicion) {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.getDocentesDeEdicion(edicion);
	}
	
	public List<DtDocente> listarDocentesInstituto(String nom_instituo) throws UsuarioNoExisteException {
		ArrayList<DtDocente> listaDocentes = new ArrayList<DtDocente>();
		List<DtUsuario> listaUsuarios = this.listarDtUsuarios();
		for (DtUsuario dtUsuario : listaUsuarios) {
			if (dtUsuario instanceof DtDocente && ((DtDocente) dtUsuario).getInstituto().equals(nom_instituo)) {
				listaDocentes.add((DtDocente) dtUsuario);
			}
		}
		return listaDocentes;
	}
	
	public boolean existeNickname(String nickname) {
		ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstance();
		return manejadorUsuario.existeNickname(nickname);
	}
	
	public boolean existeMail(String mail) {
		ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstance();
		return manejadorUsuario.existeMail(mail);
	}
	
	public DtCertificado getCertificado(String nick,String nomPrograma) throws ProgramaNoExisteException, CursoNoExisteException, EdicionNoExisteException, UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		return mUsuario.getCertificado(nick, nomPrograma);
	}
}
