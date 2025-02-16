package Company;

import Building.BuildingGen;

import java.util.ArrayList;
import java.util.List;

public class CompanyGen<T> {
    private final String name;
    private final List<T> buildings;

    public CompanyGen(String name) {
        this.name = name;
        this.buildings = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addBuilding(T building) {
        if (building != null) {
            buildings.add(building);
        }
    }

    public void removeBuilding(T building) {
        buildings.remove(building);
    }

    public T getBuilding(String buildingName) {
        for (T building : buildings) {
            if(building instanceof Building.BuildingGen) {
                if (((BuildingGen) building).getName().equals(buildingName)) {
                    return building;
                }
            }
        }
        return null;
    }

    public void displayBuildings() {
        System.out.println("Company: " + name);
        for (T building : buildings) {
            if(building instanceof Building.BuildingGen) {
                ((Building.BuildingGen) building).displayUnits();
            } else {
                System.out.println(building.toString());
            }
        }
    }
}