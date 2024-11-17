package City;
//Untertypenbeziehungsbegründung: Ein Raum ist ein Bereich, in dem sich Menschen aufhalten können
// (jeder Raum ist ein Space (aber nicht jeder Space ist ein Raum))
public class Room extends Space {
    public Room(Entity entity, Escape escape) {
        super(entity, escape);
    }
}