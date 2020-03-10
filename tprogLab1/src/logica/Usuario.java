package logica;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import excepciones.EdicionNoExisteException;
import excepciones.EstudianteYaInscripto;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;


public abstract class Usuario {
	private String nickname;
	private String nombre;
	private String apellido;
	private String mail;
	private Calendar fechaNacimiento;
	private String password;
	private String source;
	private Map<String, Usuario> sigue;
	
	public Usuario(String nick, String nom, String ape, String mail, Calendar fecha, String password) {
		this.nickname = nick;
		this.nombre = nom;
		this.apellido = ape;
		this.mail = mail;
		this.fechaNacimiento = fecha;
		this.password = password;
		this.source = null;
		this.sigue = new HashMap<String, Usuario>();
	}
	
	public abstract DtUsuario getDtUsuario();

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Calendar getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Calendar fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getSource() {
		return this.source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public Map<String, Usuario> getSigue() {
		return this.sigue;
	}
	
	public abstract DtEdicion getDtEdicion(String edicion) throws EdicionNoExisteException;
	
	public abstract void inscribirAEdicion(Edicion edicion, Calendar fecha, String video, EnumEstado estado) throws EstudianteYaInscripto;
	
	public void setDatos(String nombre, String apellido, Calendar nacimiento) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = nacimiento;
	}
	
	public void setDatosSource(String nombre, String apellido, Calendar nacimiento, String source) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = nacimiento;
		this.source = source;
	}
	
	public void seguirUsuario(String usuarioASeguir, Usuario userToFollow) throws UsuarioYaExisteException { 
		if (sigue.containsKey(usuarioASeguir)) {
			throw new UsuarioYaExisteException("El Usuario " + this.nickname +" ya sigue a " + usuarioASeguir);
		} else 
			sigue.put(usuarioASeguir, userToFollow);
	}
	
	public void dejarDeSeguirUsuario(String usuarioSeguido) throws UsuarioNoExisteException {
		Usuario eliminado = this.sigue.remove(usuarioSeguido);
		if (eliminado == null) {
			throw new UsuarioNoExisteException("No se sigue al usuario");
		}
	}
	
	public boolean validar(String pass) {
		return pass.equals(this.password);
	}
	
	public abstract Map<String, DtEdicion> getEdiciones();
}
