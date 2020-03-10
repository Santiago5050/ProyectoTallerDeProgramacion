package logica;

import java.util.Calendar;
import java.util.List;

public abstract class DtUsuario {
	private String nickname;
	private String nombre;
	private String apellido;
	private String mail;
	private Calendar fechaNacimiento;
	private String src;
	private List<String> sigue;
	
	public DtUsuario(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, List<String> sigue2, String source) {
		this.fechaNacimiento = fechaNacimiento;
		this.mail = mail;
		this.apellido = apellido;
		this.nickname = nickname;
		this.nombre = nombre;
		this.src = source;
		this.sigue = sigue2;
	}

	public String getNickname() {
		return nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getMail() {
		return mail;
	}

	public Calendar getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public String getSource() {
		return src;
	}
	
	public List<String> getSigue() {
		return this.sigue;
	}
	
	public abstract void test();
	
	
}
