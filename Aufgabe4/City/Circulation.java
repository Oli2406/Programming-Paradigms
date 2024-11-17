package City;

import java.util.HashSet;
import java.util.Set;
//Untertypenbeziehungsbegründung: Ein Circulation ist eine Erschließungsfläche,
// die für den kurz- oder langfristigen Aufenthalt von Menschen vorgesehen ist
public class Circulation extends Space {
    private final Set<Space> connectedSpaces;
    
    public Circulation(Entity entity, Escape escapePath) {
        super(entity, escapePath);
        this.connectedSpaces = new HashSet<>();
        this.setEntity(entity);
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
    public Set<Space> remove() {
        Set<Space> removedSpaces = new HashSet<>(connectedSpaces);
        connectedSpaces.clear();
        this.setEntity(null);
        removedSpaces.add(this);
        return removedSpaces;
    }
}