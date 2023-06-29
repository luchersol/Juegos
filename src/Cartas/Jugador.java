package Cartas;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
	private List<Carta> cartas;
	private int index;
	private Boolean plantado;
	
	public Jugador(List<Carta> cartas, int index) {
		this.index = index;
		this.cartas = cartas;
		this.plantado = false;
	}
	
	public static Jugador create(int i) {
		return new Jugador(new ArrayList<Carta>(), i);
	}

	public List<Carta> getCartas() {
		return this.cartas;
	}
	
	public Carta getCarta(Integer i) {
		return this.cartas.get(i);
	}
	
	public Boolean existCarta(Integer i) {
		return i >= 0 && i < getNumCartas();
	}
	
	public Integer getPuntaje() {
		Integer numAs = (int) cartas.stream()
				.filter(i -> i.getCarta().equals("As") || i.getCarta().equals("1"))
				.count();
		Integer punt = cartas.stream()
				.filter(i -> !(i.getCarta().equals("As") || i.getCarta().equals("1")))
				.mapToInt(c -> c.getValor().get(0))
				.sum();
		if(numAs != 0) {
			punt += numAs * 11;
			while(punt > 21 && numAs > 0) {
				punt -= 10;
				numAs--;
			}
		}
		return punt;
	}
	
	public Boolean isPuntajeValido() {
		return this.getPuntaje() <= 21;
	}
	
	public Integer getNumCartas() {
		return this.cartas.size();
	}

	public Boolean estaPlantado() {
		return plantado;
	}
	
	public void plantarse() {
		this.plantado = true;
	}
	
	@Override
	public String toString() {
		return "Jugador " + this.index;
	}
	
}
