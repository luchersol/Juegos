package Casino;

import java.util.Scanner;

import Casino.Baraja.TipoBaraja;

public class BlackJack21 {
	private Baraja baraja;
	private Jugador jugador1;
	private Jugador jugador2;
	private Scanner scan = new Scanner(System.in);
	
	public BlackJack21() {
		this.jugador1 = Jugador.create(1);
		this.jugador2 = Jugador.create(2);
		System.out.println("Elige baraja:\n1.Espannola\t2.Inglesa");
		TipoBaraja tipo = scan.nextInt() == 1 ? TipoBaraja.ESPANNOLA : TipoBaraja.INGLESA;
		this.baraja = Baraja.crearBaraja(tipo);
		this.baraja.barajar();
	}
	
	public void printCartas() {
		System.out.println("Jugador 1:\t\tJugador 2:");
		Integer mayorNumCartas = Integer.max(jugador1.getNumCartas(), jugador2.getNumCartas());
		for(int i = 0; i < mayorNumCartas; i++) {
			if(jugador1.existCarta(i)) {
				System.out.printf("%s\t\t", jugador1.getCarta(i));
			} else {
				System.out.print("\t\t\t");
			}
			if(jugador2.existCarta(i)) {
				System.out.printf("%s\n", jugador2.getCarta(i));
			} else {
				System.out.print("\n");
			}
		}
		System.out.println("*".repeat(36));
		System.out.printf("%d puntos\t\t%d puntos\n", jugador1.getPuntajeBlackJack21(), jugador2.getPuntajeBlackJack21());
	}
	
	public void repartirCartaA(Integer i) {
		if(i == 0) baraja.darCarta(jugador1);
		else baraja.darCarta(jugador2);
	}
	
	public void turno() {
		eleccionJugador(jugador1);
		eleccionJugador(jugador2);
	}
	
	public void eleccionJugador(Jugador jugador) {
		if(!jugador.estaPlantado()) {
			System.out.printf("%s elige:\n1.Plantarse\t2.Pedir carta", jugador);
			Integer opc = scan.nextInt();
			if(opc == 1) jugador.plantarse(); 
			else baraja.darCarta(jugador);
		}
	}
	
	public Boolean finDelJuego() {
		return this.baraja.isEmpty() 
				|| !jugador1.isPuntajeValido() 
				|| !jugador2.isPuntajeValido()  
				|| (jugador1.estaPlantado() && jugador2.estaPlantado());
	}
	
	public void repartoInicial() {
		baraja.darCarta(jugador1);
		baraja.darCarta(jugador1);
		baraja.darCarta(jugador2);
		baraja.darCarta(jugador2);
	}
	
	public void mensajeFinal() {
		String mensaje = "";
		Boolean puntValidoJ1 = jugador1.isPuntajeValido() ;
		Boolean puntValidoJ2 = jugador2.isPuntajeValido() ;
		if(puntValidoJ1 && puntValidoJ2) {
			if(jugador1.getPuntajeBlackJack21() > jugador2.getPuntajeBlackJack21()) mensaje = "Gana jugador 1";
			else if(jugador1.getPuntajeBlackJack21() < jugador2.getPuntajeBlackJack21()) mensaje = "Gana jugador 2";
			else mensaje = "Empate";
		} else if(puntValidoJ1 ^ puntValidoJ2) {
			if(puntValidoJ1) mensaje = "Gana jugador 1";
			else mensaje = "Gana jugador 2";
		} else {
			mensaje = "Pierden ambos jugadores";
		}
		System.out.println(mensaje);
	}
	
	public static void jugar() {
		BlackJack21 game;
		do {
		game = new BlackJack21();
		game.repartoInicial();
		} while(!game.jugador1.isPuntajeValido() || !game.jugador2.isPuntajeValido());
		game.printCartas();
		while(!game.finDelJuego()) {
			game.turno();
			game.printCartas();
		}
		game.mensajeFinal();
	}
	
	public static void main(String[] args) {
		jugar();
	}
}
