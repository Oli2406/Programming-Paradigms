@Responsible(developer = "Oliver Kastner")
public class LowTempHeatPump extends HeatPump {
  @Responsible(developer = "Oliver Kastner")
  public LowTempHeatPump(HeatPumpPowerLevel powerLevel, double price) {
    super(powerLevel, price);
  }
  
  @Responsible(developer = "Oliver Kastner")
  @Override
  public boolean isCompatible(HeatingType heatingType, OfficeSize size) {
    return heatingType == HeatingType.FLOOR && getPowerLevel().isCompatible(size);
  }
  
  @Responsible(developer = "Oliver Kastner")
  @Override
  public boolean isCompatibleExact(HeatingType heatingType, OfficeSize size) {
    return heatingType == HeatingType.FLOOR && getPowerLevel().isCompatibleExact(size);
  }
  
  @Responsible(developer = "Oliver Kastner")
  @Override
  public String getType() {
    return "LowTempHeatPump";
  }
  
  @Responsible(developer = "Oliver Kastner")
  @Override
  public String toString() {
    return "LowTempHeatPump: " + getPowerLevel() + " for " + getPrice() + "€";
  }
}