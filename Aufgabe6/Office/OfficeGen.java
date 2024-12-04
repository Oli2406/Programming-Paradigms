package Office;

import Room.*;

import java.util.ArrayList;

public class OfficeGen<T extends Room> {
  private final int officeNumber; // Unique office number
  private final double auxiliarySpaceArea; // Area of auxiliary rooms
  private final ArrayList<T> rooms; // List of rooms

  // Constructor
  public OfficeGen(int officeNumber, double auxiliarySpaceArea) {
    if (auxiliarySpaceArea < 0) throw new IllegalArgumentException("Auxiliary space area cannot be negative.");
    this.officeNumber = officeNumber;
    this.auxiliarySpaceArea = auxiliarySpaceArea;
    this.rooms = new ArrayList<>();
  }

  // Getters
  public int getOfficeNumber() {
    return officeNumber;
  }

  public T getRoom(String roomName) {
    for (T room : rooms) {
      if (room.getName().equals(roomName)) {
        return room;
      }
    }
    return null;
  }

  public double getAuxiliarySpaceArea() {
    return auxiliarySpaceArea;
  }

  // Calculate total area of the office unit (rooms + auxiliary spaces)
  public double getTotalArea() {
    double roomArea = 0;
    for (T room : rooms) {
      roomArea += room.getLength() * room.getWidth();
    }
    return roomArea + auxiliarySpaceArea;
  }

  // Add a room to the office unit
  public void addRoom(T room) {
    if (room == null) throw new IllegalArgumentException("Room cannot be null.");
    rooms.add(room);
  }

  // Remove a room by its name
  public void removeRoom(T room) {
    rooms.remove(room);
  }

  // Change the usage of a specific room
  public void changeRoom(T room, Room newRoom) {
    if(rooms.contains(room)) {
      room.setUsage(newRoom.getUsage());
      room.setName(newRoom.getName());
      room.setLength(newRoom.getLength());
      room.setWidth(newRoom.getWidth());
    } else {
      throw new IllegalArgumentException("Room not found: " + room.getName());
    }
  }

  // Statistical methods
  public double getAverageRoomArea() {
    if (rooms.isEmpty()) return 0;
    double totalArea = 0;
    for (T room : rooms) {
      totalArea += room.getLength() * room.getWidth();
    }
    return rooms.isEmpty()?0:totalArea / rooms.size();
  }

  public double getAverageRoomWindowsArea() {
    if (rooms.isEmpty()) return 0;
    double totalArea = 0;
    int count = 0;
    for (T room : rooms) {
      if (room instanceof RoomWindows) {
        totalArea += room.getLength() * room.getWidth();
        count++;
      }
    }
    return count == 0 ? 0 : totalArea / count;
  }

  public double getAverageRoomWindowlessArea() {
    if (rooms.isEmpty()) return 0;
    double totalArea = 0;
    int count = 0;
    for (T room : rooms) {
      if (room instanceof RoomWindowless) {
        totalArea += room.getLength() * room.getWidth();
        count++;
      }
    }
    return count == 0 ? 0 : totalArea / count;
  }

  public double getAverageStoreroomVolume() {
    if (rooms.isEmpty()) return 0;
    double totalVolume = 0;
    int count = 0;
    for (T room : rooms) {
      if (room.getUsage() instanceof UsageStoreroom) {
        totalVolume += (room.getUsage()).getStorageVolume();
        count++;
      }
    }
    return count == 0 ? 0 : totalVolume / count;
  }

  public double getAverageWorkplaces() {
    if (rooms.isEmpty()) return 0;
    double totalWorkplaces = 0;
    int count = 0;
    for (T room : rooms) {
      if (room.getUsage() instanceof UsageOffice) {
        totalWorkplaces += (room.getUsage()).getWorkplaces();
        count++;
      }
    }
    return count == 0 ? 0 : totalWorkplaces / count;
  }

  public double[] calculateAverageWindowAreaToRoomAreaRatios() {
    double totalRatio = 0;
    int totalRoomsWithWindows = 0;

    double officeRatio = 0;
    int officeRoomCount = 0;

    double storageRatio = 0;
    int storageRoomCount = 0;

    for (T room : rooms) {
      if (room instanceof RoomWindows roomWithWindow) {
        double roomArea = room.getLength() * room.getWidth();
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

  public double[] calculateAverageLuminousFluxToAreaRatios() {
    double totalLux = 0;
    int totalRoomsWithoutWindows = 0;

    double officeLux = 0;
    int officeRoomCount = 0;

    double storageLux = 0;
    int storageRoomCount = 0;

    for (T room : rooms) {
      if (room instanceof RoomWindowless roomWithoutWindow) {
        double roomArea = room.getLength() * room.getWidth();
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Office Number: ").append(getOfficeNumber()).append("\n");
    sb.append("Auxiliary Space Area: ").append(getAuxiliarySpaceArea()).append("\n");
    sb.append("Total Area: ").append(getTotalArea()).append("\n");
    sb.append("Average Room Area: ").append(getAverageRoomArea()).append("\n");
    sb.append("Average Room Windows Area: ").append(getAverageRoomWindowsArea()).append("\n");
    sb.append("Average Room Windowless Area: ").append(getAverageRoomWindowlessArea()).append("\n");
    sb.append("Average Storeroom Volume: ").append(getAverageStoreroomVolume()).append("\n");
    sb.append("Average Workplaces: ").append(getAverageWorkplaces()).append("\n");
    return sb.toString();
  }
}

