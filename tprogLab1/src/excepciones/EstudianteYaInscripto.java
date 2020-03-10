package excepciones;

@SuppressWarnings("serial")
public class EstudianteYaInscripto extends Exception{

    public EstudianteYaInscripto(String string) {
        super(string);
    }
}
