package City;

import java.util.HashSet;
import java.util.Set;

public class Complex implements Entity {
    private Set<Building> buildings;
    private Set<Space> outdoorSpaces;

    public Complex() {
        this.buildings = new HashSet<>();
        this.outdoorSpaces = new HashSet<>();
    }

    public Set<Building> buildings() {
        return buildings;
    }

    public Set<Space> spaces() {
        return outdoorSpaces;
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public void addOutdoorSpace(Space space) {
        outdoorSpaces.add(space);
    }

    @Override
    public void add(Entity entity) {

    }

    @Override
    public void remove(Entity entity) {

    }
}
