import java.util.ArrayList;
import java.util.List;

public class OfficeManager {
  private List<OfficeUnit> officeUnits = new ArrayList<>();
  private Inventory inventory = new Inventory();
  
  public void addOffice(OfficeUnit unit) {
    officeUnits.add(unit);
  }
  
  public void assignPumpToOffice(OfficeUnit unit) {
    HeatPump pump = inventory.findAndAssign(unit.getHeatingType(), unit.getSize());
    if (pump != null) {
      unit.installPump(pump);
      System.out.println("Assigned " + pump.getType() + " (" + pump.getPowerLevel() + ") to Office (" + unit.getHeatingType() + ", " + unit.getSize() + ")");
    } else {
      System.out.println("No compatible heat pump found for Office (" + unit.getHeatingType() + ", " + unit.getSize() + ")");
    }
  }
  
  public void removePumpFromOffice(OfficeUnit unit) {
    if (unit.getInstalledPump() != null) {
      inventory.returnHeatPump(unit.getInstalledPump());
      unit.removePump();
    }
  }
  
  public double totalInstalledPrice() {
    return officeUnits.stream()
        .filter(unit -> unit.getInstalledPump() != null)
        .mapToDouble(unit -> unit.getInstalledPump().getPrice())
        .sum();
  }
  
  public void showOffices() {
    officeUnits.forEach(unit -> System.out.println(unit.getHeatingType() + " - " + unit.getSize() + " - " + (unit.getInstalledPump() != null ? unit.getInstalledPump().getType() : "No Pump")));
  }
}
