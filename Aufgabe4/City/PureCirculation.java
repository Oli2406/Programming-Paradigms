package City;

import java.util.Set;

public class PureCirculation extends ServantSpace {
    
    public PureCirculation(Entity entity, Escape escape) {
        super(entity, escape);
    }
    
    @Override
    public Entity entity() {
        return null;
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