package house;

import enums.EventType;

import java.util.Random;

public class House {
  private static final int BASE_AESTHETIC_VALUE = 30;
  private static final int BASE_HISTORICAL_IMPORTANCE = 20;
  private static final int BASE_CULTURAL_SIGNIFICANCE = 25;
  private static final int BASE_ENVIRONMENTAL_INTEGRATION = 35;
  private static final int BASE_IMPACT_ON_SURROUNDINGS = 40;
  private final float wastePerYearPerResident = 0.27f;
  Random r = new Random();
  private int residents;
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
  
  
  public House() {
    this.residents = r.nextInt(1, 5);
    this.renovate();
  }
  
  public void setHighSignificanceAttributes(boolean highSignificance) {
    this.isHighSignificance = highSignificance;
    initializeAttributes();
  }
  
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
  
  public double calculateSignificanceFactor() {
    double minSignificance = BASE_CULTURAL_SIGNIFICANCE + BASE_AESTHETIC_VALUE +
        BASE_HISTORICAL_IMPORTANCE + BASE_ENVIRONMENTAL_INTEGRATION +
        BASE_IMPACT_ON_SURROUNDINGS;
    double actualSignificance = aestheticValue + historicalImportance + culturalSignificance +
        environmentalIntegration + impactOnSurroundings;
    return actualSignificance / minSignificance;
  }
  
  public int getResidents() {
    return residents;
  }
  
  public void setResidents(int residents) {
    this.residents = residents;
  }
  
  public int getCost() {
    // Since the resistances of course cost money to build, it is calculated into the build cost and also the renovation cost
    return (int) (cost * Math.pow(1.1, resistances.numberOfResistances()));
  }
  
  public void setCost(int cost) {
    this.cost = cost;
  }
  
  public int getCarbon() {
    return carbon;
  }
  
  public void setCarbon(int carbon) {
    this.carbon = carbon;
  }
  
  public int getArea() {
    return area;
  }
  
  public void setArea(int area) {
    this.area = area;
  }
  
  public int getLifetime() {
    return lifetime;
  }
  
  public void setLifetime(int lifetime) {
    this.lifetime = lifetime;
  }
  
  public int getServiceCost() {
    return serviceCost;
  }
  
  public void setServiceCost(int serviceCost) {
    this.serviceCost = serviceCost;
  }
  
  public int getRenovationCost() {
    // Since the resistances of course cost money to build, it is calculated into the build cost and also the renovation cost
    int renovationCostIncludeResistances = (int) (renovationCost * Math.pow(1.05, resistances.numberOfResistances()));
    return r.nextInt(Math.min(5000, renovationCostIncludeResistances - 1), renovationCostIncludeResistances);
  }
  
  public void setRenovationCost(int renovationCost) {
    this.renovationCost = renovationCost;
  }
  
  public int getDemolishCost() {
    return demolishCost;
  }
  
  public void setDemolishCost(int demolishCost) {
    this.demolishCost = demolishCost;
  }
  
  public int getWasteCost() {
    return wasteCost;
  }
  
  public void setWasteCost(int wasteCost) {
    this.wasteCost = wasteCost;
  }
  
  public float getSatisfactionRate() {
    return satisfactionRate;
  }
  
  public void setSatisfactionRate(float satisfactionRate) {
    this.satisfactionRate = satisfactionRate;
  }
  
  public int getRenovationInterval() {
    return renovationInterval;
  }
  
  public void setRenovationInterval(int renovationInterval) {
    this.renovationInterval = renovationInterval;
  }
  
  public void age() {
    lifetime = Math.max(0, lifetime - 1);
    renovationLifetime = Math.max(0, renovationLifetime - 1);
    if (satisfactionRate - 0.012f < 0) {
      satisfactionRate = 0;
    } else {
      satisfactionRate -= 0.0000012f;
    }
  }
  
  public int getRenovationLifetime() {
    return renovationLifetime;
  }
  
  public void setRenovationLifetime(int renovationLifetime) {
    if (renovationLifetime < 0) {
      renovationLifetime = 0;
    }
    this.renovationLifetime = renovationLifetime;
  }
  
  public void renovate() {
    renovationLifetime = (int) r.nextGaussian(renovationInterval, (double) renovationInterval / 4);
    //TODO: discuss proper value for Increase
    float satisfactionIncrease = (float) r.nextGaussian(0.13, 0.02);
    float HighSignificanceIncrease = (float) r.nextGaussian(0.2, 0.04);
    if (!isHighSignificance) {
      if (satisfactionRate + satisfactionIncrease > maxSatisfaction) {
        satisfactionRate = maxSatisfaction - 0.01f;
      } else {
        satisfactionRate += satisfactionIncrease;
      }
    } else {
      if (satisfactionRate + HighSignificanceIncrease > maxSatisfaction) {
        satisfactionRate = maxSatisfaction - 0.01f;
      } else {
        satisfactionRate += HighSignificanceIncrease;
      }
    }
  }
  
  public float getRenovationCarbon() {
    return renovationCarbon;
  }
  
  public void setRenovationCarbon(float renovationCarbon) {
    this.renovationCarbon = renovationCarbon;
  }
  
  public float getWastePerYearPerResident() {
    return wastePerYearPerResident;
  }
  
  public int getRenovationWaste() {
    return renovationWaste;
  }
  
  public void setRenovationWaste(int renovationWaste) {
    this.renovationWaste = renovationWaste;
  }
  
  public int getDemolitionWaste() {
    return demolitionWaste;
  }
  
  public void setDemolitionWaste(int demolitionWaste) {
    this.demolitionWaste = demolitionWaste;
  }
  
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
    satisfactionRate = satisfactionRate * (1.0f - satisfactionReduction);
  }
  
  public void setMaxSatisfaction(float maxSatisfaction) {
    this.maxSatisfaction = maxSatisfaction;
  }
  
  public Resistance getResistances() {
    return resistances;
  }
  
  public void setResistances(Resistance resistances) {
    this.resistances = resistances;
  }
  
  public boolean getHighSignificance() {
    return isHighSignificance;
  }
  
  public void setHighSignificance(boolean highSignificance) {
    isHighSignificance = highSignificance;
  }
  
  public int getRevitalizationCost() {
    return revitalizationCost;
  }
  
  public void setRevitalizationCost(int revitalizationCost) {
    this.revitalizationCost = revitalizationCost;
  }
  
  public void revitalize() {
    satisfactionRate = maxSatisfaction;
    lifetime = maxLifetime;
  }
  
  public void setMaxLifetime(int lifetime) {
    this.maxLifetime = lifetime;
  }
  
  public float getRevitalizationCarbon() {
    return revitalizationCarbon;
  }
  
  public void setRevitalizationCarbon(float revitalizationCarbon) {
    this.revitalizationCarbon = revitalizationCarbon;
  }
}
