package City;

import java.util.Set;

public abstract class Complex extends Building {
    private Set<Building> buildings;
    private Set<Space> exteriorSpaces;

    public Set<Building> buildings() {
        return buildings;
    }

    public Set<Space> spaces() {
        return exteriorSpaces;
    }
}
