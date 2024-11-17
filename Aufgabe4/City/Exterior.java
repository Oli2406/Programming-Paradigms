package City;

import java.util.Set;

//Untertypenbeziehungsbegründung: Ein Exterior ist ein Bereich außerhalb eines Gebäudes
// (aber immer noch ins Gebäude eingebunden),
// der den Aufenthalt von Menschen ermöglicht
//Typbegründung: Ein Exterior ist ein Außenbereich, der instanziiert werden soll
public class Exterior implements Space {
    private Entity entity;
    private Escape escape;

    public Exterior(Entity entity, Escape escape) {
        this.entity = entity;
        this.escape = escape;
    }

    @Override
    public Entity entity() {
        return this.entity;
    }

    @Override
    public Escape escape() {
        return this.escape;
    }

    @Override
    public Set<Space> remove() {
        this.entity = null;
        return Set.of(this);
    }

    @Override
    public boolean isLift() {
        return false;
    }
    //Zusicherung: muss in ein Gebäude eingebunden sein.
}