package logica;

public class Factory {
	public InterfazControladorCurso getInterfazControladorCurso() {
		return new ControladorCurso();
	}
	
	public InterfazControladorProgramaFormacion getInterfazControladorProgramaFormacion() {
		return new ControladorProgramaFormacion();
	}
	
	public InterfazControladorUsuario getInterfazControladorUsuario() {
		return new ControladorUsuario();
	}
}
