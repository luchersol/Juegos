package Casino;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Carta {
	private String carta;
	private String palo;
	private static final List<String> cartasEspeciales = List.of("As", "J", "Q", "K");
	
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
	
	public List<Integer> getValorBlackJack() {
		return switch(this.carta) {
			case "As","1" -> List.of(1,11);
			case "J","Sota" -> List.of(11);
			case "Q","Caballo" -> List.of(12);
			case "K","Rey" -> List.of(13);
			default -> List.of(Integer.valueOf(this.carta));
		};
	}

	public Integer getValorPoker() {
		return switch(this.carta) {
			case "J" -> 11;
			case "Q" -> 12;
			case "K"  -> 13;
			case "As" -> 14;
			default -> Integer.valueOf(this.carta);
		};
	}

	public static Integer getMultiplicadorAgrupacion(List<Carta> agrupacion){
		Boolean mismoPalo = agrupacion.stream().allMatch(c -> c.palo == agrupacion.get(0).palo);
		Boolean ordenDeUno = IntStream.range(0, agrupacion.size())
									.allMatch(i -> agrupacion.get(i).getValorPoker() == (agrupacion.get(i+1).getValorPoker() - 1));
		Integer valorAgrupacion = 0;
		if(mismoPalo){
			if(ordenDeUno && agrupacion.stream().map(c -> c.getValorPoker()).min(Comparator.naturalOrder()).get() == 10){
				valorAgrupacion = 250; // Escalera real
			} else if(ordenDeUno) {
				valorAgrupacion = 40; // Escalera de color
			} else {
				valorAgrupacion = 6; // Color
			}
		} else {
			Map<String, Long> contPorCarta = agrupacion.stream().collect(Collectors.groupingBy(Carta::getCarta, Collectors.counting()));
			if(contPorCarta.entrySet().stream().filter(entry -> cartasEspeciales.contains(entry.getKey())).anyMatch(entry -> entry.getValue() == 4)){
				valorAgrupacion = 20; // Poker
			} else if(contPorCarta.values().stream().sorted(Comparator.reverseOrder()).limit(2).allMatch(i -> i > 1)) {
				valorAgrupacion = 9; // Full
			} else if(ordenDeUno){
				valorAgrupacion = 5; // Escalera
			} else if(contPorCarta.values().stream().anyMatch(i -> i == 3)){
				valorAgrupacion = 3; // Trio
			} else if(contPorCarta.values().stream().filter(i -> i == 2).count() == 2){
				valorAgrupacion = 2; // Doble Pareja
			} else if(contPorCarta.entrySet().stream()
											.filter(entry -> cartasEspeciales.contains(entry.getKey()) && entry.getValue() == 2)
											.count() == 1){
				valorAgrupacion = 1; // Pareja de Jotas o Mejor
			}
		}

		return valorAgrupacion;
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
