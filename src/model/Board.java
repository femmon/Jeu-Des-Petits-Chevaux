package model;

import java.util.ArrayList;

import static model.Color.*;

public class Board {
    private Nest[] nests;
    private PathNode path;

    public Board(ArrayList<Player> playerList) {
        nests = new Nest[4];
        Color[] colors = {RED, GREEN, BLUE, YELLOW};
        for (int i = 0; i < 4; i++) {
            nests[i] = new Nest();
            nests[i].setColor(colors[i]);
        }
        resetNest(playerList);

        path = makePath();
    }

    private void resetNest(ArrayList<Player> playerList) {
        for (Player player: playerList) {
            Color color = player.getPlayerSide();

            // Find the nest with the same Color as the player
            for (Nest nest: nests) {
                if (nest.getColor() == color) {
                    nest.getHorseInNest().clear();
                    if (player.getPlayerType() != PlayerType.NONE) {
                        for (int i = 0; i < 4; i++) {
                            nest.add(new Horse(color, i));
                        }
                    }
                    break;
                }
            }
        }
    }

    private PathNode makePath() {
        Color[] colors = {BLUE, RED, GREEN, YELLOW};
        PathNode first10 = null;
        PathNode homeArrival = null;

        for (int colorIndex = 0; colorIndex < colors.length; colorIndex++) {
            PathNode startPath = new PathNode(new Position(colors[colorIndex], 0));
            PathNode slidePath = startPath;
            for (int i = 1; i <= 10; i++) {
                PathNode current = new PathNode(new Position(colors[colorIndex], i));
                slidePath.setNextAroundNode(current);
                slidePath = current;
            }
            if (first10 == null) first10 = slidePath;

            PathNode startHome = new PathNode(new Position(colors[colorIndex], 11));
            PathNode slideHome = startHome;
            for (int i = 12; i <= 17; i++) {
                PathNode current = new PathNode(new Position(colors[colorIndex], i));
                slideHome.setHomePositionNode(current);
                slideHome = current;
            }

            startHome.setNextAroundNode(startPath);

            if (homeArrival != null) slidePath.setNextAroundNode(homeArrival);
            homeArrival = startHome;
        }

        first10.setNextAroundNode(homeArrival);

        return first10;
    }

    public boolean summon(Color color) {
        for (Nest nest: nests) {
            if (nest.getColor() == color) {
                if (nest.isEmpty()) return false;
                PathNode startingSpace = getStartingSpace(color);
                if (startingSpace.getHorse() != null) return false;
                startingSpace.setHorse(nest.getHorseInNest().remove(0));
                return true;
            }
        }

        // Should never reach here
        return false;
    }

    private PathNode getStartingSpace(Color color) {
        PathNode current = path;
        while (!(current.getPosition().equals(new Position(color, 0)))) {
            current = current.getNextAroundNode();
        }
        return current;
    }


    // For debugging

    /**
     * The first four printed lines pieces inside the nests.
     * Each line after that is a node of path. 2 adjacent lines is connected.
     * If a node is connected to home path, the home path node is printed in the same line
     */
    public void printState() {
        System.out.println("-----------------------------------------------------------------");
        for (Nest n: nests) {
            System.out.print(n.getColor() + ": ");
            ArrayList<Horse> horses = n.getHorseInNest();
            for (int i = 0; i < horses.size(); i++) {
                printHorse(horses.get(i));
                if (i != horses.size() - 1) System.out.print(", ");
            }
            System.out.println(".");
        }
        System.out.println("");

        PathNode current = path;
        do {
            Position pos = current.getPosition();
            System.out.print(pos.getColor() + " / " + pos.getNumber() + ": ");
            if (current.getHorse() == null) System.out.print("None");
            else printHorse(current.getHorse());

            PathNode homePath = current.getHomePositionNode();
            while (homePath != null) {
                System.out.print(" | ");
                Position homePathPos = homePath.getPosition();
                System.out.print(homePathPos.getColor() + " / " + homePathPos.getNumber() + ": ");
                if (homePath.getHorse() == null) System.out.print("None");
                else printHorse(homePath.getHorse());

                homePath = homePath.getHomePositionNode();
            }

            System.out.println(".");
            current = current.getNextAroundNode();
        } while (current != path);
        System.out.println("-----------------------------------------------------------------");
    }

    private void printHorse(Horse horse) {
        System.out.print("{" + horse.getColor() + " - " + horse.getId() + "}");
    }
}
