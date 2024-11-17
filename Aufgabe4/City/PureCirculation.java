package City;

import java.util.HashSet;
import java.util.Set;
//Untertypenbeziehungsbegründung: PureCirculation ist eine Erschließungsfläche, die ausschließlich für die Fortbewegung genutzt wird, und in der sich Personen nur für kurze Zeit aufhalten, weswegen es ein Untertyp von ServantSpace
public class PureCirculation extends ServantSpace {
    private final Set<Space> connectedSpaces;

    public PureCirculation(Entity entity, Escape escape) {
        super(entity, escape);
        this.connectedSpaces = new HashSet<>();
    }

    public Set<Space> getConnectedSpaces() {
        return connectedSpaces;
    }

    public void addConnection(Space space) {
        connectedSpaces.add(space);
    }

    @Override
    public Set<Space> remove() {
        Set<Space> removedSpaces = new HashSet<>(connectedSpaces);
        connectedSpaces.clear();
        this.setEntity(null);
        removedSpaces.add(this);
        return removedSpaces;
    }
}