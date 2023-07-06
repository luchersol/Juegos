package TresEnRaya;

public class Traza {
	enum Tipo{X, O, VACIO};
	private Tipo tipo;
	private Integer posicion;
	
	public Traza(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public static Traza of(Tipo tipo) {
		return new Traza(tipo);
	}
	
	public static Traza X() {
		return Traza.of(Tipo.X);
	}
	
	public static Traza O() {
		return Traza.of(Tipo.O);
	}
	
	public static Traza VACIO() {
		return Traza.of(Tipo.VACIO);
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}
	
	@Override
	public String toString() {
		String res = "";
		if (tipo.equals(Tipo.X)) {
//			res = "\\ / X / \\";
			res = "    X    ";
		} else if(tipo.equals(Tipo.O)) {
//			res = "[ ]| |[ ]";
			res = "    O    ";
		} else {
//			res = "         ";
			res = "    "+posicion+"    ";
		}
		return res;
	}
}
