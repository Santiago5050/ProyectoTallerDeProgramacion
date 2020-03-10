package excepciones;

@SuppressWarnings("serial")
public class InstitutoNoExisteException extends Exception{

    public InstitutoNoExisteException(String string) {
        super(string);
    }
}
