public abstract class HeatPump {
  private final HeatPumpPowerLevel powerLevel;
  private final double price;
  
  public HeatPump(HeatPumpPowerLevel powerLevel, double price) {
    this.powerLevel = powerLevel;
    this.price = price;
  }

  public HeatPumpPowerLevel getPowerLevel() { return powerLevel; }
  public double getPrice() { return price; }
  
  public abstract boolean isCompatible(HeatingType heatingType, OfficeSize size);
  public abstract boolean isCompatibleExact(HeatingType heatingType, OfficeSize size);

  public abstract String getType();
}
