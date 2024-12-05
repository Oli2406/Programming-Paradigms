public abstract class HeatPump {
  private String type;
  private String powerLevel;
  private double price;
  
  public HeatPump(String type, String powerLevel, double price) {
    this.type = type;
    this.powerLevel = powerLevel;
    this.price = price;
  }
  
  public String getType() { return type; }
  public String getPowerLevel() { return powerLevel; }
  public double getPrice() { return price; }
  
  public abstract boolean isCompatible(String heatingType, String size);
}
