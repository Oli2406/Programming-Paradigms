import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Random;

public class Person implements Runnable {
    private final int id;
    private int rightX, rightY;
    private int leftX, leftY;
    private int steps = 0;
    private int waits = 0;
    private final Grid grid;
    private final ObjectOutputStream outputStream;
    private boolean reachedGatheringPoint = false;
    private Map<Integer, PersonPositionData> gridList;
    private boolean isLeader;
    private boolean rightFootsTurn = true;

    private static final Random random = new Random();

    public Person(int id, Grid grid, ObjectOutputStream outputStream, Map<Integer, PersonPositionData> gridList, boolean isLeader) {
        this.id = id;
        this.grid = grid;
        this.outputStream = outputStream;
        this.gridList = gridList;
        this.isLeader = isLeader;
        while (true) {
            Random random = new Random();
            int randX = random.nextInt(grid.getGrid().length);
            int randY = random.nextInt(grid.getGrid()[randX].length);

            boolean canPlace = true;

            for (PersonPositionData data : gridList.values()) {
                if (data.getLeftPositions()[0] == randX && data.getLeftPositions()[1] == randY ||
                    data.getRightPositions()[0] == randX && data.getRightPositions()[1] == randY) {
                    canPlace = false;
                }
            }
            char[][] gridCopy = grid.getGrid();
            if (gridCopy[randX][randY] != ' ' && gridCopy[randX][randY] != 'S' && canPlace) {
                rightX = randX;
                rightY = randY;
                int[][] positions = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

                int randomPlacement = random.nextInt(4);

                boolean noOtherFeet = true;

                for (int i = 0; i < positions.length; i++) {
                    int[] position = positions[(i + randomPlacement) % positions.length];
                    if (rightX + position[0] < 0 || rightX + position[0] >= grid.getGrid().length || rightY + position[1] < 0 ||
                        rightY + position[1] >= grid.getGrid()[rightX].length) {
                    } else {
                        if (grid.getGrid()[rightX + position[0]][rightY + position[1]] != 'S' && grid.getGrid()[rightX + position[0]][rightY + position[1]] != ' ') {
                            for (PersonPositionData data : gridList.values()) {
                                if (data.getLeftPositions()[0] == rightX + position[0] && data.getLeftPositions()[1] == rightY + position[1]
                                    || data.getRightPositions()[0] == rightX + position[0] && data.getRightPositions()[1] == rightY + position[1]) {
                                    noOtherFeet = false;
                                }
                            }
                            if (noOtherFeet) {
                                leftX = rightX + position[0];
                                leftY = rightY + position[1];
                                break;
                            }
                        }
                    }
                }
                if (noOtherFeet) {
                    grid.placeFeet(new PersonPositionData(id, new int[] {leftX, leftY}, new int[] {rightX, rightY}));
                    break;
                }
            }
        }
    }

