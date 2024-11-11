package City;

import java.util.Set;

public class PublicRoad implements Space {
    @Override
    public Entity entity() {
        return null;
    }

    @Override
    public Escape escape() {
        return null; // Immer null laut Aufgabenstellung
    }

    @Override
    public Set<Space> remove() {
        return Set.of();
    }
}

