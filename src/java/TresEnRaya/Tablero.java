package TresEnRaya;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import TresEnRaya.Traza.Tipo;

public class Tablero {
	private static List<Traza> tablero;
	private static List<Integer> posicionesPosibles;
	private static Tipo turno;
	private static Tipo ganador;
	private static Scanner scan = new Scanner(System.in);
	
	public static Tipo getTipoPosicion(Integer i) {
		return tablero.get(i).getTipo();
	}
	
	public static void createEmptyBoard() {
		ganador = null;
		posicionesPosibles = IntStream.range(0, 9).boxed().collect(Collectors.toList());
		List<Traza> ls = new ArrayList<Traza>();
		for (int i = 0; i<9; i++) {
			ls.add(Traza.VACIO());
			ls.get(i).setPosicion(i);
		}
		tablero = new ArrayList<>(ls);
	}
	
	public static void changePlayer() {
		turno = turno.equals(Tipo.X) ? Tipo.O : Tipo.X;
	}
	
	public static void chooseInitialPlayer() {
		Integer choice = 0;
		List<Integer> eleccionJugador = List.of(1,2);
		while(!eleccionJugador.contains(choice)) {
			System.out.println("Choose initial player:" + "\n" + "1.Player X		2.Player O");
			try {
				choice = scan.nextInt();
			} catch (Exception e) {
				System.out.println("You must introduce a number, try again");
				continue;
			}
			
			if(!eleccionJugador.contains(choice)) 
				System.out.println("Error choice, try again");
			
		}
		turno = choice == 1 ? Tipo.X : Tipo.O;
		
		showBoard();
	}
	
	public static void choosePositionTraza() {
		Integer posicion = null;
		while(!posicionesPosibles.contains(posicion)) {
			System.out.println("Choose position, player " + turno);
			try {
				posicion = scan.nextInt();
			} catch (Exception e) {
				System.out.println("You must introduce a number, try again");
				continue;
			}

			
			if(!posicionesPosibles.contains(posicion)) 
				System.out.println("Error position, try again");
		}
		tablero.set(posicion, Traza.of(turno));
		posicionesPosibles.remove(posicion);
		showBoard();
	}
	
	public static Boolean anyWinnerRow() {
		Boolean flat = false;
		for(int i = 0; i<9; i+=3) {
			if(getTipoPosicion(i).equals(turno)) {
				if(getTipoPosicion(i+1).equals(turno) && getTipoPosicion(i+2).equals(turno)) {
					flat = true;
					break;
				}
			}
		}
		return flat;
	}
	
	public static Boolean anyWinnerColumn() {
		Boolean flat = false;
		for(int i = 0; i<3; i++) {
			if(getTipoPosicion(i).equals(turno)) {
				if(getTipoPosicion(i+3).equals(turno) && getTipoPosicion(i+6).equals(turno)) {
					flat = true;
					break;
				}
			}
		}
		return flat;
	}
	
	public static Boolean anyWinnerDiagonal() {
		Boolean flat = false;
		if(getTipoPosicion(4).equals(turno)) {
			if(getTipoPosicion(0).equals(turno) && getTipoPosicion(8).equals(turno)
				|| getTipoPosicion(2).equals(turno) && getTipoPosicion(6).equals(turno)) {
				flat = true;
			}
		}
		return flat;
	}
	
	
	public static void calculateWinner() {
		if(anyWinnerRow() || anyWinnerColumn() || anyWinnerDiagonal()) 
			ganador = turno;
	}
	
	public static void showBoard() {
		String res = "===+===+===\n";
		Integer a = 0;
		for(int i = 0; i<9; i+=3) {
			for(int j = 0; j<3; j++){
				res += tablero.get(i).toString().substring(a, a+3) + "|" +
						tablero.get(i+1).toString().substring(a, a+3) + "|" +
						tablero.get(i+2).toString().substring(a, a+3) + "\n";
				a += 3;
			}
			a=0;
			res += "===+===+===\n";
		}
		System.out.println(res);
	}
	
	public static void showWinner() {
		if(ganador != null) 
			System.out.println("Gana jugador " + turno);
		else 
			System.out.println("Empate");
	}
	
	public static void questionReset() {
		String response = "";
		
		while(true) {
			System.out.print("Do you want to play again? [Y/N]:");
			response = scan.next();
			if(response.equals("Y") || response.equals("N")) break;
		}
		
		if(response.equals("Y")) 
			play();
		else
			System.out.println("Game Over");
	}
	
	public static void play() {
		createEmptyBoard();
		chooseInitialPlayer();
		while(!posicionesPosibles.isEmpty() && ganador == null) {
			choosePositionTraza();
			calculateWinner();
			if(ganador == null)
				changePlayer();
		}
		showWinner();
		questionReset();
		
	}
	
	public static void main(String[] args) {
		play();
	}
	
}