    @Override
    public void run() {
        while (!grid.shouldTerminateAll() && gridList.get(id).isActive() && !gridList.get(id).isOnGatheringPoint()) {
            boolean endMyself = true;
            for (PersonPositionData data : gridList.values()) {
                if (data.getId() == id) {
                    endMyself = false;
                    break;
                }
            }
            if (endMyself) {
                break;
            }
            try {
                boolean moved = move();
                if(reachedGatheringPoint) {
                    gridList.get(id).setOnGatheringPoint(true);
                    gridList.get(id).setActive(false);
                    sendPersonData();
                    return;
                }
                if (!moved) {
                    waits++;
                    if (isLeader) {
                        try {
                            grid.saveGridToFile();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (waits >= 64) {
                        grid.setTerminateAll(true);
                    }
                    Thread.sleep(random.nextInt(46) + 5); // Random wait
                } else {
                    steps++;
                    Thread.sleep(random.nextInt(46) + 5); // Random wait
                }
                gridList.get(id).setSteps(steps);
                gridList.get(id).setWaits(waits);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private boolean isValidPosition(int newX, int newY, int oldX, int oldY) {
        //TODO: do we need to use gridCopy?
        return grid.isWithinBounds(newX, newY) && grid.getGrid()[newX][newY] != ' ' && grid.getGrid()[newX][newY] != 'R' &&
            grid.getGrid()[newX][newY] != 'L' && grid.getGrid()[newX][newY] != 'r' && grid.getGrid()[newX][newY] != 'l' &&
            grid.getGrid()[newX][newY] != grid.getGrid()[oldX][oldY];
    }

    private int[][] checkCurrentPositionForPotentialDirections(int x, int y) {
        char[][] gridCopy = grid.getGrid();
        switch (gridCopy[x][y]) {
            case '^':
                return new int[][] {{0, -1}, {-1, -1}, {1, -1}, {-1, 0}, {1, 0}};
            case 'v':
                return new int[][] {{0, 1}, {-1, 1}, {1, 1}, {-1, 0}, {1, 0}};
            case '<':
                return new int[][] {{-1, 0}, {-1, -1}, {-1, 1}, {0, -1}, {0, 1}};
            case '>':
                return new int[][] {{1, 0}, {1, -1}, {1, 1}, {0, -1}, {0, 1}};
            //this should never happen since we always move in direction of an arrow, which means we'll also always end up on an arrow (idk how spawning works, but i hope y'all already did that)
            default:
                return new int[][] {{0, 0}};
        }
    }

    private boolean move() {
        //TODO: currently "preferation" system - 5 possible directions to choose from --> most preferred is straight, then diagonal and last either side from current position.
        // after one foot has moved, the other gets "dragged along". if you check assignment example, the second foot can only move to two potential positions relative to the other foot
        // if you look at the first example, if the right foot were to move up, the left foot (since he has to stay connected) could only move to the right or left of the right foot (all other positions are invalid)
        // (i'm basing this on the fact that there's no example where two feet are not directly connected (i.e. they aren't placed diagonally or something))

        //TODO: currently switch foot from right to left and vice versa - problem: if one foot is blocked, the other won't be taken into consideration
        // (e.g. if right foot is blocked, but left foot could move somewhere and take right foot with him, this won't happen)

        // tl;dr: character will now always move in (general) direction of arrow

        //this is the grid for all possible positions of both feet (look at assignment pic / example)
        int[][] postionsGrid = new int[][] {{0, 0}, {1, 0}, {2, 0}, {3, 0}, {1, 1}, {2, 1}, {2, 2}, {2, 3}, {3, 0}, {3, 1}, {3, 2}, {3, 3}};
        int rightXInPostionsGrid = 0;
        int rightYInPostionsGrid = 0;
        int leftXInPostionsGrid = 0;
        int leftYInPostionsGrid = 0;
        int newRightX = rightX;
        int newRightY = rightY;
        int newLeftX = leftX;
        int newLeftY = leftY;
        //there's two possible combinations - right foot is right to left foot or vice versa
        if (rightX > leftY || rightY > leftY) {
            rightXInPostionsGrid = 2;
            leftXInPostionsGrid = 1;
        } else {
            rightXInPostionsGrid = 1;
            leftXInPostionsGrid = 2;
        }
        rightYInPostionsGrid = 1;
        leftYInPostionsGrid = 1;
        //self-explanatory
        if (rightFootsTurn) {
            //get all potential directions the foot will move (general direction of arrow (won't move backwards))
            int[][] potentialDirections = checkCurrentPositionForPotentialDirections(rightX, rightY);
            newRightX = rightX;
            newRightY = rightY;
            for (int[] direction : potentialDirections) {
                newRightX = rightX + direction[0];
                newRightY = rightY + direction[1];
                //checks if new position is valid
                if (isValidPosition(newRightX, newRightY, leftX, leftY)) {
                    rightXInPostionsGrid = rightXInPostionsGrid + direction[0];
                    rightYInPostionsGrid = rightYInPostionsGrid + direction[1];
                    break;
                }
            }
            //if hasn't changed (no position was valid), return false
            if (newRightX == rightX && newRightY == rightY) {
                return false;
            }
            //once a foot has moved, the other can only be positioned around the foot (everything except diagonal) - so we only need to check for straight directions
            potentialDirections = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            newLeftX = leftX;
            newLeftY = leftY;
            for (int[] direction : potentialDirections) {
                newLeftX = leftX + direction[0];
                newLeftY = leftY + direction[1];
                //however some directions are too far, so we take the postionsGrid to check which of the four directions are actually still valid
                for (int[] position : postionsGrid) {
                    if (rightXInPostionsGrid + direction[0] == position[0] && rightYInPostionsGrid + direction[1] == position[1]) {
                        if (isValidPosition(newLeftX, newLeftY, rightX, rightY)) {
                            break;
                        }
                    }
                }
            }
            if (newLeftX == leftX && newLeftY == leftY) {
                return false;
            }
            rightFootsTurn = false;
            //if it's left foot's turn to move
        } else {
            int[][] potentialDirections = checkCurrentPositionForPotentialDirections(leftX, leftY);
            newLeftX = leftX;
            newLeftY = leftY;
            for (int[] direction : potentialDirections) {
                newLeftX = leftX + direction[0];
                newLeftY = leftY + direction[1];
                if (isValidPosition(newLeftX, newLeftY, rightX, rightY)) {
                    leftXInPostionsGrid = leftXInPostionsGrid + direction[0];
                    leftYInPostionsGrid = leftYInPostionsGrid + direction[1];
                    break;
                }
            }
            if (newLeftX == leftX && newLeftY == leftY) {
                return false;
            }
            potentialDirections = checkCurrentPositionForPotentialDirections(rightX, rightY);
            newRightX = rightX;
            newRightY = rightY;
            for (int[] direction : potentialDirections) {
                newRightX = rightX + direction[0];
                newRightY = rightY + direction[1];
                for (int[] position : postionsGrid) {
                    if (leftXInPostionsGrid + direction[0] == position[0] && leftYInPostionsGrid + direction[1] == position[1]) {
                        if (isValidPosition(newRightX, newRightY, leftX, leftY)) {
                            break;
                        }
                    }
                }
                if (newRightX == rightX && newRightY == rightY) {
                    return false;
                }
                rightFootsTurn = true;
            }
      /*
      for (int[] direction : directions) {
        int newRightX = rightX + direction[0];
        int newRightY = rightY + direction[1];
        int newLeftX = leftX + direction[0];
        int newLeftY = leftY + direction[1];
        char[][] gridCopy = grid.getGrid();
        if (grid.isWithinBounds(newRightX, newRightY) && grid.isWithinBounds(newLeftX, newLeftY) &&
            gridCopy[newRightX][newRightY] != ' ' && gridCopy[newLeftX][newLeftY] != ' ' && gridCopy[newRightX][newRightY] != 'R' &&
            gridCopy[newLeftX][newLeftY] != 'R' &&
            gridCopy[newRightX][newRightY] != 'L' && gridCopy[newLeftX][newLeftY] != 'L' && gridCopy[newRightX][newRightY] != 'r' &&
            gridCopy[newLeftX][newLeftY] != 'r' &&
            gridCopy[newRightX][newRightY] != 'l' && gridCopy[newLeftX][newLeftY] != 'l') {
            */
        }
        synchronized (grid) {
            rightX = newRightX;
            rightY = newRightY;
            leftX = newLeftX;
            leftY = newLeftY;
            gridList.get(id).setRightPositions(new int[] {rightX, rightY});
            gridList.get(id).setLeftPositions(new int[] {leftX, leftY});
            if(grid.isPositionGatheringPoint(rightX, rightY) || grid.isPositionGatheringPoint(leftX, leftY)) {
                reachedGatheringPoint = true;
            }
            return true;
        }
    }

    private boolean gridReached() {
        return false;
    }

    private void sendPersonData() {
        try {
            synchronized (outputStream) {
                outputStream.writeObject(new Sammelpunkt.PersonData(id, steps, waits));
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("Person %d: Steps=%d, Waits=%d, Position left = (%d, %d), Position right = (%d, %d)", id, steps, waits, leftX, leftY, rightX,
            rightY);
    }
}