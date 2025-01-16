public class Test {
  public static void main(String[] args) {
    try {
      String[] command = {"java", "Simulation", "5", "TestGrid"};
      Process process = new ProcessBuilder(command).start();
      process.waitFor();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
