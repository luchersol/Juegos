package Casino;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
	private int index;
	private List<Carta> cartas;
	private Boolean plantado;
	private Integer dinero;
	
	public Jugador(List<Carta> cartas, int index) {
		this.index = index;
		this.cartas = cartas;
		this.plantado = false;
		this.dinero = 1000;
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
	
	public Integer getPuntajeBlackJack21() {
		Integer numAs = (int) cartas.stream()
				.filter(i -> i.getCarta().equals("As") || i.getCarta().equals("1"))
				.count();
		Integer punt = cartas.stream()
				.filter(i -> !(i.getCarta().equals("As") || i.getCarta().equals("1")))
				.mapToInt(c -> c.getValorBlackJack().get(0))
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
		return this.getPuntajeBlackJack21() <= 21;
	}
	
	public Integer getNumCartas() {
		return this.cartas.size();
	}

	public Boolean estaPlantado() {
		return plantado;
	}

	public Boolean tieneDinero() {
		return this.dinero > 0;
	}

	public void mostrarDineroRestante(){
		System.out.printf("Dinero del jugador: %d euros\n", this.dinero);
	}
	
	public void plantarse() {
		this.plantado = true;
	}

	public int getDinero(){
		return this.dinero;
	}

	public void quitarDinero(int dinero) {
		this.dinero -= dinero;
	}

	public void darDinero(int dinero) {
		this.dinero += dinero;
	}
	
	@Override
	public String toString() {
		return "Jugador " + this.index;
	}
	
}
