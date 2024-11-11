package City;

import java.util.Set;

public class PureCirculation implements Space {
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
        return Set.of();
    }
}