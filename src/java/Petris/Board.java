package Petris;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Petris.Chip.Type;

public class Board {
    public Player playerBlue; 
    public Player playerRed; 
    public List<PetriDish> petriDishs;

    public Board(){
        this.playerBlue = Player.createPlayer(Color.BLUE);
        this.playerRed = Player.createPlayer(Color.RED);
        this.petriDishs = IntStream.range(0, 7).boxed().map(i -> PetriDish.createPetriDish(i)).collect(Collectors.toList());
        this.playerBlue.putBacterium(this, 2);
        this.playerRed.putBacterium(this, 4);
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
    public void play(Color color){
        System.out.println("Turno jugador " + color + ":");
        if(color.equals(Color.BLUE)) playerBlue.play(this);
        else playerRed.play(this);
    }
    public void doBinaryFision(){
        System.out.println("Fisión binaria");
        for (int i = 0; i < petriDishs.size(); i++) {
            PetriDish petriDish = petriDishs.get(i);
            if(!petriDish.isEmpty() && petriDish.isMonotype()){
                Color color = petriDish.chips.get(0).color;
                if(color.equals(Color.RED)){
                    playerRed.putBacterium(this, i);
                    if(this.getPetriDish(i).countBacteriumByColor(Color.RED) >= 5) {
                        playerRed.changeBacteriumToSarcina(this, i);
                    }
                } else{
                    playerBlue.putBacterium(this, i);
                    if(this.getPetriDish(i).countBacteriumByColor(Color.BLUE) >= 5) {
                        playerBlue.changeBacteriumToSarcina(this, i);
                    }
                }
            }
        }
    }
    public void doContamination(){
        System.out.println("Contaminación");
        for (PetriDish petriDish : petriDishs) {
            if(!petriDish.isEmpty()){
                int numBlue = petriDish.countBacteriumByColor(Color.BLUE),
                    numRed = petriDish.countBacteriumByColor(Color.RED);
                if(numBlue > numRed) playerBlue.contaminationLevel++;
                else if(numRed > numBlue) playerRed.contaminationLevel++;
            }
        }
        this.playerBlue.showContaminationLevel();
        this.playerRed.showContaminationLevel();
    }
    public Map<Player, Long> getChipsPlayer(Boolean filterSarcina){
        return petriDishs.stream()
        .flatMap(petriDishs -> petriDishs.chips.stream())
        .filter(chip -> !filterSarcina || chip.type.equals(Type.SARCINA))
        .collect(Collectors.groupingBy(i -> i.color.equals(Color.RED) ? playerRed : playerBlue , Collectors.counting()));
    }
    public Player getWinner(Boolean filterSarcina){
        Map<Player, Long> dict = getChipsPlayer(filterSarcina);
        long contPlayerBlue = dict.get(this.playerBlue), contPlayerRed = dict.get(this.playerRed);
        return contPlayerBlue < contPlayerRed ? this.playerBlue : contPlayerRed < contPlayerBlue ? this.playerRed : null;
    }
    public void show(){
        BiFunction<Integer, Integer, String> functionFormat = (numSpace, times) -> (" ".repeat(numSpace) + "%s").repeat(times);
        String format1 = functionFormat.apply(15, 2), 
                format2 = functionFormat.apply(10, 3);
        System.out.println(String.format(format1, petriDishs.get(0), petriDishs.get(1)));
        System.out.println(String.format(format2, petriDishs.get(2), petriDishs.get(3), petriDishs.get(4)));
        System.out.println(String.format(format1, petriDishs.get(5), petriDishs.get(6)));
    }

    public static void main(String[] args) {
        Board board = Board.createBoard();
        board.show();
    }
}
