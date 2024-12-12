@Responsible(developer = "Oliver Kastner")
public class LowTempHeatPump extends HeatPump {

  @Responsible(developer = "Oliver Kastner")
  @Precondition("powerLevel != null && price >= 0.0")
  @Postcondition("this.getPowerLevel() == powerLevel && this.getPrice() == price")
  public LowTempHeatPump(HeatPumpPowerLevel powerLevel, double price) {
    super(powerLevel, price);
  }

  @Responsible(developer = "Oliver Kastner")
  @Precondition("heatingType != null && size != null")
  @Postcondition("result == (heatingType == HeatingType.FLOOR && getPowerLevel().isCompatible(size))")
  @Override
  public boolean isCompatible(HeatingType heatingType, OfficeSize size) {
    return heatingType == HeatingType.FLOOR && getPowerLevel().isCompatible(size);
  }

  @Responsible(developer = "Oliver Kastner")
  @Precondition("heatingType != null && size != null")
  @Postcondition("result == (heatingType == HeatingType.FLOOR && getPowerLevel().isCompatibleExact(size))")
  @Override
  public boolean isCompatibleExact(HeatingType heatingType, OfficeSize size) {
    return heatingType == HeatingType.FLOOR && getPowerLevel().isCompatibleExact(size);
  }

  @Responsible(developer = "Oliver Kastner")
  @Postcondition("result.equals(\"LowTempHeatPump\")")
  @Override
  public String getType() {
    return "LowTempHeatPump";
  }

  @Responsible(developer = "Oliver Kastner")
  @Postcondition("result != null && result.contains(getPowerLevel().toString()) && result.contains(String.valueOf(getPrice()))")
  @Override
  public String toString() {
    return "LowTempHeatPump: " + getPowerLevel() + " for " + getPrice() + "€";
  }
}