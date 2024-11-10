import config.Config;
import enums.ScenarioType;
import house.Resistance;

import java.util.ArrayList;

public class Test {

  public Test() {
    // These values represent the Premium House, it should be very close to the preset
    Resistance resistances = new Resistance(true, true, true, true, true, true);
    Config c = new Config(250000, 40, 200,
        100, 25, 3000, 40000,
        20000, 5000, 0.9f,
        0.5f, 15, 50,
        resistances, 0.6f, true);
    ArrayList<Result> configScores = new ArrayList<>();
    ArrayList<Result> minimalScores = new ArrayList<>();
    ArrayList<Result> bioScores = new ArrayList<>();
    ArrayList<Result> premiumScores = new ArrayList<>();

    // STYLE: Lambda expressions are used to create threads for each scenario type
    // STYLE: Threads for the parallel execution of the scenarios
    Thread configThread = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        Scenario config = new Scenario(c);
        configScores.add(config.run());
      }
      printAggregatedResults("Config", configScores);
    });

    Thread minimalThread = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        Scenario minimal = new Scenario(ScenarioType.MINIMAL);
        minimalScores.add(minimal.run());
      }
      printAggregatedResults("Minimal", minimalScores);
    });

    Thread bioThread = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        Scenario bio = new Scenario(ScenarioType.BIO);
        bioScores.add(bio.run());
      }
      printAggregatedResults("Bio", bioScores);
    });

    Thread premiumThread = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        Scenario premium = new Scenario(ScenarioType.PREMIUM);
        premiumScores.add(premium.run());
      }
      printAggregatedResults("Premium", premiumScores);
    });

    configThread.start();
    minimalThread.start();
    bioThread.start();
    premiumThread.start();
  }

  public static void main(String[] args) {
    Test t = new Test();
  }

  /**
   * Prints the aggregated results of the simulation
   * @param name The name of the scenario
   * @param results The list of results
   */
  private void printAggregatedResults(String name, ArrayList<Result> results) {
    int totalNewHouses = 0;
    int totalDemolishedHouses = 0;
    int totalNewResidents = 0;
    int totalDiedResidents = 0;
    float totalCostPerResidentPerYear = 0;
    float totalCostPerResidentPerDecade = 0;
    float wastePerResidentPerYear = 0;
    float totalAverageCarbonPerYear = 0;
    float totalSatisfactionPerYear = 0;
    double totalSusScore = 0;

    for (Result result : results) {
      totalNewHouses += result.getTotalNewHouses();
      totalDemolishedHouses += result.getTotalDemolishedHouses();
      totalNewResidents += result.getTotalNewResidents();
      totalDiedResidents += result.getTotalDiedResidents();
      totalCostPerResidentPerYear += result.getTotalCostPerResidentPerYear();
      totalCostPerResidentPerDecade += result.getTotalCostPerResidentPerDecade();
      wastePerResidentPerYear += result.getWastePerResidentPerYear();
      totalAverageCarbonPerYear += result.getTotalAverageCarbonPerYear();
      totalSatisfactionPerYear += result.getTotalSatisfactionPerYear();
      totalSusScore += result.getSusScore();
    }

    int resultCount = results.size();
    double avgNewHouses = Math.round(((double) totalNewHouses / resultCount) * 10000.0) / 10000.0;
    double avgDemolishedHouses = Math.round(((double) totalDemolishedHouses / resultCount) * 10000.0) / 10000.0;
    double avgNewResidents = Math.round(((double) totalNewResidents / resultCount) * 10000.0) / 10000.0;
    double avgDiedResidents = Math.round(((double) totalDiedResidents / resultCount) * 10000.0) / 10000.0;
    double avgCostPerResidentPerYear = Math.round((totalCostPerResidentPerYear / resultCount) * 10000.0) / 10000.0;
    double avgCostPerResidentPerDecade = Math.round((totalCostPerResidentPerDecade / resultCount) * 10000.0) / 10000.0;
    double avgWastePerResidentPerYear = Math.round((wastePerResidentPerYear / resultCount) * 10000.0) / 10000.0;
    double avgAverageCarbonPerYear = Math.round((totalAverageCarbonPerYear / resultCount) * 10000.0) / 10000.0;
    double avgSatisfactionPerYear = Math.round((totalSatisfactionPerYear / resultCount) * 10000.0) / 10000.0;
    double avgSusScore = Math.round((totalSusScore / resultCount) * 10000.0) / 10000.0;

    String sb = "-------------------------------\n"
        + name
        + ":\n"
        + "Average New Houses: " + avgNewHouses + "\n"
        + "Average Demolished Houses: " + avgDemolishedHouses + "\n"
        + "Average New Residents: " + avgNewResidents + "\n"
        + "Average Died Residents: " + avgDiedResidents + "\n"
        + "Average Cost Per Resident Per Year: " + avgCostPerResidentPerYear + "\n"
        + "Average Cost Per Resident Per Decade: " + avgCostPerResidentPerDecade + "\n"
        + "Average Waste Per Resident Per Year: " + avgWastePerResidentPerYear + "\n"
        + "Average Carbon Per Year: " + avgAverageCarbonPerYear + "\n"
        + "Average Satisfaction Per Year: " + avgSatisfactionPerYear + "\n"
        + "Average Sustainability Score: " + avgSusScore + "\n"
        + "-------------------------------";
    System.out.println(sb);
  }
}