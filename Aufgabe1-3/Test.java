import config.Config;
import enums.ScenarioType;

import java.util.ArrayList;

/**
 * Wer hat an was gearbeitet:
 * Ryan Foster (12222173): Basic setup, Enums, basic run method for Scenario, Events, Basic House
 * Oliver Kastner (51908223): Calculation method for sustainability score, average score calculation, satisfaction and carbon statistics
 * Noah Oguamalam (12225111): Premium House, Bio House, Research for house values, (Peer Programming)
 */
public class Test {

    public Test() {

        Config c = new Config(250000, 35, 100, 100, 25, 3000, 25000, 20000, 5000, 0.9f, 0.5f, 15, 50, false);
        ArrayList<Double> configScores = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Scenario config = new Scenario(c);
            double configScore = config.run();
            //System.out.println(minimalScore);
            configScores.add(configScore);
        }

        ArrayList<Double> minimalScores = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Scenario minimal = new Scenario(ScenarioType.MINIMAL);
            double minimalScore = minimal.run();
            //System.out.println(minimalScore);
            minimalScores.add(minimalScore);
        }

        ArrayList<Double> bioScores = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Scenario bio = new Scenario(ScenarioType.BIO);
            double bioScore = bio.run();
            //System.out.println(bioScore);
            bioScores.add(bioScore);
        }

        ArrayList<Double> premiumScores = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Scenario premium = new Scenario(ScenarioType.PREMIUM);
            double premiumScore = premium.run();
            //System.out.println(premiumScore);
            premiumScores.add(premiumScore);
        }

        double totalConfigScore = 0;
        for(double con: configScores) {
            totalConfigScore += con;
        }
        double avgConfigScore = totalConfigScore / configScores.size();

        double totalMinimalScore = 0;
        for(double min: minimalScores) {
            totalMinimalScore += min;
        }
        double avgMinimalScore = totalMinimalScore / minimalScores.size();

        double totalBioScore = 0;
        for(double bioScore: bioScores) {
            totalBioScore += bioScore;
        }
        double avgBioScore = totalBioScore / bioScores.size();

        double totalPremiumScore = 0;
        for(double premiumScore: premiumScores) {
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
