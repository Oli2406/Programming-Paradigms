package City;

//Untertypenbeziehungsbegründung: Ein ServantSpace ist ein dienender Innenraum
//Typbegründung: Ein ServantSpace ist ein spezifischer Raum, der instanziiert werden soll
public class ServantSpace extends Interior {
    public ServantSpace(Entity entity, Escape escape) {
        super(entity, escape);
    }

}