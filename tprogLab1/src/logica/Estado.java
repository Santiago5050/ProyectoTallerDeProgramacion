package logica;

public abstract class Estado {
	public abstract Estado cambiarEstado(boolean aceptada);
	public abstract EnumEstado getEstado();
}
