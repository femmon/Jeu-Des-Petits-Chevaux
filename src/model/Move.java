package model;

public class Move {
    private Horse movingHorse;
    private Position start;
    private Position finish;
    private Horse kickedHorse;

    public Move() {
        movingHorse = null;
        start = null;
        finish = null;
        kickedHorse = null;
    }

    public Move(Horse movingHorse, Position start, Position finish, Horse kickedHorse) {
        this.movingHorse = movingHorse;
        this.start = start;
        this.finish = finish;
        this.kickedHorse = kickedHorse;
    }

    public Horse getMovingHorse() {
        return movingHorse;
    }

    public void setMovingHorse(Horse movingHorse) {
        this.movingHorse = movingHorse;
    }

    public Position getStart() {
        return start;
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public Position getFinish() {
        return finish;
    }

    public void setFinish(Position finish) {
        this.finish = finish;
    }

    public Horse getKickedHorse() {
        return kickedHorse;
    }

    public void setKickedHorse(Horse kickedHorse) {
        this.kickedHorse = kickedHorse;
    }
}
