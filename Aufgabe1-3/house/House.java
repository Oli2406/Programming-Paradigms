package house;

import Resident.Resident;
import enums.EventType;

import java.util.ArrayList;
import java.util.Random;
// GOOD: Die Verwendung von schwacher Kopplung in der Klasse House durch die Verwendung von Schnittstellen für die Resistances ermöglicht es,
//       verschiedene Implementierungen der Resistances zu verwenden, ohne die Klasse House zu ändern. Dies verbessert die Flexibilität und Wartbarkeit.
// STYLE: Objektorientiertes Paradigma
// Nominale Abstraktion (Klasse House)
public class House {
  private static final int BASE_AESTHETIC_VALUE = 30;
  private static final int BASE_HISTORICAL_IMPORTANCE = 20;
  private static final int BASE_CULTURAL_SIGNIFICANCE = 25;
  private static final int BASE_ENVIRONMENTAL_INTEGRATION = 35;
  private static final int BASE_IMPACT_ON_SURROUNDINGS = 40;
  private final float wastePerYearPerResident = 0.27f;
  Random r = new Random();
  private int cost;
  private int carbon;
  private int area;
  private int lifetime;
  private int serviceCost;
  private int renovationCost;
  private int demolishCost;
  private int wasteCost;
  private float satisfactionRate;
  private int renovationInterval;
  private int renovationLifetime;
  private float renovationCarbon;
  private float maxSatisfaction;
  private int renovationWaste;
  private int demolitionWaste;
  private Resistance resistances;
  private boolean isHighSignificance;
  private int revitalizationCost;
  private int maxLifetime;
  private float revitalizationCarbon;
  private int aestheticValue;
  private int historicalImportance;
  private int culturalSignificance;
  private int environmentalIntegration;
  private int impactOnSurroundings;

  private final ArrayList<Resident> residents;

  /**
   * Constructor for House
   * @param residents List of residents for the house
   */
  public House(ArrayList<Resident> residents) {
    this.residents = residents;
    this.renovate();
  }

  /**
   * Constructor for House
   * @param numberOfResident Number of residents for the house
   */
  public House(int numberOfResident) {
    this.residents = new ArrayList<>();
    for (int i = 0; i < numberOfResident; i++) {
      this.residents.add(new Resident(
          r.nextInt(20, 41),
          satisfactionRate,
          false,
          false,
          true
      ));
    }
    this.renovate();
  }

  /**
   * Set the high significance attributes for the house
   * @param highSignificance Boolean value for high significance
   */
  public void setHighSignificanceAttributes(boolean highSignificance) {
    this.isHighSignificance = highSignificance;
    initializeAttributes();
  }

  /**
   * Initialize the attributes for the house
   */
  protected void initializeAttributes() {
    if (this.isHighSignificance) {
      this.aestheticValue = (int) r.nextGaussian(BASE_AESTHETIC_VALUE * 1.5, 8);
      this.historicalImportance = (int) r.nextGaussian(BASE_HISTORICAL_IMPORTANCE * 1.5, 5);
      this.culturalSignificance = (int) r.nextGaussian(BASE_CULTURAL_SIGNIFICANCE * 1.5, 7);
      this.environmentalIntegration = (int) r.nextGaussian(BASE_ENVIRONMENTAL_INTEGRATION * 1.5, 8);
      this.impactOnSurroundings = (int) r.nextGaussian(BASE_IMPACT_ON_SURROUNDINGS * 1.5, 10);

    } else {
      this.aestheticValue = BASE_AESTHETIC_VALUE;
      this.historicalImportance = BASE_HISTORICAL_IMPORTANCE;
      this.culturalSignificance = BASE_CULTURAL_SIGNIFICANCE;
      this.environmentalIntegration = BASE_ENVIRONMENTAL_INTEGRATION;
      this.impactOnSurroundings = BASE_IMPACT_ON_SURROUNDINGS;
    }
  }

