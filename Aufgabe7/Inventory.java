import java.util.ArrayList;
import java.util.List;

public class Inventory {
  private final List<HeatPump> heatPumps = new ArrayList<>();
  
  public void addHeatPump(HeatPump pump) {
    heatPumps.add(pump);
  }
  
  public void deleteHeatPump(HeatPump pump) {
    heatPumps.remove(pump);
  }
  
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
  
  
  public void returnHeatPump(HeatPump pump) {
    heatPumps.add(pump);
  }
  
  public double totalAvailablePrice() {
    return heatPumps.stream().mapToDouble(HeatPump::getPrice).sum();
  }
  
  public void showHeatPumps() {
    heatPumps.forEach(pump -> System.out.println(pump));
  }
}