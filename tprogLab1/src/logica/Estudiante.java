package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import excepciones.EdicionNoExisteException;
import excepciones.EstudianteYaInscripto;
import excepciones.ProgramaNoExisteException;


public class Estudiante extends Usuario {
	private Map<String, EstudianteEdicion> ediciones;
	private Map<String, EstudiantePrograma> programas;
	private Map<String, DtCertificado> certificadosEmitidos;
	
	public Estudiante(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String password, String source) {
		super(nickname, nombre, apellido, mail, fechaNacimiento, password);
		this.ediciones = new HashMap<String, EstudianteEdicion>();
		this.programas = new HashMap<String, EstudiantePrograma>();
		this.certificadosEmitidos = new HashMap<String, DtCertificado>();
		this.setSource(source);
	}
	
	public DtEstudiante getDtUsuario() {
		Map<String, DtFechaEstadoNota> estadoEdiciones = new HashMap<String, DtFechaEstadoNota>();
		ArrayList<String> nombreProgramas = new ArrayList<String>();
		ArrayList<String> sigue = new ArrayList<String>();
		if (!this.ediciones.isEmpty()) {
			for (Map.Entry<String, EstudianteEdicion> entry : this.ediciones.entrySet()) {
				DtFechaEstadoNota pair = new DtFechaEstadoNota(entry.getValue().getEstado(), entry.getValue().getInscripcion(), entry.getValue().getVideo());
				if (entry.getValue().getEstado() == EnumEstado.ACEPTADA) {
					pair.agregarNota(entry.getValue().getNota());
				}
				estadoEdiciones.put(entry.getValue().getNombre(), pair);
			}
		}
		if (!this.programas.isEmpty()) {
			for (Map.Entry<String, EstudiantePrograma> entry : this.programas.entrySet()) {
				nombreProgramas.add(entry.getValue().getNombre());
			}
		}
		if (!this.getSigue().isEmpty()) {
			for (Map.Entry<String, Usuario> entry : this.getSigue().entrySet()) {
				sigue.add(entry.getValue().getNickname());
			}
		}
		return new DtEstudiante(this.getNickname(), this.getNombre(), this.getApellido(), this.getMail(), this.getFechaNacimiento(), estadoEdiciones, nombreProgramas, sigue, this.getSource());
	}
	
	public DtProgramaFormacionEstudiante getDtProgramaFormacionEstudiante(String programa) throws ProgramaNoExisteException{
		EstudiantePrograma programaBuscado = this.programas.get(programa);
		if (programaBuscado == null) {
			throw new ProgramaNoExisteException("El programa no existe");
		}
		return programaBuscado.getDtProgramaFormacion();
	}
	
	public DtEdicion getDtEdicion(String edicion) throws EdicionNoExisteException {
		EstudianteEdicion estudianteEdicionBuscada = this.ediciones.get(edicion);
		if (estudianteEdicionBuscada == null) {
			throw new EdicionNoExisteException("La edicion no existe");
		}
		DtEdicion retorno = estudianteEdicionBuscada.getDtEdicion();
		return retorno;
	}
	
	@Override
	public void inscribirAEdicion(Edicion edi, Calendar fecha, String video, EnumEstado estado) throws EstudianteYaInscripto {
		EstudianteEdicion buscar = this.ediciones.get(edi.getNombre());
		if (buscar == null) {
			EstudianteEdicion eEdic = new EstudianteEdicion(fecha, edi, video, estado);
			ediciones.put(eEdic.getNombre(), eEdic);
		} else {
			throw new EstudianteYaInscripto("El estudiante ya estaba inscripto en la edición");
		}
	}
	
	public void inscribirAPrograma(ProgramaFormacion pForm, Calendar fechaIns) throws EstudianteYaInscripto {
		EstudiantePrograma buscar = this.programas.get(pForm.getNombre());
		if (buscar == null) {
			EstudiantePrograma auxEp = new EstudiantePrograma(fechaIns, pForm);
			programas.put(auxEp.getNombre(), auxEp);
		} else {
			throw new EstudianteYaInscripto("El estudiante ya estaba inscripto en el programa");
		}
	}
	
	public boolean inscriptoA(String nomEd) {
		return this.ediciones.containsKey(nomEd);
	}
	
	public EstudianteEdicion getEstudianteEdicion(String nomEdicion) {
		return this.ediciones.get(nomEdicion);
	}
	
	public Map<String, DtEdicion> getEdiciones() {
		Map<String, DtEdicion> ediciones = new HashMap<String, DtEdicion>();
		for (Map.Entry<String, EstudianteEdicion> entry : this.ediciones.entrySet()) {
			ediciones.put(entry.getKey(), entry.getValue().getDtEdicion());
		}
		return ediciones;
	}
	
	public void desistirAInscripcionDeCurso(String edicion) throws EdicionNoExisteException {
		EstudianteEdicion inscripcionEdicion = this.ediciones.remove(edicion);
		if (inscripcionEdicion == null) {
			throw new EdicionNoExisteException("La edicion no existe");
		}
		inscripcionEdicion = null;
	}
	
	public Map<String, DtEdicion> getEdicionesAceptado(){
		Map<String, DtEdicion> res = new HashMap<String, DtEdicion>();
		for (Map.Entry<String, EstudianteEdicion> entry : this.ediciones.entrySet()) {
			if(entry.getValue().getEstado()==EnumEstado.ACEPTADA)
				res.put(entry.getKey(), entry.getValue().getDtEdicion());
		}
		return res;
	}
	
	public boolean aproboPrograma(String nomPrograma,Map<String, List<String>> ediciones) {
		if(programas.containsKey(nomPrograma)) {
			Map<String, Calendar> aprobadasMap=new HashMap<String, Calendar>();
			List<String> cursos = new ArrayList<String>();
			ManejadorCurso mCurso = ManejadorCurso.getInstance();
			for(Map.Entry<String, List<String>> eds: ediciones.entrySet()) {
				boolean aprobado=false;
				int iter = 0;
				while(!aprobado && iter<eds.getValue().size()) {
					if(this.ediciones.containsKey(eds.getValue().get(iter))){
						EstudianteEdicion estEd= this.ediciones.get(eds.getValue().get(iter));
						if(estEd.getEstado()==EnumEstado.ACEPTADA && estEd.getNota()>=3 && mCurso.edicionCerrada(eds.getKey(),eds.getValue().get(iter))) {
							aprobadasMap.put(eds.getValue().get(iter),estEd.getFechaPublicacion());
							aprobado=true;
						}	
					}
					iter++;
				}
				cursos.add(eds.getKey());
			}
			if(aprobadasMap.size()==ediciones.size()) {
				DtCertificado cert= new DtCertificado(nomPrograma, aprobadasMap, cursos, Calendar.getInstance(), this.getNombre(), this.getApellido());
				certificadosEmitidos.put(nomPrograma, cert);
				return true;
			}
			return false;
			
		}else {
			return false;
		}
	}
	
	public DtCertificado getDtCertificado(String nomPrograma) {
		if(certificadosEmitidos.containsKey(nomPrograma)) {
			return certificadosEmitidos.get(nomPrograma);
		}else {
	        return null;
		}
	}
}
