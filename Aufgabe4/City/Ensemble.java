package City;

import java.util.Set;

public abstract class Ensemble extends Complex {
    private Set<Entity> entities;
    private Space definedSpace;

    public Set<Entity> entities() {
        return entities;
    }

    public Space space() {
        return definedSpace;
    }
}
