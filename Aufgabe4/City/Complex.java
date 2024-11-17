package City;


import java.util.HashSet;
import java.util.Set;
//Untertypenbeziehungsbegründung: Ein Komplex ist ein Entity.
//Typbegründung: Ein Komplex soll instanziert werden können
public class Complex implements Entity {
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
        for(Building building : buildings) {
            if(building.getEntity() == exterior.entity()) {
                exteriors.add(exterior);
                return;
            }
        }
        throw new IllegalArgumentException("Exterior must be associated with a building in the complex.");
    }
    
    public void removeExterior(Exterior exterior) {
        exteriors.remove(exterior);
    }

    @Override
    public void add(Entity entity) {

    }

    @Override
    public void remove(Entity entity) {

    }

    @Override
    public Entity getEntity() {
        return null;
    }
}