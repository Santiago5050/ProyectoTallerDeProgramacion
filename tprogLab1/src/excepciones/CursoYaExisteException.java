package excepciones;

@SuppressWarnings("serial")
public class CursoYaExisteException extends Exception {

	public CursoYaExisteException(String string) {
		super(string);
	}
}
