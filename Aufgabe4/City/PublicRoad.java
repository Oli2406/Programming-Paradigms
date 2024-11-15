package City;

import java.util.HashSet;
import java.util.Set;

public class PublicRoad extends Space {
    private final String name;
    
    public PublicRoad(String name) {
        super(null, null);
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
}