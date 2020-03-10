package logica;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import excepciones.ProgramaNoExisteException;
import java.util.ArrayList;
import java.util.Calendar;

public class ManejadorProgramaFormacion {
	private Map<String, ProgramaFormacion> programas;
	private static ManejadorProgramaFormacion instance = null;
	
	private ManejadorProgramaFormacion() {
		this.programas = new HashMap<String, ProgramaFormacion>();
	}
	
	public static ManejadorProgramaFormacion getInstance() {
		if (instance == null) {
			instance = new ManejadorProgramaFormacion();
		}
		return instance;
	}
	
	public boolean crearPrograma(String nombre, String descripcion, Calendar fechaInicio, Calendar fechaFin, String source) {
		boolean creoPrograma = false;
		ProgramaFormacion programa = programas.get(nombre);
		if (programa == null) {
			ProgramaFormacion nuevoPrograma = new ProgramaFormacion(nombre, descripcion, fechaInicio, fechaFin, source);
			programas.put(nombre, nuevoPrograma);
			creoPrograma = true;
		}
		return creoPrograma;
	}
	
	public List<String> getProgramasConCurso(String nomCurso) {
		ArrayList<String> arrayNombres = new ArrayList<String>();
		programas.forEach((k, v) -> {
			if (v.tieneCurso(nomCurso)) {
				arrayNombres.add(v.getNombre());
			}});
		return arrayNombres;
	}
	
	public DtProgramaFormacion getDtProgramaFormacion(String nomPrograma) throws ProgramaNoExisteException {
		if (programas.containsKey(nomPrograma)) {
			ProgramaFormacion programaFormacion = programas.get(nomPrograma);
			return programaFormacion.getDtProgramaFormacion();
		} else {
			throw new ProgramaNoExisteException("El programa no existe!");
		}
		
	}
	
	public List<String> getNombresProgramaFormacion() {
		ArrayList<String> listaProgramas = new ArrayList<String>();
		this.programas.forEach((k, v) -> {
			listaProgramas.add(v.getNombre());
		});
		return listaProgramas;
	}
	
	public ProgramaFormacion getProgramaFormacion(String nomPrograma) throws ProgramaNoExisteException {
		ProgramaFormacion pForm = this.programas.get(nomPrograma);
		if (pForm == null)
			throw new ProgramaNoExisteException("El programa de formacion ingresado no existe");
		return pForm;
	}
	
	public Map<String, DtProgramaFormacion> listarDtProgramasFormacion() throws ProgramaNoExisteException {
		Map<String, DtProgramaFormacion> listaDtProgramas = new HashMap<String, DtProgramaFormacion>();
		if (!(this.programas.isEmpty())) {
			this.programas.forEach((k, v)-> {
				listaDtProgramas.put(k, v.getDtProgramaFormacion());
			});
		} else {
			throw new ProgramaNoExisteException("No hay programas de formacion para listar");
		}
		return listaDtProgramas;
	}
	
	public boolean existePrograma(String programa) {
		return (this.programas.get(programa) != null);
	}
	
}
