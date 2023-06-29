package Cartas;

import java.util.List;

public class Carta {
	private String carta;
	private String palo;
	
	public Carta(String carta, String palo) {
		this.carta = carta;
		this.palo = palo;
	}
	
	public static Carta of(String carta, String palo) {
		return new Carta(carta, palo);
	}
	
	public String getCarta() {
		return this.carta;
	}
	
	public List<Integer> getValor() {
		return switch(this.carta) {
			case "As","1" -> List.of(1,11);
			case "J","Sota" -> List.of(11);
			case "Q","Caballo" -> List.of(12);
			case "K","Rey" -> List.of(13);
			default -> List.of(Integer.valueOf(this.carta));
		};
	}
	
	public static List<String> getPalosBarajaInglesa() {
		return List.of("picas", "corazones", "rombos", "treboles");
	}
	
	public static List<String> getPalosBarajaEspannola() {
		return List.of("oros", "copas", "espadas", "bastos");
	}
	
	@Override
	public String toString() {
		return String.format("%s de %s", this.carta, this.palo);
	}
	
}
