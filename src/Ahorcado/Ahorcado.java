package Ahorcado;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Utils.Files2;
import Utils.List2;
import Utils.Math2;

public class Ahorcado {
    private List<String> words;
    private String randomWord;
    private List<Character> choisedWords;
    private Boolean isCompletedWord;
    private Integer attemps;
    private static final Integer MAX_ATTEMPS = 6;
    private static Scanner scan = new Scanner(System.in);

    public Ahorcado() {
        this.attemps = MAX_ATTEMPS;
        this.words = Files2.linesFromFile("info/Ahorcado/palabras")
                                .stream()
                                .flatMap(line -> Stream.of(line.split(",")))
                                .map(word -> word.trim())
                                .collect(Collectors.toList());
        this.isCompletedWord = false;
        this.choisedWords = List2.empty();
        getRandomWord();
    }

    public void getRandomWord(){
        Integer rnd = Math2.getRandom().nextInt(0, words.size());
        this.randomWord = this.words.get(rnd).toUpperCase();
    }

    public void chooseLetter(String letter){
        choisedWords.add(letter.toUpperCase().charAt(0));
        if(!randomWord.contains(letter.toUpperCase())) attemps--;
    }

    public String[] doGallow(){
        String[] res = new String[6];
        res[0] = "_____";
        res[1] = "  |  |";
        res[2] = "     |";
        res[3] = "     |";
        res[4] = "     |";
        res[5] = "    _|_";
        switch(this.attemps){
            case 5 -> {res[2] = "  O  |";}
            case 4 -> {res[2] = "  O  |";res[3] = "  |  |";}
            case 3 -> {res[2] = "  O  |";res[3] = " -|  |";}
            case 2 -> {res[2] = "  O  |";res[3] = " -|- |";}
            case 1 -> {res[2] = "  O  |";res[3] = " -|- |";res[4] = " /   |";}
            case 0 -> {res[2] = "  O  |";res[3] = " -|- |";res[4] = " / \\ |";}
        }
        return res;
    }

    public String doSecretWord(){
        String secretWord = " ";
        for(int letter = 0; letter < randomWord.length(); letter++){
            secretWord += choisedWords.contains(randomWord.charAt(letter)) ? randomWord.charAt(letter) : "_" + " ";
        }
        if(!secretWord.contains("_")) this.isCompletedWord = true;
        return secretWord;
    }

    public void printGame(){
        String[] gallow = doGallow();
        String secretWord = doSecretWord();
        for(int row = 0; row < gallow.length; row++){
            System.out.println(gallow[row]);
        }
        System.out.println(secretWord);
    }

    public void play(){
        printGame();
        while(this.attemps > 0 && !this.isCompletedWord){
            System.out.println("Choise a letter:");
            String letter = scan.nextLine();
            if(letter.isEmpty()) {
                System.out.println("You must choise only a letter, no more.");
                continue;
            } else if(choisedWords.contains(letter.charAt(0))){
                System.out.println("This letter have been choised before.");
                continue;
            }
            chooseLetter(letter);
            printGame();
        }
        String mesg = this.isCompletedWord ? "WINNER" : String.format("LOSER. The answer was %s.", randomWord);
        System.out.println(mesg);
    }

    public static void main(String[] args) {
        Ahorcado game = new Ahorcado();
        game.play();
    }



}
