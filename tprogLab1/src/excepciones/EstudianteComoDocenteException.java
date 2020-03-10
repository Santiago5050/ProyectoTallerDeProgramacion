package excepciones;

@SuppressWarnings("serial")
public class EstudianteComoDocenteException extends Exception {
	
    public EstudianteComoDocenteException(String string) {
        super(string);
    }
}