package City;
import java.util.HashSet;
import java.util.Set;

public abstract class Space {
    private Entity entity;
    private Escape escapePath;
    
    public Space(Entity entity, Escape escapePath) {
        this.entity = entity;
        if(escapePath != null) {
            escapePath.add(this);
        }
        this.escapePath = escapePath;
    }
    
    public Entity entity() {
        return this.entity;
    }
    
    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    
    public Escape escape() {
        return this.escapePath;
    }
    
    public void setEscape(Escape escapePath) {
        this.escapePath = escapePath;
    }
    
    public Set<Space> remove() {
        Set<Space> removedSpaces = new HashSet<>();
        removedSpaces.add(this);
        this.entity = null;
        this.escapePath = null;
        return removedSpaces;
    }
    
    public boolean isLift() {
        return this instanceof Lift;
    }
}