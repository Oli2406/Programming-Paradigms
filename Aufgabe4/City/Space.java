package City;
import java.util.Set;
//Untertypenbeziehungsbegründung: Ein Space ist ein Bereich, in dem sich Menschen aufhalten können.
//Typbegründung: Ein Space muss nicht instanziert werden, da es nur als Oberklasse für spezifischere Bereiche dient.
public interface Space {
    
    Entity entity();
    
    Escape escape();
    
    Set<Space> remove();
    
    boolean isLift();
}