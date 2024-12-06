package Room;

public abstract class Room {
  private String name;
  private double length, width;
  private Usage usage;

  public void setUsage(Usage usage) {
    this.usage = usage;
  }
  public Usage getUsage() {
    return usage;
  }

  public double getArea() {
    return length * width;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

}

