package Tetris;

import java.util.function.Predicate;

import Utils.Math2;

public class Tetramino {
    // Tipo T,L(y su inverso),I,O,Zig-Zag(y su inverso)
    private enum TipoTetramino {T, L, L_INV, I, O, ZIG_ZAG, ZIG_ZAG_INV}

    private String[][] slots;
    private TipoTetramino tipo;
    
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

    public static Tetramino createRandomTetramonio(){
        return new Tetramino(Math2.getEnteroAleatorio(0,7));
    }


    public void rotate(){
        switch(this.tipo){
            case I -> null;
            case L -> null;
            case L_INV -> null;
            case O -> null;
            case T -> null;
            case ZIG_ZAG -> null;
            case ZIG_ZAG_INV -> null;   
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
        this.tipo = TipoTetramino.T;
    }

    private void createL(){
        createStructure(3, 2, p -> p.i == 2 || p.j == 0);
        this.tipo = TipoTetramino.L;
    }

    private void createL_inv(){
        createStructure(3, 2, p -> p.i == 2 || p.j == 1);
        this.tipo = TipoTetramino.L_INV;
    }

    private void createCube(){
        createStructure(2, 2, p -> true);
        this.tipo = TipoTetramino.O;
    }

    private void createLine(){
        createStructure(1, 4, p -> true);
        this.tipo = TipoTetramino.I;
    }

    private void createZigZag(){
        createStructure(2, 3, p -> !(p.i == 0 && p.j == 2 || p.i == 1 && p.j == 0));
        this.tipo = TipoTetramino.ZIG_ZAG;
    }

    private void createZigZag_inv(){
        createStructure(2, 3, p -> !(p.i == 0 && p.j == 0 || p.i == 1 && p.j == 2));
        this.tipo = TipoTetramino.ZIG_ZAG_INV;
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
