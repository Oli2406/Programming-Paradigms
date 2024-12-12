import java.util.ArrayList;
import java.util.List;
@Responsible(developer = "Noah Oguamalam")
public class OfficeManager {
  private final List<OfficeUnit> officeUnits = new ArrayList<>();
  private final Inventory inventory;

  @Responsible(developer = "Noah Oguamalam")
  @Precondition("inventory != null")
  @Postcondition("this.inventory == inventory")
  public OfficeManager(Inventory inventory) {
    this.inventory = inventory;
  }

  @Responsible(developer = "Noah Oguamalam")
  @Precondition("unit != null")
  @Postcondition("officeUnits.contains(unit)")
  public void addOffice(OfficeUnit unit) {
    officeUnits.add(unit);
  }

  @Responsible(developer = "Noah Oguamalam")
  @Precondition("unit != null")
  @Postcondition("unit.getInstalledPump() != null ? unit.getInstalledPump() != null : true")
  public void assignPumpToOffice(OfficeUnit unit) {
    HeatPump pump = inventory.findAndAssign(unit.getHeatingType(), unit.getSize());
    if (pump != null) {
      unit.installPump(pump);
      System.out.println("Assigned " + pump.getType() + " (" + pump.getPowerLevel() + ") to Office (" + unit.getHeatingType() + ", " + unit.getSize() + ")");
    } else {
      System.out.println("No compatible heat pump found for Office (" + unit.getHeatingType() + ", " + unit.getSize() + ")");
    }
  }

  @Responsible(developer = "Noah Oguamalam")
  @Precondition("unit != null && unit.getInstalledPump() != null")
  @Postcondition("unit.getInstalledPump() == null")
  public void removePumpFromOffice(OfficeUnit unit) {
    if (unit.getInstalledPump() != null) {
      inventory.returnHeatPump(unit.getInstalledPump());
      unit.removePump();
    }
  }

  @Responsible(developer = "Noah Oguamalam")
  @Postcondition("result >= 0.0")
  public double totalInstalledPrice() {
    return officeUnits.stream()
        .filter(unit -> unit.getInstalledPump() != null)
        .mapToDouble(unit -> unit.getInstalledPump().getPrice())
        .sum();
  }

  @Responsible(developer = "Ryan Foster")
  @Postcondition("officeUnits.size() == old(officeUnits.size())")
  public void showOffices() {
    officeUnits.forEach(unit -> System.out.println(unit.getHeatingType() + " - " + unit.getSize() + " - " + (unit.getInstalledPump() != null ? unit.getInstalledPump().getType() : "No Pump")));
  }
}