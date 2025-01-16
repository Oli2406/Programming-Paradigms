import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
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
  private List<PersonPositionData> gridList;
  
  private static final Random random = new Random();
  
  public Person(int id, Grid grid, ObjectOutputStream outputStream, List<PersonPositionData> gridList) {
    this.id = id;
    this.grid = grid;
    this.outputStream = outputStream;
    this.gridList = gridList;
    while(true) {
      Random random = new Random();
      int randX = random.nextInt(grid.getGrid().length);
      int randY = random.nextInt(grid.getGrid()[randX].length);
      
      boolean canPlace = true;
      
      for(PersonPositionData data : gridList) {
        if(data.getLeftPositions()[0] == randX && data.getLeftPositions()[1] == randY || data.getRightPositions()[0] == randX && data.getRightPositions()[1] == randY) {
          canPlace = false;
        }
      }
      if(grid.getGrid()[randX][randY] != ' ' && grid.getGrid()[randX][randY] != 'S' && canPlace) {
        rightX = randX;
        rightY = randY;
        int[][] positions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        int randomPlacement = random.nextInt(4);

        boolean noOtherFeet = true;

        for(int i = 0; i < positions.length; i++) {
          int[] position = positions[(i + randomPlacement) % positions.length];
          if(rightX + position[0] < 0 || rightX + position[0] >= grid.getGrid().length || rightY + position[1] < 0 || rightY + position[1] >= grid.getGrid()[rightX].length) {

          } else {
            if(grid.getGrid()[rightX + position[0]][rightY + position[1]] != 'S' && grid.getGrid()[rightX + position[0]][rightY + position[1]] != ' ') {
              for(PersonPositionData data : gridList) {
                if(data.getLeftPositions()[0] == rightX + position[0] && data.getLeftPositions()[1] == rightY + position[1] || data.getRightPositions()[0] == rightX + position[0] && data.getRightPositions()[1] == rightY + position[1]) {
                  noOtherFeet = false;
                }
              }
              if(noOtherFeet) {
                leftX = rightX + position[0];
                leftY = rightY + position[1];
                break;
              }
            }
          }
        }
        if(noOtherFeet) {
          grid.placeFeet(new PersonPositionData(id, new int[]{leftX, leftY}, new int[]{rightX, rightY}));
          break;
        }
      }
    }
  }
  
  @Override
  public void run() {
    while (!reachedGatheringPoint && waits < 64) {
      try {
        if (!move()) {
          Thread.sleep(random.nextInt(50) + 5); // Random wait
          waits++;
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break;
      }
    }
    
    if (reachedGatheringPoint) {
      sendPersonData();
    }
  }
  
  private boolean move() {
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
  
  public String report() {
    return String.format("Person %d: Steps=%d, Waits=%d, Position left = (%d, %d), Position right = (%d, %d)", id, steps, waits, leftX, leftY, rightX, rightY);
  }
}
