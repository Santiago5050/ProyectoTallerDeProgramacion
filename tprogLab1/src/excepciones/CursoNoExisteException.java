package excepciones;

@SuppressWarnings("serial")
public class CursoNoExisteException extends Exception {
	
    public CursoNoExisteException(String string) {
        super(string);
    }
}
