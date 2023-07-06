package MiniJuegoRPG;

import java.util.ArrayList;
import java.util.List;

public class Personaje {
	private String nombre;
	private Integer vidaTotal;
	private Integer vidaRestante;
	private Arma arma;
	private List<Objeto> inventario;
	private Tipo tipo;
	private Tipo debilidad;
	private Efecto estado;
	
	public Personaje(String nombre, Integer vida, Arma arma, List<Objeto> inventario, Tipo tipo, Tipo debilidad, Efecto estado) {
		this.nombre = nombre;
		this.vidaTotal = vida;
		this.vidaRestante = vida;
		this.arma = arma;
		this.inventario = inventario;
		this.tipo = tipo;
		this.debilidad = debilidad;
		this.estado = estado;
	}
	
	public static Personaje crearPersonaje(String nombre, Integer vida, Arma arma, String strTipo) {
		Tipo tipo = Tipo.valueOf(strTipo);
		return new Personaje(nombre, 
				vida, 
				arma, 
				new ArrayList<Objeto>(), 
				tipo, 
				Tipo.debilidad(tipo),
				Efecto.NINGUNO);
	}
	
	public void mostrarInfo() {
		System.out.println(this.toString());
	}
	
	public void mostrarInventario() {
		String inventario = "";
		for(Objeto obj: this.inventario) {
			inventario += "+" + obj.toString() + "\n";
		}
		System.out.println(inventario);
	}
	
	public void atacar(Integer index) {
		Personaje obj = Juego.enemigos.get(index);
		Integer daño = this.arma.getDaño();
		Boolean isCritico = Math.random() >= 0.5;
		Boolean isSuperEficaz = obj.debilidad.equals(this.tipo);
		Boolean isPocoEficaz = this.debilidad.equals(obj.tipo);
		if(isCritico) {
			daño = (int) (daño * 1.5);
			System.out.println("GOLPE CRÍTICO");
		}
		if(isSuperEficaz) {
			daño = (int) (daño * 1.5);
			System.out.println("Es super eficaz");
		}
		if(isPocoEficaz) {
			daño = (int) (daño * 0.5);
			System.out.println("Es poco eficaz");
		}
		obj.bajarVida(daño);
		this.causarEstado(obj);
		this.arma.bajarDurabilidad();
		if(this.arma.getDurabilidad() == 0) {
			System.out.println("El arma se ha roto");
			this.arma = Juego.armas.get(0);
		}
	}
	
	public void bajarVida(Integer cant) {
		this.vidaRestante -= cant;
		if(this.vidaRestante < 0) this.vidaRestante = 0;
	}

	public void causarEstado(Personaje obj) {
		if(obj.estado.equals(Efecto.NINGUNO) && !this.arma.getEfecto().equals(Efecto.NINGUNO)) {
			Boolean causarEfecto = Math.random() <= 0.1;
			if(causarEfecto) obj.estado = this.arma.getEfecto();
		}
	}
	
	public Integer getVidaRestante() {
		return this.vidaRestante;
	}
	
	public static Personaje parseo(String linea) {
		String[] partes = linea.split(";");
		String nombre = partes[0].trim();
		Integer vida = Integer.valueOf(partes[1].trim());
		Integer indexArma = Integer.valueOf(partes[2].trim());
		Arma arma = Juego.armas.get(indexArma);
		String strTipo = partes[3].trim();
		return crearPersonaje(nombre, vida, arma, strTipo);
	}
	
	@Override
	public String toString() {
		return String.format("Nombre: %s\t\tVida: %d/%d", this.nombre, this.vidaRestante, this.vidaTotal);
	}
	
}
