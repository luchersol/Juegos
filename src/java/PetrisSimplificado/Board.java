package PetrisSimplificado;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    public Player playerBlue;
    public Player playerRed; 
    public List<PetriDish> petriDishs;

    public Board(){
        this.playerBlue = Player.createPlayer("Azul");
        this.playerRed = Player.createPlayer("Rojo");
        this.petriDishs = IntStream.range(0, 7).boxed()
                                    .map(i -> PetriDish.createPetriDish(i, this.playerBlue, this.playerRed))
                                    .collect(Collectors.toList());
        this.playerBlue.addBacterium(this, 2);
        this.playerRed.addBacterium(this, 4);
    }
    public static Board createBoard(){
        return new Board();
    }
    public List<Integer> getAdjacentDishsByIndex(int index){
        return switch(index){
            case 0 -> Arrays.asList(1,2,3);
            case 1 -> Arrays.asList(0,3,4);
            case 2 -> Arrays.asList(0,3,5);
            case 3 -> Arrays.asList(0,1,2,4,5,6);
            case 4 -> Arrays.asList(1,3,6);
            case 5 -> Arrays.asList(2,3,6);
            case 6 -> Arrays.asList(3,4,5);
            default -> null;
            
        };
    }
    public PetriDish getPetriDish(int index){
        return petriDishs.get(index);
    }
    public void playBlue(){
        System.out.println("Turno jugador azul:");
        playerBlue.play(this);
    }
    public void playRed(){
        System.out.println("Turno jugador rojo:");
        playerRed.play(this);
    }
    public void doBinaryFision(){
        System.out.println("Fisión binaria");
        for (int i = 0; i < petriDishs.size(); i++) {
            PetriDish petriDish = getPetriDish(i);
            if(!petriDish.isEmpty() && petriDish.isMonotype()){
                if(petriDish.chips.get(this.playerRed) > 0){
                    if(!getPetriDish(i).containsSarcinaByPlayer(this.playerRed)){
                        playerRed.addBacterium(this, i);
                        if(getPetriDish(i).chips.get(this.playerRed) == 5) playerRed.addSarcina();
                    }
                } else{
                    if(!getPetriDish(i).containsSarcinaByPlayer(this.playerBlue)){
                        playerBlue.addBacterium(this, i);
                        if(getPetriDish(i).chips.get(this.playerBlue) == 5) playerBlue.addSarcina();
                    }
                }
            }
        }
    }
    public void doContamination(){
        doBinaryFision();
        System.out.println("Contaminación");
        for (PetriDish petriDish : petriDishs) {
            if(!petriDish.isEmpty() && !petriDish.isMonotype()){
                int numBlue = petriDish.countBacteriumByPlayer(this.playerBlue),
                    numRed = petriDish.countBacteriumByPlayer(this.playerRed);
                if(numBlue > numRed) playerBlue.incrementContamination();
                else if(numRed > numBlue) playerRed.incrementContamination();
            }
        }
        this.playerBlue.showContaminationLevel();
        this.playerRed.showContaminationLevel();
    }
    public Map<Player, Integer> getChipsPlayer(boolean filterSarcina){   
        Map<Player, Integer> res = new HashMap<Player, Integer>(Map.of(playerBlue, 0, playerRed, 0));
        for(PetriDish petriDish: this.petriDishs){
            for(Map.Entry<Player, Integer> entry :petriDish.chips.entrySet()){
                Integer chips = entry.getValue();
                chips = chips == 5 ? filterSarcina ? 0 : 1 : chips;
                Integer newValue = res.get(entry.getKey()) + chips;
                res.put(entry.getKey(), newValue);
            }
        }
        return res;
    }
    public Player getWinner(boolean filterSarcina){
        Map<Player, Integer> dict = getChipsPlayer(filterSarcina);
        int contPlayerBlue = dict.get(this.playerBlue),
            contPlayerRed = dict.get(this.playerRed);
        return contPlayerBlue < contPlayerRed ? this.playerBlue : contPlayerRed < contPlayerBlue ? this.playerRed : null;
    }
    public void checkPlayerCanMove(Player player){
        for (PetriDish petriDish : petriDishs) {
            if(petriDish.containAnyBacteriumByPlayer(player)){
                Integer limit = petriDish.countBacteriumByPlayer(player);
                List<Integer> adjacents = getAdjacentDishsByIndex(petriDish.id);
                for (Integer adj : adjacents) {
                    
                }
            }
        }
    }
    public String dishString(int index){
        Function<Integer,String> parseNum = num -> num == 5 ? "X" : String.valueOf(num);
        String numBlue = parseNum.apply(getPetriDish(index).chips.get(this.playerBlue)),
                numRed = parseNum.apply(getPetriDish(index).chips.get(this.playerRed));
        return String.format("P%d: %s|%s", index, numBlue, numRed);
    }
    public void showInfoPlayers(){
        this.playerBlue.showInfo();
        this.playerRed.showInfo();
    }
    public void show(){
        BiFunction<Integer, Integer, String> functionFormat = (numSpace, times) -> (" ".repeat(numSpace) + "%s").repeat(times);
        String format1 = functionFormat.apply(15, 2), 
                format2 = functionFormat.apply(10, 3);
        System.out.println(String.format(format1, dishString(0), dishString(1)));
        System.out.println(String.format(format2, dishString(2), dishString(3), dishString(4)));
        System.out.println(String.format(format1, dishString(5), dishString(6)));
    }

    public static void main(String[] args) {
        Board board = Board.createBoard();
        board.show();
    }
}
