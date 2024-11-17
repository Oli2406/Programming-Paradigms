package City;

//Untertypenbeziehungsbegründung: Ein Exterior ist ein Bereich außerhalb eines Gebäudes
// (aber immer noch ins Gebäude eingebunden),
// der den Aufenthalt von Menschen ermöglicht
public class Exterior extends Space {
    public Exterior(Entity entity, Escape escape) {
        super(entity, escape);
    }
    //Zusicherung: muss in ein Gebäude eingebunden sein.
}