package house;

import java.util.Random;

public class PremiumHouse extends House {
  private static final Random r = new Random();

  private final int BUILD_COST = (int) r.nextGaussian(250000, (double) 250000 /5);
  private final int CARBON = (int) r.nextGaussian(40, (double) 40 /5);
  private final int AREA = (int) r.nextGaussian(200, (double) 200 /5);
  private final int LIFETIME = 100;
  private final int RENOVATION_INTERVAL = 25;
  private final int SERVICE_COST = (int) r.nextGaussian(3000, (double) 3000 /5);
  private final int RENOVATION_COST = (int) r.nextGaussian(40000, (double) 40000 /5);
  private final int DEMOLISH_COST = (int) r.nextGaussian(20000, (double) 20000 /5);
  private final int WASTE_COST = (int) r.nextGaussian(5000, (double) 5000 /5);
  private final float SATISFACTION_RATE = 0.9f;
  private final float RENOVATION_CARBON = 0.5f * CARBON;
  private final int RENOVATION_WASTE = (int) r.nextGaussian(15, (double) 15 /5);
  private final int DEMOLITION_WASTE = (int) r.nextGaussian(50, (double) 50 /5);
  private final boolean EARTHQUAKE_RESISTANCES = true, WILDFIRE_RESISTANCES = true, FLOOD_RESISTANCES = true, TORNADO_RESISTANCES = true, ENERGY_RESISTANCES = true, FIRE_RESISTANCES = true;

  public PremiumHouse() {
    super();
    setCost(BUILD_COST);
    setCarbon(CARBON);
    setArea(AREA);
    setLifetime(LIFETIME);
    setRenovationInterval(RENOVATION_INTERVAL);
    setServiceCost(SERVICE_COST);
    setRenovationCost(RENOVATION_COST);
    setDemolishCost(DEMOLISH_COST);
    setWasteCost(WASTE_COST);
    setSatisfactionRate(SATISFACTION_RATE);
    setMaxSatisfaction(SATISFACTION_RATE);
    setRenovationCarbon(RENOVATION_CARBON);
    setRenovationWaste(RENOVATION_WASTE);
    setDemolitionWaste(DEMOLITION_WASTE);
    setResistances(new Resistance(EARTHQUAKE_RESISTANCES, WILDFIRE_RESISTANCES, FLOOD_RESISTANCES, TORNADO_RESISTANCES, ENERGY_RESISTANCES, FIRE_RESISTANCES));
  }
}