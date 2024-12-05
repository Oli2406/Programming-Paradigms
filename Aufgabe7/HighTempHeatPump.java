public class HighTempHeatPump extends HeatPump {
  public HighTempHeatPump(String powerLevel, double price) {
    super("HighTemp", powerLevel, price);
  }
  
  @Override
  public boolean isCompatible(String heatingType, String size) {
    return heatingType.equals("Radiator") &&
        (size.equals(getPowerLevel()) || (size.equals("medium") && getPowerLevel().equals("large")));
  }
}
