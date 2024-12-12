@Responsible(developer = "Noah Oguamalam")
@Invariant("heatingType != null && size != null")
public class OfficeUnit {
  private final HeatingType heatingType;
  private final OfficeSize size;
  private HeatPump installedPump;

  @Responsible(developer = "Noah Oguamalam")
  @Precondition("heatingType != null && size != null")
  @Postcondition("this.heatingType == heatingType && this.size == size && this.installedPump == null")
  public OfficeUnit(HeatingType heatingType, OfficeSize size) {
    this.heatingType = heatingType;
    this.size = size;
  }

  @Responsible(developer = "Noah Oguamalam")
  @Postcondition("result == this.heatingType")
  public HeatingType getHeatingType() { return heatingType; }

  @Responsible(developer = "Noah Oguamalam")
  @Postcondition("result == this.size")
  public OfficeSize getSize() { return size; }

  @Responsible(developer = "Noah Oguamalam")
  @Postcondition("result == this.installedPump")
  public HeatPump getInstalledPump() { return installedPump; }

  @Responsible(developer = "Noah Oguamalam")
  @Precondition("pump != null")
  @Postcondition("this.installedPump == pump")
  public void installPump(HeatPump pump) {
    this.installedPump = pump;
  }

  @Responsible(developer = "Noah Oguamalam")
  @Postcondition("this.installedPump == null")
  public void removePump() {
    this.installedPump = null;
  }
}