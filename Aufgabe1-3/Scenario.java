import config.Config;
import enums.EventType;
import enums.ScenarioType;
import house.BioHouse;
import house.House;
import house.MinimalHouse;
import house.PremiumHouse;

import java.util.ArrayList;

public class Scenario {
    // Global Risks
    private static final float RISK_EARTHQUAKE = 0.008f;
    private static final float RISK_FLOOD = 0.01f;
    private static final float RISK_TORNADO = 0.005f;
    private static final float RISK_WILDFIRE = 0.001f;


    // Local Risks
    private static final float RISK_INFESTATION = 0.005f;
    private static final float RISK_FIRE = 0.01f;
    private static final float RISK_BUILDING_COLLAPSE = 0.0005f;
    private static final float RISK_POWER_OUTAGE = 0.025f;
    private static final float RISK_MAINTENANCE = 0.02f;
    private static final float RISK_PLUMBING = 0.015f;


    // Runtime
    private static final int RUNTIME = 101;

    private static final int HOUSES = 10;

    private int initialCost = 0;

    ArrayList<House> houses = new ArrayList<>();

    public Scenario(ScenarioType type) {
        switch (type) {
            case MINIMAL:
                for (int i = 0; i < HOUSES; i++) {
                    House h = new MinimalHouse();
                    initialCost += h.getCost();
                    initialCost += h.getRenovationCost();
                    houses.add(h);
                }
                break;
            case BIO:
                for (int i = 0; i < HOUSES; i++) {
                    House h = new BioHouse();
                    initialCost += h.getCost();
                    initialCost += h.getRenovationCost();
                    houses.add(h);
                }
                break;
            case PREMIUM:
                for (int i = 0; i < HOUSES; i++) {
                    House h = new PremiumHouse();
                    initialCost += h.getCost();
                    initialCost += h.getRenovationCost();
                    houses.add(h);
                }
                break;
        }
    }

    public Scenario(Config c) {
        for (int i = 0; i < HOUSES; i++) {
            House h = c.createHouse();
            initialCost += h.getCost();
            initialCost += h.getRenovationCost();
            houses.add(h);
        }
    }

    public double calculateScore(double avgCostPerYear, double avgCostPerDecade, double avgWastePerYear, double avgCarbonPerYear, double avgSatisfactionPerDecade) {

        // Normalization factors (basically example values to scale the inputs)
        double maxCostPerYear = 10000;
        double maxCostPerDecade = 100000;
        double maxWastePerYear = 10;
        double maxCarbonPerYear = 10;
        double maxSatisfactionPoints = 20;

        // Normalize each input parameter
        double normCostPerYear = avgCostPerYear / maxCostPerYear;
        double normCostPerDecade = avgCostPerDecade / maxCostPerDecade;
        double normWastePerYear = avgWastePerYear / maxWastePerYear;
        double normCarbonPerYear = avgCarbonPerYear / maxCarbonPerYear;
        double satisfaction = maxSatisfactionPoints * avgSatisfactionPerDecade;

        // Calculate sustainability score: higher satisfaction, lower costs, waste, and CO2 increase the score
        return (1.0 / (normCostPerYear + normCostPerDecade)) +  // Lower costs lead to a higher score
            (1.0 / normWastePerYear) +                       // Lower waste leads to a higher score
            (1.5 / normCarbonPerYear) +                      // Lower CO2 emissions lead to a higher score (increased weight)
            (satisfaction);                                // Higher satisfaction leads to a higher score
    }

