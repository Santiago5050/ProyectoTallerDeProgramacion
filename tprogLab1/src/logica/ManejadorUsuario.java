package logica;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import java.util.List;



import excepciones.EdicionNoExisteException;
import excepciones.EstudianteComoDocenteException;
import excepciones.EstudianteYaInscripto;
import excepciones.ProgramaNoExisteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import servidor.Publicador;
import excepciones.EstudianteNoInscriptoException;
import excepciones.CursoNoExisteException;
import excepciones.DocenteNoAsignadoAEdicionException;
import excepciones.NotaErroneaException;


public class ManejadorUsuario {
	private static ManejadorUsuario instance = null;
	private Map<String, Usuario> coleccionUsuarios;
	
	private ManejadorUsuario() {
		this.coleccionUsuarios = new HashMap<String, Usuario>();
	}
	
	public static ManejadorUsuario getInstance() {
		if (instance == null)
			instance = new ManejadorUsuario();
		return instance;
	}
	
	public boolean crearEstudiante(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String password, String src) {
		Usuario existeNickname = coleccionUsuarios.get(nickname);
		boolean existeMail = false;
		if (existeNickname == null) {
			if (!this.coleccionUsuarios.isEmpty()) {
				for (Map.Entry<String, Usuario> entry : this.coleccionUsuarios.entrySet()) {
					String mailGuardado = entry.getValue().getMail();
					existeMail = mailGuardado.equals(mail) || existeMail;
				}
			}
			if (!existeMail) {
				Estudiante nuevoEstudiante = new Estudiante(nickname, nombre, apellido, mail, fechaNacimiento, password, src);
				coleccionUsuarios.put(nickname, nuevoEstudiante);
			}
		}
		return existeNickname == null && (!existeMail);
	}
	
	public boolean crearDocente(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String instituto, String password, String src) {
		Usuario existeNickname = coleccionUsuarios.get(nickname);
		boolean existeMail = false;
		if (existeNickname == null) {
			if (!this.coleccionUsuarios.isEmpty()) {
				for (Map.Entry<String, Usuario> entry : this.coleccionUsuarios.entrySet()) {
					String mailGuardado = entry.getValue().getMail();
					existeMail = mailGuardado.equals(mail) || existeMail;
				}
			}
			if (!existeMail) {
				Docente nuevoDocente = new Docente(nickname, nombre, apellido, mail, fechaNacimiento, instituto, password, src);
				coleccionUsuarios.put(nickname, nuevoDocente);
			}
		}
		return existeNickname == null && !existeMail;
	}
	
	public DtUsuario informacionDeUsuario(String nickname) throws UsuarioNoExisteException{
		Usuario usuarioBuscado = this.coleccionUsuarios.get(nickname);
		if (usuarioBuscado == null) {
			throw new UsuarioNoExisteException("El usuario no existe");
		}
		return usuarioBuscado.getDtUsuario();
	}
	
	public DtProgramaFormacionEstudiante consultaProgramaEstudiante(String nickname, String programa) throws UsuarioNoExisteException, ProgramaNoExisteException {
		Usuario usuarioBuscado = this.coleccionUsuarios.get(nickname);
		if (usuarioBuscado == null) {
			throw new UsuarioNoExisteException("El usuario no existe");
		}
		if (usuarioBuscado instanceof Estudiante) {
			return ((Estudiante) usuarioBuscado).getDtProgramaFormacionEstudiante(programa);
		} else {
			throw new ProgramaNoExisteException("No hay programa disponible");
		}
	}
	
	public DtEdicion consultaEdicion(String nickname, String edicion) throws UsuarioNoExisteException, EdicionNoExisteException {
		Usuario usuarioBuscado = this.coleccionUsuarios.get(nickname);
		if (usuarioBuscado == null) {
			throw new UsuarioNoExisteException("El usuario no existe");
		}
		return usuarioBuscado.getDtEdicion(edicion);
	}
	
