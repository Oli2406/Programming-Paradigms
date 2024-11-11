package City;

import java.util.Set;

public interface Space {
    Entity entity();
    Escape escape();
    Set<Space> remove();
}

