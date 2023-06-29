package RuletaRusa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Utils.Preconditions;

public class RuletaRusa {
	private List<Integer> ruleta;
	private List<Integer> huecosBalas;
	private Scanner scan = new Scanner(System.in);
	private List<Integer> jugadores;
	private final Integer TAM_RULETA = 6;
	private final Integer NUM_JUGADORES = 2;
	
	public RuletaRusa() {
		this.ruleta = IntStream.range(0, TAM_RULETA).boxed().collect(Collectors.toList());
		this.huecosBalas = new ArrayList<Integer>();
		this.jugadores = IntStream.range(0, NUM_JUGADORES).boxed().collect(Collectors.toList());
	}
	
	public void girar() {
		Collections.shuffle(ruleta);
	}
	
	public void annadirNBalas(int n) {
		Preconditions.checkArgument(n > 0 && n < ruleta.size(), "No puedes poner ninguna bala o llenar todos los huecos");
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
