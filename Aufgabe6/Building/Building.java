package Building;

import Office.Office;
import java.util.ArrayList;
import java.util.List;

public class Building {
    private final String name;
    private final List<Office> offices;

    public Building(String name) {
        this.name = name;
        this.offices = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addOffice(Office office) {
        if (office != null) {
            for (Office existingOffice : offices) {
                if (existingOffice.getOfficeNumber() == office.getOfficeNumber()) {
                    return;
                }
            }
            offices.add(office);
        }
    }

    public void removeOffice(Office office) {
        offices.remove(office);
    }

    public Office getOffice(int officeNumber) {
        for (Office office : offices) {
            if (office.getOfficeNumber() == officeNumber) {
                return office;
            }
        }
        return null;
    }

    public void displayOffices() {
        System.out.println("Building: " + name);
        for (Office office : offices) {
            System.out.println("Office Number: " + office.getOfficeNumber());
            System.out.println("Auxiliary Space Area: " + office.getAuxiliarySpaceArea());
            System.out.println("Total Area: " + office.getTotalArea());
            System.out.println("Average Room Area: " + office.getAverageRoomArea());
            System.out.println("Average Room Windows Area: " + office.getAverageRoomWindowsArea());
            System.out.println("Average Room Windowless Area: " + office.getAverageRoomWindowlessArea());
            System.out.println("Average Storeroom Volume: " + office.getAverageStoreroomVolume());
            System.out.println("Average Workplaces: " + office.getAverageWorkplaces());
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Building: " + name + "\n" + offices;
    }
}