  /**
   * Calculate the significance factor for the house
   * @return Significance factor for the house
   */
  public double calculateSignificanceFactor() {
    double minSignificance = BASE_CULTURAL_SIGNIFICANCE + BASE_AESTHETIC_VALUE +
        BASE_HISTORICAL_IMPORTANCE + BASE_ENVIRONMENTAL_INTEGRATION +
        BASE_IMPACT_ON_SURROUNDINGS;
    double actualSignificance = aestheticValue + historicalImportance + culturalSignificance +
        environmentalIntegration + impactOnSurroundings;
    return actualSignificance / minSignificance;
  }

  /**
   * Get the residents for the house
   * @return List of residents for the house
   */
  public ArrayList<Resident> getResidents() {
    return residents;
  }

  /**
   * Add a resident to the house
   * @param residents Resident to be added to the house
   */
  public void addResident(Resident residents) {
    this.residents.add(residents);
  }

  /**
   * Add residents to the house
   * @param residents List of residents to be added to the house
   */
  public void addResidents(ArrayList<Resident> residents) {
    this.residents.addAll(residents);
  }

  /**
   * Remove a resident from the house
   * @param resident Resident to be removed from the house
   */
  public void removeResident(Resident resident) {
    residents.remove(resident);
  }

  /**
   * Remove residents from the house
   * @param residents List of residents to be removed from the house
   */
  public void removeResidents(ArrayList<Resident> residents) {
    this.residents.removeAll(residents);
  }

  /**
   * Get the cost of the house
   * @return Cost of the house
   */
  public int getCost() {
    // Since the resistances of course cost money to build, it is calculated into the build cost and also the renovation cost
    return (int) (cost * Math.pow(1.1, resistances.numberOfResistances()));
  }

  /**
   * Set the cost of the house
   * @param cost Cost of the house
   */
  public void setCost(int cost) {
    this.cost = cost;
  }

  /**
   * Get the carbon of the house
   * @return Carbon of the house
   */
  public int getCarbon() {
    return carbon;
  }

  /**
   * Set the carbon of the house
   * @param carbon Carbon of the house
   */
  public void setCarbon(int carbon) {
    this.carbon = carbon;
  }

  /**
   * Get the area of the house
   * @return Area of the house
   */
  public int getArea() {
    return area;
  }

  /**
   * Set the area of the house
   * @param area Area of the house
   */
  public void setArea(int area) {
    this.area = area;
  }

  /**
   * Get the lifetime of the house
   * @return Lifetime of the house
   */
  public int getLifetime() {
    return lifetime;
  }

  /**
   * Set the lifetime of the house
   * @param lifetime Lifetime of the house
   */
  public void setLifetime(int lifetime) {
    this.lifetime = lifetime;
  }

  /**
   * Get the service cost of the house
   * @return Service cost of the house
   */
  public int getServiceCost() {
    return serviceCost;
  }

  /**
   * Set the service cost of the house
   * @param serviceCost Service cost of the house
   */
  public void setServiceCost(int serviceCost) {
    this.serviceCost = serviceCost;
  }

  /**
   * Get the renovation cost of the house
   * @return Renovation cost of the house
   */
  public int getRenovationCost() {
    // Since the resistances of course cost money to build, it is calculated into the build cost and also the renovation cost
    int renovationCostIncludeResistances = (int) (renovationCost * Math.pow(1.05, resistances.numberOfResistances()));
    return r.nextInt(Math.min(5000, renovationCostIncludeResistances - 1), renovationCostIncludeResistances);
  }

  /**
   * Set the renovation cost of the house
   * @param renovationCost Renovation cost of the house
   */
  public void setRenovationCost(int renovationCost) {
    this.renovationCost = renovationCost;
  }

  /**
   * Get the demolish cost of the house
   * @return Demolish cost of the house
   */
  public int getDemolishCost() {
    return demolishCost;
  }

  /**
   * Set the demolish cost of the house
   * @param demolishCost Demolish cost of the house
   */
  public void setDemolishCost(int demolishCost) {
    this.demolishCost = demolishCost;
  }

  /**
   * Get the waste cost of the house
   * @return Waste cost of the house
   */
  public int getWasteCost() {
    return wasteCost;
  }

