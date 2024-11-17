package City;
//Untertypenbeziehungsbegründung: Ein Lift ist ausschließlich für den Transport nutzbar, deswegen ist es ein Untertyp von PureCirculation
public class Lift extends PureCirculation {
    
    public Lift(Entity entity) {
        super(entity, null);
    }
    
    @Override
    public Escape escape() {
        return null;
    }
}