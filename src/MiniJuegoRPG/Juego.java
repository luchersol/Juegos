package MiniJuegoRPG;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Utils.Files2;

public class Juego {

	public static List<Personaje> aliados;
	public static List<Personaje> enemigos;
	public static List<Personaje> personajes;
	public static List<Arma> armas;
	public static Integer persSeleccionado;
	
	public static void mostrarEstadoBatalla() {
		System.out.println("ALIADOS:");
		for(Personaje p: aliados) {
			System.out.println(p);
		}
		System.out.println("\nENEMIGOS:");
		for(Personaje p: enemigos) {
			System.out.println(p);
		}
	}
	
	public static void cargarArmas() {
		armas = Files2.streamFromFile("../pruebas/info/MiniJuegoRPG/armas")
						.skip(1)
						.map(Arma::parseo)
						.collect(Collectors.toList());
	}
	
	public static void cargarAliados() {
		persSeleccionado = 0;
		aliados = Files2.streamFromFile("../pruebas/info/MiniJuegoRPG/aliados")
				.skip(1)
				.map(Personaje::parseo)
				.collect(Collectors.toList());
	}
	
	public static void cargarEnemigos() {
		enemigos  = Files2.streamFromFile("../pruebas/info/MiniJuegoRPG/enemigos")
				.skip(1)
				.map(Personaje::parseo)
				.collect(Collectors.toList());
	}
	
	public static void listadoPersonajes() {
		List<Personaje> ls = new ArrayList<>();
		for(int i = 0; i < aliados.size(); i++) {
			ls.add(aliados.get(i));
			ls.add(enemigos.get(i));
		}
		personajes = ls;
	}
	
	public static void carga() {
		cargarArmas();
		cargarAliados();
		cargarEnemigos();
		listadoPersonajes();
	}
	
	public static void turnoBatalla() {
		System.out.println("################################");
		if(personajes.get(persSeleccionado).getVidaRestante() != 0) {
			Scanner scan = new Scanner(System.in);
			System.out.println("TURNO DE " + personajes.get(persSeleccionado));
			System.out.println("Opciones: 1-Atacar\t2-Inventario\tOtro-Mostrar estado de batalla");
			Integer opc = scan.nextInt();
			if(opc == 1) {
				atacar();
//			TODO Implementar inventario
//			} else if(opc == 2) {
//				
			} else {
				mostrarEstadoBatalla();
			}
		}
		pasarTurno();
		mostrarEstadoBatalla();
		System.out.println("################################");
	}
	
	public static void pasarTurno() {
		persSeleccionado = (persSeleccionado + 1) % (aliados.size() + enemigos.size());
	}
	
	public static void ataqueAliado() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Posibles objetivos:");
		String enemigosPosibles = "";
		for(int i = 0; i < enemigos.size(); i++) {
			if(enemigos.get(i).getVidaRestante() != 0) {
				enemigosPosibles += String.format("%d: %s\n", i, enemigos.get(i));
			}
		}
		System.out.println(enemigosPosibles);
		System.out.print("Elegir objetivo: ");
		aliados.get(persSeleccionado).atacar(scan.nextInt());
	}
	
	public static void ataqueEnemigo() {
		List<Integer> aliadosVivos = IntStream.range(0, aliados.size())
				.filter(i->aliados.get(i).getVidaRestante() > 0)
				.boxed()
				.collect(Collectors.toList());
		
		Integer opcionRandom = (int) Math.random() % aliadosVivos.size();
		enemigos.get(persSeleccionado).atacar(opcionRandom);
	}
	
	public static void atacar() {
		if(aliados.contains(personajes.get(persSeleccionado))) {
			ataqueAliado();
		} else {
			ataqueEnemigo();
		}
	}
	
	public static String batallaTerminada() {
		String res = "";
		if(aliados.stream().allMatch(aliado -> aliado.getVidaRestante() == 0)) {
			res = "Enemigos ganan";
		} else if (enemigos.stream().allMatch(aliado -> aliado.getVidaRestante() == 0)) {
			res = "Aliados ganan";
		}
		return res;
	}
	
	public static void play() {
		carga();
		mostrarEstadoBatalla();
		String res = batallaTerminada();
		while(res.equals("")) {
			turnoBatalla();
			res = batallaTerminada();
		}
		System.out.println(res);
	}
	
	
	
	public static void main(String[] args) {
		play();
	}
}

