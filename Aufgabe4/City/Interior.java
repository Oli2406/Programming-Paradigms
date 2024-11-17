package City;

//Untertypenbeziehungsbegründung: Ein Interior ist ein Raum innerhalb eines Gebäudes
public class Interior extends Room {
    public Interior(Entity entity, Escape escape) {
        super(entity, escape);
    }
    //Zusicherung: Muss rundum durch eine Hülle abgeschlossen sein.
}