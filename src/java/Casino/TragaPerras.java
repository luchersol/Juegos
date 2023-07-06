package Casino;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Utils.Math2;

public class TragaPerras {
    private int[][] slots = new int[3][3];
    private Jugador jugador;
    private Scanner scan = new Scanner(System.in);
    private static final List<Integer> PRIZES_LIST = List.of(2, 6, 8, 8, 15, 15, 100, 300);
    private static int[] column = new int[7];

    public TragaPerras() {
        this.jugador = Jugador.create(0);
        for (int i = 0; i < column.length; i++) {
            column[i] = i+1;
        }
    }

    public void thorw() {
        for (int j = 0; j < 3; j++) {
            int random = Math2.getRandom().nextInt(0, column.length);
            for (int i = 0; i < 3; i++) {
                int stop = (random + i) % column.length;
                slots[i][j] = column[stop];
            }
        }
    }

    public void staker(int numLines, int mult) {
        jugador.quitarDinero(numLines * mult);
        thorw();
        printMachine();
        int prize = mult * calcTotalPrize(numLines);
        jugador.darDinero(prize);
    }

    public void printMachine(){
        String res = "3 |       | 3\n";
        res += String.format("2 | %d %d %d | 2\n", slots[0][0], slots[0][1], slots[0][2]);
        res += String.format("1 | %d %d %d | 1\n", slots[1][0], slots[1][1], slots[1][2]);
        res += String.format("2 | %d %d %d | 2\n", slots[2][0], slots[2][1], slots[2][2]);
        res += "3 |       | 3\n";
        System.out.println(res);
    }

    public int calcLinePrize(int a, int b, int c) {
        /*
         * DIFERENTES PREMIOS
         * 7 -> 7 7 7 == 300
         * 6 -> 6 6 6 == 100
         * 5 -> 5 5 5 == 15
         * 4 -> 4 4 4 == 15
         * 3 -> 3 3 3 == 8
         * 2 -> 2 2 2 == 8
         * 1 -> 1 1 - == 6
         * 0 -> 1 - - == 2
         */
        int prize = 0;

        for (int i = 1; i <= 7; i++) {
            if (i > 1) {
                if (isIntEquals(i, a, b, c)) {
                    prize += PRIZES_LIST.get(i);
                }
            } else {
                if (isIntEquals(i, a, b) || isIntEquals(i, a, c) || isIntEquals(i, b, c)) {
                    prize += PRIZES_LIST.get(1);
                } else if (isIntEquals(i, a) || isIntEquals(i, b) || isIntEquals(i, c)) {
                    prize += PRIZES_LIST.get(0);
                }
            }
        }

        return prize;
    }

    public int calcTotalPrize(int numLines){
        return switch (numLines) {
            case 1 -> calcPrizeLineOne();
            case 2 -> calcPrizeLineTwo();
            case 3 -> calcPrizeLineThree();
            default -> 0;
        };
    }

    public int calcPrizeLineOne(){
        return calcLinePrize(slots[1][0], slots[1][1], slots[1][2]);
    }

    public int calcPrizeLineTwo(){
        return calcLinePrize(slots[0][0], slots[0][1], slots[0][2]) +
                calcLinePrize(slots[2][0], slots[2][1], slots[2][2]) +
                calcPrizeLineOne();
    }

    public int calcPrizeLineThree(){
        return calcLinePrize(slots[0][0], slots[1][1], slots[2][2]) +
                calcLinePrize(slots[0][2], slots[1][1], slots[2][0]) +
                calcPrizeLineTwo();
    }

    public boolean isIntEquals(int value, Integer... elements) {
        return Arrays.asList(elements).stream().allMatch(e -> e == value);
    }

    public void play() {
        System.out.print("¿Seguro que quieres jugar? [Y/N]:");
        boolean empezarJuego = scan.next().equals("Y");
        jugador.mostrarDineroRestante();
        int numLines;
        int mult;
        while (empezarJuego) {
            
            do {
                System.out.println("¿Cuánto hasta que línea quieres activar?\n1. Primera\t\t2. Segunda\t\t3. Tercera");
                numLines = scan.nextInt();
            } while (numLines < 0 || numLines > 3 );
            do {
                System.out.println("¿Cuánto quieres apostar?\n1. x1\t\t2. x2\t\t3. x3");
                mult = scan.nextInt();
            } while (mult < 0 || mult > 3 );

            staker(numLines, mult);
            jugador.mostrarDineroRestante();
            if (!jugador.tieneDinero()) {
                System.out.println("Ya has acabado con tu dinero. Por favor, sal de la instancia");
                break;
            }
            System.out.println("¿Deseas seguir jugando? [Y/N]:");
            if (scan.next().equals("N"))
                break;
        }
    }

    public static void main(String[] args) {
        TragaPerras game = new TragaPerras();
        game.play();
    }
}
