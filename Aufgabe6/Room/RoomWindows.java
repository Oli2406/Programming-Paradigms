package Room;

public class RoomWindows extends Room {
  private double windowArea;

  public RoomWindows(String name, double length, double width, Usage usage, double windowArea) {
    this.setName(name);
    this.setLength(length);
    this.setWidth(width);
    this.setUsage(usage);
    this.setWindowArea(windowArea);
  }

  public double getWindowArea() {
    return windowArea;
  }
  public void setWindowArea(double windowArea) {
    this.windowArea = windowArea;
  }
}
