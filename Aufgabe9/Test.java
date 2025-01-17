public class Test {
  /*
   * Wer hat was gemacht:
   * - Ryan Foster: grid1, Person Movement, Simulation, Test, Map
   * - Oliver Kastner: grid2, Person placement, Sammelpunkt
   * - Noah Oguamalam: Person Movement, Grid, Person position data, Person
   */
  public static void main(String[] args) {
    Simulation sim1 = new Simulation("grid1", 10);
    sim1.run();
    Simulation sim2 = new Simulation("grid2", 15);
    sim2.run();
    //Simulation sim3 = new Simulation("grid3", 20);
    //sim3.run();

  }
}
