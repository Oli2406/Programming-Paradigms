package House;

import java.util.Random;

public class BioHouse extends House {
  private static final Random r = new Random();

  private final int BUILD_COST = (int) r.nextGaussian(150000, 9000);
  private final int CARBON = (int) r.nextGaussian(20, 4);
  private final int AREA = (int) r.nextGaussian(100, 25);
  private final int LIFETIME = 50;
  private final int RENOVATION_INTERVAL = 20;
  private final int SERVICE_COST = (int) r.nextGaussian(3000, 250);
  private final int RENOVATION_COST = (int) r.nextGaussian(25000, 6000);
  private final int DEMOLISH_COST = (int) r.nextGaussian(20000, 1200);
  private final int WASTE_COST = (int) r.nextGaussian(7500, 500);
  private final float SATISFACTION_RATE = 0.8f;
  private final float RENOVATION_CARBON = 0.5f * CARBON;
  private final int RENOVATION_WASTE = (int) r.nextGaussian(30, 12);
  private final int DEMOLITION_WASTE = (int) r.nextGaussian(50, 20);

  public BioHouse() {
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
