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
        return false;
    }

    private PathNode getStartingSpace(Color color) {
        PathNode current = path;
        while (!(current.getPosition().equals(new Position(color, 0)))) {
            current = current.getNextAroundNode();
        }
        return current;
    }

    public boolean move(Color color, int id, int moves) {
        if (id < 0 || id > 3) {
            throw new IllegalArgumentException();
        }
        if (moves < 1 || moves > 12) {
            throw new IllegalArgumentException();
        }

        if (isHorseInNest(color, id)) {
            if (moves == 6) return summon(color);
            else return false;
        }

        PathNode nodeWithHorse = findHorseInPath(color, id);
        if (isMoveInMovePath(nodeWithHorse)) return movePath(nodeWithHorse, moves);
        else return moveHomePath(nodeWithHorse, moves);
    }

    private boolean isHorseInNest(Color color, int id) {
        for (Nest nest: nests) {
            if (nest.getColor() == color) {
                for (Horse horse: nest.getHorseInNest()) {
                    if (horse.getId() == id) return true;
                }
                return false;
            }
        }
        return false;
    }

    private PathNode findHorseInPath(Color color, int id) {
        PathNode current = path;
        do {
            Horse currentHorse = current.getHorse();
            if (currentHorse != null) {
                if (currentHorse.getColor() == color && currentHorse.getId() == id) {
                    return current;
                }
            }

            PathNode homePath = current.getHomePositionNode();
            while (homePath != null) {
                Horse homePathHorse = homePath.getHorse();
                if (homePathHorse != null) {
                    if (homePathHorse.getColor() == color && homePathHorse.getId() == id) {
                        return homePath;
                    }
                }
                homePath = homePath.getHomePositionNode();
            }
            current = current.getNextAroundNode();
        } while (current != path);
        return null;
    }

    private boolean isMoveInMovePath(PathNode nodeWithHorse) {
        Position position = nodeWithHorse.getPosition();
        if (position.getNumber() < 11) return true;
        else if (position.getNumber() == 11) {
            if (position.getColor() == nodeWithHorse.getHorse().getColor()) return false;
            return true;
        } else {
            return false;
        }
    }

    private boolean movePath(PathNode nodeWithHorse, int moves) {
        Horse horseToMove = nodeWithHorse.getHorse();
        PathNode current = nodeWithHorse;
        // Go moves - 1 steps
        for (int i = 0; i < moves - 1; i++) {
            // Can't go pass home arrival
            if (current.getPosition().equals(new Position(horseToMove.getColor(), 11))) {
                return false;
            }

            current = current.getNextAroundNode();
            if (current.getHorse() != null) return false;
        }

        // Can't go pass home arrival
        if (current.getPosition().equals(new Position(horseToMove.getColor(), 11))) {
            return false;
        }

        current = current.getNextAroundNode();
        Horse horseAtDestination = current.getHorse();
        if (horseAtDestination == null) {
            nodeWithHorse.setHorse(null);

            current.setHorse(horseToMove);
            return true;
        } else if (horseAtDestination.getColor() == horseToMove.getColor()) return false;
        else {
            returnHorse(current.getHorse());
            nodeWithHorse.setHorse(null);

            current.setHorse(horseToMove);
            return true;
        }
    }

    private void returnHorse(Horse horse) {
        for (Nest nest: nests) {
            if (nest.getColor() == horse.getColor()) {
                nest.add(horse);
            }
        }
    }

    private boolean moveHomePath(PathNode nodeWithHorse, int moves) {
        Position position = nodeWithHorse.getPosition();
        Horse horse = nodeWithHorse.getHorse();
        // If standing at home arrival
        if (position.equals(new Position(horse.getColor(), 11))) {
            PathNode current = nodeWithHorse;
            for (int i = 0; i < moves; i++) {
                current = current.getHomePositionNode();
                if (current.getHorse() != null) return false;
            }

            nodeWithHorse.setHorse(null);
            current.setHorse(horse);
            return true;
        } else {
            PathNode destination = nodeWithHorse.getHomePositionNode();
            if (destination.getHorse() != null) return false;
            // Need specific moves to get to next home path
            if (destination.getPosition().getNumber() - 11 == moves) {
                destination.setHorse(nodeWithHorse.getHorse());
                nodeWithHorse.setHorse(null);
                return true;
            } else return false;
        }
    }

    // For debugging

    /**
     * Option:
     *   "" - print nest with node/node with horses/node connected to homepath
     *   "ignore-nest" - print node with horses/node connected to homepath
     *   "verbose" - print every nest/node
     * The first four printed lines pieces inside the nests.
     * Each line after that is a node of path. 2 adjacent lines is connected.
     * If a node is connected to home path, the home path node is printed in the same line
     */
    public void printState(String mode) {
        System.out.println("-----------------------------------------------------------------");
        for (Nest n: nests) {
            if (mode == "ignore-nest") continue;
            ArrayList<Horse> horses = n.getHorseInNest();

            if (mode != "verbose" && horses.size() == 0) continue;

            System.out.print(n.getColor() + ": ");
            for (int i = 0; i < horses.size(); i++) {
                printHorse(horses.get(i));
                if (i != horses.size() - 1) System.out.print(", ");
            }
            System.out.println(".");
        }
        System.out.println("");

        PathNode current = path;
        do {
            if (mode != "verbose" && current.getHorse() == null && current.getHomePositionNode() == null) {
                current = current.getNextAroundNode();
                continue;
            }

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

    public void printState() {
        printState("");
    }

    private void printHorse(Horse horse) {
        System.out.print("{" + horse.getColor() + " - " + horse.getId() + "}");
    }
}
