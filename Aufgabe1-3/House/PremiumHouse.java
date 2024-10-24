package House;

import java.util.Random;

public class PremiumHouse extends House {
  private static final Random r = new Random();

  private final int BUILD_COST = (int) r.nextGaussian(250000, 20000);
  private final int CARBON = (int) r.nextGaussian(40, 5);
  private final int AREA = (int) r.nextGaussian(200, 40);
  private final int LIFETIME = 100;
  private final int RENOVATION_INTERVAL = 25;
  private final int SERVICE_COST = (int) r.nextGaussian(3000, 300);
  private final int RENOVATION_COST = (int) r.nextGaussian(40000, 6000);
  private final int DEMOLISH_COST = (int) r.nextGaussian(20000, 4500);
  private final int WASTE_COST = (int) r.nextGaussian(5000, 1000);
  private final float SATISFACTION_RATE = 0.9f;
  private final float RENOVATION_CARBON = 0.5f * CARBON;
  private final int RENOVATION_WASTE = (int) r.nextGaussian(15, 3);
  private final int DEMOLITION_WASTE = (int) r.nextGaussian(50, 20);

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
  }
}