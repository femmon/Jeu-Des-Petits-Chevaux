package model;

/**
 * A pathNode represent a position in the game
 * It contain its current position and the relationship to the next position
 * A pathNode also store a horse if there is a horse in its
*/

public class PathNode {
    private Position position;
    private Horse horse;
    private PathNode nextAroundNode;
    private PathNode homePositionNode;

    public PathNode(Position position) {
        this.position = position;
        horse = null;
        nextAroundNode = null;
        homePositionNode = null;
    }

    public Position getPosition() {
        return position;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public PathNode getNextAroundNode() {
        return nextAroundNode;
    }

    public void setNextAroundNode(PathNode nextAroundNode) {
        this.nextAroundNode = nextAroundNode;
    }

    public PathNode getHomePositionNode() {
        return homePositionNode;
    }

    public void setHomePositionNode(PathNode homePositionNode) {
        this.homePositionNode = homePositionNode;
    }

    public boolean isHorseInNode() {
        return this.horse != null;
    }

    public boolean isHomePosition(Color color) {
        return color == this.position.getColor() && this.position.getNumber() == 11;
    }
}
