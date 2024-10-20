package House;

public class PremiumHouse extends House {
  private static final int BUILD_COST = 250000;
  private static final int CARBON = 40;
  private static final int AREA = 200;
  private static final int LIFETIME = 100;
  private static final int RENOVATION_INTERVAL = 25;
  private static final int SERVICE_COST = 3000;
  private static final int RENOVATION_COST = 40000;
  private static final int DEMOLISH_COST = 20000;
  private static final int WASTE_COST = 5000;
  private static final float SATISFACTION_RATE = 0.9f;
  private static final float RENOVATION_CARBON = 0.5f * CARBON;
  private static final int RENOVATION_WASTE = 15;
  private static final int DEMOLITION_WASTE = 50;

  public PremiumHouse() {
    super(BUILD_COST, CARBON, AREA, LIFETIME,
        SERVICE_COST, RENOVATION_COST, DEMOLISH_COST, WASTE_COST,
        SATISFACTION_RATE, RENOVATION_INTERVAL, RENOVATION_CARBON,
        RENOVATION_WASTE, DEMOLITION_WASTE);
  }
}
