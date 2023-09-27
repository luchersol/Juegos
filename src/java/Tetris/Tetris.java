package Tetris;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Tetris {
    private String[][] slots;
    private int points;
    private Tetramino tetramino;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;
    private static final int SPACE = 5;
    private static final String EMPTY = "-";
    private static Scanner scan = new Scanner(System.in);
    private static int initialX = 1;
    private static int initialY = 3;

    public Tetris(){
        this.points = 0;
        this.slots = new String[HEIGHT+SPACE][WIDTH];
    }

    public void createTetramonio(){
        this.tetramino = Tetramino.createRandomTetramonio();
        // Empieza sustitución en fila 2, columna 3
        for(int i = 0; i < this.tetramino.getHeight(); i++){
            for(int j = 0; j < this.tetramino.getWidth(); j++){
                slots[initialX+i][initialY+j] = this.tetramino.get(i, j);
            }
        }
    }

    public void createTetris(){
        for(int row = 0; row < slots.length; row++){
            for(int column = 0; column < slots[row].length; column++){
                slots[row][column] = row == SPACE ? "_" : EMPTY;
            }
        }
    }

    public void printTetris(){

        for(int row = 0; row < slots.length; row++){
            String res = "";
            for(int column = 0; column < slots[row].length; column++){
                res += slots[row][column] + " ";
            }
            System.out.println(res);
        }

        
        System.out.println(String.format("#### Points: %d ####", points));
    }

    public void moveRight(){
        Boolean isValid = IntStream.range(0, 4).boxed().allMatch(i -> slots[i][WIDTH-1].equals(EMPTY));
        if(isValid){
            for(int i = 0; i < SPACE; i++){
                for(int j = WIDTH - 1; j >= 0; j--){
                    if(slots[i][j].equals("X")){
                        slots[i][j+1] = "X";
                        slots[i][j] = EMPTY;
                    }
                }
            }
        }
    }

    public void moveLeft(){
        Boolean isValid = IntStream.range(0, 4).boxed().allMatch(i -> slots[i][0].equals(EMPTY));
        if(isValid){
            for(int i = 0; i < SPACE; i++){
                for(int j = 1; j < WIDTH; j++){
                    if(slots[i][j].equals("X")){
                        slots[i][j-1] = "X";
                        slots[i][j] = EMPTY;
                    }
                }
            }
        }
        
    }

    public void rotate(){
        // TODO
    }

    public void down(){
        Integer cont = Integer.MAX_VALUE;
        for(int j = 0; j < WIDTH; j++){
            for(int i = SPACE - 1; i >= 0; i--){
                if(slots[i][j].equals("X")){
                    cont = Integer.min(cont, contColision(i, j));
                    break;
                }
            }
        }

        for(int i = 0; i < SPACE; i++){
            for(int j = 0; j < WIDTH; j++){
                if(slots[i][j].equals("X")){
                    slots[i + cont][j] = "X";
                    slots[i][j] = EMPTY;
                }
            }
        }
        
    }

    public void checkLines(){
        for(int i = SPACE + 1; i < slots.length; i++){
            Boolean isLineComplete = Arrays.asList(slots[i]).stream().allMatch(slot -> slot.equals("X"));
            if(isLineComplete){
                points += 100;
                for(int j = i; j > SPACE + 1; j--){
                    for(int z = 0; z < WIDTH; z++){
                        slots[j][z] = slots[j-1][z];
                    }
                }
            }
        }
    }

    public Boolean checkGameOver(){
        return Arrays.asList(slots[SPACE]).stream().anyMatch(slot -> slot.equals("X"));
    }

    public int contColision(int i, int j){
        int res = 0;
        int next = i + 1;
        while(next < slots.length && !slots[next][j].equals("X")){
            res++;
            next++;
        }
        return res;
    }


    /*
     * >: derecha
     * <: izquierda
     * G: girar
     * |: bajar
     */
    public Boolean controller(){
        Boolean finish = false;
        String secuence = scan.nextLine();
        for(int i = 0; i < secuence.length() && !finish; i++){
            switch(secuence.charAt(i)){
                case '>' -> moveRight();
                case '<' -> moveLeft();
                case 'G' -> rotate();
                case '|' -> {
                    down();
                    finish = true;
                }
            }
        }
        return finish;
    }

    public static void main(String[] args) {
        Tetris game = new Tetris();
        game.createTetris();
        while (true) {
            game.createTetramonio();
            game.printTetris();
            Boolean finish_Turn = false;
            do {
                finish_Turn = game.controller();
                game.checkLines();
                game.printTetris();
            } while (!finish_Turn);
            if(game.checkGameOver()){
                break;
            }
            
        }
        

    }

}
