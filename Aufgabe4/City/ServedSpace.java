package City;

public abstract class ServedSpace extends Interior {
    public ServedSpace(Entity entity, Escape escape) {
        super(entity, escape);
    }
    
    //Zusicherung: Summe der lichten Flächen >= 1.1m²
    public abstract double alternativeEscape();
}