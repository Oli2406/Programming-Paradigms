import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sammelpunkt implements Runnable {
    private final InputStream inputStream;
    private final List<PersonData> personDataList = new ArrayList<>();
    private boolean running = true;

    public Sammelpunkt(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void addPersonData(PersonData data) {
        System.out.println("Person made it to sammelpunkt");
        personDataList.add(data);
    }

    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.append('\n');
            writer.append("Sammelpunkt:\n");
            for (PersonData data : personDataList) {
                writer.append(data.toString());
                writer.append('\n');
            }
            writer.flush();
        }
    }

    public void listenForData() {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            while (running) {
                PersonData data = (PersonData) ois.readObject();
                addPersonData(data);
            }
        } catch (EOFException e) {
            // End of stream, normal termination
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        listenForData();
        try {
            saveToFile("test.out");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sammelpunkt finished");
    }

    public void shutdown() {
        running = false;
    }

    public static class PersonData implements Serializable {
        private final int id;
        private int steps;
        private final int waits;

        public PersonData(int id, int steps, int waits) {
            this.id = id;
            this.steps = steps;
            this.waits = waits;
        }

        @Override
        public String toString() {
            return String.format("Person %d: Steps=%d, Waits=%d", id, steps, waits);
        }
    }
}
