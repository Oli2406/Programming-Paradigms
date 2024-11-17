package City;

import java.util.HashSet;
import java.util.Set;
//Untertypenbeziehungsbegründung: Ein Bereich, der den Aufenthalt von Menschen ermöglicht
//Typbegründung: Ein PublicRoad ist ein Bereich, der instanziiert werden soll
public class PublicRoad implements Space {
    private final String name;
    
    public PublicRoad(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public Entity entity() {
        return null;
    }

    @Override
    public Escape escape() {
        return null; // Immer null laut Aufgabenstellung
    }

    @Override
    public Set<Space> remove() {
        Set<Space> removedSpaces = new HashSet<>();
        removedSpaces.add(this);
        return removedSpaces;
    }

    @Override
    public boolean isLift() {
        return false;
    }
}