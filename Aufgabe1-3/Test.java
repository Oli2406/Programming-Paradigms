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
        resistances, true, 0.6f);
    ArrayList<Double> configScores = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      Scenario config = new Scenario(c);
      double configScore = config.run();
      //System.out.println(configScore);
      configScores.add(configScore);
    }
    
    ArrayList<Double> minimalScores = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      Scenario minimal = new Scenario(ScenarioType.MINIMAL);
      double minimalScore = minimal.run();
      //System.out.println(minimalScore);
      minimalScores.add(minimalScore);
    }
    
    ArrayList<Double> bioScores = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      Scenario bio = new Scenario(ScenarioType.BIO);
      double bioScore = bio.run();
      //System.out.println(bioScore);
      bioScores.add(bioScore);
    }
    
    ArrayList<Double> premiumScores = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      Scenario premium = new Scenario(ScenarioType.PREMIUM);
      double premiumScore = premium.run();
      //System.out.println(premiumScore);
      premiumScores.add(premiumScore);
    }
    
    double totalConfigScore = 0;
    for (double con : configScores) {
      totalConfigScore += con;
    }
    double avgConfigScore = totalConfigScore / configScores.size();
    
    double totalMinimalScore = 0;
    for (double min : minimalScores) {
      totalMinimalScore += min;
    }
    double avgMinimalScore = totalMinimalScore / minimalScores.size();
    
    double totalBioScore = 0;
    for (double bioScore : bioScores) {
      totalBioScore += bioScore;
    }
    double avgBioScore = totalBioScore / bioScores.size();
    
    double totalPremiumScore = 0;
    for (double premiumScore : premiumScores) {
      totalPremiumScore += premiumScore;
    }
    double avgPremiumScore = totalPremiumScore / premiumScores.size();
    
    System.out.println("Config: " + avgConfigScore);
    System.out.println("--------------------");
    System.out.println("Minimal: " + avgMinimalScore);
    System.out.println("--------------------");
    System.out.println("Bio: " + avgBioScore);
    System.out.println("--------------------");
    System.out.println("Premium: " + avgPremiumScore);
  }
  
  public static void main(String[] args) {
    Test t = new Test();
  }
}
