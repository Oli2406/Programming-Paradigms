package City;
import java.util.HashSet;
import java.util.Set;
//Untertypenbeziehungsbegründung: Ein Gebäude ist ein Entity.
//Typbegründung: Ein Gebäude soll instanziert werden können
public class Building implements Entity {
    private final Set<Space> spaces;
    private final String name;
    private Building child;
    private boolean isOnGround;
    
    // Zusicherung: Gebäude muss mit dem Untergrund verbunden sein.
    public Building(String name) {
        this.name = name;
        this.spaces = new HashSet<>();
        this.child = null;
        this.isOnGround = true;
    }

    public Building(String name, boolean isOnGround) {
        this.name = name;
        this.spaces = new HashSet<>();
        this.child = null;
        this.isOnGround = isOnGround;
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
    
    public void setChild(Building child) {
        this.child = child;
    }
    
    public void removeParent() {
        this.child = null;
    }
    
    public boolean isStandalone() {
        return this.child == null;
    }

    public void setOnGround(boolean isOnGround) {
        this.isOnGround = isOnGround;
    }
    
    @Override
    public void add(Entity entity) {
        if(!(entity instanceof Building)) {
            throw new IllegalArgumentException("Only buildings can be added to buildings.");
        }
        child = (Building) entity;
        child.setOnGround(false);
    }
    
    @Override
    public void remove(Entity entity) {
        child = null;
    }
    
    @Override
    public Entity getEntity() {
        return this;
    }
}