/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Project
  Created date: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Last modified: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/
package model;

import java.util.ArrayList;

import static model.Color.*;

/**
 * Board contains the position of horses and the rules on how to move them
 */
public class Board {
    private Nest[] nests;
    private PathNode path;
    private boolean isEndGame;

    /**
     * Create a board from the player list
     * @param playerList
     */
    public Board(ArrayList<Player> playerList) {
        if (notEnoughPlayer(playerList)) {
            throw new IllegalArgumentException("Need at least 2 people/machine to play");
        }

        nests = new Nest[4];
        Color[] colors = {RED, GREEN, BLUE, YELLOW};
        for (int i = 0; i < 4; i++) {
            nests[i] = new Nest();
            nests[i].setColor(colors[i]);
        }
        resetNest(playerList);

        path = makePath();

        isEndGame = false;
    }

    /**
     *Check player size, if not, return false
     * @param playerList
     * @return boolean
     */
    private boolean notEnoughPlayer(ArrayList<Player> playerList) {
        int noneCount = 0;
        for (Player player: playerList) {
            if (player.getPlayerType() == PlayerType.NONE) {
                noneCount++;
            }

            if (noneCount == 3) return true;
        }

        return false;
    }

    /**
     * Clear the horses in nests. Refill if the player is playing (not NONE PlayerType)
     * @param playerList
     */
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

    /**
     * Build the graph of game path
     * @return
     */
    private PathNode makePath() {
        Color[] colors = {RED, GREEN, YELLOW, BLUE};
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
            System.out.println(startHome.getPosition().getColor() + " - " + startPath.getPosition().getColor());
            startHome.setNextAroundNode(startPath);

            if (homeArrival != null) slidePath.setNextAroundNode(homeArrival);
            homeArrival = startHome;
        }

        first10.setNextAroundNode(homeArrival);

