package Petris;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Player {
    public Color color;
    public int contaminationLevel;
    public List<Chip> bacteriums;
    public List<Chip> sarcinas;
    private static Scanner scanner = new Scanner(System.in);
    public Player(Color color){
        this.color = color;
        this.contaminationLevel = 0;
        this.bacteriums = IntStream.range(0, 20).boxed().map(i -> Chip.createBacterium(color)).collect(Collectors.toList());
        this.sarcinas = IntStream.range(0, 4).boxed().map(i -> Chip.createSarcina(color)).collect(Collectors.toList());
    }
    public static Player createPlayer(Color color){
        return new Player(color);
    }
    public void putBacterium(Board board, int indexDish){
        board.getPetriDish(indexDish).chips.add(bacteriums.remove(0));
    }
    public void removeBacterium(Board board, int indexDish){
        Chip chip = board.getPetriDish(indexDish).removeBateriumByColor(color);
        this.bacteriums.add(chip);
    }
    public void putSarcina(Board board, int indexDish){
        board.petriDishs.get(indexDish).chips.add(sarcinas.remove(0));
    }
    public void moveBacterium(Board board, int source, int destination, int quantity){
        if(isValid(board, source, destination, quantity)){
            for(int i = 0; i < quantity; i++){
                Chip chip = board.getPetriDish(source).removeBateriumByColor(this.color);
                board.getPetriDish(destination).chips.add(chip);
            }
            if(board.getPetriDish(destination).countBacteriumByColor(this.color) >= 5) {
                changeBacteriumToSarcina(board, quantity);
            }
        }
    }
    public void changeBacteriumToSarcina(Board board, int index){
        IntStream.range(0, 5).forEach(i -> this.removeBacterium(board, index));
        this.putSarcina(board, index);
    }
    public void showContaminationLevel(){
        System.out.println("Lv Contamination: " + this.contaminationLevel);
    }
    public Boolean isValid(Board board, int source, int destination, int quantity){
        Boolean isMoveToAdjacentDish = board.getAdjacentDishsByIndex(source).contains(destination),
                containsBacterium = board.getPetriDish(source).containAnyBacteriumByColor(this.color),
                notEqualNumberBacterium = board.getPetriDish(destination).countBacteriumByColor(Color.reverse(this.color)) != board.getPetriDish(destination).countBacteriumByColor(this.color) + quantity,
                notMoreFiveBacterium = board.getPetriDish(destination).countBacteriumByColor(this.color) + quantity < 6,
                notContainSarcina = !board.getPetriDish(destination).containsSarcinaByColor(this.color),
                enoughBacterium = board.getPetriDish(source).countBacteriumByColor(this.color) <= quantity;
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
}