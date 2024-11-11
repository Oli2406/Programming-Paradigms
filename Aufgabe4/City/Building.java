package City;

import java.util.Set;

public class Building implements Entity {
    private Set<Space> spaces;

    private Set<Room> rooms;
    private boolean isOnGround;

    public Set<Space> spaces() {
        return spaces;
    }

    @Override
    public void add(Entity entity) {

    }

    @Override
    public void remove(Entity entity) {

    }
}
