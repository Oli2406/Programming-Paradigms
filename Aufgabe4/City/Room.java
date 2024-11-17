package City;

import java.util.Set;

//Untertypenbeziehungsbegründung: Ein Raum ist ein Bereich, in dem sich Menschen aufhalten können
// (jeder Raum ist ein Space (aber nicht jeder Space ist ein Raum))
//Typbegründung: Ein Raum ist ein grobe Beschreibung eines Raums, deswegen ist es eine abstrakte Klasse
public abstract class Room implements Space {

    private Entity entity;
    private Escape escape;
    public Room(Entity entity, Escape escape) {
        this.entity = entity;
        this.escape = escape;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
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
}