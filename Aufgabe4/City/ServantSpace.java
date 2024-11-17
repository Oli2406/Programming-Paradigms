package City;

//Untertypenbeziehungsbegründung: Ein ServantSpace ist ein dienender Innenraum
public abstract class ServantSpace extends Interior {
    public ServantSpace(Entity entity, Escape escape) {
        super(entity, escape);
    }

}