package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import excepciones.EdicionNoExisteException;

public class Docente extends Usuario {
	private Instituto instituto;
	private Map<String, Edicion> ediciones;
	
	public Docente(String nickname, String nombre, String apellido, String mail, Calendar fechaNacimiento, String instituto, String password, String source) {
		super(nickname, nombre, apellido, mail, fechaNacimiento, password);
		ManejadorInstituto mInstituto = ManejadorInstituto.getInstance();
		Instituto agregar = mInstituto.getInstituto(instituto);
		this.instituto = agregar;
		this.ediciones = new HashMap<String, Edicion>();
		this.setSource(source);
	}
	
	public DtDocente getDtUsuario() {
		ArrayList<String> nombreEdiciones = new ArrayList<String>();
		if (!this.ediciones.isEmpty()) {
			for (Map.Entry<String, Edicion> entry : this.ediciones.entrySet()) {
				nombreEdiciones.add(entry.getValue().getNombre());
			}
		}
		ArrayList<String> sigue = new ArrayList<String>();
		if (!this.getSigue().isEmpty()) {
			for (Map.Entry<String, Usuario> entry : this.getSigue().entrySet()) {
				sigue.add(entry.getValue().getNickname());
			}
		}
		return new DtDocente(this.getNickname(), this.getNombre(), this.getApellido(), this.getMail(), this.getFechaNacimiento(), this.instituto.getNombre(), nombreEdiciones, sigue, this.getSource());
	}
	
	public DtEdicion getDtEdicion(String edicion) throws EdicionNoExisteException {
		Edicion edicionBuscada = this.ediciones.get(edicion);
		if (edicionBuscada == null) {
			throw new EdicionNoExisteException("La edicion no existe");
		}
		return edicionBuscada.getDtEdicion();
	}
	
	public void asignarEdicion(Edicion edicion) {
		this.ediciones.put(edicion.getNombre(), edicion);
	}
	
	@Override
	public void inscribirAEdicion(Edicion edicion, Calendar fecha, String video, EnumEstado estado) {}
	
	public String getInstituto() {
		return this.instituto.getNombre();
	}
	
	public boolean edicionAsignada(String nomEd) {
		return this.ediciones.containsKey(nomEd);
	}
	
	public Map<String, DtEdicion> getEdiciones() {
		Map<String, DtEdicion> mapEdiciones = new HashMap<String, DtEdicion>();
		for (Map.Entry<String, Edicion> entry : this.ediciones.entrySet()) {
			mapEdiciones.put(entry.getKey(), entry.getValue().getDtEdicion());
		}
		return mapEdiciones;
	}
	
}