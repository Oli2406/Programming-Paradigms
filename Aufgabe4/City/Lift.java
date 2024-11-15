package City;

import java.util.Set;

// File: Lift.java
public class Lift extends Interior {
    
    public Lift(Entity entity) {
        super(entity, null);
    }
    
    @Override
    public Escape escape() {
        return null;
    }
    
    @Override
    public Set<Space> remove() {
        super.remove();
        return null;
    }
}