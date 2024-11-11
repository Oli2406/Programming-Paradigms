package City;

import java.util.HashSet;
import java.util.Set;

public class Ensemble extends Complex {
    private Set<Entity> entities;
    private Space definedSpace;

    public Ensemble() {
        this.entities = new HashSet<>();
    }

    public Set<Entity> entities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public Space space() {
        return definedSpace;
    }

    public void setSpace(Space space) {
        this.definedSpace = space;
    }
}
