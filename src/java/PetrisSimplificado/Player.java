package PetrisSimplificado;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Player {
    public String color;
    public int contaminationLevel;
    public int numBacteriums;
    public int numSarcinas;
    private static Scanner scanner = new Scanner(System.in);

    public Player(String color){
        this.color = color;
        this.contaminationLevel = 0;
        this.numBacteriums = 20;
        this.numSarcinas = 4;
    }
    public static Player createPlayer(String color){
        return new Player(color);
    }
    public void addBacterium(Board board, int indexDish){
        this.numBacteriums--;
        board.getPetriDish(indexDish).increment(this);
    }
    public void addSarcina(){
        this.numBacteriums += 5;
        this.numSarcinas--;
    }
    public void removeBacterium(Board board, int indexDish){
        this.numBacteriums++;
        board.getPetriDish(indexDish).decrement(this);
    }
    public void moveBacterium(Board board, int source, int destination, int quantity){
        if(isValid(board, source, destination, quantity)){
            for(int i = 0; i < quantity; i++){
                removeBacterium(board, source);
                addBacterium(board, destination);
            }
        }
    }
    public void incrementContamination(){
        this.contaminationLevel++;
    }
    public Boolean isValid(Board board, int source, int destination, int quantity){
        Boolean isMoveToAdjacentDish = board.getAdjacentDishsByIndex(source).contains(destination),
                containsBacterium = board.getPetriDish(source).containAnyBacteriumByPlayer(this),
                notEqualNumberBacterium = board.getPetriDish(destination).countBacteriumByPlayer(this) != board.getPetriDish(destination).countBacteriumByPlayer(this) + quantity,
                notMoreFiveBacterium = board.getPetriDish(destination).countBacteriumByPlayer(this) + quantity < 6,
                notContainSarcina = !board.getPetriDish(destination).containsSarcinaByPlayer(this),
                enoughBacterium = board.getPetriDish(source).countBacteriumByPlayer(this) <= quantity;
        Map<String,Boolean> validations = new HashMap<String,Boolean>();
        validations.put("isMoveToAdjacentDish", isMoveToAdjacentDish);
        validations.put("containsBacterium", containsBacterium);
        validations.put("notEqualNumberBacterium", notEqualNumberBacterium);
        validations.put("notMoreFiveBacterium", notMoreFiveBacterium);
        validations.put("notContainSarcina", notContainSarcina);
        validations.put("enoughBacterium", enoughBacterium);
        Boolean isValid = true;
        for(Map.Entry<String,Boolean> validation: validations.entrySet()){
            if(!validation.getValue()) {
                System.out.println("Error: " + validation.getKey());
                isValid = false;
            }
        }
        return isValid;
    }
    public void showInfo(){
        System.out.printf("Color:%s NumBacterium:%d, NumSarcinas:%d, Contamination:%d\n", this.color, this.numBacteriums, this.numSarcinas, this.contaminationLevel);
    }
    public void showContaminationLevel(){
        System.out.println("Lv Contamination: " + this.contaminationLevel);
    }
    public void play(Board board){
        // Formato: *source* ; *destination*:*quantity* , ...
        String moves = scanner.nextLine();
        String[] sourceDestination = moves.split(";"); 
        String[] differentDestination = sourceDestination[1].split(",");
        int source = Integer.valueOf(sourceDestination[0].trim());
        for (String elem : differentDestination) {
            String[] p = elem.split(":");
            int destination = Integer.valueOf(p[0].trim()), 
                quantity = Integer.valueOf(p[1].trim());
            moveBacterium(board, source, destination, quantity);
        }
    }
    @Override
    public String toString() {
        return "Player " + color;
    }

    
}