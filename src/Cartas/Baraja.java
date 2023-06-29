package Cartas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja {
	enum TipoBaraja {ESPANNOLA, INGLESA}
	
	private List<Carta> baraja;
	
	public Baraja() {
		this.baraja = new ArrayList<>();
	}
	
	public void addCarta(Carta carta) {
		this.baraja.add(carta);
	}
	
	public void darCarta(Jugador jugador) {
		jugador.getCartas().add(this.baraja.remove(0));
	}
	
	public static Baraja crearBaraja(TipoBaraja tipo) {
		Baraja baraja = new Baraja();
		List<String> palos = tipo.equals(TipoBaraja.ESPANNOLA) ? Carta.getPalosBarajaEspannola(): Carta.getPalosBarajaInglesa();
		String carta1 = tipo.equals(TipoBaraja.ESPANNOLA) ? "1":"As",
				carta11 = tipo.equals(TipoBaraja.ESPANNOLA) ? "Sota":"J",
				carta12 = tipo.equals(TipoBaraja.ESPANNOLA) ? "Caballo":"Q",
				carta13 = tipo.equals(TipoBaraja.ESPANNOLA) ? "Rey":"K";
		for(int palo = 0; palo < palos.size(); palo++) {
			for(int carta = 1; carta <= 13; carta++) {
				if(carta == 1) baraja.addCarta(Carta.of(carta1, palos.get(palo)));
				else if(carta == 11) baraja.addCarta(Carta.of(carta11, palos.get(palo)));
				else if(carta == 12) baraja.addCarta(Carta.of(carta12, palos.get(palo)));
				else if(carta == 13) baraja.addCarta(Carta.of(carta13, palos.get(palo)));
				else baraja.addCarta(Carta.of(String.valueOf(carta), palos.get(palo)));
			}
		}
		return baraja;
	}
	
	public void barajar() {
		Collections.shuffle(baraja);
	}
	
	public Boolean isEmpty() {
		return baraja.isEmpty();
	}
	
}
