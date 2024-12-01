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
  public void removeRoom(String roomName) {
    rooms.removeIf(room -> room.getName().equals(roomName));
  }

  // Change the usage of a specific room
  public void changeRoomUsage(String roomName, Usage newUsage) {
    for (T room : rooms) {
      if (room.getName().equals(roomName)) {
        room.setUsage(newUsage);
        return;
      }
    }
    throw new IllegalArgumentException("Room not found: " + roomName);
  }

  // Statistical methods
  public double getAverageRoomArea() {
    if (rooms.isEmpty()) return 0;
    double totalArea = 0;
    for (T room : rooms) {
      totalArea += room.getLength() * room.getWidth();
    }
    return totalArea / rooms.size();
  }
}

