import Building.*;
import Company.*;
import Room.*;
import Office.*;
/*
 * Wer hat was gemacht:
 * - Ryan Foster: Building, BuildingGen,  Office, OfficeGen, Test
 * - Oliver Kastner: Company, CompanyGen, Office, OfficeGen
 * - Noah Oguamalam: Room, RoomWindows, RoomWindowless, Usage, UsageOffice, UsageStoreroom
 */

public class Test {
    public static void main(String[] args) {
        // Create a Company and a CompanyGen object
        Company company = new Company("TechCorp");
        CompanyGen<BuildingGen<OfficeGen<Room>>> companyGen = new CompanyGen<BuildingGen<OfficeGen<Room>>>("TechCorpGen");

        // Create buildings
        Building building1 = new Building("Building 1");
        Building building2 = new Building("Building 2");

        // Create BuildingGen objects
        BuildingGen<OfficeGen<Room>> buildingGen1 = new BuildingGen<OfficeGen<Room>>("BuildingGen 1");
        BuildingGen<OfficeGen<Room>> buildingGen2 = new BuildingGen<OfficeGen<Room>>("BuildingGen 2");

        // Add buildings to the companies
        company.addBuilding(building1);
        company.addBuilding(building2);
        companyGen.addBuilding(buildingGen1);

        // Create office units with rooms
        Office office1 = new Office(101, 50);
        Office office2 = new Office(102, 60);
        OfficeGen<Room> office3 = new OfficeGen<Room>(201, 70);
        OfficeGen<Room> office4 = new OfficeGen<Room>(202, 80);

        // Add rooms to office units
        Room room1 = new RoomWindowless("Toilet", 10, 10, new UsageStoreroom(20), 100);
        Room room2 = new RoomWindows("Office Room 1", 20, 20, new UsageOffice(5), 50);
        Room room3 = new RoomWindowless("Storage", 15, 15, new UsageStoreroom(30), 200);
        Room room4 = new RoomWindows("Office Room 2", 25, 25, new UsageOffice(10), 75);
        Room room5 = new RoomWindowless("Meeting Room", 30, 30, new UsageOffice(0), 150);

        office1.addRoom(room1);
        office1.addRoom(room2);
        office2.addRoom(room3);
        office3.addRoom(room4);
        office4.addRoom(room5);

        // Add office units to buildings
        building1.addOffice(office1);
        building2.addOffice(office2);

        // Add office units to BuildingGen objects
        buildingGen1.addUnit(office3);
        buildingGen2.addUnit(office4);

        // Get Offices from Building
        System.out.println(building1.getOffice(101));
        System.out.println(building2.getOffice(102));
        System.out.println(buildingGen1.getUnit(201));
        System.out.println(buildingGen2.getUnit(202));

        // Get Room from Office
        System.out.println(office1.getRoom("Toilet"));
        System.out.println(office1.getRoom("Office Room 1"));
        System.out.println(office2.getRoom("Storage"));
        System.out.println(office3.getRoom("Office Room 1"));
        System.out.println(office4.getRoom("Meeting Room"));

        // Display buildings and their offices
        company.displayBuildings();
        companyGen.displayBuildings();

        // Display BuildingGen units
        buildingGen1.displayUnits();
        buildingGen2.displayUnits();

        // Change room information
        Room changedRoom = new RoomWindows("Office Room 1", 22, 22, new UsageOffice(6), 55);
        office1.changeRoom(room2, changedRoom);

        // Change room usage
        office3.changeRoomUsage(room4, new UsageStoreroom(40));

        // Remove an office unit
        building1.removeOffice(office2);

        // Remove a room from an office unit
        office1.removeRoom(room2);
        office3.removeRoom(room4);

        // Remove a building
        company.removeBuilding(building2);
        companyGen.removeBuilding(buildingGen1);

        // Remove a unit from BuildingGen
        buildingGen1.removeUnit(office3);

        // Display updated buildings and their offices
        company.displayBuildings();
        companyGen.displayBuildings();

        // Display updated BuildingGen units
        buildingGen1.displayUnits();
        buildingGen2.displayUnits();

        // Calculate and display statistical values
        System.out.println("Average Room Area: " + office1.getAverageRoomArea());
        System.out.println("Average Room Windows Area: " + office1.getAverageRoomWindowsArea());
        System.out.println("Average Room Windowless Area: " + office1.getAverageRoomWindowlessArea());
        System.out.println("Average Storeroom Volume: " + office1.getAverageStoreroomVolume());
        System.out.println("Average Workplaces: " + office1.getAverageWorkplaces());

        double[] windowAreaRatios = office1.calculateAverageWindowAreaToRoomAreaRatios();
        System.out.println("Average Window Area to Room Area Ratio (Total): " + windowAreaRatios[0]);
        System.out.println("Average Window Area to Room Area Ratio (Office): " + windowAreaRatios[1]);
        System.out.println("Average Window Area to Room Area Ratio (Storage): " + windowAreaRatios[2]);

        double[] luminousFluxRatios = office1.calculateAverageLuminousFluxToAreaRatios();
        System.out.println("Average Luminous Flux to Area Ratio (Total): " + luminousFluxRatios[0]);
        System.out.println("Average Luminous Flux to Area Ratio (Office): " + luminousFluxRatios[1]);
        System.out.println("Average Luminous Flux to Area Ratio (Storage): " + luminousFluxRatios[2]);

        System.out.println("Average Room Area: " + office3.getAverageRoomArea());
        System.out.println("Average Room Windows Area: " + office3.getAverageRoomWindowsArea());
        System.out.println("Average Room Windowless Area: " + office3.getAverageRoomWindowlessArea());
        System.out.println("Average Storeroom Volume: " + office3.getAverageStoreroomVolume());
        System.out.println("Average Workplaces: " + office3.getAverageWorkplaces());

        double[] windowAreaRatiosGen = office3.calculateAverageWindowAreaToRoomAreaRatios();
        System.out.println("Average Window Area to Room Area Ratio (Total): " + windowAreaRatiosGen[0]);
        System.out.println("Average Window Area to Room Area Ratio (Office): " + windowAreaRatiosGen[1]);
        System.out.println("Average Window Area to Room Area Ratio (Storage): " + windowAreaRatiosGen[2]);

        double[] luminousFluxRatiosGen = office3.calculateAverageLuminousFluxToAreaRatios();
        System.out.println("Average Luminous Flux to Area Ratio (Total): " + luminousFluxRatiosGen[0]);
        System.out.println("Average Luminous Flux to Area Ratio (Office): " + luminousFluxRatiosGen[1]);
        System.out.println("Average Luminous Flux to Area Ratio (Storage): " + luminousFluxRatiosGen[2]);
    }
}