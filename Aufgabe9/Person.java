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
            int randX = random.nextInt(grid.getGridWithPositions().length);
            int randY = random.nextInt(grid.getGridWithPositions()[randX].length);

            boolean canPlace = true;

            for (PersonPositionData data : gridList.values()) {
                if (data.getLeftPositions()[0] == randX && data.getLeftPositions()[1] == randY ||
                    data.getRightPositions()[0] == randX && data.getRightPositions()[1] == randY) {
                    canPlace = false;
                }
            }
            char[][] gridCopy = grid.getGridWithPositions();
            if (gridCopy[randX][randY] != ' ' && gridCopy[randX][randY] != 'S' && canPlace) {
                rightX = randX;
                rightY = randY;
                int[][] positions = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

                int randomPlacement = random.nextInt(4);

                boolean noOtherFeet = true;

                for (int i = 0; i < positions.length; i++) {
                    int[] position = positions[(i + randomPlacement) % positions.length];
                    if (rightX + position[0] < 0 || rightX + position[0] >= grid.getGridWithPositions().length || rightY + position[1] < 0 ||
                        rightY + position[1] >= grid.getGridWithPositions()[rightX].length) {
                    } else {
                        if (grid.getGridWithPositions()[rightX + position[0]][rightY + position[1]] != 'S' && grid.getGridWithPositions()[rightX + position[0]][rightY + position[1]] != ' ') {
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
                if (reachedGatheringPoint) {
                    gridList.get(id).setOnGatheringPoint(true);
                    gridList.get(id).setActive(false);
                    sendPersonData();
                    return;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private boolean isValidPosition(int newX, int newY, int oldX, int oldY) {
        //TODO: do we need to use gridCopy?
        char[][] gridCopy = grid.getGridWithPositions();
        return grid.isWithinBounds(newX, newY) && gridCopy[newX][newY] != ' ' && gridCopy[newX][newY] != 'R' &&
            gridCopy[newX][newY] != 'L' && gridCopy[newX][newY] != 'r' && gridCopy[newX][newY] != 'l' &&
            gridCopy[newX][newY] != gridCopy[oldX][oldY];
    }

    private int[][] checkCurrentPositionForPotentialDirections(int x, int y) {
        char[][] gridCopy = grid.getGrid();
        switch (gridCopy[x][y]) {
            case '^':
                return new int[][] {{-1, 0}, {-1, -1}, {-1, 1}, {0, -1}, {0, 1}};
            case 'v':
                return new int[][] {{1, 0}, {1, -1}, {1, 1}, {0, 1}, {0, 1}};
            case '<':
                return new int[][] {{0, -1}, {-1, -1}, {1, -1}, {-1, 0}, {1, 0}};
            case '>':
                return new int[][] {{0, 1}, {-1, 1}, {1, 1}, {-1, 0}, {1, 0}};
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
        int[][] verticalPositionsGrid = new int[][]{{0,0}, {0,1}, {0,2}, {1,0}, {1,1}, {1,2}, {2,0}, {2,1}, {2,2}, {3,0}, {3,1}, {3,2}};
        int[][] horizontalPostionsGrid = new int[][] {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {1, 0}, {1, 1}, {1, 2}, {1, 3}, {2, 0}, {2, 1}, {2, 2}, {2, 3}};
        int rightXInPostionsGrid = 0;
        int rightYInPostionsGrid = 0;
        int leftXInPostionsGrid = 0;
        int leftYInPostionsGrid = 0;
        int[][] positionsGrid;
        if (rightX == leftX) {
            positionsGrid = horizontalPostionsGrid;
            rightXInPostionsGrid = 1;
            leftXInPostionsGrid = 1;
            if (rightY > leftY) {
                rightYInPostionsGrid = 2;
                leftYInPostionsGrid = 1;
            } else {
                rightYInPostionsGrid = 1;
                leftYInPostionsGrid = 2;
            }
        }
        else {
            positionsGrid = verticalPositionsGrid;
            rightYInPostionsGrid = 1;
            leftYInPostionsGrid = 1;
            if (rightX > leftX) {
                rightXInPostionsGrid = 2;
                leftXInPostionsGrid = 1;
            } else {
                rightXInPostionsGrid = 1;
                leftXInPostionsGrid = 2;
            }
        }
        int newRightX = rightX;
        int newRightY = rightY;
        int newLeftX = leftX;
        int newLeftY = leftY;
        boolean foundValidPosition = false;
        //self-explanatory
        if (rightFootsTurn) {
            //get all potential directions the foot will move (general direction of arrow (won't move backwards))
            int[][] potentialDirections = checkCurrentPositionForPotentialDirections(rightX, rightY);
            newRightX = rightX;
            newRightY = rightY;
            foundValidPosition = false;
            for (int[] direction : potentialDirections) {
                newRightX = rightX + direction[0];
                newRightY = rightY + direction[1];
                //checks if new position is valid
                if (isValidPosition(newRightX, newRightY, leftX, leftY)) {
                    rightXInPostionsGrid = rightXInPostionsGrid + direction[0];
                    rightYInPostionsGrid = rightYInPostionsGrid + direction[1];
                    foundValidPosition = true;
                    break;
                }
            }
            //if hasn't changed (no position was valid), return false
            if (!foundValidPosition) {
                return false;
            }
            //once a foot has moved, the other can only be positioned around the foot (everything except diagonal) - so we only need to check for straight directions
            potentialDirections = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            newLeftX = newRightX;
            newLeftY = newRightY;
            foundValidPosition = false;
            for (int[] direction : potentialDirections) {
                newLeftX = newRightX + direction[0];
                newLeftY = newRightY + direction[1];
                //however some directions are too far, so we take the postionsGrid to check which of the four directions are actually still valid
                for (int[] position : positionsGrid) {
                    if (rightXInPostionsGrid + direction[0] == position[0] && rightYInPostionsGrid + direction[1] == position[1]) {
                        if (isValidPosition(newLeftX, newLeftY, rightX, rightY)) {
                            foundValidPosition = true;
                            break;
                        }
                    }
                }
                if (foundValidPosition) {
                    break;
                }
            }
            if (!foundValidPosition) {
                return false;
            }
            rightFootsTurn = false;
            //if it's left foot's turn to move
        } else {
            int[][] potentialDirections = checkCurrentPositionForPotentialDirections(leftX, leftY);
            newLeftX = leftX;
            newLeftY = leftY;
            foundValidPosition = false;
            for (int[] direction : potentialDirections) {
                newLeftX = leftX + direction[0];
                newLeftY = leftY + direction[1];
                if (isValidPosition(newLeftX, newLeftY, rightX, rightY)) {
                    leftXInPostionsGrid = leftXInPostionsGrid + direction[0];
                    leftYInPostionsGrid = leftYInPostionsGrid + direction[1];
                    foundValidPosition = true;
                    break;
                }
            }
            if (!foundValidPosition) {
                return false;
            }
            potentialDirections = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            newRightX = newLeftX;
            newRightY = newLeftY;
            foundValidPosition = false;
            for (int[] direction : potentialDirections) {
                newRightX = newLeftX + direction[0];
                newRightY = newLeftY + direction[1];
                for (int[] position : positionsGrid) {
                    if (leftXInPostionsGrid + direction[0] == position[0] && leftYInPostionsGrid + direction[1] == position[1]) {
                        if (isValidPosition(newRightX, newRightY, leftX, leftY)) {
                            foundValidPosition = true;
                            break;
                        }
                    }
                }
                if (!foundValidPosition) {
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
            if (grid.isPositionGatheringPoint(rightX, rightY) || grid.isPositionGatheringPoint(leftX, leftY)) {
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