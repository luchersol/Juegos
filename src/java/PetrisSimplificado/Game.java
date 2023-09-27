package PetrisSimplificado;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    enum Turn{PROPAGATION_RED, PROPAGATION_BLUE, BINARY_FISION, CONTAMINATION}
    public List<Turn> turns;
    public Board board;
    public Player winner;

    public Game(){
        this.winner = null;
        this.board = Board.createBoard();
        Turn[] propagation1 = {Turn.PROPAGATION_BLUE, Turn.PROPAGATION_RED};
        Turn[] propagation2 = {Turn.PROPAGATION_RED, Turn.PROPAGATION_BLUE};
        Turn[][] propagations = {propagation1, propagation2};
        this.turns = new ArrayList<>();
        int i = 0;
        int contamination = 0;
        while(contamination < 3){
            turns.addAll(Arrays.stream(propagations[i % 2]).toList());
            i++;
            if(i%3 == 0) {
                turns.add(Turn.CONTAMINATION);
                contamination++;
            } else {
                turns.add(Turn.BINARY_FISION);
            }
        }
    }
    public void showTurns(){
        for (Turn turn: this.turns) {
           System.out.println(turn);
           if(turn.equals(Turn.CONTAMINATION)) System.out.println("--------");
        }
    }
    public void showWinner(){
        System.out.println(String.format("The winner is %s", winner));
    }
    public void play(){
        System.out.println("Init Game");
        for (Turn turn : turns) {
            this.board.show();
            switch (turn) {
                case PROPAGATION_BLUE -> board.playBlue();
                case PROPAGATION_RED -> board.playRed();
                case BINARY_FISION -> board.doBinaryFision();
                case CONTAMINATION -> board.doContamination();
            }
            if (winner != null) break;
        }
        if(winner == null){
            winner = board.getWinner(false);
            if (winner == null) winner = board.getWinner(true);
        }
        showWinner();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
