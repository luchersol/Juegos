package MiniJuegoRPG;


public class Arma extends Objeto {

	private Integer daño;
	private Integer durabilidad;
	private Efecto efecto;
	
	public Arma(String nombre, Integer precio, Integer daño, Integer durabilidad, Efecto efecto) {
		super(nombre, precio);
		this.daño = daño;
		this.durabilidad = durabilidad;
		this.efecto = efecto;
	}

	public Integer getDaño() {
		return this.daño;
	}
	
	public Integer getDurabilidad() {
		return this.durabilidad;
	}
	
	public Efecto getEfecto() {
		return this.efecto;
	}
	
	public void bajarDurabilidad() {
		if(this.durabilidad != Integer.MAX_VALUE) {
			this.durabilidad--;
		}
	}
	
	public static Arma parseo(String linea) {
		String[] partes = linea.split(";");
		String nombre = partes[0].trim();
		Integer precio = Integer.valueOf(partes[1].trim());
		Integer daño = Integer.valueOf(partes[2].trim());
		Integer durabilidad = Integer.MAX_VALUE;
		if(partes[3].trim().length() != 0) 
			durabilidad = Integer.valueOf(partes[3].trim());
		Efecto efecto = Efecto.valueOf(partes[4].trim());
		return new Arma(nombre, precio, daño, durabilidad, efecto);
	}
}
