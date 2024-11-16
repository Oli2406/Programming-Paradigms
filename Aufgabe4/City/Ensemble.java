package City;

import java.util.HashSet;
import java.util.Set;

public class Ensemble implements Entity {
    private final Set<Entity> entities;
    private final Space enclosedSpace;
    
    public Ensemble(Space space) {
        this.entities = new HashSet<>();
        this.enclosedSpace = space;
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
    
    
    public Space space() {
        return this.enclosedSpace;
    }
    
    @Override
    public void add(Entity entity) {
        entities.add(entity);
    }
    
    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }
    
    @Override
    public Entity getEntity() {
        return this;
    }
}