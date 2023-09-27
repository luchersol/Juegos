package Petris;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import Petris.Chip.Type;

public class PetriDish {
    public int id;
    public List<Chip> chips;

    public PetriDish(int id){
        this.id = id;
        this.chips = new ArrayList<>();
    }
    public static PetriDish createPetriDish(int id){
        return new PetriDish(id);
    }
    public boolean isEmpty() {
        return this.chips.size() == 0;
    }
    public boolean isMonotype() {
        return this.chips.stream().map(i -> i.color).distinct().count() == 1;
    }
    public boolean containAnyBacteriumByColor(Color color){
        return chips.stream().anyMatch(chip -> chip.color.equals(color));
    }
    public int countBacteriumByColor(Color color){
        return (int) chips.stream().filter(chip -> chip.color.equals(color)).count();
    }
    public boolean containsSarcinaByColor(Color color){
        return this.chips.stream().anyMatch(i -> i.type.equals(Type.SARCINA) && i.color.equals(color));
    }
    public Chip removeBateriumByColor(Color colorPlayer){
        Chip res = null;
        for (Chip chip: chips) {
            if(chip.color.equals(colorPlayer) && chip.type.equals(Type.BACTERIUM)){
                chips.remove(chip);
                res = chip;
                break;
            }
        }
        return res;
    }
    
    @Override
    public String toString() {
        String numBlue = containsSarcinaByColor(Color.BLUE) ? "X" : String.valueOf(countBacteriumByColor(Color.BLUE)),
                numRed = containsSarcinaByColor(Color.RED) ? "X" : String.valueOf(countBacteriumByColor(Color.RED));
        return String.format("P%d: %s|%s", id, numBlue, numRed);
    }

}
