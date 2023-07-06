package Casino;

import java.util.List;
import java.util.Scanner;

public class VideoPoker {

    private Jugador jugador;
    private List<Carta> baraja;
    private static Scanner scan = new Scanner(System.in);

    public VideoPoker(){
        this.jugador = Jugador.create(0);
    }

    public void tirar(){
        this.baraja = Baraja.getNCartas(5);
    }

    public void mostrarCartas(){
        baraja.forEach(carta -> System.out.printf("\t%s\t|",carta));
        System.out.println();
    }

    public void apostar(Integer apuesta){
        jugador.quitarDinero(apuesta);
        tirar();
        mostrarCartas();
        Integer ganancia = Carta.getMultiplicadorAgrupacion(baraja) * apuesta;
        if(ganancia > 0) System.out.printf("Enhorabuena, has ganado %d euros\n", ganancia);
        else System.out.println("Lo siento, has perdido. Otra vez será");
        jugador.darDinero(ganancia);
    }

    public static void play(){
        VideoPoker game = new VideoPoker();
        System.out.print("¿Seguro que quieres jugar? [Y/N]:");
        Boolean empezarJuego = scan.next().equals("Y");
        game.jugador.mostrarDineroRestante();
        Integer apuesta = 0;
        while(empezarJuego){
            System.out.print("¿Cuánto dinero quiere apostar?\nEuros: ");
            apuesta = scan.nextInt();
            while(apuesta > game.jugador.getDinero()){
                System.out.println("No posee tanto dinero\nEuros: ");
                apuesta = scan.nextInt();
            }
            game.apostar(apuesta);
            game.jugador.mostrarDineroRestante();
            if(!game.jugador.tieneDinero()){
                System.out.println("Ya has acabado con tu dinero. Por favor, sal de la instancia");
                break;
            }
            System.out.println("¿Deseas seguir jugando? [Y/N]:");
            if(scan.next().equals("N")) break;
        }     
    }

    public static void main(String[] args) {
        play();
    }


}
