package model;

public class Horse {
    private Color color;
    private int id;

    public Horse(Color color, int id) {
        this.color = color;
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
