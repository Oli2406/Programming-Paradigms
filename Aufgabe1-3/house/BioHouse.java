package house;

import java.util.Random;

public class BioHouse extends House {
  private static final Random r = new Random();
  
  private final int BUILD_COST = (int) r.nextGaussian(150000, (double) 150000 / 5);
  private final int CARBON = (int) r.nextGaussian(20, (double) 20 / 5);
  private final int AREA = (int) r.nextGaussian(100, (double) 100 / 5);
  private final int LIFETIME = 50;
  private final int RENOVATION_INTERVAL = 20;
  private final int SERVICE_COST = (int) r.nextGaussian(3000, (double) 3000 / 5);
  private final int RENOVATION_COST = (int) r.nextGaussian(25000, (double) 25000 / 5);
  private final int DEMOLISH_COST = (int) r.nextGaussian(20000, (double) 20000 / 5);
  private final int WASTE_COST = (int) r.nextGaussian(7500, (double) 7500 / 5);
  private final float SATISFACTION_RATE = 0.8f;
  private final float RENOVATION_CARBON = 0.5f * CARBON;
  private final float REVITALIZATION_CARBON = RENOVATION_CARBON * 3;
  private final int RENOVATION_WASTE = (int) r.nextGaussian(30, (double) 30 / 5);
  private final int DEMOLITION_WASTE = (int) r.nextGaussian(50, (double) 50 / 5);
  private final boolean EARTHQUAKE_RESISTANCES = true, WILDFIRE_RESISTANCES = false, FLOOD_RESISTANCES = true, TORNADO_RESISTANCES = false, ENERGY_RESISTANCES = false, FIRE_RESISTANCES = true;
  private boolean HIGH_SIGNIFICANCE = false;
  private final int REVITALIZATION_COST = RENOVATION_COST * 3;
  
  public BioHouse() {
    super();
    if (r.nextFloat() < 0.1f) {
      HIGH_SIGNIFICANCE = true;
      setLifetime(calculateLifetime(true));
      setRenovationCost(calculateRenovationCost(true));
      setSatisfactionRate(SATISFACTION_RATE + 0.1f);
      setMaxSatisfaction(SATISFACTION_RATE + 0.1f);
      setHighSignificance(true);
      setHighSignificanceAttributes(true);
      setMaxLifetime(calculateLifetime(true));
    } else {
      setLifetime(calculateLifetime(false));
      setRenovationCost(calculateRenovationCost(false));
      setSatisfactionRate(SATISFACTION_RATE);
      setMaxSatisfaction(SATISFACTION_RATE);
      setHighSignificanceAttributes(false);
      setMaxLifetime(calculateLifetime(false));
    }
    setCost(BUILD_COST);
    setCarbon(CARBON);
    setArea(AREA);
    setRenovationInterval(RENOVATION_INTERVAL);
    setServiceCost(SERVICE_COST);
    setDemolishCost(DEMOLISH_COST);
    setWasteCost(WASTE_COST);
    setRenovationCarbon(RENOVATION_CARBON);
    setRenovationWaste(RENOVATION_WASTE);
    setDemolitionWaste(DEMOLITION_WASTE);
    setResistances(new Resistance(EARTHQUAKE_RESISTANCES, WILDFIRE_RESISTANCES, FLOOD_RESISTANCES, TORNADO_RESISTANCES, ENERGY_RESISTANCES, FIRE_RESISTANCES));
    setHighSignificance(HIGH_SIGNIFICANCE);
    setRevitalizationCost(REVITALIZATION_COST);
    setRevitalizationCarbon(REVITALIZATION_CARBON);
  }
  
  private int calculateLifetime(boolean isHighSignificance) {
    return isHighSignificance ? (int) (LIFETIME * 1.5) : LIFETIME;
  }
  
  private int calculateRenovationCost(boolean isHighSignificance) {
    return isHighSignificance ? (int) (RENOVATION_COST * 1.5) : RENOVATION_COST;
  }
}