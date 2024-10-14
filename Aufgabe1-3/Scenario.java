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

    public int run() {
        int totalResidents = 0;
        int totalCostDecade = 0;
        int totalCost;
        int totalCarbon;
        int waste;
        float averageCarbon;
        float averageCost;
        float averageCostDecade;

        ArrayList<House> toRemove = new ArrayList<House>();

        for (int year = 1; year < RUNTIME; year++) {
            totalCost = 0;
            totalCarbon = 0;
            waste = 0;
            totalResidents = 0;
            averageCarbon = 0;
            averageCost = 0;
            if(year%10 == 0) {
                totalCostDecade = 0;
                averageCostDecade = 0;
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
                    house.setResidents(Math.max(house.getResidents()-1,0));
                }
            }

            toRemove.clear();
            for (House house : houses) {
                house.age();
                // Local risk factors
                if (Math.random() < RISK_INFESTATION) {
                    System.out.println("Infestation");
                    if(Math.random() < 0.01) {
                        house.setLifetime(0);
                    } else {
                        house.setRenovationLifetime(0);
                    }
                }
                if (Math.random() < RISK_FIRE) {
                    System.out.println("Fire");
                    if(Math.random() < 0.15) {
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
                if(house.getLifetime() == 0) {
                    totalCost += house.getDemolishCost();
                    totalCost += house.getWasteCost();

                    toRemove.add(house);
                } else {
                    // Check for renovation
                    if(house.getRenovationLifetime() == 0) {
                        totalCost += house.getRenovationCost();
                        house.renovate();
                    }
                }
            }
            houses.removeAll(toRemove);
            totalCostDecade += totalCost;
            if(totalResidents == 0) {
                System.out.println("All residents have left");
                break;
            }
            if(houses.isEmpty()) {
                System.out.println("All houses have been demolished");
                break;
            }
            averageCarbon = (float) (totalCarbon / totalResidents)/year;
            averageCost = (float) (totalCost + initialCost) / totalResidents / year;
            averageCostDecade = (float) (totalCostDecade + initialCost) / totalResidents / year;
            System.out.println("Year: " + year + " Residents: " + totalResidents + " Cost: " + totalCost + " Carbon: " + totalCarbon + " Waste: " + waste + " Average Carbon: " + averageCarbon + " Number of Houses: " + houses.size());
            if(year%10 == 9) {
                System.out.println("Total cost for the decade: " + totalCostDecade);
            }
        }


        return 1;
    }
}
