package logica;


import java.util.Calendar;
import java.util.Map;
import java.util.List;

import excepciones.CursoNoExisteException;
import excepciones.DocenteNoAsignadoAEdicionException;
import excepciones.EdicionNoExisteException;
import excepciones.EstudianteComoDocenteException;
import excepciones.EstudianteNoInscriptoException;
import excepciones.EstudianteYaInscripto;
import excepciones.NotaErroneaException;
import excepciones.ProgramaNoExisteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;

public interface InterfazControladorUsuario {
	public boolean altaEstudianteSrc(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String password, String src);
	public boolean altaDocenteSrc(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String instituto, String password, String src);
	public boolean altaEstudiante(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String password);
	public boolean altaDocente(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String instituto, String password);
	public DtUsuario informacionDeUsuario(String nickname) throws UsuarioNoExisteException;
	public DtProgramaFormacionEstudiante consultaProgramaEstudiante(String nickname, String programa) throws UsuarioNoExisteException, ProgramaNoExisteException;
	public DtEdicion consultaEdicion(String nickname, String edicion) throws UsuarioNoExisteException, EdicionNoExisteException;
	
	public List<String> listarUsuarios() throws UsuarioNoExisteException;
	public void modificarDatosUsuario(String nickname, String nombre, String apellido, Calendar fecha) throws UsuarioNoExisteException;
	public List<String> listarInstitutos();
	public void inscribirEstudiantesPorUnDocente(String nickDocente, String nomEdicion, Map<String, Boolean> eleccion) throws UsuarioNoExisteException, DocenteNoAsignadoAEdicionException, EstudianteNoInscriptoException, EstudianteYaInscripto;
	public void inscribirEstudiantePrograma(String nickname, String nomPrograma, Calendar fechaIns) throws ProgramaNoExisteException, UsuarioNoExisteException, EstudianteComoDocenteException, EstudianteYaInscripto;
	public boolean validarDatosInicioSesion(String nick, String pass) throws UsuarioNoExisteException;
	public int obtenerCantidadInscripcionesRechazadas(String curso, String nickEstudiante) throws EstudianteComoDocenteException, UsuarioNoExisteException, EdicionNoExisteException, CursoNoExisteException;
	public Map<String, DtEdicion> getEdicionesEstudiante(String nickname) throws UsuarioNoExisteException;
	public Map<String, DtDocente> getDocentesDeEdicion(String nickname);
	public void seguirUsuario(String usuarioSigue, String usuarioASeguir) throws UsuarioNoExisteException, UsuarioYaExisteException;
	public void ingresarResultadosDeEvaluaciones(String nickDocente, String nomEdicion, String nickEstudiante, int nota) throws UsuarioNoExisteException, DocenteNoAsignadoAEdicionException, EstudianteNoInscriptoException, NotaErroneaException;
	public boolean existeNickname(String nickname);
	public boolean existeMail(String mail);
	public DtCertificado getCertificado(String nick,String nomPrograma) throws ProgramaNoExisteException, CursoNoExisteException, EdicionNoExisteException, UsuarioNoExisteException;
}
