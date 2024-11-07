import config.Config;
import enums.ScenarioType;
import house.Resistance;

import java.util.ArrayList;

/**
 * Wer hat an was gearbeitet:
 * Ryan Foster (12222173): Basic setup, Enums, basic run method for Scenario, Events, Basic House, Config, Resistance, Events
 * Oliver Kastner (51908223): Calculation method for sustainability score, average score calculation, satisfaction and carbon statistics, revitalisation instead of demolition, High significance buildings, shaping of houses
 * Noah Oguamalam (12225111): Premium House, Bio House, Research for house values, Residents, new Houses during simulation
 */
public class Test {
  
  public Test() {
    //These values represent the Premium House, it should be very close to the preset
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

    //STYLE: Lambda expressions are used to create threads for each scenario type
    //STYLE: Threads for the parallel execution of the scenarios
    Thread configThread = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        Scenario config = new Scenario(c);
        configScores.add(config.run());
      }
      double totalConfigScore = 0;
      double totalConfigSatisfaction = 0;
      for (Result con : configScores) {
        totalConfigScore += con.getSusScore();
        totalConfigSatisfaction += con.getTotalSatisfactionPerYear();
      }
      double avgConfigScore = Math.round((totalConfigScore / configScores.size())*10000.0)/10000.0;
      double avgConfigSatisfaction = Math.round((totalConfigSatisfaction / configScores.size())*10000.0)/10000.0;
      printResult("Config", avgConfigScore, avgConfigSatisfaction);
    });

    Thread minimalThread = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        Scenario minimal = new Scenario(ScenarioType.MINIMAL);
        minimalScores.add(minimal.run());
      }
      double totalMinimalScore = 0;
      double totalMinimalSatisfaction = 0;
      for (Result min : minimalScores) {
        totalMinimalScore += min.getSusScore();
        totalMinimalSatisfaction += min.getTotalSatisfactionPerYear();
      }
      double avgMinimalScore = Math.round((totalMinimalScore / minimalScores.size())*10000.0)/10000.0;
      double avgMinimalSatisfaction = Math.round((totalMinimalSatisfaction / minimalScores.size())*10000.0)/10000.0;
      printResult("Minimal", avgMinimalScore, avgMinimalSatisfaction);
    });

    Thread bioThread = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        Scenario bio = new Scenario(ScenarioType.BIO);
        bioScores.add(bio.run());
      }
      double totalBioScore = 0;
      double totalBioSatisfaction = 0;
      for (Result bioScore : bioScores) {
        totalBioScore += bioScore.getSusScore();
        totalBioSatisfaction += bioScore.getTotalSatisfactionPerYear();
      }
      double avgBioScore = Math.round((totalBioScore / bioScores.size())*10000.0)/10000.0;
      double avgBioSatisfaction = Math.round((totalBioSatisfaction / bioScores.size())*10000.0)/10000.0;
      printResult("Bio", avgBioScore, avgBioSatisfaction);
    });

    Thread premiumThread = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        Scenario premium = new Scenario(ScenarioType.PREMIUM);
        premiumScores.add(premium.run());
      }
      double totalPremiumScore = 0;
      for (Result premiumScore : premiumScores) {
        totalPremiumScore += premiumScore.getSusScore();
      }
      double totalPremiumSatisfaction = 0;
        for (Result premiumScore : premiumScores) {
            totalPremiumSatisfaction += premiumScore.getTotalSatisfactionPerYear();
        }
      double avgPremiumScore = Math.round((totalPremiumScore / premiumScores.size())*10000.0)/10000.0;
      double avgPremiumSatisfaction = Math.round((totalPremiumSatisfaction / premiumScores.size())*10000.0)/10000.0;
      printResult("Premium", avgPremiumScore, avgPremiumSatisfaction);
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
   * Prints the result of the simulation
   * @param name The name of the scenario
   * @param avgScore The average sustainability score
   * @param avgSatisfaction The average satisfaction
   */
  private void printResult(String name, double avgScore, double avgSatisfaction) {
      String sb = "-------------------------------\n"
          + name
          + ":\t"
          + avgScore
          + "\n"
          + "\t - Satisfaction: "
          + avgSatisfaction
          + "\n"
          + "-------------------------------";
    System.out.println(sb);
  }
}
