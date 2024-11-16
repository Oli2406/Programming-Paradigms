package City;

import java.util.Set;

public class Lift extends PureCirculation {
    
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