@Responsible(developer = "Oliver Kastner")
public abstract class HeatPump {
  private final HeatPumpPowerLevel powerLevel;
  private final double price;

  @Responsible(developer = "Oliver Kastner")
  public HeatPump(HeatPumpPowerLevel powerLevel, double price) {
    this.powerLevel = powerLevel;
    this.price = price;
  }

  @Responsible(developer = "Oliver Kastner")
  public HeatPumpPowerLevel getPowerLevel() { return powerLevel; }

  @Responsible(developer = "Oliver Kastner")
  public double getPrice() { return price; }

  @Responsible(developer = "Oliver Kastner")
  public abstract boolean isCompatible(HeatingType heatingType, OfficeSize size);

  @Responsible(developer = "Ryan Foster")
  public abstract boolean isCompatibleExact(HeatingType heatingType, OfficeSize size);

  @Responsible(developer = "Oliver Kastner")
  public abstract String getType();
}
