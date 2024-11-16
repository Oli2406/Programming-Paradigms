package City;
import java.util.HashSet;
import java.util.Set;

public class Building implements Entity {
    private final Set<Space> spaces;
    private final String name;
    private Entity parent;
    
    // Zusicherung: Gebäude muss mit dem Untergrund verbunden sein.
    public Building(String name) {
        this.name = name;
        this.spaces = new HashSet<>();
        this.parent = null;
    }
    
    public Set<Space> spaces() {
        return new HashSet<>(spaces);
    }
    
    public void addSpace(Space space) {
        spaces.add(space);
    }
    
    public void removeSpace(Space space) {
        spaces.remove(space);
    }
    
    public String getName() {
        return name;
    }
    
    public void setParent(Entity parent) {
        this.parent = parent;
    }
    
    public void removeParent() {
        this.parent = null;
    }
    
    public boolean isStandalone() {
        return this.parent == null;
    }
    
    
    @Override
    public void add(Entity entity) {
    
    }
    
    @Override
    public void remove(Entity entity) {
    
    }
    
    @Override
    public Entity getEntity() {
        return this;
    }
}