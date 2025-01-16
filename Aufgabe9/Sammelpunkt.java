import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Sammelpunkt {
  private final List<PersonData> personDataList = new ArrayList<>();
  
  public void addPersonData(PersonData data) {
    personDataList.add(data);
  }
  
  public void saveToFile(String fileName) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      for (PersonData data : personDataList) {
        writer.write(data.toString());
        writer.newLine();
      }
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
