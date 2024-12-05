public class HighTempHeatPump extends HeatPump {
  public HighTempHeatPump(HeatpumpPowerLevel powerLevel, double price) {
    super(HeatpumpType.HIGH_TEMP, powerLevel, price);
  }

  @Override
  public boolean isCompatible(HeatingType heatingType, OfficeSize size) {
    return heatingType == HeatingType.RADIATOR && getPowerLevel().isCompatible(size);
  }

  @Override
  public boolean isCompatibleExact(HeatingType heatingType, OfficeSize size) {
    return heatingType == HeatingType.RADIATOR && getPowerLevel().isCompatibleExact(size);
  }
}
