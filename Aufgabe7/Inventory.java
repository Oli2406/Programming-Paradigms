import java.util.ArrayList;
import java.util.List;
@Responsible(developer = "Ryan Foster")
public class Inventory {
  
  private final List<HeatPump> heatPumps = new ArrayList<>();
  
  @Responsible(developer = "Ryan Foster")
  public void addHeatPump(HeatPump pump) {
    heatPumps.add(pump);
  }
  
  @Responsible(developer = "Ryan Foster")
  public void deleteHeatPump(HeatPump pump) {
    heatPumps.remove(pump);
  }
  
  @Responsible(developer = "Ryan Foster")
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
  public void returnHeatPump(HeatPump pump) {
    heatPumps.add(pump);
  }
  
  @Responsible(developer = "Ryan Foster")
  public double totalAvailablePrice() {
    return heatPumps.stream().mapToDouble(HeatPump::getPrice).sum();
  }
  
  @Responsible(developer = "Ryan Foster")
  public void showHeatPumps() {
    heatPumps.forEach(System.out::println);
  }
}