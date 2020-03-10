package logica;


import java.util.Calendar;
import java.util.Map;
import java.util.List;

import excepciones.CursoNoExisteException;
import excepciones.ProgramaNoExisteException;

public interface InterfazControladorProgramaFormacion {
	public boolean crearPrograma(String nombre, String descripcion, Calendar fechaInicio, Calendar fechaFin);
	public boolean crearProgramaSource(String nombre, String descripcion, Calendar fechaInicio, Calendar fechaFin, String source);
	public DtProgramaFormacion mostrarProgramaFormacion(String nomPrograma) throws ProgramaNoExisteException;
	public boolean agregarCursoAPrograma(String programa, String curso) throws ProgramaNoExisteException, CursoNoExisteException;
	public Map<String, DtCurso> listarCursos();
	public List<String> listarProgramasFormacion();
	public boolean existePrograma(String programa);
}
