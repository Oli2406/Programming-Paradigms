package Room;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomGenRoomList implements Iterable<Room> {
  private Room[] rooms;
  private int size;
  
  public CustomGenRoomList() {
    this.rooms = new Room[10];
    this.size = 0;
  }
  
  public void add(Room room) {
    if (room == null) return;
    if (contains(room)) return;
    
    if (size >= rooms.length) {
      expandCapacity();
    }
    rooms[size++] = room;
  }
  
  public boolean remove(Room room) {
    for (int i = 0; i < size; i++) {
      if (rooms[i].equals(room)) {
        System.arraycopy(rooms, i + 1, rooms, i, size - i - 1);
        size--;
        return true;
      }
    }
    return false;
  }
  
  public Room get(String name) {
    for (int i = 0; i < size; i++) {
      if (rooms[i].getName().equals(name)) {
        return rooms[i];
      }
    }
    return null;
  }
  
  public int size() {
    return size;
  }
  
  public boolean isEmpty() {
    return size == 0;
  }
  
  public boolean contains(Room room) {
    for (int i = 0; i < size; i++) {
      if (rooms[i].equals(room)) return true;
    }
    return false;
  }
  
  private void expandCapacity() {
    Room[] newRooms = new Room[rooms.length * 2];
    System.arraycopy(rooms, 0, newRooms, 0, size);
    rooms = newRooms;
  }
  
  @Override
  public Iterator<Room> iterator() {
    return new CustomRoomIterator();
  }
  
  // Inner class for Iterator
  private class CustomRoomIterator implements Iterator<Room> {
    private int currentIndex = 0;
    
    @Override
    public boolean hasNext() {
      return currentIndex < size;
    }
    
    @Override
    public Room next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return rooms[currentIndex++];
    }
    
    @Override
    public void remove() {
      throw new UnsupportedOperationException("Remove not supported in CustomRoomIterator");
    }
  }
}



