package Tetris;

public class Tetris {
    private String[][] slots;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;
    private static final String EMPTY = "-";

    public Tetris(){
        this.slots = new String[HEIGHT+5][WIDTH];
    }

    public void createTetramonio(){

    }

    public void createTetris(){
        for(int row = 0; row < slots.length; row++){
            for(int column = 0; column < slots[row].length; column++){
                slots[row][column] = row == 4 ? "_" : EMPTY;
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
    }

    public void bajar(){
    
    }

    public static void main(String[] args) {
        Tetris game = new Tetris();
        game.createTetris();
        game.printTetris();

    }

}
