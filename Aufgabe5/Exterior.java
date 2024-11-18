public class Exterior<P> extends Space<P> {
    private boolean isPublic = false;

    public Exterior(String description) {
        super(description);
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return "Exterior: " + super.toString() + ", Public: " + isPublic;
    }
}