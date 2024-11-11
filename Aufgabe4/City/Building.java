package City;

import java.util.Set;

public abstract class Building implements Entity {
    private Set<Space> spaces;

    public Set<Space> spaces() {
        return spaces;
    }

    public abstract void add(Entity entity);
    public abstract void remove(Entity entity);
}
