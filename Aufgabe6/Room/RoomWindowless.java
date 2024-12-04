package Room;

public class RoomWindowless extends Room{
  private double luminousFlux;
    public RoomWindowless(String name, double length, double width, Usage usage, double luminousFlux) {
        this.setName(name);
        this.setLength(length);
        this.setWidth(width);
        this.setUsage(usage);
        this.setLuminousFlux(luminousFlux);
    }
    public double getLuminousFlux() {
        return this.luminousFlux;
    }
    public void setLuminousFlux(double luminousFlux) {
        this.luminousFlux = luminousFlux;
    }

    @Override
    public String toString() {
        return "RoomWindowless{" +
                "name='" + this.getName() + '\'' +
                ", length=" + this.getLength() +
                ", width=" + this.getWidth() +
                ", usage=" + this.getUsage() +
                ", luminousFlux=" + this.getLuminousFlux() +
                '}';
    }
}
