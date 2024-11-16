import City.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        System.out.println("Testing substitutability and compatibility...");
        
        // Test 1: Building as an Entity
        Entity library = new Building("Library");
        assert library.getEntity() == library : "Entity substitutability test failed";
        
        // Test 2: PublicRoad as Space
        Space mainRoad = new PublicRoad("Main Street");
        assert mainRoad.escape() == null : "PublicRoad escape() test failed under substitution";
        assert mainRoad instanceof Space : "PublicRoad substitutability as Space failed";
        
        // Test 3: PureCirculation as Space and Circulation
        Space pureCorridor = new PureCirculation(null, null); // No associated entity
        assert pureCorridor instanceof Space : "PureCirculation substitutability as Space failed";
        assert pureCorridor instanceof Circulation : "PureCirculation substitutability as Circulation failed";
        assert ((Circulation) pureCorridor).isAccessible() : "PureCirculation accessibility test failed";
        
        // Test 4: Lift as Interior and Space
        Interior elevator = new Lift(null);
        assert elevator instanceof Space : "Lift substitutability as Space failed";
        assert elevator instanceof Interior : "Lift substitutability as Interior failed";
        assert elevator.escape() == null : "Lift escape() test failed under substitution";
        
        // Test 5: Substituting Spaces in Building
        Building building = new Building("Test Building");
        Space publicRoad = new PublicRoad("TU");
        List<Space> list = new ArrayList<>();
        list.add(publicRoad);
        Circulation circulation = new Circulation(building, new Escape(list));
        Space room1 = new Room(building, circulation.escape());
        Space room2 = new Lift(building);
        circulation.addConnection(room1);
        building.addSpace(room1);
        building.addSpace(room2);
        Set<Space> buildingSpaces = building.spaces();
        assert buildingSpaces.contains(room1) && buildingSpaces.contains(room2) : "Building substitution test failed";
        assert room1.escape().getPath().size() == 3 : "Escape path construction test failed";
        Iterator iterator = room1.escape().iterator(false, true);
        while (iterator.hasNext()) {
            Space space = (Space) iterator.next();
            assert space.entity() == building : "Escape path construction test failed";
        }
        
        // Test 6: Complex containing Building as Entity
        Complex complex = new Complex() {
            @Override
            public void remove(Entity entity) {
            
            }
            
            @Override
            public Entity getEntity() {
                return this;
            }
            
            @Override
            public void add(Entity entity) {
            
            }
            
            private final Set<Building> buildings = Set.of(building);
            
            @Override
            public Set<Building> buildings() {
                return buildings;
            }
            
            @Override
            public Set<Exterior> spaces() {
                return null;
            }
        };
        assert complex.buildings().size() == 1 : "Complex buildings() test failed under substitution";
        assert complex.spaces().size() == 2 : "Complex spaces() test failed under substitution";
        
        System.out.println("All substitutability tests passed!");
    }
}
