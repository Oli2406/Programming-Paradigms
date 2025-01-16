import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
  
  public static String[] grid1 = {
      "        vv                           >>>>>>>>>>>>>vv",
      "        vv       >>>>>>vv            >>>>>>>>>>>>>vv",
      "        vv       >>>>>>vv<<<<        ^^           vv",
      "        vv       ^^    vv<<<<        ^^           vv",
      "        vv             vv                         vv",
      "        vv             vv                         vv",
      ">>>>>>>>>>>>>vv<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",
      ">>>>>>>>>>>>>vv<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",
      "             vv         ^^              ^^          ",
      "             SS         ^^              ^^          ",
      "                        ^^              ^^<<<<<<    ",
      "                        ^^              ^^<<<<<<    ",
      "                   >>>>>^^<<<<<         ^^          ",
      "                   >>>>>^^<<<<<         ^^          ",
      "                                >>>>>>>>^^          ",
      "                                >>>>>>>>^^          "
  };

  public static String[] grid2 = {
      ">>>>>>vvv<<<<<<            vvvv          vvvv",
          ">>>>>>vvv<<<<<<            vvvv          vvvv",
          "      vvv                  vvvv          vvvv",
          "      vvv                  vvvv          vvvv",
          "      vvv                  vvvv          vvvv",
          "      vvv                  >>vv          vv<<",
          "      vvv                  >>vv          vv<<",
          "      vvv                    >>>>>>vv<<<<<<",
          "      vvv                    >>>>>>vv<<<<<<",
          "      v>>>>>>>>>>>vvvv             vv",
          "      >>>>>>>>>>>>vvvv             vv",
          "                  vvvv<<<<<<<<<<<<<<<",
          "                  vvvv<<<<<<<<<<<<<<<",
          "                  vvvv",
          "                  vvvv",
          "                  vvvv",
          "                  >vv<",
          "                  >vv<",
          "                   SS"
  };
  
  public static void main(String[] args) {
    int numPeople = 20;
    List<Thread> threads = new ArrayList<>();
    Grid grid = new Grid(grid2);
    
    PipedOutputStream simulationOutput = new PipedOutputStream();
    PipedInputStream gatheringInput = new PipedInputStream();
    
    try {
      simulationOutput.connect(gatheringInput);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    
    Sammelpunkt gatheringPoint = new Sammelpunkt();
    Thread gatheringThread = new Thread(() -> gatheringPoint.listenForData(gatheringInput));
    gatheringThread.start();
    
    try (ObjectOutputStream outputStream = new ObjectOutputStream(simulationOutput)) {
      for (int i = 0; i < numPeople; i++) {
        Person person = new Person(i, grid, outputStream, grid.getGridList());
        Thread thread = new Thread(person);
        threads.add(thread);
        thread.start();
      }
      
      for (Thread thread : threads) {
        try {
          thread.join();
        } catch (InterruptedException e) {
          System.err.println("Thread interrupted: " + e.getMessage());
        }
      }
      
      simulationOutput.close();
      
      grid.saveGridToFile("test.out");
      
      gatheringThread.join();
      
      gatheringPoint.saveToFile("test.out");
      grid.display();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
