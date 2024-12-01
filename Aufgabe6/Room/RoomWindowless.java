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
}
