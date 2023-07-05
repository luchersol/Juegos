package Casino;

import java.util.List;
import java.util.Scanner;

import Utils.Math2;

public class Ruleta {
    /* 
     * La tirada caerá entre el 0 y el 36 incluidos
     * Puedes apostar a los numeros pares (even) o impares (odd)
     * A uno de los tres conjunto (1 a 12, 13 a 24, 25 a 36)
     * A una de las tres filas posibles
     * A un color (rojo o negro)
     * A un número en específico
     * A una de las mitades (1 a 18, 19 a 36)
     * A una de las casillas en específico
    */
    
    private record Casilla(Integer number, Color color){
        public static Casilla of(int number, Color color){
            return new Casilla(number, color);
        }

        public Paridad getParidad(){
            return this.number == 0 ? Paridad.NULO : this.number % 2 == 0 ? Paridad.PAR : Paridad.IMPAR;
        }

        public int getFila(){
            return this.number % 3;
        }

        public int getColumna(){
            return this.number / 12;
        }

        public int getMitad() {
            return this.number / 18;
        }

        public static Casilla parsearCasilla(int number){
            List<Integer> rojas = List.of(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36);
            Color color = number == 0 ? Color.GREEN : rojas.contains(number) ? Color.RED : Color.BLACK;
            return of(number, color);
        }
    }

    private enum TipoApuesta {COLOR, CASILLA, COLUMNAS, FILAS, MITAD, PARIDAD};
    private enum Paridad {PAR, IMPAR, NULO};
    private enum Color {GREEN, RED, BLACK};

    // private List<Casilla> casillas;
    private Casilla tirada;
    private Jugador jugador;
    private static Scanner scanner = new Scanner(System.in);

    public Ruleta() {
        this.jugador = Jugador.create(0);
    }

    public void tirar(){
        this.tirada = Casilla.parsearCasilla(Math2.getEnteroAleatorio(0, 37));
    }

    public void apostar(int apuesta){
        this.jugador.quitarDinero(apuesta);
        this.tirar();
        this.jugador.darDinero(getPremio(apuesta));
    }

    /* Tipo de apuesta : 
     * Color => Color_{color}
     * Casilla => Casilla_{numero}
     * Tercio de columnas => Columna_{numeroColumna} 
     * Tercio de filas => Fila_{numeroFila}
     * Mitad => Mitad_{numeroMitad}
     * Pares => Par
     * Impar => Impar 
    */

    public Integer getPremio(int apuesta){
        System.out.println("¿Qué tipo de apuesta desea hacer?");
        String[] trozos = scanner.next().split("_");
        TipoApuesta tipo = TipoApuesta.valueOf(trozos[0].toUpperCase());
        Integer data = null;
        Color color = null;
        Paridad paridad = null;
        if(trozos.length == 2) {
            if(tipo.equals(TipoApuesta.COLOR)){
                color = Color.valueOf(trozos[1].toUpperCase());
            } else if(tipo.equals(TipoApuesta.PARIDAD)){
                paridad = Paridad.valueOf(trozos[1].toUpperCase());
            } else {
                data = Integer.valueOf(trozos[1]);
            }
        } 
        return apuesta * switch (tipo) {
            case CASILLA -> getPremioCasilla(data);
            case COLOR -> getPremioColor(color);
            case COLUMNAS -> getPremioColumna(data);
            case FILAS -> getPremioFila(data);
            case MITAD -> getPremioMitad(data);
            case PARIDAD -> getPremioParidad(paridad);
            default -> 0;
        };
    }

    public Integer getPremioCasilla(Integer number){
        return this.tirada.number == number ? 36 : 0;
    }

    public Integer getPremioFila(Integer row){
        return this.tirada.getFila() == row ? 24 : 0;
    }

    public Integer getPremioColumna(Integer column){
        return this.tirada.getColumna() == column ? 24 : 0;
    }

    public Integer getPremioColor(Color color){
        return this.tirada.color.equals(color) ? 17 : 0;
    }

    public Integer getPremioMitad(Integer mitad){
        return this.tirada.getMitad() == mitad ? 18 : 0;
    }

    public Integer getPremioParidad(Paridad paridad){
        return this.tirada.getParidad().equals(paridad) ? 17 : 0;
    }

    public void play(){
        System.out.print("¿Seguro que quieres jugar? [Y/N]:");
        this.jugador.mostrarDineroRestante();
        while (scanner.next().equals("Y")) {
            System.out.println("¿Cuánto quieres apostar?");
            Integer apuesta = scanner.nextInt();
            apostar(apuesta);
            this.jugador.mostrarDineroRestante();
            if (!this.jugador.tieneDinero()) {
                System.out.println("Ya has acabado con tu dinero. Por favor, sal de la instancia");
                break;
            }
            System.out.println("¿Deseas seguir jugando? [Y/N]:");
            if (scanner.next().equals("N"))
                break;
        }
    }


    public static void main(String[] args) {
        Ruleta game = new Ruleta();
        game.play();
    }

}
