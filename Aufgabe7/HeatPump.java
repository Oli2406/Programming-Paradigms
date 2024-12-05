public abstract class HeatPump {
  private final HeatpumpType type;
  private final HeatpumpPowerLevel powerLevel;
  private final double price;
  
  public HeatPump(HeatpumpType type, HeatpumpPowerLevel powerLevel, double price) {
    this.type = type;
    this.powerLevel = powerLevel;
    this.price = price;
  }
  
  public HeatpumpType getType() { return type; }
  public HeatpumpPowerLevel getPowerLevel() { return powerLevel; }
  public double getPrice() { return price; }
  
  public abstract boolean isCompatible(HeatingType heatingType, OfficeSize size);
  public abstract boolean isCompatibleExact(HeatingType heatingType, OfficeSize size);
}
