package Office;

import Room.*;

public class Office {
  private final int officeNumber;
  private double auxiliarySpaceArea;
  private final CustomRoomList rooms;
  
  public Office(int officeNumber, double auxiliarySpaceArea) {
    this.officeNumber = officeNumber;
    this.setAuxiliarySpaceArea(auxiliarySpaceArea);
    this.rooms = new CustomRoomList();
  }
  
  public int getOfficeNumber() {
    return officeNumber;
  }
  
  public double getAuxiliarySpaceArea() {
    return auxiliarySpaceArea;
  }
  
  public Room getRoom(String roomName) {
    for (Object obj : rooms) {
      Room room = (Room) obj;
      if (room.getName().equals(roomName)) {
        return room;
      }
    }
    return null;
  }
  
  public void setAuxiliarySpaceArea(double auxiliarySpaceArea) {
    if (auxiliarySpaceArea < 0) {
      this.auxiliarySpaceArea = 0;
    } else {
      this.auxiliarySpaceArea = auxiliarySpaceArea;
    }
  }
  
  public double getTotalArea() {
    double roomArea = 0;
    for (Object obj : rooms) {
      Room room = (Room) obj;
      roomArea += room.getLength() * room.getWidth();
    }
    return roomArea + auxiliarySpaceArea;
  }
  
  public void addRoom(Room room) {
    if (room != null) {
      for (Object obj : rooms) {
        Room existingRoom = (Room) obj;
        if (existingRoom.getName().equals(room.getName())) {
          return;
        }
      }
      rooms.add(room);
    }
  }
  
  public void removeRoom(Room roomToRemove) {
    rooms.remove(roomToRemove);
  }
  
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
  
  public double getAverageRoomArea() {
    if (rooms.isEmpty()) {
      return 0;
    }
    double totalArea = 0;
    for (Object obj : rooms) {
      Room room = (Room) obj;
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
    for (Object obj : rooms) {
      Room room = (Room) obj;
      if (room instanceof RoomWindows) {
        totalArea += room.getArea();
        count++;
      }
    }
    return count == 0 ? 0 : totalArea / count;
  }
  
  public double getAverageRoomWindowlessArea() {
    if (rooms.isEmpty()) {
      return 0;
    }
    double totalArea = 0;
    int count = 0;
    for (Object obj : rooms) {
      Room room = (Room) obj;
      if (room instanceof RoomWindowless) {
        totalArea += room.getArea();
        count++;
      }
    }
    return count == 0 ? 0 : totalArea / count;
  }
  
  public double getAverageStoreroomVolume() {
    if (rooms.isEmpty()) {
      return 0;
    }
    double totalVolume = 0;
    int count = 0;
    for (Object obj : rooms) {
      Room room = (Room) obj;
      if (room.getUsage() instanceof UsageStoreroom) {
        totalVolume += room.getUsage().getStorageVolume();
        count++;
      }
    }
    return count == 0 ? 0 : totalVolume / count;
  }
  
  public double getAverageWorkplaces() {
    if (rooms.isEmpty()) {
      return 0;
    }
    double totalWorkplaces = 0;
    int count = 0;
    for (Object obj : rooms) {
      Room room = (Room) obj;
      if (room.getUsage() instanceof UsageOffice) {
        totalWorkplaces += room.getUsage().getWorkplaces();
        count++;
      }
    }
    return count == 0 ? 0 : totalWorkplaces / count;
  }
  
  public double[] calculateAverageWindowAreaToRoomAreaRatios() {
    double[] ratios = new double[3];
    int[] counts = new int[3];
    
    for (Object obj : rooms) {
      Room room = (Room) obj;
      if (room instanceof RoomWindows roomWithWindow) {
        double roomArea = room.getArea();
        if (roomArea == 0) continue;
        
        double ratio = roomWithWindow.getWindowArea() / roomArea;
        
        ratios[0] += ratio;
        counts[0]++;
        
        if (room.getUsage() instanceof UsageOffice) {
          ratios[1] += ratio;
          counts[1]++;
        } else if (room.getUsage() instanceof UsageStoreroom) {
          ratios[2] += ratio;
          counts[2]++;
        }
      }
    }
    
    for (int i = 0; i < ratios.length; i++) {
      ratios[i] = counts[i] == 0 ? 0 : ratios[i] / counts[i];
    }
    
    return ratios;
  }
  
  public double[] calculateAverageLuminousFluxToAreaRatios() {
    double[] luxRatios = new double[3];
    int[] counts = new int[3];
    
    for (Object obj : rooms) {
      Room room = (Room) obj;
      if (room instanceof RoomWindowless roomWithoutWindow) {
        double roomArea = room.getArea();
        if (roomArea == 0) continue;
        
        double lux = roomWithoutWindow.getLuminousFlux() / roomArea;
        
        luxRatios[0] += lux;
        counts[0]++;
        
        if (room.getUsage() instanceof UsageOffice) {
          luxRatios[1] += lux;
          counts[1]++;
        } else if (room.getUsage() instanceof UsageStoreroom) {
          luxRatios[2] += lux;
          counts[2]++;
        }
      }
    }
    
    for (int i = 0; i < luxRatios.length; i++) {
      luxRatios[i] = counts[i] == 0 ? 0 : luxRatios[i] / counts[i];
    }
    
    return luxRatios;
  }
  
  @Override
  public String toString() {
    return "Office Number: " + officeNumber + "\n" +
        "Auxiliary Space Area: " + auxiliarySpaceArea + "\n" +
        "Total Area: " + getTotalArea() + "\n" +
        "Average Room Area: " + getAverageRoomArea() + "\n" +
        "Average Room Windows Area: " + getAverageRoomWindowsArea() + "\n" +
        "Average Room Windowless Area: " + getAverageRoomWindowlessArea() + "\n" +
        "Average Storeroom Volume: " + getAverageStoreroomVolume() + "\n" +
        "Average Workplaces: " + getAverageWorkplaces() + "\n";
  }
}
