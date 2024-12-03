package Company;

import Building.Building;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private final String name;
    private final List<Building> buildings;

    public Company(String name) {
        this.name = name;
        this.buildings = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addBuilding(Building building) {
        if (building != null) {
            buildings.add(building);
        }
    }

    public void removeBuilding(Building building) {
        buildings.remove(building);
    }

    public void displayBuildings() {
        System.out.println("Company: " + name);
        for (Building building : buildings) {
            building.displayOffices();
        }
    }
}