@Responsible(developer = "Noah Oguamalam")
public class OfficeUnit {
  private final HeatingType heatingType;
  private final OfficeSize size;
  private HeatPump installedPump;

  @Responsible(developer = "Noah Oguamalam")
  public OfficeUnit(HeatingType heatingType, OfficeSize size) {
    this.heatingType = heatingType;
    this.size = size;
  }

  @Responsible(developer = "Noah Oguamalam")
  public HeatingType getHeatingType() { return heatingType; }

  @Responsible(developer = "Noah Oguamalam")
  public OfficeSize getSize() { return size; }

  @Responsible(developer = "Noah Oguamalam")
  public HeatPump getInstalledPump() { return installedPump; }

  @Responsible(developer = "Noah Oguamalam")
  public void installPump(HeatPump pump) {
    this.installedPump = pump;
  }

  @Responsible(developer = "Noah Oguamalam")
  public void removePump() {
    this.installedPump = null;
  }
}