	public List<String> getListaDeUsuarios() throws UsuarioNoExisteException {
		ArrayList<String> usuarios = new ArrayList<String>();
		if (!this.coleccionUsuarios.isEmpty()) {
			for (Map.Entry<String, Usuario> entry : this.coleccionUsuarios.entrySet()) {
				usuarios.add(entry.getValue().getNickname());
			}
		} else {
			throw new UsuarioNoExisteException("No hay usuarios en el sistema");
		}
		return usuarios;
	}
	
	public Map<String, DtDocente> getColDtDocente() {
		Map<String, DtDocente> listaDtDocente = new HashMap<String, DtDocente>();
		this.coleccionUsuarios.forEach((k, v)-> {
			if (v instanceof Docente)
				listaDtDocente.put(k, (DtDocente) v.getDtUsuario());
		});
		return listaDtDocente;
	}
	
	public Docente getDocente(String nombre) throws EstudianteComoDocenteException, UsuarioNoExisteException {
		if (this.coleccionUsuarios.get(nombre) == null)
			throw new UsuarioNoExisteException("El docente ingresado no existe");
		if (!(this.coleccionUsuarios.get(nombre) instanceof Docente))
			throw new EstudianteComoDocenteException("Se ha seleccionado a un usuario estudiante en lugar de un docente");
		return (Docente) this.coleccionUsuarios.get(nombre);
	}
	
	public List<String> getListaDeEstudiantes() throws UsuarioNoExisteException {
		ArrayList<String> estudiantes = new ArrayList<String>();
		if (!this.coleccionUsuarios.isEmpty()) {
			for (Map.Entry<String, Usuario> entry : this.coleccionUsuarios.entrySet()) {
				if (entry.getValue() instanceof Estudiante) {
					estudiantes.add(entry.getValue().getNickname());
				}
			}
		} else {
			throw new UsuarioNoExisteException("No hay usuarios en el sistema");
		}
		return estudiantes;
	}
	
	public void inscribirEstudianteAEdicion(String nickname, Edicion edicion, Calendar fecha, String video, EnumEstado estado) throws UsuarioNoExisteException, EstudianteYaInscripto {	
		Usuario user = coleccionUsuarios.get(nickname);
		if (user != null) {
			if (user instanceof Estudiante) {
				user.inscribirAEdicion(edicion, fecha, video, estado);
			} else {
				throw new UsuarioNoExisteException("El usuario no es estudiante");
			}
		} else {
			throw new UsuarioNoExisteException("El usuario no existe");
		}
	}
	
	public void modificarDatosUsuario(String nickname, String nombre, String apellido, Calendar fecha) throws UsuarioNoExisteException {
		Usuario user = coleccionUsuarios.get(nickname);
		if (user != null) {
			user.setDatos(nombre, apellido, fecha);
		} else {
			throw new UsuarioNoExisteException("El usuario no existe");
		}
	}
	
	public void modificarDatosUsuarioSource(String nickname, String nombre, String apellido, Calendar fecha, String source) throws UsuarioNoExisteException {
		Usuario user = coleccionUsuarios.get(nickname);
		if (user != null) {
			user.setDatosSource(nombre, apellido, fecha, source);
		} else {
			throw new UsuarioNoExisteException("El usuario no existe");
		}
	}
	
	public void seguirUsuario(String usuarioSigue, String usuarioASeguir) throws UsuarioNoExisteException, UsuarioYaExisteException {
		Usuario userFollows = this.coleccionUsuarios.get(usuarioSigue);
		if (userFollows == null) {
			throw new UsuarioNoExisteException("No existe el usuario");
		} else {
			Usuario userToFollow = this.coleccionUsuarios.get(usuarioASeguir);
			if (userToFollow == null) {
				throw new UsuarioNoExisteException("No existe el usuario a seguir");
			} else {
				userFollows.seguirUsuario(usuarioASeguir, userToFollow);
			}
		}
	}
	
