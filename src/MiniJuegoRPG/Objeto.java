package MiniJuegoRPG;

public class Objeto {
	private String nombre;
	private Integer precio;

	public Objeto(String nombre, Integer precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}
	
	public Integer getPrecio() {
		return precio;
	}
	
}
