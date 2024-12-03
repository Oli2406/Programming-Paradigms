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
        CompanyGen<Building> companyGen = new CompanyGen<Building>("TechCorpGen");

        // Create buildings
        Building building1 = new Building("Building 1");
        Building building2 = new Building("Building 2");

        // Add buildings to the companies
        company.addBuilding(building1);
        company.addBuilding(building2);
        companyGen.addBuilding(building1);
        companyGen.addBuilding(building2);

        // Create office units with rooms
        Office office1 = new Office(101, 50);
        Office office2 = new Office(102, 60);
        Office office3 = new Office(201, 70);
        Office office4 = new Office(202, 80);

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
        building1.addOffice(office2);
        building2.addOffice(office3);
        building2.addOffice(office4);

        // Display buildings and their offices
        company.displayBuildings();
        companyGen.displayBuildings();

        // Remove an office unit
        building1.removeOffice(office3);

        // Change room information
        Room changedRoom = new RoomWindows("Office Room 1", 22, 22, new UsageOffice(6), 55);
        office1.changeRoom(room2, changedRoom);

        // Display updated buildings and their offices
        company.displayBuildings();
        companyGen.displayBuildings();

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
    }
}