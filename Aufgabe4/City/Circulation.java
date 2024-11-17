package City;

import java.util.HashSet;
import java.util.Set;
//Untertypenbeziehungsbegründung: Ein Circulation ist eine Erschließungsfläche,
// die für den kurz- oder langfristigen Aufenthalt von Menschen vorgesehen ist
//Typbegründung: Eine Circulation (zb Gang, Flur, Treppe), soll instanzierbar sein
public class Circulation implements Space {
    private Entity entity;
    private Escape escapePath;
    private final Set<Space> connectedSpaces;
    
    public Circulation(Entity entity, Escape escapePath) {
        this.entity = entity;
        this.escapePath = escapePath;
        this.connectedSpaces = new HashSet<>();
    }
    
    public boolean isAccessible() {
        return !connectedSpaces.isEmpty();
    }
    
    public Set<Space> getConnectedSpaces() {
        return connectedSpaces;
    }
    
    public void addConnection(Space space) {
        connectedSpaces.add(space);
    }

    @Override
    public Entity entity() {
        return this.entity;
    }

    @Override
    public Escape escape() {
        return this.escapePath;
    }

    @Override
    public Set<Space> remove() {
        Set<Space> removedSpaces = new HashSet<>(connectedSpaces);
        connectedSpaces.clear();
        this.entity = null;
        removedSpaces.add(this);
        return removedSpaces;
    }

    @Override
    public boolean isLift() {
        return false;
    }
}