  /**
   * Set the waste cost of the house
   * @param wasteCost Waste cost of the house
   */
  public void setWasteCost(int wasteCost) {
    this.wasteCost = wasteCost;
  }

  /**
   * Get the satisfaction rate of the house
   * @return Satisfaction rate of the house
   */
  public float getSatisfactionRate() {
    return satisfactionRate;
  }

  /**
   * Set the satisfaction rate of the house
   * @param satisfactionRate Satisfaction rate of the house
   */
  public void setSatisfactionRate(float satisfactionRate) {
    this.satisfactionRate = satisfactionRate;
  }

  /**
   * Get the renovation interval of the house
   * @return Renovation interval of the house
   */
  public int getRenovationInterval() {
    return renovationInterval;
  }

  /**
   * Set the renovation interval of the house
   * @param renovationInterval Renovation interval of the house
   */
  public void setRenovationInterval(int renovationInterval) {
    this.renovationInterval = renovationInterval;
  }

  /**
   * Age the house
   */
  public void age() {
    lifetime = Math.max(0, lifetime - 1);
    renovationLifetime = Math.max(0, renovationLifetime - 1);
  }

  /**
   * Get the renovation lifetime of the house
   * @return Renovation lifetime of the house
   */
  public int getRenovationLifetime() {
    return renovationLifetime;
  }

  /**
   * Set the renovation lifetime of the house
   * @param renovationLifetime Renovation lifetime of the house
   */
  public void setRenovationLifetime(int renovationLifetime) {
    if (renovationLifetime < 0) {
      renovationLifetime = 0;
    }
    this.renovationLifetime = renovationLifetime;
  }

  /**
   * Renovate the house
   */
  public void renovate() {
    renovationLifetime = (int) r.nextGaussian(renovationInterval, (double) renovationInterval / 4);
    float satisfactionIncrease = (float) r.nextGaussian(0.13, 0.02);
    float HighSignificanceIncrease = (float) r.nextGaussian(0.2, 0.04);
    if (!isHighSignificance) {
      for (Resident resident : residents) {
        if (resident.getSatisfaction() + satisfactionIncrease > maxSatisfaction) {
          resident.setSatisfaction(maxSatisfaction - 0.01f);
        } else {
          resident.setSatisfaction(resident.getSatisfaction() + satisfactionIncrease);
        }
      }
    } else {
      for (Resident resident : residents) {
        if (resident.getSatisfaction() + HighSignificanceIncrease > maxSatisfaction) {
          resident.setSatisfaction(maxSatisfaction - 0.01f);
        } else {
          resident.setSatisfaction(resident.getSatisfaction() + HighSignificanceIncrease);
        }
      }
    }
  }

  /**
   * Get the renovation carbon of the house
   * @return Renovation carbon of the house
   */
  public float getRenovationCarbon() {
    return renovationCarbon;
  }

  /**
   * Set the renovation carbon of the house
   * @param renovationCarbon Renovation carbon of the house
   */
  public void setRenovationCarbon(float renovationCarbon) {
    this.renovationCarbon = renovationCarbon;
  }

  /**
   * Get the waste per year per resident of the house
   * @return Waste per year per resident of the house
   */
  public float getWastePerYearPerResident() {
    return wastePerYearPerResident;
  }

  /**
   * Get the renovation waste of the house
   * @return Renovation waste of the house
   */
  public int getRenovationWaste() {
    return renovationWaste;
  }

  /**
   * Set the renovation waste of the house
   * @param renovationWaste Renovation waste of the house
   */
  public void setRenovationWaste(int renovationWaste) {
    this.renovationWaste = renovationWaste;
  }

  /**
   * Get the demolition waste of the house
   * @return The demolition waste of the house
   */
  public int getDemolitionWaste() {
    return demolitionWaste;
  }

  /**
   * Set the demolition waste of the house
   * @param demolitionWaste The demolition waste of the house
   */
  public void setDemolitionWaste(int demolitionWaste) {
    this.demolitionWaste = demolitionWaste;
  }

