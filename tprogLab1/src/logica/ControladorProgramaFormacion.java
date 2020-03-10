package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.List;

import excepciones.CursoNoExisteException;
import excepciones.ProgramaNoExisteException;

public class ControladorProgramaFormacion implements InterfazControladorProgramaFormacion {
	
	public boolean crearPrograma(String nombre, String descripcion, Calendar fechaInicio, Calendar fechaFin) {
		ManejadorProgramaFormacion mpf = ManejadorProgramaFormacion.getInstance();
		return mpf.crearPrograma(nombre, descripcion, fechaInicio, fechaFin, "");
	}
	
	public boolean crearProgramaSource(String nombre, String descripcion, Calendar fechaInicio, Calendar fechaFin, String source) {
		ManejadorProgramaFormacion mpf = ManejadorProgramaFormacion.getInstance();
		return mpf.crearPrograma(nombre, descripcion, fechaInicio, fechaFin, source);
	}
	
	public DtProgramaFormacion mostrarProgramaFormacion(String nomPrograma) throws ProgramaNoExisteException {
		return ManejadorProgramaFormacion.getInstance().getDtProgramaFormacion(nomPrograma);
	}
	
	public boolean agregarCursoAPrograma(String programa, String curso) throws ProgramaNoExisteException, CursoNoExisteException {
		ManejadorProgramaFormacion mProg = ManejadorProgramaFormacion.getInstance();
		ProgramaFormacion auxPf = mProg.getProgramaFormacion(programa);
		return auxPf.addCurso(curso);
	}
	
	public Map<String, DtCurso> listarCursos() {
		ManejadorCurso mCurso = ManejadorCurso.getInstance();
		Map<String, DtCurso> listaDtCurso = mCurso.getColDtCurso();
		return listaDtCurso;
	}
	
	public List<String> listarProgramasFormacion() {
		return ManejadorProgramaFormacion.getInstance().getNombresProgramaFormacion();
	}
	
	public Map<String, DtProgramaFormacion> listarDtProgramasFormacion() throws ProgramaNoExisteException {
		ManejadorProgramaFormacion mpf = ManejadorProgramaFormacion.getInstance();
		Map<String, DtProgramaFormacion> listaDtProgramas = mpf.listarDtProgramasFormacion();
		return listaDtProgramas;
	}
	
	public boolean agregarCursosAPrograma(String programa, List<String> listaCursos) throws ProgramaNoExisteException, CursoNoExisteException {
		boolean resultado = true;
		for (String nomCurso : listaCursos) {
			resultado = resultado && this.agregarCursoAPrograma(programa, nomCurso);
		}
		
		return resultado;
	}
	
	public List<DtProgramaFormacion> obtenerProgramasDeCurso(String nomCurso) throws ProgramaNoExisteException {
		ArrayList<DtProgramaFormacion> retorno = new ArrayList<DtProgramaFormacion>();
		Map<String, DtProgramaFormacion> listaProgramas = this.listarDtProgramasFormacion();
		for (Map.Entry<String, DtProgramaFormacion> programa : listaProgramas.entrySet()) {
			if (programa.getValue().getCursos().contains(nomCurso)) {
				retorno.add(programa.getValue());
			}
		}
		return retorno;
	}
	
	public List<DtProgramaFormacion> obtenerDtProgramas(List<String> nomProgramas) throws ProgramaNoExisteException {
		ArrayList<DtProgramaFormacion> retorno = new ArrayList<DtProgramaFormacion>();
		Map<String, DtProgramaFormacion> listaProgramas = this.listarDtProgramasFormacion();
		for (String nomPrograma : nomProgramas) {
			if (listaProgramas.get(nomPrograma) != null) {
				retorno.add(listaProgramas.get(nomPrograma));				
			} else {
				throw new ProgramaNoExisteException("Uno de los programas ingresados no existe en el sistema!");
			}
		}
		return retorno;
	}
	
	public boolean existePrograma(String programa) {
		ManejadorProgramaFormacion manejadorProgramaFormacion = ManejadorProgramaFormacion.getInstance();
		return manejadorProgramaFormacion.existePrograma(programa);
	}

}
