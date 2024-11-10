import Resident.Resident;
import config.Config;
import enums.EventType;
import enums.ScenarioType;
import house.House;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

// STYLE: Objektorientiertes Paradigma
public class Scenario {
  // Nominale Abstraktion (Wahrscheinlichkeiten von Risiken)
  // BAD: Die Verwendung von statischen Methoden und Konstanten in der Klasse Scenario reduziert die Flexibilität und erschwert das Testen und die Wartung.
  //      Dynamisches Binden würde flexibleren und wartbareren Code ermöglichen.

  // BAD: Die Klasse Scenario hat zu viele Verantwortlichkeiten, einschließlich der Generierung von Häusern, der Berechnung von Risiken und dem Ausführen der Simulation.
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

  // GOOD: Die Klasse Scenario hat einen hohen Klassenzusammenhalt.
  //       Sie ist für die Simulation eines Szenarios zuständig. Die Klasse könnte auch in kleinere Klassen aufgeteilt werden.
  /**
   * Create a new scenario with the given type.
   * @param type The type of the scenario
   */
  public Scenario(ScenarioType type) {
    this.c = new Config(type);
    this.generateHouses();
  }

  /**
   * Create a new scenario with the given configuration.
   * @param c The configuration for the scenario
   */
  public Scenario(Config c) {
    this.c = c;
    this.generateHouses();
  }

  /**
   * Generate the houses for the scenario.
   */
  private void generateHouses() {
    IntStream.range(0, HOUSES).forEach(i -> {
      House h = c.createHouse(2);
      initialCost += h.getCost();
      initialCost += h.getRenovationCost();
      houses.add(h);
    });
  }

  // STYLE: Prozedurales Paradigma
  // GOOD: Durch dynamisches Binden, kann die CalculateScore Methode in einer Subklasse überschrieben werden.
  // BAD: Die Methode `calculateScore` verwendet viele magische Zahlen, was die Lesbarkeit und Wartbarkeit reduziert. Diese Werte sollten als Konstanten definiert werden.
  /**
   * Calculate the sustainability score based on the given input parameters.
   *
   * @param avgCostPerYear      The average cost per resident per year
   * @param avgCostPerDecade    The average cost per resident per decade
   * @param avgWastePerYear     The average waste per resident per year
   * @param avgCarbonPerYear    The average carbon per resident per year
   * @param avgSatisfactionPerDecade The average satisfaction per decade
   * @param significanceFactor  The significance factor of the buildings
   * @return The calculated sustainability score
   *
   * Zusicherung: Alle Eingabeparameter müssen gültige Werte sein (z.B. keine negativen Zahlen).
   */
  public double calculateScore(double avgCostPerYear, double avgCostPerDecade,
                               double avgWastePerYear, double avgCarbonPerYear,
                               double avgSatisfactionPerDecade, double significanceFactor) {

    double maxCostPerYear = 10000;
    double maxCostPerDecade = 100000;
    double maxWastePerYear = 10;
    double maxCarbonPerYear = 10;
    double maxSatisfactionPoints = 20;

    double normCostPerYear = avgCostPerYear / maxCostPerYear;
    double normCostPerDecade = avgCostPerDecade / maxCostPerDecade;
    double normWastePerYear = avgWastePerYear / maxWastePerYear;
    double normCarbonPerYear = avgCarbonPerYear / maxCarbonPerYear;
    double satisfaction = maxSatisfactionPoints * avgSatisfactionPerDecade;

    return ((1.0 / (normCostPerYear + normCostPerDecade)) +
        (1.0 / normWastePerYear) +
        (1.5 / normCarbonPerYear) +
        (satisfaction) * significanceFactor);
  }

  // STYLE: Prozedurales Paradigma
  // BAD: Die Methode `run` ist zu lang und komplex, was den Kontrollfluss schwer nachvollziehbar macht.
  // Sie sollte in kleinere Methoden aufgeteilt werden, um die Lesbarkeit und Wartbarkeit zu verbessern.

