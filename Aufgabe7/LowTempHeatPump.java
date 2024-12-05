public class LowTempHeatPump extends HeatPump {
  public LowTempHeatPump(String powerLevel, double price) {
    super("LowTemp", powerLevel, price);
  }
  
  @Override
  public boolean isCompatible(String heatingType, String size) {
    return heatingType.equals("Floor") &&
        (size.equals(getPowerLevel()) || (size.equals("small") && getPowerLevel().equals("medium")));
  }
}