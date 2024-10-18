import House.House;
import House.MinimalHouse;

import java.util.ArrayList;

public class Scenario {
    // Global Risks
    private static float RISK_EARTHQUAKE = 0.01f;
    private static float RISK_NUCLEAR = 0.00002f;

    // Local Risks
    private static float RISK_INFESTATION = 0.005f;
    private static float RISK_FIRE = 0.01f;

    // Scenario Type
    private ScenarioType type;

    // Runtime
    private static int RUNTIME = 100;

    private static int HOUSES = 10;

    private int initialCost = 0;

    ArrayList<House> houses = new ArrayList<House>();

    public Scenario(ScenarioType type) {
        this.type = type;
        switch (type) {
            case MINIMAL:
                for (int i = 0; i < HOUSES; i++) {
                    House h = new MinimalHouse();
                    initialCost += h.getCost();
                    houses.add(h);
                }
                break;
            case BIO:
                for (int i = 0; i < HOUSES; i++) {
                    //houses.add(new House.BioHouse());
                }
                break;
            case PREMIUM:
                for (int i = 0; i < HOUSES; i++) {
                    //houses.add(new House.PremiumHouse());
                }
                break;
        }
    }
    
    public double calculateScore(double avgCostPerYear, double avgCostPerDecade, double avgWastePerYear, double avgCarbonPerYear, double avgSatisfactionPerDecade) {
        
        // Normalization factors (example values to scale the inputs)
        double maxCostPerYear = 10000;   // Maximum realistic yearly cost
        double maxCostPerDecade = 100000; // Maximum realistic decade cost
        double maxWastePerYear = 10;     // Maximum waste per year in tons
        double maxCarbonPerYear = 10;    // Maximum CO2 per year in tons
        double maxSatisfaction = 1;      // Satisfaction ranges from 0 to 1
        
        // Normalize each input parameter
        double normCostPerYear = avgCostPerYear / maxCostPerYear;
        double normCostPerDecade = avgCostPerDecade / maxCostPerDecade;
        double normWastePerYear = avgWastePerYear / maxWastePerYear;
        double normCarbonPerYear = avgCarbonPerYear / maxCarbonPerYear;
        double normSatisfaction = avgSatisfactionPerDecade / maxSatisfaction;
        
        // Calculate sustainability score: higher satisfaction, lower costs, waste, and CO2 increase the score
        double sustainabilityScore =
            (1.0 / (normCostPerYear + normCostPerDecade)) +  // Lower costs lead to a higher score
                (1.0 / normWastePerYear) +                      // Lower waste leads to a higher score
                (1.0 / normCarbonPerYear) +                     // Lower CO2 emissions lead to a higher score
                normSatisfaction;                               // Higher satisfaction leads to a higher score
        
        return sustainabilityScore;
    }

    public int run() {
        int totalResidents = 0;
        int totalCostDecade = 0;
        int totalCost;
        int totalCarbon;
        int waste;
        float averageCarbon;
        float averageCost;
        float averageCostDecade;
        float satisfaction = 0;
        int totalResidentsPerDecade = 0;
        float wastePerResidentPerYear = 0.0f;
        float totalAverageCarbonPerYear = 0.0f;
        float totalSatisfactionPerYear = 0.0f;
        float totalCostPerResidentPerYear = 0.0f;
        float totalCostPerResidetnPerDecade = 0.0f;
        
        ArrayList<House> toRemove = new ArrayList<House>();

        for (int year = 1; year < RUNTIME; year++) {
            totalCost = 0;
            totalCarbon = 0;
            waste = 0;
            totalResidents = 0;
            averageCarbon = 0;
            averageCost = 0;
            
            
            if(year%10 == 0) {
                totalSatisfactionPerYear += satisfaction;
                totalCostPerResidetnPerDecade += totalCostDecade;
                totalResidentsPerDecade = 0;
                totalCostDecade = 0;
                averageCostDecade = 0;
                satisfaction = 0;
            }

            // Global risk factors
            if(Math.random() < RISK_EARTHQUAKE) {
                System.out.println("Earthquake");
                for (House house : houses) {
                    if(Math.random() < 0.1) {
                        house.setLifetime(0);
                    } else {
                        house.setRenovationLifetime(0);
                    }
                }
            }
            if(Math.random() < RISK_NUCLEAR) {
                System.out.println("Nuclear Meltdown");
                for (House house : houses) {
                    toRemove.add(house);
                }
                houses.removeAll(houses);
            }
            float residentDemolitionWaste = 0;
            float residentRenovationWaste = 0;
            toRemove.clear();
            for (House house : houses) {
                house.age();
                satisfaction += house.getSatisfactionRate();
                totalResidentsPerDecade += house.getResidents();
                if (Math.random() < RISK_INFESTATION) {
                    System.out.println("Infestation");
                    if (Math.random() < 0.01) {
                        house.setLifetime(0);
                    } else {
                        house.setRenovationLifetime(0);
                    }
                }
                if (Math.random() < RISK_FIRE) {
                    System.out.println("Fire");
                    if (Math.random() < 0.15) {
                        house.setLifetime(0);
                    } else {
                        house.setRenovationLifetime(0);
                    }
                }
                totalResidents += house.getResidents();
                totalCost += house.getServiceCost();
                totalCarbon += house.getCarbon();
                waste += house.getWasteCost();
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
            totalCostDecade += totalCost;
            if(totalResidents == 0) {
                System.out.println("All residents have left");
                break;
            }
            if(houses.isEmpty()) {
                totalCostPerResidentPerYear /= year;
                totalCostPerResidetnPerDecade /= ((float) year);
                wastePerResidentPerYear /= year + 0.27; //TODO: Add variable for waste per year per resident.
                totalAverageCarbonPerYear /= year;
                totalSatisfactionPerYear /= ((float) year / 10);
                
                System.out.println("All houses have been demolished");
                System.out.println("average cost per resident per year: " + totalCostPerResidentPerYear);
                System.out.println("average cost per resident per decade: " + totalCostPerResidetnPerDecade);
                System.out.println("average waste per resident per year: " + wastePerResidentPerYear + " tons");
                System.out.println("average carbon per resident per year: " + totalAverageCarbonPerYear + " tons");
                System.out.println("average satisfaction per decade: " + totalSatisfactionPerYear);
                double susScore = calculateScore(totalCostPerResidentPerYear, totalCostPerResidetnPerDecade,
                                                 wastePerResidentPerYear, totalAverageCarbonPerYear,
                                                 totalSatisfactionPerYear);
                System.out.println("Sustainability score for this scenario: " + susScore);
                break;
            }
            averageCarbon = (float) (totalCarbon / totalResidents)/year;
            totalAverageCarbonPerYear += averageCarbon;
            averageCost = (float) (totalCost + initialCost) / totalResidents / year;
            averageCostDecade = (float) (totalCostDecade + initialCost) / totalResidents / year;
            totalCostPerResidentPerYear += averageCost;
            System.out.println("Year: " + year + " Residents: " + totalResidents + " Cost: " + totalCost + " Carbon: " + totalCarbon + " Waste: " + waste + " Average Carbon: " + averageCarbon + " Number of Houses: " + houses.size());
            if(year%10 == 9) {
                satisfaction /= (houses.size() * 10);
                System.out.println("Average satisfaction: " + satisfaction);
                System.out.println("Total cost for the decade: " + totalCostDecade);
            }
        }
        return 1;
    }
}
