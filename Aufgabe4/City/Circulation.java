package City;

import java.util.HashSet;
import java.util.Set;

public class Circulation implements Space {
    private Set<Space> connectedSpaces;
    private String type; // e.g., "hallway", "staircase", "access path"

    public Circulation(String type) {
        this.type = type;
        this.connectedSpaces = new HashSet<>();
    }

    public String getType() {
        return type;
    }

    public void addConnectedSpace(Space space) {
        connectedSpaces.add(space);
    }

    public Set<Space> getConnectedSpaces() {
        return connectedSpaces;
    }

    public boolean isCirculationArea() {
        return !connectedSpaces.isEmpty();
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
        return Set.of();
    }
}
