package model;

/*
* Mỗi pathnode là một vị trí của game.
* Và quan hệ của nó vs nhau cố định. Quan hệ này chính là nextAround và nextHome.
* Khi di chuyển mình chỉ get/setHorse. SetNextAround/nextHome chỉ dùng khi build cái graph
* */

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
}
