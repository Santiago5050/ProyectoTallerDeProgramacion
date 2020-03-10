package excepciones;

@SuppressWarnings("serial")
public class ProgramaNoExisteException extends Exception {
	
    public ProgramaNoExisteException(String string) {
        super(string);
    }
}
