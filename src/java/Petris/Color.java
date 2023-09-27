package Petris;

public enum Color { 
    RED, BLUE;
    public static Color reverse(Color color){
        return color.equals(RED) ? BLUE : RED;
    }
}
