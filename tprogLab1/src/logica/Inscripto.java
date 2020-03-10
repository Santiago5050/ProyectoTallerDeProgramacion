package logica;



public class Inscripto extends Estado {
	@Override
	public Estado cambiarEstado(boolean aceptada) {
		if (aceptada)
				return new Aceptada();
		else 
				return new Rechazada();
	}
	
	public EnumEstado getEstado() {
		return EnumEstado.INSCRIPTO;
	}
}
