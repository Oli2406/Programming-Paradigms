package City;

import java.util.HashSet;
import java.util.Set;

public abstract class Ensemble implements Entity {
    private final Set<Entity> entities;
    private Space enclosedSpace;
    
    public Ensemble() {
        this.entities = new HashSet<>();
        this.enclosedSpace = null;
    }
    
    public Set<Entity> entities() {
        return entities;
    }
    
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
    
    public void setSpace(Space space) {
        this.enclosedSpace = space;
    }
    
    public Space space() {
        return this.enclosedSpace;
    }
}