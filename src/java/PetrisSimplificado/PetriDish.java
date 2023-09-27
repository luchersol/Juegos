package PetrisSimplificado;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PetriDish {
    public int id;
    public Map<Player, Integer> chips;

    public PetriDish(int id, Player ...players){
        this.id = id;
        this.chips = Arrays.asList(players).stream()
                            .collect(Collectors.toMap(Function.identity(), value -> 0));
    }
    public static PetriDish createPetriDish(int id, Player ...players){
        return new PetriDish(id, players);
    }
    public void variation(Player player, int variation){
        int newQuantity = this.chips.get(player) + variation;
        this.chips.put(player, newQuantity);
    }
    public void increment(Player player){
        variation(player, 1);
    }
    public void decrement(Player player){
        variation(player, -1);
    }
    public boolean isEmpty() {
        return this.chips.values().stream().allMatch(value -> value == 0);
    }
    public boolean isMonotype() {
        int numColors = 0;
        for (Integer value : this.chips.values()) {
            if(value > 0) numColors++;
        }
        return numColors == 1;
    }
    public boolean containAnyBacteriumByPlayer(Player player){
        return this.chips.get(player) > 0;
    }
    public int countBacteriumByPlayer(Player player){
        int res = chips.get(player);
        return res == 5 ? 1 : res;
    }
    public boolean containsSarcinaByPlayer(Player player){
        return this.chips.get(player) == 5;
    }

}
