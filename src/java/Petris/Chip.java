package Petris;

public class Chip {
    enum Type{BACTERIUM, SARCINA};
    public Type type;
    public Color color;

    public Chip(Type type, Color color){
        this.type = type;
        this.color = color;
    }
    public static Chip createBacterium(Color color){
        return new Chip(Type.BACTERIUM, color);
    }
    public static Chip createSarcina(Color color){
        return new Chip(Type.SARCINA, color);
    }
}