	public void dejarDeSeguirUsuario(String usuarioSigue, String usuarioSeguido) throws UsuarioNoExisteException {
		Usuario usuario = this.coleccionUsuarios.get(usuarioSigue);
		if (usuario == null) {
			throw new UsuarioNoExisteException("No existe el usuario");
		} else {
			usuario.dejarDeSeguirUsuario(usuarioSeguido);
		}
	}
	
	public List<DtUsuario> getDtUsuarios() throws UsuarioNoExisteException {
		ArrayList<DtUsuario> auxColUsuarios = new ArrayList<DtUsuario>();
		if (!this.coleccionUsuarios.isEmpty()) {
			for (Map.Entry<String, Usuario> entry : this.coleccionUsuarios.entrySet()) {
				auxColUsuarios.add(entry.getValue().getDtUsuario());
			}
		} else {
			throw new UsuarioNoExisteException("No hay usuarios en el sistema");
		}
		return auxColUsuarios;
	}
	
	public void inscribirEstudiantePrograma(String nickname, ProgramaFormacion pForm, Calendar fechaIns) throws UsuarioNoExisteException, EstudianteComoDocenteException, EstudianteYaInscripto {
		Usuario auxUsuario = this.coleccionUsuarios.get(nickname);
		if (auxUsuario != null) {
			if (auxUsuario instanceof Estudiante) {
				Estudiante auxEstudiante = (Estudiante) auxUsuario;
				auxEstudiante.inscribirAPrograma(pForm, fechaIns);
			} else {
				throw new EstudianteComoDocenteException("El usuario seleccionado no es un estudiante");
			}
		} else {
			throw new UsuarioNoExisteException("No existe el usuario");
		}
	}
	
	public boolean validarPassword(String nick, String pass) throws UsuarioNoExisteException {
		if (this.coleccionUsuarios.containsKey(nick)) {
			Usuario user = coleccionUsuarios.get(nick);
			return user.validar(pass);
		}
		 throw new UsuarioNoExisteException("El usuario no existe.");
		
	}
	
	public Map<String, DtEstudiante> getEstudiantes(String nomEd) {
		Map<String, DtEstudiante> resMap = new HashMap<String, DtEstudiante>();
		for (Map.Entry<String, Usuario> it : this.coleccionUsuarios.entrySet() ) {
			if  (it.getValue() instanceof Estudiante) {
				if  (((Estudiante) it.getValue()).inscriptoA(nomEd)) {
					resMap.put(it.getKey(), (DtEstudiante) it.getValue().getDtUsuario());
				}
			}
		}
		return resMap;
	}
	
