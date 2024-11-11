package City;

import java.util.Set;

public class ServantSpace extends Room {
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
    // Implementierung spezifischer Methoden für dienende Räume
}
