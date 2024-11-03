import Resident.Resident;
import config.Config;
import enums.EventType;
import enums.ScenarioType;
import house.House;

import java.util.ArrayList;

// STYLE: Objektorientiertes Paradigma
public class Scenario {
  // Nominale Abstraktion (Wahrscheinlichkeiten von Risiken)
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
  ArrayList<House> houses = new ArrayList<>();
  private int initialCost = 0;

  private final Config c;

  public Scenario(ScenarioType type) {
    this.c = new Config(type);
    this.generateHouses();
  }

  public Scenario(Config c) {
    this.c = c;
    this.generateHouses();
  }

  private void generateHouses() {
    for (int i = 0; i < HOUSES; i++) {
      House h = c.createHouse(2);
      initialCost += h.getCost();
      initialCost += h.getRenovationCost();
      houses.add(h);
    }
  }

  // STYLE: Prozedurales Paradigma
  public double calculateScore(double avgCostPerYear, double avgCostPerDecade,
                               double avgWastePerYear, double avgCarbonPerYear,
                               double avgSatisfactionPerDecade, double significanceFactor) {

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
    return ((1.0 / (normCostPerYear + normCostPerDecade)) +  // Lower costs lead to a higher score
        (1.0 / normWastePerYear) +                       // Lower waste leads to a higher score
        (1.5 / normCarbonPerYear) +                      // Lower CO2 emissions lead to a higher score (increased weight)
        (satisfaction) * significanceFactor);            // Higher satisfaction leads to a higher score
  }

