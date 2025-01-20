import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation implements Runnable{
    private final int numPeople;
    private final String name;

    public Simulation(String name, int numPeople) {
        this.numPeople = numPeople;
        this.name = name;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test.out", true))) {
            writer.append("Simulation "+name+" started");
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Thread> threads = new ArrayList<>();
        String[] gridArray = Map.getGrid(name);
        assert gridArray != null;
        Grid grid = new Grid(gridArray);
        PipedOutputStream simulationOutput = new PipedOutputStream();
        PipedInputStream gatheringInput = new PipedInputStream();

        try {
            simulationOutput.connect(gatheringInput);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Sammelpunkt gatheringPoint = new Sammelpunkt(gatheringInput);
        Thread gatheringThread = new Thread(gatheringPoint);
        gatheringThread.start();

        ArrayList<Person> persons = new ArrayList<>();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(simulationOutput)) {
            for (int i = 0; i < numPeople; i++) {
                Person person = new Person(i, grid, outputStream, grid.getGridList(), i == 0);
                persons.add(person);
            }
            for (int i = 0; i < numPeople; i++) {
                Thread thread = new Thread(persons.get(i));
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

            gatheringPoint.shutdown();

            // Print persons to file after simulation ends
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("test.out", true))) {
                writer.append("Simulation "+name+" ended\n");
                writer.append("Persons:\n");
                for (Person person : persons) {
                    writer.append(person.toString());
                    writer.newLine();
                }
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            simulationOutput.close();

            grid.saveGridToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter number of people: ");
        int numPeople = s.nextInt();
        System.out.println("Enter grid name: ");
        System.out.println("Available grids: grid1, grid2, grid3");
        String name = s.next();
        Simulation sim = new Simulation(name, numPeople);
        sim.run();
    }
}
