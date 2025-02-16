import City.*;

import java.util.Iterator;
import java.util.Set;
/*
 * Wer hat was gemacht:
 * - Ryan Foster: Test.java, Ensemble.java, ServedSpace.java, Lift.java, City.java, Interior.java
 * - Oliver Kastner: Test.java, Entity.java, Exterior.java, Building.java, Complex.java, Room.java, ServantSpace.java
 * - Noah Oguamalam: Test.java, PublicRoad.java, Circulation.java, Escape.java, Space.java, PureCirculation.java
 */
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
        
        // Test 3: Lift as Interior and Space
        Interior elevator = new Lift(null);
        assert elevator instanceof Space : "Lift substitutability as Space failed";
        assert elevator instanceof Interior : "Lift substitutability as Interior failed";
        assert elevator.escape() == null : "Lift escape() test failed under substitution";
        
        // Test 4: Substituting Spaces in Building
        Building building = new Building("Test Building");
        vienna.add(building);
        PublicRoad publicRoad = new PublicRoad("TU");
        vienna.addPublicRoad(publicRoad);
        Circulation circulation = new Circulation(building, new Escape(publicRoad));
        Space room1 = new ServedSpace(building, circulation.escape());
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

        // Test 5: Complex containing Building as Entity
        Complex complex = new Complex();
        complex.addBuilding(building);
        Exterior exterior = new Exterior(building, circulation.escape());
        complex.addExterior(exterior);
        Exterior exterior2 = new Exterior(building, new Escape(publicRoad));
        complex.addExterior(exterior2);
        assert complex.buildings().size() == 1 : "Complex buildings() test failed under substitution";
        assert complex.spaces().size() == 2 : "Complex spaces() test failed under substitution";
        assert complex instanceof Entity : "Complex substitutability as Entity failed";

        // Test 6: Escape path with optional elevator
        Building building2 = new Building("Test Building 2");
        Escape escape = new Escape(publicRoad);
        Circulation stairs = new Circulation(building2, escape);
        Space room3 = new ServedSpace(building2, stairs.escape());
        Space room4 = new ServedSpace(building2, room3.escape());
        stairs.addConnection(room3);
        stairs.addConnection(room4);
        building2.addSpace(stairs);
        Lift elevator2 = new Lift(building2);
        Space room5 = new ServedSpace(building2, elevator2.escape());
        assert room5.escape() == null : "Lift escape path test failed";
        assert room4.escape().getPath().size() == 3 : "Optional escape path construction test failed";

        System.out.println("All substitutability tests passed!");
    }
}
