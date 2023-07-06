package MiniJuegoRPG;

public class Pocion extends Objeto{
	
	private Efecto efecto;
	private Integer duracion;
	
	public Pocion(String nombre, Integer precio, Efecto efecto, Integer duracion) {
		super(nombre, precio);
		this.efecto = efecto;
		this.duracion = duracion;
	}

	public Efecto getEfecto() {
		return efecto;
	}

	public Integer getDuracion() {
		return duracion;
	}
}
