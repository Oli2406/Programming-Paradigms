public class LowTempHeatPump extends HeatPump {
  public LowTempHeatPump(HeatpumpPowerLevel powerLevel, double price) {
    super(HeatpumpType.LOW_TEMP, powerLevel, price);
  }
  
  @Override
  public boolean isCompatible(HeatingType heatingType, OfficeSize size) {
    return heatingType == HeatingType.FLOOR && getPowerLevel().isCompatible(size);
  }

  @Override
  public boolean isCompatibleExact(HeatingType heatingType, OfficeSize size) {
    return heatingType == HeatingType.FLOOR && getPowerLevel().isCompatibleExact(size);
  }
}