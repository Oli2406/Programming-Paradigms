package City;

import java.util.Set;

public class ServedSpace extends Room {
    private double alternativeEscapeArea;

    public double alternativeEscape() {
        return alternativeEscapeArea;
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

