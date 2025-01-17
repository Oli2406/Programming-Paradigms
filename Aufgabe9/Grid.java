import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Grid {
    private final int rows;
    private final int cols;
    private final Map<Integer, PersonPositionData> gridList = new ConcurrentHashMap<>();
    private final char[][] grid;
    private volatile boolean terminateAll = false; // Shared flag

    // Constructor to initialize grid from a custom array
    public Grid(String[] customGrid) {
        this.rows = customGrid.length;
        this.cols = customGrid[0].length();
        this.grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = customGrid[i].toCharArray();
        }
    }

    // Place the feet of a person on the grid
    public synchronized void placeFeet(PersonPositionData data) {
        gridList.put(data.getId(), data);
    }

    // Save the current grid state to a file
    public synchronized void saveGridToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test.out", true))) {
            writer.append('\n');
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    char toPrint = grid[i][j];
                    for (PersonPositionData data : gridList.values()) {
                        if(!data.isOnGatheringPoint()) {
                            if (data.getLeftPositions()[0] == i && data.getLeftPositions()[1] == j) {
                                if(toPrint == 'S') {
                                    System.out.println("Something went wrong");
                                }
                                toPrint = 'L';
                                break;
                            } else if (data.getRightPositions()[0] == i && data.getRightPositions()[1] == j) {
                                if(toPrint == 'S') {
                                    System.out.println("Something went wrong");
                                }
                                toPrint = 'R';
                                break;
                            } else if (data.getOldRightPosition()[0] == i && data.getOldRightPosition()[1] == j) {
                                if(toPrint == 'S') {
                                    System.out.println("Something went wrong");
                                }
                                toPrint = 'r';
                            } else if (data.getOldLeftPosition()[0] == i && data.getOldLeftPosition()[1] == j) {
                                if(toPrint == 'S') {
                                    System.out.println("Something went wrong");
                                }
                                toPrint = 'l';
                            }
                        }
                    }
                    writer.append(toPrint);
                }
                writer.append("\n");
            }
            writer.flush();
        }
    }

    // Display the grid to the console
    public synchronized void display() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char toPrint = ' ';
                for (PersonPositionData data : gridList.values()) {
                    if (data.getLeftPositions()[0] == i && data.getLeftPositions()[1] == j) {
                        toPrint = 'L';
                        break;
                    } else if (data.getRightPositions()[0] == i && data.getRightPositions()[1] == j) {
                        toPrint = 'R';
                        break;
                    } else if (data.getOldRightPosition()[0] == i && data.getOldRightPosition()[1] == j) {
                        toPrint = 'r';
                    } else if (data.getOldLeftPosition()[0] == i && data.getOldLeftPosition()[1] == j) {
                        toPrint = 'l';
                    } else {
                        toPrint = grid[i][j];

                    }
                }
                System.out.print(toPrint);
            }
            System.out.println();
        }
    }

    // Helper method to check if coordinates are within bounds
    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    public Map<Integer, PersonPositionData> getGridList() {
        return gridList;
    }

    public boolean shouldTerminateAll() {
        return terminateAll;
    }

    public void setTerminateAll(boolean terminateAll) {
        this.terminateAll = terminateAll;
    }

    public char[][] getGrid() {
        char[][] output = new char[rows][cols];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char toPrint = grid[i][j];
                for (PersonPositionData data : gridList.values()) {
                    if(!data.isOnGatheringPoint()) {
                        if (data.getLeftPositions()[0] == i && data.getLeftPositions()[1] == j) {
                            toPrint = 'L';
                            break;
                        } else if (data.getRightPositions()[0] == i && data.getRightPositions()[1] == j) {
                            toPrint = 'R';
                            break;
                        } else if (data.getOldRightPosition()[0] == i && data.getOldRightPosition()[1] == j) {
                            toPrint = 'r';
                        } else if (data.getOldLeftPosition()[0] == i && data.getOldLeftPosition()[1] == j) {
                            toPrint = 'l';
                        }
                    }
                }
                output[i][j] = toPrint;
            }
        }
        return output;
    }
}