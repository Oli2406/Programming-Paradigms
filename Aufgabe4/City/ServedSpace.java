package City;

// Zusicherung:  ausreichende natürliche und künstliche
// Belichtung und Belüftung, Heizung, Mindesthöhen,
// Bewegungsflächen, zweite Fluchtwege über öffenbare Fenster ins Freie

import java.util.HashSet;
import java.util.Set;

//Zusicherung: Summe der lichten Flächen >= 1.1m²
//Untertypenbeziehungsbegründung: Ein ServedSpace ist ein bedienter Innenraum
public abstract class ServedSpace extends Interior {
    //Ein bedienter Raum kann dienende Räume beinhalten, deswegen speichern wir ein Set von ServantSpaces
    private final Set<ServantSpace> servantSpace;
    public ServedSpace(Entity entity, Escape escape) {
        super(entity, escape);
        servantSpace = new HashSet<>();
    }

    public void addServantSpace(ServantSpace space) {
        if(space.isLift()) {
            throw new IllegalArgumentException("Lifts are not allowed in ServedSpaces");
        }
        servantSpace.add(space);
    }

    public Set<ServantSpace> getServantSpaces() {
        return servantSpace;
    }

    public void remove(ServantSpace space) {
        servantSpace.remove(space);
    }

    public abstract double alternativeEscape();
}