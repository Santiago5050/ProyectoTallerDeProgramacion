package logica;

public class Rechazada extends Estado {

	@Override
	public Estado cambiarEstado(boolean aceptada) {
		return null;
	}
	
	public EnumEstado getEstado() {
		return EnumEstado.RECHAZADA;
	}
}
