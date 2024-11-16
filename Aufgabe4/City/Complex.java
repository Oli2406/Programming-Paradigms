package City;


import java.util.HashSet;
import java.util.Set;

public abstract class Complex implements Entity {
    private final Set<Building> buildings;
    private final Set<Exterior> exteriors;
    
    public Complex() {
        this.buildings = new HashSet<>();
        this.exteriors = new HashSet<>();
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
    
    public Set<Exterior> spaces() {
        return exteriors;
    }
    
    public void addExterior(Exterior exterior) {
        exteriors.add(exterior);
    }
    
    public void removeExterior(Exterior exterior) {
        exteriors.remove(exterior);
    }
}