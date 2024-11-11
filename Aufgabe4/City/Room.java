package City;

public abstract class Room implements Space {
    private boolean servantSpace;
    private boolean servedSpace;

    public boolean isServantSpace() {
        return servantSpace;
    }

    public boolean isServedSpace() {
        return servedSpace;
    }
}
