import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sammelpunkt implements Runnable {
    int[][] sammlungspunkte;
    Map<Integer, PersonPositionData> gridList;

    public Sammelpunkt(int[][] sammelpunkte, Map<Integer, PersonPositionData> gridList) {
        this.sammlungspunkte = sammelpunkte;
        this.gridList = gridList;
    }

    private final List<PersonData> personDataList = new ArrayList<>();

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

    public void listenForData(InputStream inputStream) {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            while (true) {
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
        while (isGridListActive()) {
            for (PersonPositionData data : gridList.values()) {
                if (checkPersonOnSammelpunkt(data)) {
                    addPersonData(new PersonData(data.getId(), data.getSteps(), data.getWaits()));
                    data.setOnGatheringPoint(true);
                    data.setActive(false);
                }
            }
        }
    }

    private boolean isGridListActive() {
        for (PersonPositionData data : gridList.values()) {
            if (data.isActive()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPersonOnSammelpunkt(PersonPositionData data) {
        for (int[] sammelpunkt : sammlungspunkte) {
            if ((data.getLeftPositions()[0] == sammelpunkt[0] && data.getLeftPositions()[1] == sammelpunkt[1] || data.getRightPositions()[0] == sammelpunkt[0] && data.getRightPositions()[1] == sammelpunkt[1]) && !data.isOnGatheringPoint()) {
                return true;
            }
        }
        return false;
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
