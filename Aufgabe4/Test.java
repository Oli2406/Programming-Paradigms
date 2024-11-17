import City.*;

import java.util.Iterator;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        System.out.println("Testing substitutability and compatibility...");
        City vienna = new City();
        
        // Test 1: Building as an Entity
        Building library = new Building("Library");
        vienna.add(library);
        assert library.getEntity() == library : "Entity substitutability test failed";
        
        // Test 2: PublicRoad as Space
        PublicRoad mainRoad = new PublicRoad("Main Street");
        vienna.addPublicRoad(mainRoad);
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
        vienna.add(building);
        PublicRoad publicRoad = new PublicRoad("TU");
        vienna.addPublicRoad(publicRoad);
        Circulation circulation = new Circulation(building, new Escape(publicRoad));
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
        assert circulation instanceof Space : "Circulation substitutability as Space failed";
        assert building instanceof Entity : "Building substitutability as Entity failed";

        Ensemble e = new Ensemble(publicRoad);
        e.add(building);
        e.add(library);
        assert e.entities().size() == 2 : "Ensemble size test failed";
        assert e instanceof Entity : "Ensemble substitutability as Entity failed";

        // Test 6: Complex containing Building as Entity
        Complex complex = new Complex();
        complex.addBuilding(building);
        Exterior exterior = new Exterior(building, circulation.escape());
        complex.addExterior(exterior);
        Exterior exterior2 = new Exterior(building, new Escape(publicRoad));
        complex.addExterior(exterior2);
        assert complex.buildings().size() == 1 : "Complex buildings() test failed under substitution";
        assert complex.spaces().size() == 2 : "Complex spaces() test failed under substitution";
        assert complex instanceof Entity : "Complex substitutability as Entity failed";

        System.out.println("All substitutability tests passed!");
    }
}
