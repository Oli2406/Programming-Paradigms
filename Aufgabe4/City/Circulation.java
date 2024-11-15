package City;

import java.util.HashSet;
import java.util.Set;

public class Circulation extends Space {
    private final Set<Space> connectedSpaces;
    private Entity entity;
    
    public Circulation(Entity entity, Escape escapePath) {
        super(entity, escapePath);
        this.connectedSpaces = new HashSet<>();
        this.entity = entity;
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
        this.entity = null;
        removedSpaces.add(this);
        return removedSpaces;
    }
    
    @Override
    public Entity entity() {
        return this.entity;
    }
}