  // STYLE: Prozedurales Paradigma
  public double run() {
    int totalDemolishedHouses = 0;
    int totalNewHouses = 0;
    int totalNewResidents = 0;
    int totalDiedResidents = 0;
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
    double significanceFactor = 0.0f;
    int toDivide = 0;


    ArrayList<House> housesToRemove = new ArrayList<>();
    ArrayList<Resident> residentsToRemove = new ArrayList<>();
    ArrayList<Resident> toMoveOut = new ArrayList<>();
    ArrayList<House> housesToMoveIn = new ArrayList<>();
    ArrayList<House> removeFromHousesToMoveIn = new ArrayList<>();


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
            house.setLifetime(0);
          } else {
            house.setRenovationLifetime(house.getResistances().isEarthquakeResistance() ? house.getRenovationLifetime() - 1 : 0);
            house.reduceSatisfaction(EventType.EARTHQUAKE, house.getResistances().isEarthquakeResistance());
          }
        }
      }
      if (Math.random() < RISK_WILDFIRE) {
        for (House house : houses) {
          if (Math.random() < 0.05 && !house.getResistances().isWildfireResistance()) {
            house.setLifetime(0);
          } else {
            house.setRenovationLifetime(house.getResistances().isWildfireResistance() ? house.getRenovationLifetime() - 1 : 0);
            house.reduceSatisfaction(EventType.WILDFIRE, house.getResistances().isWildfireResistance());
          }
        }
      }
      if (Math.random() < RISK_FLOOD) {
        for (House house : houses) {
          if (Math.random() < 0.05 && !house.getResistances().isFloodResistance()) {
            house.setLifetime(0);
          } else {
            house.setRenovationLifetime(house.getResistances().isFloodResistance() ? house.getRenovationLifetime() - 1 : 0);
            house.reduceSatisfaction(EventType.FLOOD, house.getResistances().isFloodResistance());
          }
        }
      }
      if (Math.random() < RISK_TORNADO) {
        for (House house : houses) {
          if (Math.random() < 0.15 && !house.getResistances().isTornadoResistance()) {
            house.setLifetime(0);
          } else {
            house.setRenovationLifetime(house.getResistances().isTornadoResistance() ? house.getRenovationLifetime() - 1 : 0);
            house.reduceSatisfaction(EventType.TORNADO, house.getResistances().isTornadoResistance());
          }
        }
      }

      float residentDemolitionWaste = 0;
      float residentRenovationWaste = 0;
      housesToRemove.clear();
      significanceFactor = 0;
      toDivide = 0;
      for (House house : houses) {
        house.age();
        if (!houses.isEmpty()) {
          significanceFactor += house.calculateSignificanceFactor();
          toDivide++;
        }
        //Local risk factors
        if (Math.random() < RISK_INFESTATION) {
          house.setRenovationLifetime(0);
          house.reduceSatisfaction(EventType.INFESTATION, false);
        }
        if (Math.random() < RISK_FIRE) {
          if (Math.random() < 0.15 && !house.getResistances().isFireResistance()) {
            house.setLifetime(0);
          } else {
            house.setRenovationLifetime(house.getResistances().isFireResistance() ? house.getRenovationLifetime() - 1 : 0);
            house.reduceSatisfaction(EventType.FIRE, house.getResistances().isFireResistance());
          }
        }
        if (Math.random() < RISK_BUILDING_COLLAPSE) {
          if (Math.random() < 0.5) {
            house.setLifetime(0);
          } else {
            house.setRenovationLifetime(0);
            house.reduceSatisfaction(EventType.BUILDING_COLLAPSE, false);
          }
        }
        if (Math.random() < RISK_POWER_OUTAGE) {
          house.reduceSatisfaction(EventType.POWER_OUTAGE, house.getResistances().isEnergyResistance());
        }
        if (Math.random() < RISK_MAINTENANCE) {
          house.reduceSatisfaction(EventType.MAINTENANCE, false);
        }
        if (Math.random() < RISK_PLUMBING) {
          house.reduceSatisfaction(EventType.PLUMBING, false);
        }


        for (Resident resident : house.getResidents()) {
          resident.age();
          if (resident.isDead()) {
            residentsToRemove.add(resident);
            totalDiedResidents++;
            break;
          }
          if (resident.isMovingOut()) {
            toMoveOut.add(resident);
            resident.setLivesWithParents(false);
            residentsToRemove.add(resident);
          }
        }

        house.getResidents().removeAll(residentsToRemove);
        residentsToRemove.clear();

        if (house.getResidents().size() >= 2) {
          if (house.getResidents().get(0).isCanHaveChildren() && house.getResidents().get(1).isCanHaveChildren()) {
            for (Resident resident : house.getResidents()) {
              resident.setSatisfaction(Math.min(resident.getSatisfaction() + 0.012f, c.getSatisfactionRate()));
            }
            if (Math.random() < 0.12) {
              house.addResident(new Resident(
                  0,
                  c.getSatisfactionRate(),
                  true,
                  false,
                  false
              ));
              totalNewResidents++;
            }
          }
        }

        if (house.getResidents().size() <= 1) {
          housesToMoveIn.add(house);
        }



        totalResidents += house.getResidents().size();
        totalCost += house.getServiceCost();
        totalCarbon += house.getCarbon();

        // Check for demolition
        if (house.getLifetime() == 0) {
          if (Math.random() < 0.5) {
            house.revitalize();
            totalCost += house.getRevitalizationCost();
            totalCarbon += house.getRevitalizationCarbon();
          } else {
            totalCost += house.getDemolishCost();
            totalCost += house.getWasteCost();
            if (house.getResidents().size() > 0) {
              residentDemolitionWaste += (float) house.getDemolitionWaste() / house.getResidents().size();
            }
            housesToRemove.add(house);
            //if only one resident lived in  house -> moves out to other house
            if (house.getResidents().size() == 1) toMoveOut.add(house.getResidents().getFirst());
            for (Resident resident : house.getResidents()) {
              resident.setSatisfaction(Math.max(resident.getSatisfaction() - 0.012f, 0));
            }
          }
        } else {
          // Check for renovation
          if (house.getRenovationLifetime() == 0) {
            totalCost += house.getRenovationCost();
            house.renovate();
            totalCarbon += house.getRenovationCarbon();
            if (house.getResidents().size() > 0) {
              residentRenovationWaste += (float) house.getRenovationWaste() / house.getResidents().size();
            }
          }
        }
      }

      for (Resident resident : toMoveOut) {
        for (House house : housesToMoveIn) {
          if (house.getResidents().size() == 0) {
            house.addResident(resident);
            resident.setMovingOut(false);
            break;
          }
          if (Math.abs(house.getResidents().getFirst().getAge() - resident.getAge()) <= 5) {
            house.addResident(resident);
            resident.setMovingOut(false);
            house.getResidents().get(0).setCanHaveChildren(true);
            house.getResidents().get(1).setCanHaveChildren(true);
            house.getResidents().get(0).setSatisfaction(Math.min(house.getResidents().get(0).getSatisfaction() + 0.012f, c.getSatisfactionRate()));
            house.getResidents().get(1).setSatisfaction(Math.min(house.getResidents().get(1).getSatisfaction() + 0.012f, c.getSatisfactionRate()));
            removeFromHousesToMoveIn.add(house);
            break;
          }
        }
        if (resident.isMovingOut()) {
          houses.add(c.createHouse(new ArrayList<>() {{
            add(resident);
          }}));
          housesToMoveIn.add(houses.getLast());
          resident.setMovingOut(false);
          totalNewHouses++;
        }
        housesToMoveIn.removeAll(removeFromHousesToMoveIn);
      }
      toMoveOut.clear();

      for (House house : housesToRemove) {
        if (house.getResidents().size() > 1) {
          for (House houseToMoveIn : housesToMoveIn) {
            if (houseToMoveIn.getResidents().size() == 0) {
              houseToMoveIn.addResidents(house.getResidents());
              removeFromHousesToMoveIn.add(houseToMoveIn);
              house.removeResidents(house.getResidents());
              break;
            }
          }
          if (house.getResidents().size() != 0) {
            houses.add(c.createHouse(house.getResidents()));
            totalNewHouses++;
          }
        }
      }
      housesToMoveIn.removeAll(removeFromHousesToMoveIn);

      for (House house : houses) {
        for (Resident resident : house.getResidents()) {
          satisfaction += resident.getSatisfaction();
        }
      }


      residentDemolitionWaste /= HOUSES;
      residentRenovationWaste /= HOUSES;
      wastePerResidentPerYear += residentDemolitionWaste + residentRenovationWaste;
      houses.removeAll(housesToRemove);
      totalDemolishedHouses += housesToRemove.size();
      totalCostPerDecade += totalCost;


      // If no houses are left, break
      if (houses.isEmpty()) {
        break;
      }
      //If no residents are left, break
      if (totalResidents == 0) {
        break;
      }

      averageCarbon = (totalCarbon / totalResidents) / year;
      totalAverageCarbonPerYear += averageCarbon;
      averageCost = (float) (totalCost + initialCost) / totalResidents / year;
      totalCostPerResidentPerYear += averageCost;
      if (year % 10 == 9) {
        satisfaction /= (totalResidents * 10);
      }
      //System.out.println(satisfaction);
    }
    // Calculate the statistics
    significanceFactor /= toDivide;
    totalCostPerResidentPerYear /= RUNTIME;
    totalCostPerResidentPerDecade /= ((float) RUNTIME);
    wastePerResidentPerYear /= RUNTIME + 0.27f;
    totalAverageCarbonPerYear /= RUNTIME;
    totalSatisfactionPerYear /= ((float) RUNTIME / 10);
    susScore =

        calculateScore(totalCostPerResidentPerYear, totalCostPerResidentPerDecade,
            wastePerResidentPerYear, totalAverageCarbonPerYear,
            totalSatisfactionPerYear, significanceFactor);

    // Print statistics
    /*
    System.out.println("number of new houses: " + totalNewHouses);
    System.out.println("number of demolished houses: " + totalDemolishedHouses);
    System.out.println("number of new residents: " + totalNewResidents);
    System.out.println("number of died residents: " + totalDiedResidents);
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