	public void inscribirEstudiantesPorUnDocente(String nickDocente, String nomEdicion, Map<String, Boolean> eleccion) throws UsuarioNoExisteException, DocenteNoAsignadoAEdicionException, EstudianteNoInscriptoException, EstudianteYaInscripto {
		if (this.coleccionUsuarios.containsKey(nickDocente)) {
			Usuario docente = coleccionUsuarios.get(nickDocente);
			if ((docente instanceof Docente) && (((Docente) docente).edicionAsignada(nomEdicion))) {
				for (Map.Entry<String, Boolean> entry : eleccion.entrySet()) {
					if (this.coleccionUsuarios.containsKey(entry.getKey())) {
						int cantidadAceptadosEdicion = this.getCantidadAceptados(nomEdicion);
						ManejadorCurso mCurso = ManejadorCurso.getInstance();
						if (!mCurso.cupoLleno(nomEdicion, cantidadAceptadosEdicion) || !entry.getValue()) {
							Usuario estudiante = coleccionUsuarios.get(entry.getKey());
							if ((estudiante instanceof Estudiante) && (((Estudiante) estudiante).inscriptoA(nomEdicion))) {
								EstudianteEdicion eEdic = ((Estudiante) estudiante).getEstudianteEdicion(nomEdicion);
								if (eEdic.getEstado() == EnumEstado.INSCRIPTO) {
									eEdic.cambiarEstado(entry.getValue());
								} else {
									throw new EstudianteYaInscripto("No se puede pasar del estado Aceptado a Rechazado, ni viceversa.");
								}
							} else {
								throw new EstudianteNoInscriptoException("El estudiante no está inscripto a la edición.");
							}							
						} else {
							throw new EstudianteNoInscriptoException("El curso ya esta completo");
						}
					} else {
						throw new UsuarioNoExisteException("El estudiante no existe.");
					}
				}
			} else {
				throw new DocenteNoAsignadoAEdicionException("El docente no está asignado a la edición.");
			}
			
		} else {
			throw new UsuarioNoExisteException("El docente no existe.");
		}
	}
	
	
	public void ingresarResultadosDeEvaluaciones(String nickDocente, String nomEdicion, String nickEstudiante, int nota, Calendar fechaEvaluacion) throws UsuarioNoExisteException, DocenteNoAsignadoAEdicionException, EstudianteNoInscriptoException, NotaErroneaException {
		if (this.coleccionUsuarios.containsKey(nickDocente)) {
			Usuario docente = coleccionUsuarios.get(nickDocente);
			if ((docente instanceof Docente) && (((Docente) docente).edicionAsignada(nomEdicion))) {
				if (this.coleccionUsuarios.containsKey(nickEstudiante)) {
					Usuario estudiante = coleccionUsuarios.get(nickEstudiante);
					if ((estudiante instanceof Estudiante) && (((Estudiante) estudiante).inscriptoA(nomEdicion))) {
						EstudianteEdicion eEdic = ((Estudiante) estudiante).getEstudianteEdicion(nomEdicion);
						if (eEdic.getEstado() == EnumEstado.ACEPTADA) {
							if ((nota >= 0) && (nota <= 12)) {
								eEdic.agregarNota(nota, fechaEvaluacion);
							} else {
								throw new NotaErroneaException("La nota debe estar entre 0 y 12");
							}
						} else {
							throw new EstudianteNoInscriptoException("No se puede asignar nota a un estudiante no aceptado a la edición.");
						}
					} else {
						throw new EstudianteNoInscriptoException("El estudiante no está inscripto a la edición.");
					}							
				} else {
					throw new UsuarioNoExisteException("El estudiante no existe.");
				}
			} else {
				throw new DocenteNoAsignadoAEdicionException("El docente no está asignado a la edición.");
			}
			
		} else {
			throw new UsuarioNoExisteException("El docente no existe.");
		}
	}
	
	
	public Estudiante getEstudiante(String nick) throws EstudianteComoDocenteException, UsuarioNoExisteException {
		Usuario auxUsuario = this.coleccionUsuarios.get(nick);
		if (auxUsuario != null) {
			if (auxUsuario instanceof Estudiante) {
				Estudiante retorno = (Estudiante) auxUsuario;
				return (Estudiante) retorno;
			} else {
				throw new EstudianteComoDocenteException("El usuario es un docente");
			}
		} else {
			throw new UsuarioNoExisteException("El usuario no existe");
		}
	}
	
	public Map<String, DtEdicion> getEdicionesEstudiante(String nickname) throws UsuarioNoExisteException {
		Usuario usuario = this.coleccionUsuarios.get(nickname);
		if (usuario != null) {
			return usuario.getEdiciones();
		} else {
			throw new UsuarioNoExisteException("El usuario no existe");
		}
	}
	
	public Map<String, DtDocente> getDocentesDeEdicion(String edicion) {		
		Map<String, DtDocente> docentes = new HashMap<String, DtDocente>();
		for (Map.Entry<String, Usuario> usuario : this.coleccionUsuarios.entrySet()) {
			if (usuario.getValue() instanceof Docente) {
				Docente docente = (Docente) usuario.getValue();
				if (docente.edicionAsignada(edicion)) {
					docentes.put(docente.getNickname(), docente.getDtUsuario());
				}
			}
		}
		return docentes;
	}
	
