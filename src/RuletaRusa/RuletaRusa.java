package RuletaRusa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RuletaRusa {
	private List<Integer> ruleta;
	private List<Integer> huecosBalas;
	private Scanner scan = new Scanner(System.in);
	private List<Integer> jugadores;
	
	public RuletaRusa() {
		this.ruleta = IntStream.rangeClosed(0, 5).boxed().collect(Collectors.toList());
		this.huecosBalas = new ArrayList<Integer>();
		this.jugadores = IntStream.rangeClosed(0, 1).boxed().collect(Collectors.toList());
	}
	
	public void girar() {
		Collections.shuffle(ruleta);
	}
	
	public void annadirNBalas(int n) {
		if(n <= 0 || n >= ruleta.size()) throw new IllegalArgumentException("Has llenado el cargador, nadie puede ganar");
	
		List<Integer> copiaRuleta = new ArrayList<Integer>(ruleta);
		Collections.shuffle(copiaRuleta);
		this.huecosBalas = copiaRuleta.subList(0, n);
	}
	
	public Integer disparar() {
		return ruleta.remove(0);
	}
	
	public void jugar() {
		System.out.println("Diga cuantas balas desea annadir:");
		Integer numBalas = scan.nextInt();
		System.out.printf("Se annadiran %d balas\n", numBalas);
		annadirNBalas(numBalas);
		int i = 0;
		while(!huecosBalas.isEmpty()) {
			System.out.printf("\nDiparar jugador %d\n", i);
			Integer balaDisparada = disparar();
			if(huecosBalas.contains(balaDisparada)) {
				System.out.printf("BANG!!!, muere jugador %d\n", i);
				jugadores.remove(i);
				break;
			}
			System.out.println("Click! Tuvo suerte");
			i = (i+1)%jugadores.size();
		}
		System.out.printf("Gana el jugador %d", jugadores.get(0));
 	}
	
	public static void main(String[] args) {
		RuletaRusa game = new RuletaRusa();
		game.jugar();
	}
}
