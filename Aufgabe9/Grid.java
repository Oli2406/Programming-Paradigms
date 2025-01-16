import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Grid {
  private final int rows;
  private final int cols;
  private final List<PersonPositionData> gridList = new ArrayList<>();
  
  public char[][] getGrid() {
    return grid;
  }
  
  private final char[][] grid;
  
  // Constructor to initialize grid from a custom array
  public Grid(String[] customGrid) {
    this.rows = customGrid.length;
    this.cols = customGrid[0].length();
    this.grid = new char[rows][cols];
    
    for (int i = 0; i < rows; i++) {
      grid[i] = customGrid[i].toCharArray();
    }
  }
  
  // Check if a move is valid and make the move
  public synchronized boolean tryMove(int oldX, int oldY, int newX, int newY) {
    if (isWithinBounds(newX, newY) && grid[newX][newY] == ' ') {
      grid[oldX][oldY] = ' ';  // Clear old position
      grid[newX][newY] = 'P';  // Mark new position
      return true;
    }
    return false;
  }
  
  // Place the feet of a person on the grid
  public synchronized void placeFeet(PersonPositionData data) {
    gridList.add(data);
  }
  
  // Save the current grid state to a file
  public synchronized void saveGridToFile(String fileName) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
      for (char[] row : grid) {
        writer.write(new String(row));
        writer.newLine();
      }
      writer.write("\n");
    }
  }
  
  // Display the grid to the console
  public synchronized void display() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        char toPrint = ' ';
        for(PersonPositionData data : gridList) {
          if(data.getLeftPositions()[0] == i && data.getLeftPositions()[1] == j) {
            toPrint = 'L';
            break;
          } else if ( data.getRightPositions()[0] == i && data.getRightPositions()[1] == j) {
            toPrint = 'R';
            break;
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
  private boolean isWithinBounds(int x, int y) {
    return x >= 0 && x < rows && y >= 0 && y < cols;
  }
  
  public List<PersonPositionData> getGridList() {
    return gridList;
  }
}
