package City;
//Untertypenbeziehungsbegründung: Ein Lift ist ausschließlich für den Transport nutzbar, deswegen ist es ein Untertyp von PureCirculation
//Typbegründung: Ein Lift ist ein Art von Erschließungsfläche, die instanziiert werden soll
public class Lift extends PureCirculation {
    
    public Lift(Entity entity) {
        super(entity, null);
    }
    
    @Override
    public Escape escape() {
        return null;
    }
}