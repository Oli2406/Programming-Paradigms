package House;

public class BioHouse extends House {
  private static final int BUILD_COST = 150000;
  private static final int CARBON = 20;
  private static final int AREA = 100;
  private static final int LIFETIME = 50;
  private static final int RENOVATION_INTERVAL = 20;
  private static final int SERVICE_COST = 3000;
  private static final int RENOVATION_COST = 25000;
  private static final int DEMOLISH_COST = 20000;
  private static final int WASTE_COST = 7500;
  private static final float SATISFACTION_RATE = 0.8f;
  private static final float RENOVATION_CARBON = 0.5f * CARBON;
  private static final int RENOVATION_WASTE = 30;
  private static final int DEMOLITION_WASTE = 50;

  public BioHouse() {
    super(BUILD_COST, CARBON, AREA, LIFETIME,
        SERVICE_COST, RENOVATION_COST, DEMOLISH_COST, WASTE_COST,
        SATISFACTION_RATE, RENOVATION_INTERVAL, RENOVATION_CARBON,
        RENOVATION_WASTE, DEMOLITION_WASTE);
  }
}
