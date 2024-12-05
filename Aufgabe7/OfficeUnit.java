public class OfficeUnit {
  private String heatingType;
  private String size;
  private HeatPump installedPump;
  
  public OfficeUnit(String heatingType, String size) {
    this.heatingType = heatingType;
    this.size = size;
  }
  
  public String getHeatingType() { return heatingType; }
  public String getSize() { return size; }
  public HeatPump getInstalledPump() { return installedPump; }
  
  public void installPump(HeatPump pump) {
    this.installedPump = pump;
  }
  
  public void removePump() {
    this.installedPump = null;
  }
}