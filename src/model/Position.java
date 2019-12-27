package model;

public class Position {
    private Color color;
    private int number;

    public Position(Color color, int number) {
        if (number < 0 || number > 17) throw new IllegalArgumentException("Number of Position needs to be from 0 to 17");
        this.color = color;
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return number == position.number &&
                color == position.color;
    }
}
