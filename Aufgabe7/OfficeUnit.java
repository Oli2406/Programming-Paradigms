public class OfficeUnit {
  private final HeatingType heatingType;
  private final OfficeSize size;
  private HeatPump installedPump;

  public OfficeUnit(HeatingType heatingType, OfficeSize size) {
    this.heatingType = heatingType;
    this.size = size;
  }
  
  public HeatingType getHeatingType() { return heatingType; }
  public OfficeSize getSize() { return size; }
  public HeatPump getInstalledPump() { return installedPump; }
  
  public void installPump(HeatPump pump) {
    this.installedPump = pump;
  }
  
  public void removePump() {
    this.installedPump = null;
  }
}