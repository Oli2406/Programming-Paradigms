@Responsible(developer = "Oliver Kastner")
@Invariant("powerLevel != null && price >= 0.0")
public abstract class HeatPump {
  private final HeatPumpPowerLevel powerLevel;
  private final double price;

  @Responsible(developer = "Oliver Kastner")
  @Precondition("powerLevel != null && price >= 0.0")
  @Postcondition("this.powerLevel == powerLevel && this.price == price")
  public HeatPump(HeatPumpPowerLevel powerLevel, double price) {
    this.powerLevel = powerLevel;
    this.price = price;
  }

  @Responsible(developer = "Oliver Kastner")
  @Postcondition("result == this.powerLevel")
  public HeatPumpPowerLevel getPowerLevel() { return powerLevel; }

  @Responsible(developer = "Oliver Kastner")
  @Postcondition("result == this.price && result >= 0.0")
  public double getPrice() { return price; }

  @Responsible(developer = "Oliver Kastner")
  @Precondition("heatingType != null && size != null")
  public abstract boolean isCompatible(HeatingType heatingType, OfficeSize size);

  @Responsible(developer = "Ryan Foster")
  @Precondition("heatingType != null && size != null")
  public abstract boolean isCompatibleExact(HeatingType heatingType, OfficeSize size);

  @Responsible(developer = "Oliver Kastner")
  @Postcondition("result != null")
  public abstract String getType();
}
