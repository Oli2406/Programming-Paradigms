package City;

import java.util.Set;

public class PureCirculation extends Circulation {
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
    // PureCirculation-specific implementation
}