  /**
   * Run the simulation for the given scenario.
   *
   * @return The calculated sustainability score
   */
  public Result run() {
    int totalDemolishedHouses = 0;
    int totalNewHouses = 0;
    int totalNewResidents = 0;
    int totalDiedResidents = 0;
    int totalResidents;
    int totalResidentsPerDecade = 0;
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
      float residentDemolitionWaste = 0;
      float residentRenovationWaste = 0;
      totalResidents = 0;
      housesToRemove.clear();
      significanceFactor = 0;
      toDivide = 0;
      
      if (year % 10 == 0) {
        totalSatisfactionPerYear = satisfaction;
        totalCostPerResidentPerDecade += totalCostPerDecade;
        totalCostPerDecade = 0;
        satisfaction = 0;
        totalResidentsPerDecade = 0;
      }
      
      calculateGlobalRisks();
      for (House house : houses) {
        house.age();
        if (!houses.isEmpty()) {
          significanceFactor += house.calculateSignificanceFactor();
          toDivide++;
        }
        calculateLocalRisks(house);
        
        totalDiedResidents += checkResident(house, residentsToRemove, toMoveOut);
        totalNewResidents += checkIfChildIsBorn(house);
        
        if (house.getResidents().size() <= 1) {
          housesToMoveIn.add(house);
        }
        
        totalResidents += house.getResidents().size();
        totalResidentsPerDecade += house.getResidents().size();
        totalCost += house.getServiceCost();
        totalCarbon += house.getCarbon();
        
        if (house.getLifetime() == 0) {
          if (Math.random() < 0.5) {
            house.revitalize();
            totalCost += house.getRevitalizationCost();
            totalCarbon += house.getRevitalizationCarbon();
          } else {
            totalCost += house.getDemolishCost();
            totalCost += house.getWasteCost();
            
            handleDemolition(house, housesToRemove, residentDemolitionWaste, toMoveOut);
          }
        } else if (house.getRenovationLifetime() == 0) {
          totalCost += house.getRenovationCost();
          house.renovate();
          totalCarbon += house.getRenovationCarbon();
          if (!house.getResidents().isEmpty()) {
            residentRenovationWaste += (float) house.getRenovationWaste() / house.getResidents().size();
          }
        }
      }
      
      totalNewHouses += moveResidents(toMoveOut, housesToMoveIn, removeFromHousesToMoveIn);
      totalNewHouses += moveResidentsOutOfDestroyedHouses(housesToRemove, housesToMoveIn, removeFromHousesToMoveIn);
      
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
      
      if (houses.isEmpty() || totalResidents == 0) break;
      
      averageCarbon = (totalCarbon / totalResidents) / year;
      totalAverageCarbonPerYear += averageCarbon;
      averageCost = (float) (totalCost + initialCost) / totalResidents / year;
      totalCostPerResidentPerYear += averageCost;
      
      if (year % 10 == 9) {
        satisfaction /= totalResidentsPerDecade;
      }
    }
    
    susScore = calculateStatistics(significanceFactor, toDivide, totalCostPerResidentPerYear,
        totalCostPerResidentPerDecade, wastePerResidentPerYear,
        totalAverageCarbonPerYear, totalSatisfactionPerYear);
    