        return first10;
    }

    public boolean getIsEndGame() {
        return isEndGame;
    }

    /**
     * Summon a horse from nest to start point
     * @param color
     * @return the id of the summoned horse, or -1 if can't summon
     */
    public int summon(Color color) {
        if (!canSummon(color)) return -1;

        for (Nest nest: nests) {
            if (nest.getColor() == color) {
                PathNode startingSpace = getStartingSpace(color);
                Horse summoned = nest.getHorseInNest().remove(0);
                startingSpace.setHorse(summoned);
                return summoned.getId();
            }
        }
        return -1;
    }

    /**
     * Check if it is possible to summon a horse
     * @param color
     * @return
     */
    public boolean canSummon(Color color) {
        for (Nest nest: nests) {
            if (nest.getColor() == color) {
                if (nest.isEmpty()) return false;
                PathNode startingSpace = getStartingSpace(color);
                if (startingSpace.getHorse() != null) return false;
                return true;
            }
        }
        return true;
    }

    /**
     * Get the PathNode of the starting space of a Color
     * @param color
     * @return
     */
    private PathNode getStartingSpace(Color color) {
        PathNode current = path;
        while (!(current.getPosition().equals(new Position(color, 0)))) {
            current = current.getNextAroundNode();
        }
        return current;
    }

    /**
     * Move the horse with the specified color and id. Update isEndGame if necessary
     * @param color
     * @param id
     * @param moves
     * @return a Move object
     */
    public Move move(Color color, int id, int moves) {
        PathNode start = findHorseInPath(color, id);
        PathNode destination = findMoveDestination(start, moves);
        Move move = buildMove(start, destination);
        if (destination == null) return move;

        // Move horse (and kick if needed)
        if (destination.getHorse() != null) returnHorse(destination.getHorse());
        destination.setHorse(start.getHorse());
        start.setHorse(null);

        // Update end game flag
        Position destPosition = destination.getPosition();
        // Moving the horse to the fourth home position could end the game
        if (destPosition.getNumber() == 14) {
            PathNode homePosition = destination;
            for (int i = 0; i < 3; i++) {
                homePosition = homePosition.getHomePositionNode();
                if (homePosition.getHorse() == null) return move;
            }
            isEndGame = true;
        }
        return move;
    }

    public Move move(Position postion, int moves) {
        PathNode start = findPathNodeFromPosition(postion);
        Horse horseAtStart = start.getHorse();
        if (horseAtStart == null) throw new IllegalArgumentException("This position doesn't contain horse");

        return move(horseAtStart.getColor(), horseAtStart.getId(), moves);
    }

    /**
     * Find the new position without actually moving
     * @param start
     * @param moves
     * @return the new position, or null if unsuccessful
     */
    PathNode findMoveDestination(PathNode start, int moves) {
        if (moves < 1 || moves > 12) {
            throw new IllegalArgumentException("A horse can move from 1 to 12 at a time");
        }

        if (isMoveInMovePath(start)) return movePathDryRun(start, moves);
        else return moveHomePathDryRun(start, moves);
    }

    /**
     * Create a Move object from start and finish PathNode
     * @param start
     * @param finish
     * @return
     */
    private Move buildMove(PathNode start, PathNode finish) {
        Move move = new Move(start.getHorse(), start.getPosition(),
                finish == null ? null : finish.getPosition(), null);

        if (finish == null) return move;
        if (finish.getHorse() != null) move.setKickedHorse(finish.getHorse());
        return move;
    }

    /**
     * Check if a horse is inside nest
     * @param color
     * @param id
     * @return
     */
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

    /**
     * Find the position of a horse
     * @param color
     * @param id
     * @return
     */
    public PathNode findHorseInPath(Color color, int id) {
        if (id < 0 || id > 3) {
            throw new IllegalArgumentException("Horse ID must be from 0 to 3");
        }

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

    /**
     * find all horse in the board of a player
     * @param player
     * @return horseList
     */
    public ArrayList<Horse> findAllHorse(Player player) {
        ArrayList<Horse> horseInBoardList = new ArrayList<Horse>();
        for (int i = 0; i < 4; i++) {
            PathNode NodeWithHorse = findHorseInPath(player.getPlayerSide(), i);
            if (NodeWithHorse != null){
                horseInBoardList.add(NodeWithHorse.getHorse());
            }
        }
        return horseInBoardList;
    }

    /**
     * Find pathNode from the position
     * @param position
     * @return
     */
    public PathNode findPathNodeFromPosition(Position position) {
        PathNode current = path;
        do {
            if (current.getPosition().equals(position)) return current;

            PathNode homePath = current.getHomePositionNode();
            while (homePath != null) {
                if (homePath.getPosition().equals(position)) return homePath;
                homePath = homePath.getHomePositionNode();
            }
            current = current.getNextAroundNode();
        } while (current != path);
        return null;
    }

    /**
     * Check to see if a horse is moving around the path or going up home path
     * @param nodeWithHorse
     * @return
     */
    private boolean isMoveInMovePath(PathNode nodeWithHorse) {
        return isMoveInMovePath(nodeWithHorse.getPosition(), nodeWithHorse.getHorse().getColor());
    }

    public boolean isMoveInMovePath(Move move) {
        return isMoveInMovePath(move.getStart(), move.getMovingHorse().getColor());
    }

    /**
     * Check whether the horse is in its home position or not
     * @param startPosition
     * @param horseColor
     * @return
     */
    private boolean isMoveInMovePath(Position startPosition, Color horseColor) {
        if (startPosition.getNumber() < 11) return true;
        else if (startPosition.getNumber() == 11) {
            if (startPosition.getColor() == horseColor) return false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * check whether the horse in the homepath or not
     * @param currentPosition
     * @return
     */
    public boolean isInHomePath(Move currentPosition) {
        return currentPosition.getStart().getNumber() >= 12
                && currentPosition.getStart().getNumber() <= 17;
    }

    public boolean isInHomePath(PathNode currentPosition) {
        return currentPosition.getPosition().getNumber() >= 12
                && currentPosition.getPosition().getNumber() <= 17;
    }

    /**
     * Find the destination without moving. Use this when horse move around path (instead of moving up home path)
     * @param nodeWithHorse
     * @param moves
     * @return
     */
     PathNode movePathDryRun(PathNode nodeWithHorse, int moves) {
        Horse horseToMove = nodeWithHorse.getHorse();
        PathNode current = nodeWithHorse;
        // Go moves - 1 steps
        for (int i = 0; i < moves - 1; i++) {
            // Can't go pass home arrival
            if (current.getPosition().equals(new Position(horseToMove.getColor(), 11))) {
                return null;
            }

            current = current.getNextAroundNode();
            if (current.getHorse() != null) return null;
        }

        // Can't go pass home arrival
        if (current.getPosition().equals(new Position(horseToMove.getColor(), 11))) {
            return null;
        }

        current = current.getNextAroundNode();
        Horse horseAtDestination = current.getHorse();
        if (horseAtDestination == null) {
            return current;
        } else if (horseAtDestination.getColor() == horseToMove.getColor()) return null;
        else {
            return current;
        }
    }

    /**
     * Find the destination without moving. Use this when horse move up home path (instead of moving around path)
     * @param nodeWithHorse
     * @param moves
     * @return
     */
     PathNode moveHomePathDryRun(PathNode nodeWithHorse, int moves) {
        Position position = nodeWithHorse.getPosition();
        Horse horse = nodeWithHorse.getHorse();
        // If standing at home arrival
        if (position.equals(new Position(horse.getColor(), 11))) {
            PathNode current = nodeWithHorse;
            for (int i = 0; i < moves; i++) {
                current = current.getHomePositionNode();
                if (current == null) return null;
                if (current.getHorse() != null) return null;
            }

            return current;
        } else {
            PathNode destination = nodeWithHorse.getHomePositionNode();
            // Horse is at the highest position
            if (destination == null) return null;
            if (destination.getHorse() != null) return null;
            // Need specific moves to get to next home path
            if (destination.getPosition().getNumber() - 11 == moves) {
                return destination;
            } else return null;
        }
    }

    /**
     * Return a horse to its nest
     * @param horse
     */
    private void returnHorse(Horse horse) {
        for (Nest nest: nests) {
            if (nest.getColor() == horse.getColor()) {
                nest.add(horse);
            }
        }
    }

    /**
     * Find the destination of a move without moving
     * This is different from findMoveDestination() where it returns PathNode. This only returns Position
     * @param color
     * @param id
     * @param moves
     * @return
     */
    public Move moveDryRun(Color color, int id, int moves) {
        PathNode start = findHorseInPath(color, id);
        PathNode destination = findMoveDestination(start, moves);
        return buildMove(start, destination);
    }



    /**
     * Calculate the distance remain of the horse to its homeposition
     * @param currentPosition
     * @param color
     * @return numberOfStep
     */
    public int calculateDistanceToHomePosition(PathNode currentPosition, Color color) {
        int numberOfStep = 1;
        PathNode nextNode = currentPosition;
        while (true) {
            nextNode = nextNode.getNextAroundNode();
            if (nextNode.getPosition().getNumber() == 11 && nextNode.getPosition().getColor() == color) {
                return numberOfStep;
            }
            numberOfStep++;
        }
    }

    /**
     * Check if a move is possible
     * @param color
     * @param id
     * @param moves
     * @return
     */
    public boolean canMove(Color color, int id, int moves) {
        if (isHorseInNest(color, id)) {
            return false;
        }
        PathNode start = findHorseInPath(color, id);
        return findMoveDestination(start, moves) != null;
    }

    public Horse getHorseInPosition(Position position) {
        return findPathNodeFromPosition(position).getHorse();
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
