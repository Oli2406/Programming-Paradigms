import Enums.ScenarioType;

import java.util.ArrayList;

/**
 * Wer hat an was gearbeitet:
 * Ryan Foster (12222173): Basic setup, Enums, basic run method for Scenario, Events, Basic House
 * Oliver Kastner (51908223): Calculation method for sustainability score, average score calculation, satisfaction and carbon statistics
 * Noah Oguamalam (12225111): Premium House, Bio House, Research for house values, (Peer Programming)
 */
public class Test {

    public Test() {
        ArrayList<Double> minimalScores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Scenario minimal = new Scenario(ScenarioType.MINIMAL);
            minimalScores.add(minimal.run());
        }

        ArrayList<Double> bioScores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Scenario bio = new Scenario(ScenarioType.BIO);
            bioScores.add(bio.run());
        }

        ArrayList<Double> premiumScores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Scenario premium = new Scenario(ScenarioType.PREMIUM);
            premiumScores.add(premium.run());
        }

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
