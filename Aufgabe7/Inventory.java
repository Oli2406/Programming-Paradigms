import java.util.ArrayList;
import java.util.List;
@Responsible(developer = "Ryan Foster")
@Invariant("heatPumps != null") // Die Liste der Wärmepumpen darf niemals null sein
public class Inventory {

  private final List<HeatPump> heatPumps = new ArrayList<>();

  @Responsible(developer = "Ryan Foster")
  @Precondition("pump != null")
  @Postcondition("heatPumps.contains(pump)")
  public void addHeatPump(HeatPump pump) {
    heatPumps.add(pump);
  }

  @Responsible(developer = "Ryan Foster")
  @Precondition("pump != null")
  @Postcondition("!heatPumps.contains(pump)")
  public void deleteHeatPump(HeatPump pump) {
    heatPumps.remove(pump);
  }

  @Responsible(developer = "Ryan Foster")
  @Precondition("heatingType != null && size != null")
  @Postcondition("result != null -> !heatPumps.contains(result)")
  public HeatPump findAndAssign(HeatingType heatingType, OfficeSize size) {
    for (HeatPump pump : heatPumps) {
      if (pump.isCompatibleExact(heatingType, size)) {
        heatPumps.remove(pump);
        return pump;
      }
    }
    for (HeatPump pump : heatPumps) {
      if (pump.isCompatible(heatingType, size)) {
        heatPumps.remove(pump);
        return pump;
      }
    }
    return null;
  }

  @Responsible(developer = "Ryan Foster")
  @Precondition("pump != null")
  @Postcondition("heatPumps.contains(pump)")
  public void returnHeatPump(HeatPump pump) {
    heatPumps.add(pump);
  }

  @Responsible(developer = "Ryan Foster")
  @Postcondition("result >= 0.0")
  public double totalAvailablePrice() {
    return heatPumps.stream().mapToDouble(HeatPump::getPrice).sum();
  }

  @Responsible(developer = "Ryan Foster")
  @Postcondition("heatPumps.size() == old(heatPumps.size())")
  public void showHeatPumps() {
    heatPumps.forEach(System.out::println);
  }
}