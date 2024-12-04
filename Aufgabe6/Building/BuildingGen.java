package Building;

import Office.OfficeGen;

import java.util.ArrayList;
import java.util.List;

public class BuildingGen<T> {
    private final String name;
    private final List<T> units;

    public BuildingGen(String name) {
        this.name = name;
        this.units = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addUnit(T unit) {
        if (unit != null) {
            units.add(unit);
        }
    }

    public T getUnit(int unitNumber) {
        for (T unit : units) {
            if(unit instanceof OfficeGen) {
                if (((OfficeGen) unit).getOfficeNumber() == unitNumber) {
                    return unit;
                }
            }
        }
        return null;
    }

    public void removeUnit(T unit) {
        units.remove(unit);
    }

    public void displayUnits() {
        System.out.println("Building: " + name);
        for (T unit : units) {
            System.out.println(unit.toString());
        }
    }
}