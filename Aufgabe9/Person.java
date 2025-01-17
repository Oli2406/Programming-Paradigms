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
                if (data.getLeftPositions()[0] == randX && data.getLeftPositions()[1] == randY || data.getRightPositions()[0] == randX && data.getRightPositions()[1] == randY) {
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
                    if (rightX + position[0] < 0 || rightX + position[0] >= grid.getGrid().length || rightY + position[1] < 0 || rightY + position[1] >= grid.getGrid()[rightX].length) {

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
                if (!move()) {
                    Thread.sleep(random.nextInt(46) + 5); // Random wait
                    waits++;
                    if(waits >= 64) {
                        grid.setTerminateAll(true);
                    }
                    if (isLeader) {
                        try {
                            grid.saveGridToFile();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    steps++;
                }
                gridList.get(id).setSteps(steps);
                gridList.get(id).setWaits(waits);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        gridList.get(id).setActive(false);
        if (reachedGatheringPoint) {
            sendPersonData();
        }
    }

    private boolean move() {
        //TODO Start somewhere random in this array to randomise the movement
        //TODO Split up the movement from both feet to make more moves possible

        //TODO research how it should be possible to go around a corner
        //TODO Person may be able to spawn on Sammelpunkt or move away from Sammelpunkt (dunno if that's intended :shrug:)
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
        for (int[] direction : directions) {
            int newRightX = rightX + direction[0];
            int newRightY = rightY + direction[1];
            int newLeftX = leftX + direction[0];
            int newLeftY = leftY + direction[1];
            char[][] gridCopy = grid.getGrid();
            if (grid.isWithinBounds(newRightX, newRightY) && grid.isWithinBounds(newLeftX, newLeftY) &&
                gridCopy[newRightX][newRightY] != ' ' && gridCopy[newLeftX][newLeftY] != ' ' && gridCopy[newRightX][newRightY] != 'R' && gridCopy[newLeftX][newLeftY] != 'R' &&
                gridCopy[newRightX][newRightY] != 'L' && gridCopy[newLeftX][newLeftY] != 'L' && gridCopy[newRightX][newRightY] != 'r' && gridCopy[newLeftX][newLeftY] != 'r' &&
                gridCopy[newRightX][newRightY] != 'l' && gridCopy[newLeftX][newLeftY] != 'l') {
                synchronized (grid) {
                    rightX = newRightX;
                    rightY = newRightY;
                    leftX = newLeftX;
                    leftY = newLeftY;
                    gridList.get(id).setRightPositions(new int[] {rightX, rightY});
                    gridList.get(id).setLeftPositions(new int[] {leftX, leftY});
                    return true;
                }
            }
        }
        return false;
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
        return String.format("Person %d: Steps=%d, Waits=%d, Position left = (%d, %d), Position right = (%d, %d)", id, steps, waits, leftX, leftY, rightX, rightY);
    }
}