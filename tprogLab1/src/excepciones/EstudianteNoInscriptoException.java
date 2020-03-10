package excepciones;

@SuppressWarnings("serial")
public class EstudianteNoInscriptoException extends Exception {

	public EstudianteNoInscriptoException(String string) {
		super(string);
	}
}