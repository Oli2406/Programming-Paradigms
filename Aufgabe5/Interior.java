public class Interior<P> extends Space<P> {
    private double area = 0;

    public Interior(String description) {
        super(description);
    }

    public double area() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Interior: " + super.toString() + ", Area: " + area + " m²";
    }
}