    return new Result(totalNewHouses, totalDemolishedHouses, totalNewResidents, totalDiedResidents,
        totalCostPerResidentPerYear, totalCostPerResidentPerDecade, wastePerResidentPerYear,
        totalAverageCarbonPerYear, totalSatisfactionPerYear, susScore);
  }

  // GOOD: Die Methode `calculateGlobalRisks` hat einen klaren und nachvollziehbaren Kontrollfluss.
  // Jede Risikoart wird separat behandelt, was die Lesbarkeit und Wartbarkeit verbessert.
  /**
   * Calculate the global risks for the houses.
   */
  private void calculateGlobalRisks() {
    List<Runnable> globalRiskActions = List.of(
        () -> {
          if (Math.random() < RISK_EARTHQUAKE) {
            houses.forEach(house -> {
              if (Math.random() < 0.1 && !house.getResistances().isEarthquakeResistance()) {
                house.setLifetime(0);
              } else {
                house.setRenovationLifetime(house.getResistances().isEarthquakeResistance() ? house.getRenovationLifetime() - 1 : 0);
                house.reduceSatisfaction(EventType.EARTHQUAKE, house.getResistances().isEarthquakeResistance());
              }
            });
          }
        },
        () -> {
          if (Math.random() < RISK_WILDFIRE) {
            houses.forEach(house -> {
              if (Math.random() < 0.05 && !house.getResistances().isWildfireResistance()) {
                house.setLifetime(0);
              } else {
                house.setRenovationLifetime(house.getResistances().isWildfireResistance() ? house.getRenovationLifetime() - 1 : 0);
                house.reduceSatisfaction(EventType.WILDFIRE, house.getResistances().isWildfireResistance());
              }
            });
          }
        },
        () -> {
          if (Math.random() < RISK_FLOOD) {
            houses.forEach(house -> {
              if (Math.random() < 0.05 && !house.getResistances().isFloodResistance()) {
                house.setLifetime(0);
              } else {
                house.setRenovationLifetime(house.getResistances().isFloodResistance() ? house.getRenovationLifetime() - 1 : 0);
                house.reduceSatisfaction(EventType.FLOOD, house.getResistances().isFloodResistance());
              }
            });
          }
        },
        () -> {
          if (Math.random() < RISK_TORNADO) {
            houses.forEach(house -> {
              if (Math.random() < 0.15 && !house.getResistances().isTornadoResistance()) {
                house.setLifetime(0);
              } else {
                house.setRenovationLifetime(house.getResistances().isTornadoResistance() ? house.getRenovationLifetime() - 1 : 0);
                house.reduceSatisfaction(EventType.TORNADO, house.getResistances().isTornadoResistance());
              }
            });
          }
        }
    );
    globalRiskActions.forEach(Runnable::run);
  }
  
  
  // BAD: Die Methode `calculateLocalRisks` verwendet globale Konstanten, was die Flexibilität und Testbarkeit reduziert.
  // Besser wäre es, diese Werte als Parameter zu übergeben.
  /**
   * Calculate the local risks for the given house.
   * @param house The house to calculate the risks for
   */
  private void calculateLocalRisks(House house) {
    List<Runnable> riskActions = List.of(
        () -> {
          if (Math.random() < RISK_INFESTATION) {
            house.setRenovationLifetime(0);
            house.reduceSatisfaction(EventType.INFESTATION, false);
          }
        },
        () -> {
          if (Math.random() < RISK_FIRE) {
            if (Math.random() < 0.15 && !house.getResistances().isFireResistance()) {
              house.setLifetime(0);
            } else {
              house.setRenovationLifetime(house.getResistances().isFireResistance() ? house.getRenovationLifetime() - 1 : 0);
              house.reduceSatisfaction(EventType.FIRE, house.getResistances().isFireResistance());
            }
          }
        },
        () -> {
          if (Math.random() < RISK_BUILDING_COLLAPSE) {
            if (Math.random() < 0.5) {
              house.setLifetime(0);
            } else {
              house.setRenovationLifetime(0);
              house.reduceSatisfaction(EventType.BUILDING_COLLAPSE, false);
            }
          }
        },
        () -> {
          if (Math.random() < RISK_POWER_OUTAGE) {
            house.reduceSatisfaction(EventType.POWER_OUTAGE, house.getResistances().isEnergyResistance());
          }
        },
        () -> {
          if (Math.random() < RISK_MAINTENANCE) {
            house.reduceSatisfaction(EventType.MAINTENANCE, false);
          }
        },
        () -> {
          if (Math.random() < RISK_PLUMBING) {
            house.reduceSatisfaction(EventType.PLUMBING, false);
          }
        }
    );
    riskActions.forEach(Runnable::run);
  }
  
  /**
   * Calculate the statistics for the given input parameters.
   *
   * @param significanceFactor The significance factor of the buildings
   * @param toDivide The number to divide the significance factor by
   * @param totalCostPerResidentPerYear The total cost per resident per year
   * @param totalCostPerResidentPerDecade The total cost per resident per decade
   * @param wastePerResidentPerYear The waste per resident per year
   * @param totalAverageCarbonPerYear The total average carbon per year
   * @param totalSatisfactionPerYear The total satisfaction per year
   * @return The calculated sustainability score
   */
  private double calculateStatistics(double significanceFactor, int toDivide, float totalCostPerResidentPerYear, float totalCostPerResidentPerDecade, float wastePerResidentPerYear, float totalAverageCarbonPerYear, float totalSatisfactionPerYear) {
    significanceFactor /= toDivide;
    totalCostPerResidentPerYear /= RUNTIME;
    totalCostPerResidentPerDecade /= ((float) RUNTIME);
    wastePerResidentPerYear /= RUNTIME + 0.27f;
    totalAverageCarbonPerYear /= RUNTIME;
    totalSatisfactionPerYear /= ((float) RUNTIME / 10);
    return calculateScore(totalCostPerResidentPerYear, totalCostPerResidentPerDecade,
            wastePerResidentPerYear, totalAverageCarbonPerYear,
            totalSatisfactionPerYear, significanceFactor);
  }

  // GOOD: Die Methode `moveResidents` vermeidet die Verwendung globaler Variablen und hat einen klaren Kontrollfluss. Die Verwendung von Parametern und lokalen Variablen macht die Methode leicht nachvollziehbar.
  /**
   * Move the residents from the given list to the given houses.
   *
   * @param toMoveOut The list of residents to move out
   * @param housesToMoveIn The list of houses to move in
   * @param removeFromHousesToMoveIn The list of houses to remove from the houses to move in
   * @return The number of new houses created
   */
  private int moveResidents(ArrayList<Resident> toMoveOut, ArrayList<House> housesToMoveIn, ArrayList<House> removeFromHousesToMoveIn) {
    AtomicInteger totalNewHouses = new AtomicInteger(0);
    toMoveOut.forEach(resident -> {
      boolean placedInExistingHouse = housesToMoveIn.stream()
          .filter(house -> house.getResidents().isEmpty() || Math.abs(house.getResidents().getFirst().getAge() - resident.getAge()) <= 5)
          .findFirst()
          .map(house -> {
            house.addResident(resident);
            resident.setMovingOut(false);
            if (house.getResidents().size() >= 2) {
              house.getResidents().get(0).setCanHaveChildren(true);
              house.getResidents().get(1).setCanHaveChildren(true);
              house.getResidents().stream().limit(2).forEach(r ->
                  r.setSatisfaction(Math.min(r.getSatisfaction() + 0.012f, c.getSatisfactionRate()))
              );
            }
            removeFromHousesToMoveIn.add(house);
            return true;
          }).orElse(false);
      if (!placedInExistingHouse) {
        House newHouse = c.createHouse(new ArrayList<>() {{ add(resident); }});
        houses.add(newHouse);
        housesToMoveIn.add(newHouse);
        resident.setMovingOut(false);
        totalNewHouses.incrementAndGet();
      }
    });
    housesToMoveIn.removeAll(removeFromHousesToMoveIn);
    toMoveOut.clear();
    return totalNewHouses.get();
  }
  
  /**
   * Move the residents out of the destroyed houses.
   *
   * @param housesToRemove The list of houses to remove
   * @param housesToMoveIn The list of houses to move in
   * @param removeFromHousesToMoveIn The list of houses to remove from the houses to move in
   * @return The number of new houses created
   */
  private int moveResidentsOutOfDestroyedHouses(ArrayList<House> housesToRemove, ArrayList<House> housesToMoveIn, ArrayList<House> removeFromHousesToMoveIn) {
    AtomicInteger totalNewHouses = new AtomicInteger();
    housesToRemove.stream()
        .filter(house -> house.getResidents().size() > 1)
        .forEach(house -> {
          housesToMoveIn.stream()
              .filter(houseToMoveIn -> houseToMoveIn.getResidents().isEmpty())
              .findFirst()
              .ifPresentOrElse(
                  houseToMoveIn -> {
                    houseToMoveIn.addResidents(house.getResidents());
                    removeFromHousesToMoveIn.add(houseToMoveIn);
                    house.removeResidents(house.getResidents());
                  },
                  () -> {
                    houses.add(c.createHouse(house.getResidents()));
                    totalNewHouses.getAndIncrement();
                  }
              );
        });
    housesToMoveIn.removeAll(removeFromHousesToMoveIn);
    return totalNewHouses.get();
  }
  
  
  /**
   * Check if a child is born in the given house.
   *
   * @param house The house to check
   * @return The number of new residents
   */
  private int checkIfChildIsBorn(House house) {
    if (house.getResidents().size() < 2) {
      return 0;
    }
    boolean canHaveChild = house.getResidents().stream()
        .limit(2)
        .allMatch(Resident::isCanHaveChildren);
    
    if (canHaveChild) {
      house.getResidents().forEach(resident ->
          resident.setSatisfaction(Math.min(resident.getSatisfaction() + 0.012f, c.getSatisfactionRate()))
      );
      if (Math.random() < 0.12) {
        house.addResident(new Resident(0, c.getSatisfactionRate(), true, false, false));
        return 1;
      }
    }
    return 0;
  }
  
  
  // GOOD: Die Methode `checkResident` hat einen klaren und nachvollziehbaren Kontrollfluss.
  // Die Verwendung von Parametern und lokalen Variablen macht die Methode leicht verständlich.
  /**
   * Check the residents
   *
   * @param house The house to check
   * @param residentsToRemove The list of residents to remove
   * @param toMoveOut The list of residents to move out
   * @return The number of died residents
   */
  private int checkResident(House house, ArrayList<Resident> residentsToRemove, ArrayList<Resident> toMoveOut) {
    AtomicInteger totalDiedResidents = new AtomicInteger();
    house.getResidents().removeIf(resident -> {
      resident.age();
      if (resident.isDead()) {
        residentsToRemove.add(resident);
        totalDiedResidents.getAndIncrement();
        return true;
      } else if (resident.isMovingOut()) {
        toMoveOut.add(resident);
        resident.setLivesWithParents(false);
        residentsToRemove.add(resident);
        return true;
      }
      return false;
    });
    
    house.getResidents().removeAll(residentsToRemove);
    residentsToRemove.clear();
    return totalDiedResidents.get();
  }
  
  /**
   * Handles demolition by updating resident satisfaction, demolition waste, and
   * adding the house to the demolition list.
   *
   * @param house The house to demolish
   * @param housesToRemove List to add house if it's demolished
   * @param residentDemolitionWaste Total demolition waste per resident
   * @param toMoveOut List of residents who need to move out
   */
  private void handleDemolition(House house, ArrayList<House> housesToRemove, float residentDemolitionWaste, ArrayList<Resident> toMoveOut) {
    if (!house.getResidents().isEmpty()) {
      residentDemolitionWaste += (float) house.getDemolitionWaste() / house.getResidents().size();
    }
    housesToRemove.add(house);
    
    house.getResidents().forEach(resident ->
        resident.setSatisfaction(Math.max(resident.getSatisfaction() - 0.012f, 0))
    );
    
    if (house.getResidents().size() == 1) {
      toMoveOut.add(house.getResidents().getFirst());
    }
  }
}
