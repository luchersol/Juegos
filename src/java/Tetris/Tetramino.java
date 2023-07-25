package Tetris;

import java.util.function.Predicate;

public class Tetramino {
    private String[][] slots;
    // Tipo T,L(y su inverso),I,O,Zig-Zag(y su inverso)
    
    public Tetramino(int i){
        switch (i) {
            case 0 -> createCube();
            case 1 -> createLine();
            case 2 -> createT();
            case 3 -> createL();
            case 4 -> createL_inv();
            case 5 -> createZigZag();
            default -> createZigZag_inv(); 
        }
    }

    public String get(int i, int j){
        return slots[i][j];
    }

    public int getHeight(){
        return slots.length;
    }

    public int getWidth(){
        return slots[0].length;
    }

    private void createT(){
        createStructure(2, 3, p -> p.i == 0 || p.j == 1);
    }

    private void createL(){
        createStructure(3, 2, p -> p.i == 2 || p.j == 0);
    }

    private void createL_inv(){
        createStructure(3, 2, p -> p.i == 2 || p.j == 1);
    }

    private void createCube(){
        createStructure(2, 2, p -> true);
    }

    private void createLine(){
        createStructure(1, 4, p -> true);
    }

    private void createZigZag(){
        createStructure(2, 3, p -> !(p.i == 0 && p.j == 2 || p.i == 1 && p.j == 0));
    }

    private void createZigZag_inv(){
        createStructure(2, 3, p -> !(p.i == 0 && p.j == 0 || p.i == 1 && p.j == 2));
    }

    private record Pair(int i, int j) {

        public static Pair of(int i, int j){
            return new Pair(i, j);
        }
    }

    private void createStructure(int height, int width, Predicate<Pair> conditionDraw){
        this.slots = new String[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                slots[i][j] = conditionDraw.test(Pair.of(i, j)) ? "X" : "-";
            }
        }
    }
    

    public String toString(){
        String res = "";
        for(int i = 0; i < slots.length; i++){
            for(int j = 0; j < slots[i].length; j++){
                res += slots[i][j] + " ";
            }
            res += "\n";
        }
        return res;
    }

    public static void main(String[] args) {
        int i=0;
        while(i <= 6){
            Tetramino t = new Tetramino(i);
            System.out.println(t);
            i++;
        }
    }
}
