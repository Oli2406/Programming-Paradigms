package Office;

import Room.*;

import java.util.HashSet;

public class Office {
  private final int officeNumber; // Unique office number
  private double auxiliarySpaceArea; // Area of auxiliary rooms
  //TODO: might have to create our own list implementation
  private final HashSet<Room> rooms; // List of rooms

  // Constructor
  public Office(int officeNumber, double auxiliarySpaceArea) {
    this.officeNumber = officeNumber;
    this.setAuxiliarySpaceArea(auxiliarySpaceArea);
    this.rooms = new HashSet<>();
  }

  // Getters
  public int getOfficeNumber() {
    return officeNumber;
  }

  public double getAuxiliarySpaceArea() {
    return auxiliarySpaceArea;
  }

  // Setters
  public void setAuxiliarySpaceArea(double auxiliarySpaceArea) {
    if (auxiliarySpaceArea < 0) {
      this.auxiliarySpaceArea = 0;
    } else {
      this.auxiliarySpaceArea = auxiliarySpaceArea;
    }
  }

  // Calculate total area of the office unit (rooms + auxiliary spaces)
  public double getTotalArea() {
    double roomArea = 0;
    for (Room room : rooms) {
      roomArea += room.getLength() * room.getWidth();
    }
    return roomArea + auxiliarySpaceArea;
  }

  // Add a room to the office unit
  public void addRoom(Room room) {
    if (room != null) {
      rooms.add(room);
    }
  }

  // Remove a room by its name
  public void removeRoom(Room roomToRemove) {
    rooms.remove(roomToRemove);
  }

  // Change the room
  public void changeRoom(Room roomToChange, Room changedRoom) {
    if (rooms.contains(roomToChange)) {
      roomToChange.setName(changedRoom.getName());
      roomToChange.setLength(changedRoom.getLength());
      roomToChange.setWidth(changedRoom.getWidth());
      roomToChange.setUsage(changedRoom.getUsage());
      if (roomToChange instanceof RoomWindows) {
        ((RoomWindows) roomToChange).setWindowArea(((RoomWindows) changedRoom).getWindowArea());
      } else if (roomToChange instanceof RoomWindowless) {
        ((RoomWindowless) roomToChange).setLuminousFlux(((RoomWindowless) changedRoom).getLuminousFlux());
      }
    }
  }

  // Statistical methods
  public double getAverageRoomArea() {
    if (rooms.isEmpty()) {
      return 0;
    }
    double totalArea = 0;
    for (Room room : rooms) {
      totalArea += room.getArea();
    }
    return totalArea / rooms.size();
  }
  public double getAverageRoomWindowsArea() {
    if (rooms.isEmpty()) {
      return 0;
    }
    double totalArea = 0;
    int count = 0;
    for (Room room : rooms) {
      if (room instanceof RoomWindows) {
        totalArea += room.getArea();
        count++;
      }
    }
    return totalArea / count;
  }

  public double getAverageRoomWindowlessArea() {
    if (rooms.isEmpty()) {
      return 0;
    }
    double totalArea = 0;
    int count = 0;
    for (Room room : rooms) {
      if (room instanceof RoomWindowless) {
        totalArea += room.getArea();
        count++;
      }
    }
    return totalArea / count;
  }

  public double getAverageStoreroomVolume() {
    if (rooms.isEmpty()) {
      return 0;
    }
    double totalVolume = 0;
    int count = 0;
    for (Room room : rooms) {
      if (room.getUsage() instanceof UsageStoreroom) {
        totalVolume += room.getUsage().getStorageVolume();
        count++;
      }
    }
    return totalVolume / count;
  }

  public double getAverageWorkplaces() {
    if (rooms.isEmpty()) {
      return 0;
    }
    double totalWorkplaces = 0;
    int count = 0;
    for (Room room : rooms) {
      if (room.getUsage() instanceof UsageOffice) {
        totalWorkplaces += room.getUsage().getWorkplaces();
        count++;
      }
    }
    return totalWorkplaces / count;
  }

  //TODO: is this legit?
  public double[] calculateAverageWindowAreaToRoomAreaRatios() {
    double totalRatio = 0;
    int totalRoomsWithWindows = 0;

    double officeRatio = 0;
    int officeRoomCount = 0;

    double storageRatio = 0;
    int storageRoomCount = 0;

    for (Room room : rooms) {
      if (room instanceof RoomWindows roomWithWindow) {
        double roomArea = room.getArea();
        double windowArea = roomWithWindow.getWindowArea();
        double ratio = windowArea / roomArea;

        totalRatio += ratio;
        totalRoomsWithWindows++;

        if (room.getUsage() instanceof UsageOffice) {
          officeRatio += ratio;
          officeRoomCount++;
        } else if (room.getUsage() instanceof UsageStoreroom) {
          storageRatio += ratio;
          storageRoomCount++;
        }
      }
    }

    return new double[]{
        totalRoomsWithWindows == 0 ? 0 : totalRatio / totalRoomsWithWindows,
        officeRoomCount == 0 ? 0 : officeRatio / officeRoomCount,
        storageRoomCount == 0 ? 0 : storageRatio / storageRoomCount
    };
  }
  //TODO: is this legit?
  public double[] calculateAverageLuminousFluxToAreaRatios() {
    double totalLux = 0;
    int totalRoomsWithoutWindows = 0;

    double officeLux = 0;
    int officeRoomCount = 0;

    double storageLux = 0;
    int storageRoomCount = 0;

    for (Room room : rooms) {
      if (room instanceof RoomWindowless) {
        RoomWindowless roomWithoutWindow = (RoomWindowless) room;
        double roomArea = room.getArea();
        double lux = roomWithoutWindow.getLuminousFlux() / roomArea;

        totalLux += lux;
        totalRoomsWithoutWindows++;

        if (room.getUsage() instanceof UsageOffice) {
          officeLux += lux;
          officeRoomCount++;
        } else if (room.getUsage() instanceof UsageStoreroom) {
          storageLux += lux;
          storageRoomCount++;
        }
      }
    }

    return new double[]{
        totalRoomsWithoutWindows == 0 ? 0 : totalLux / totalRoomsWithoutWindows,
        officeRoomCount == 0 ? 0 : officeLux / officeRoomCount,
        storageRoomCount == 0 ? 0 : storageLux / storageRoomCount
    };
  }
}