    public double run() {
        int totalResidents;
        int totalCostPerDecade = 0;
        int totalCost;
        float totalCarbon;
        float averageCarbon;
        float averageCost;
        float satisfaction = 0;
        double susScore;
        float wastePerResidentPerYear = 0.0f;
        float totalAverageCarbonPerYear = 0.0f;
        float totalSatisfactionPerYear = 0.0f;
        float totalCostPerResidentPerYear = 0.0f;
        float totalCostPerResidentPerDecade = 0.0f;

        ArrayList<House> toRemove = new ArrayList<>();

        for (int year = 1; year < RUNTIME; year++) {
            totalCost = 0;
            totalCarbon = 0;
            totalResidents = 0;

            if (year % 10 == 0) {
                totalSatisfactionPerYear += satisfaction;
                totalCostPerResidentPerDecade += totalCostPerDecade;
                totalCostPerDecade = 0;
                satisfaction = 0;
            }

            // Global risk factors
            if (Math.random() < RISK_EARTHQUAKE) {
                for (House house : houses) {
                    if (Math.random() < 0.1 && !house.getResistances().isEarthquakeResistance()) {
                        //TODO: Fix calculations if house is destroyed
                        house.setLifetime(0);
                    } else {
                        house.setRenovationLifetime(house.getResistances().isEarthquakeResistance() ? house.getRenovationLifetime()-1 : 0);
                        house.reduceSatisfaction(EventType.EARTHQUAKE, house.getResistances().isEarthquakeResistance());
                    }
                }
            }
            if (Math.random() < RISK_WILDFIRE) {
                for (House house : houses) {
                    if (Math.random() < 0.05 && !house.getResistances().isWildfireResistance()) {
                        //TODO: Fix calculations if house is destroyed
                        house.setLifetime(0);
                    } else {
                        house.setRenovationLifetime(house.getResistances().isWildfireResistance()? house.getRenovationLifetime()-1 : 0);
                        house.reduceSatisfaction(EventType.WILDFIRE, house.getResistances().isWildfireResistance());
                    }
                }
            }
            if (Math.random() < RISK_FLOOD) {
                for (House house : houses) {
                    if(Math.random() < 0.05 && !house.getResistances().isFloodResistance()) {
                        //TODO: Fix calculations if house is destroyed
                        house.setLifetime(0);
                    } else {
                        house.setRenovationLifetime(house.getResistances().isFloodResistance() ? house.getRenovationLifetime()-1 : 0);
                        house.reduceSatisfaction(EventType.FLOOD, house.getResistances().isFloodResistance());
                    }
                }
            }
            if (Math.random() < RISK_TORNADO) {
                for (House house : houses) {
                    if (Math.random() < 0.15 && !house.getResistances().isTornadoResistance()) {
                        //TODO: Fix calculations if house is destroyed
                        house.setLifetime(0);
                    } else {
                        house.setRenovationLifetime(house.getResistances().isTornadoResistance()? house.getRenovationLifetime()-1 : 0);
                        house.reduceSatisfaction(EventType.TORNADO, house.getResistances().isTornadoResistance());
                    }
                }
            }

            float residentDemolitionWaste = 0;
            float residentRenovationWaste = 0;
            toRemove.clear();
            for (House house : houses) {
                house.age();

                //Local risk factors
                if (Math.random() < RISK_INFESTATION) {
                    house.setRenovationLifetime(0);
                    house.reduceSatisfaction(EventType.INFESTATION, false);
                }
                if (Math.random() < RISK_FIRE) {
                    if (Math.random() < 0.15 && !house.getResistances().isFireResistance()) {
                        //TODO: Fix calculations if house is destroyed
                        house.setLifetime(0);
                    } else {
                        house.setRenovationLifetime(house.getResistances().isFireResistance() ? house.getRenovationLifetime()-1 : 0);
                        house.reduceSatisfaction(EventType.FIRE,house.getResistances().isFireResistance());
                    }
                }
                if (Math.random() < RISK_BUILDING_COLLAPSE) {
                    if (Math.random() < 0.5) {
                        //TODO: Fix calculations if house is destroyed
                        house.setLifetime(0);
                    } else {
                        house.setRenovationLifetime(0);
                        house.reduceSatisfaction(EventType.BUILDING_COLLAPSE,false);
                    }
                }
                if (Math.random() < RISK_POWER_OUTAGE) {
                    house.reduceSatisfaction(EventType.POWER_OUTAGE,house.getResistances().isEnergyResistance());
                }
                if (Math.random() < RISK_MAINTENANCE) {
                    house.reduceSatisfaction(EventType.MAINTENANCE,false);
                }
                if (Math.random() < RISK_PLUMBING) {
                    house.reduceSatisfaction(EventType.PLUMBING,false);
                }

                satisfaction += house.getSatisfactionRate();
                totalResidents += house.getResidents();
                totalCost += house.getServiceCost();
                totalCarbon += house.getCarbon();

                // Check for demolition
                if (house.getLifetime() == 0) {
                    totalCost += house.getDemolishCost();
                    totalCost += house.getWasteCost();
                    residentDemolitionWaste += (float) house.getDemolitionWaste() / house.getResidents();
                    toRemove.add(house);
                } else {
                    // Check for renovation
                    if (house.getRenovationLifetime() == 0) {
                        totalCost += house.getRenovationCost();
                        house.renovate();
                        totalCarbon += house.getRenovationCarbon();
                        residentRenovationWaste += (float) house.getRenovationWaste() / house.getResidents();
                    }
                }
            }
            residentDemolitionWaste /= HOUSES;
            residentRenovationWaste /= HOUSES;
            wastePerResidentPerYear += residentDemolitionWaste + residentRenovationWaste;
            houses.removeAll(toRemove);
            totalCostPerDecade += totalCost;

            // If no houses are left, break
            if (houses.isEmpty()) {
                break;
            }

            averageCarbon = (totalCarbon / totalResidents) / year;
            totalAverageCarbonPerYear += averageCarbon;
            averageCost = (float) (totalCost + initialCost) / totalResidents / year;
            totalCostPerResidentPerYear += averageCost;
            if (year % 10 == 9) {
                satisfaction /= (houses.size() * 10);
            }
            //System.out.println(satisfaction);
        }
        // Calculate the statistics
        totalCostPerResidentPerYear /= RUNTIME;
        totalCostPerResidentPerDecade /= ((float) RUNTIME);
        wastePerResidentPerYear /= RUNTIME + 0.27f; //TODO: Add variable for waste per year per resident.
        totalAverageCarbonPerYear /= RUNTIME;
        totalSatisfactionPerYear /= ((float) RUNTIME / 10);
        susScore = calculateScore(totalCostPerResidentPerYear, totalCostPerResidentPerDecade,
            wastePerResidentPerYear, totalAverageCarbonPerYear,
            totalSatisfactionPerYear);
        /*
        // Print statistics
        System.out.println("average cost per resident per year: " + totalCostPerResidentPerYear);
        System.out.println("average cost per resident per decade: " + totalCostPerResidentPerDecade);
        System.out.println("average waste per resident per year: " + wastePerResidentPerYear + " tons");
        System.out.println("average carbon per resident per year: " + totalAverageCarbonPerYear + " tons");
        System.out.println("average satisfaction per decade: " + totalSatisfactionPerYear);
        System.out.println("Sustainability score for this scenario: " + susScore);
        */

        return susScore;
    }
}
