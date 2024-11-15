package City;


import java.util.HashSet;
import java.util.Set;

public abstract class Complex implements Entity {
    private final Set<Building> buildings;
    private final Set<Space> externalSpaces;
    
    public Complex() {
        this.buildings = new HashSet<>();
        this.externalSpaces = new HashSet<>();
    }
    
    public Set<Building> buildings() {
        return buildings;
    }
    
    public void addBuilding(Building building) {
        buildings.add(building);
    }
    
    public void removeBuilding(Building building) {
        buildings.remove(building);
    }
    
    public Set<Space> spaces() {
        return externalSpaces;
    }
    
    public void addExternalSpace(Space space) {
        externalSpaces.add(space);
    }
    
    public void removeExternalSpace(Space space) {
        externalSpaces.remove(space);
    }
}