	public int getCantidadAceptados(String nomEdicion) {
		int cant = 0;
		for (Map.Entry<String, Usuario> user : this.coleccionUsuarios.entrySet()) {
			if (user.getValue() instanceof Estudiante) {
				Estudiante auxEstudiante = (Estudiante) user.getValue();
				EstudianteEdicion auxEdicion = auxEstudiante.getEstudianteEdicion(nomEdicion);
				if (auxEdicion != null && auxEdicion.getEstado() == EnumEstado.ACEPTADA) {
					cant = cant + 1;
				}
			}
		}
		return cant;
	}
	
	public void desistirAInscripcionDeCurso(String nickname, String edicion) throws EdicionNoExisteException, UsuarioNoExisteException {
		Usuario usuario = this.coleccionUsuarios.get(nickname);
		if (usuario != null) {
			if (usuario instanceof Estudiante) {
				Estudiante estudiante = (Estudiante) usuario;
				estudiante.desistirAInscripcionDeCurso(edicion);
			} else {
				throw new UsuarioNoExisteException("El usuario no es estudiante");
			}
		} else {
			throw new UsuarioNoExisteException("El usuario no existe");
		}
	}
	
	public Map<String, DtEdicion> getEdicionesAceptadasEstudiante(String nickname) throws UsuarioNoExisteException {
		Usuario usuario = this.coleccionUsuarios.get(nickname);
		if (usuario != null && usuario instanceof Estudiante) {
			return ((Estudiante) usuario).getEdicionesAceptado();
		} else {
			throw new UsuarioNoExisteException("Debe iniciar sesión como estudiante para valorar.");
		}
	}
	
	
	public boolean existeNickname(String nickname) {
		Usuario usuario = this.coleccionUsuarios.get(nickname);
		return (usuario != null);
	}
	
	public boolean existeMail(String mail) {
		boolean existe = false;
		for (Map.Entry<String, Usuario> usuario : this.coleccionUsuarios.entrySet()) {
			existe = usuario.getValue().getMail().equals(mail);
			if (existe) {
				break;
			}
		}
		return existe;
	}
	
	private boolean programaAprobado(String nick,String nomPrograma) throws ProgramaNoExisteException, CursoNoExisteException, EdicionNoExisteException, UsuarioNoExisteException {
		if(coleccionUsuarios.containsKey(nick)) {
		    Usuario user = coleccionUsuarios.get(nick);
		    if(user instanceof Estudiante) {
		    	ManejadorProgramaFormacion mFormacion = ManejadorProgramaFormacion.getInstance();
				DtProgramaFormacion dFormacion = mFormacion.getDtProgramaFormacion(nomPrograma);
				ManejadorCurso mCurso = ManejadorCurso.getInstance();
				Map<String, List<String>> edicionesMap=new HashMap<String, List<String>>();
				for(int i=0;i<dFormacion.getCursos().size();i++) {
					edicionesMap.put(dFormacion.getCursos().get(i),mCurso.mostrarEdicionesDeCurso(dFormacion.getCursos().get(i)));
				}
				boolean b = ((Estudiante) user).aproboPrograma(nomPrograma,edicionesMap);
				return b;
		    }else {
		    	throw new UsuarioNoExisteException("El usuario no es estudiante");
		    }
		}else {
			throw new UsuarioNoExisteException("El usuario no existe");
		}
			
	}
	
	public DtCertificado getCertificado(String nick,String nomPrograma) throws ProgramaNoExisteException, CursoNoExisteException, EdicionNoExisteException, UsuarioNoExisteException {
		
		if(coleccionUsuarios.containsKey(nick)) {
			Usuario user = coleccionUsuarios.get(nick);
			if(user instanceof Estudiante) {
				DtCertificado dtCertificado = ((Estudiante) user).getDtCertificado(nomPrograma);
				if(dtCertificado!=null) {
					return dtCertificado;
				}else {
					if(programaAprobado(nick, nomPrograma)) {
						dtCertificado = ((Estudiante) user).getDtCertificado(nomPrograma);
						return dtCertificado;
					}else {
						return null;
					}
				}
			}
			return null;
		}
		return null;
	}
}