  /**
   * Reduce the satisfaction of the residents
   * @param event The event that caused the reduction
   * @param isResistant Whether the house is resistant to the event
   */
  public void reduceSatisfaction(EventType event, boolean isResistant) {
    float maxSatisfactionReduction;
    float minSatisfactionReduction = switch (event) {
      case EARTHQUAKE -> {
        maxSatisfactionReduction = 0.3f;
        yield 0.05f;
      }
      case FIRE -> {
        maxSatisfactionReduction = 0.15f;
        yield 0.01f;
      }
      case INFESTATION -> {
        maxSatisfactionReduction = 0.07f;
        yield 0.01f;
      }
      case FLOOD -> {
        maxSatisfactionReduction = 0.15f;
        yield 0.1f;
      }
      case TORNADO -> {
        maxSatisfactionReduction = 0.2f;
        yield 0.1f;
      }
      case PLUMBING -> {
        maxSatisfactionReduction = 0.05f;
        yield 0.01f;
      }
      case POWER_OUTAGE -> {
        maxSatisfactionReduction = 0.02f;
        yield 0.01f;
      }
      case WILDFIRE -> {
        maxSatisfactionReduction = 0.15f;
        yield 0.05f;
      }
      case BUILDING_COLLAPSE -> {
        maxSatisfactionReduction = 0.3f;
        yield 0.2f;
      }
      case MAINTENANCE -> {
        maxSatisfactionReduction = 0.03f;
        yield 0.01f;
      }
      default -> {
        maxSatisfactionReduction = 0.1f;
        yield 0.01f;
      }
    };
    if (isResistant) {
      maxSatisfactionReduction = maxSatisfactionReduction / 2;
      minSatisfactionReduction = minSatisfactionReduction / 2;
    }
    float satisfactionReduction = r.nextFloat(minSatisfactionReduction, maxSatisfactionReduction);
    for (Resident resident : residents) {
      resident.setSatisfaction(resident.getSatisfaction() * (1.0f - satisfactionReduction));
    }
  }

  /**
   * Get the max satisfaction of the house
   * @param maxSatisfaction Max satisfaction of the house
   */
  public void setMaxSatisfaction(float maxSatisfaction) {
    this.maxSatisfaction = maxSatisfaction;
  }

  /**
   * Get the resistances of the house
   * @return Resistances of the house
   */
  public Resistance getResistances() {
    return resistances;
  }

  /**
   * Set the resistances of the house
   * @param resistances Resistances of the house
   */
  public void setResistances(Resistance resistances) {
    this.resistances = resistances;
  }

  /**
   * Get the high significance of the house
   * @return High significance of the house
   */
  public boolean getHighSignificance() {
    return isHighSignificance;
  }

  /**
   * Set the high significance of the house
   * @param highSignificance High significance of the house
   */
  public void setHighSignificance(boolean highSignificance) {
    isHighSignificance = highSignificance;
  }

  /**
   * Get the revitalization cost of the house
   * @return Revitalization cost of the house
   */
  public int getRevitalizationCost() {
    return revitalizationCost;
  }

  /**
   * Set the revitalization cost of the house
   * @param revitalizationCost Revitalization cost of the house
   */
  public void setRevitalizationCost(int revitalizationCost) {
    this.revitalizationCost = revitalizationCost;
  }

  /**
   * Revitalize the house
   */
  public void revitalize() {
    for (Resident resident : residents) {
      resident.setSatisfaction(maxSatisfaction);
    }
    lifetime = maxLifetime;
  }

  /**
   * Get the max lifetime of the house
   * @param lifetime Max lifetime of the house
   */
  public void setMaxLifetime(int lifetime) {
    this.maxLifetime = lifetime;
  }

  /**
   * Get the revitalization carbon of the house
   * @return Revitalization carbon of the house
   */
  public float getRevitalizationCarbon() {
    return revitalizationCarbon;
  }

  /**
   * Set the revitalization carbon of the house
   * @param revitalizationCarbon Revitalization carbon of the house
   */
  public void setRevitalizationCarbon(float revitalizationCarbon) {
    this.revitalizationCarbon = revitalizationCarbon;
  }
}
