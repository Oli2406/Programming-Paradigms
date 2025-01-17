public class PersonPositionData {
    private int id;
    private int[] leftPositions;
    private int[] rightPositions;
    private int[] oldLeftPosition;
    private int[] oldRightPosition;
    private int steps;
    private int waits;
    private boolean isActive;
    private boolean onGatheringPoint;

    public PersonPositionData(int id, int[] leftPositions, int[] rightPositions) {
        this.id = id;
        this.leftPositions = leftPositions;
        this.rightPositions = rightPositions;
        this.oldRightPosition = new int[]{-1,-1};
        this.oldLeftPosition = new int[]{-1,-1};
        this.steps = 0;
        this.waits = 0;
        this.isActive = true;
        this.onGatheringPoint = false;
    }

    public int getId() {
        return id;
    }

    public int[] getLeftPositions() {
        return leftPositions;
    }

    public void setLeftPositions(int[] leftPositions) {
        this.oldLeftPosition = this.leftPositions;
        this.leftPositions = leftPositions;
    }

    public int[] getRightPositions() {
        return rightPositions;
    }

    public void setRightPositions(int[] rightPositions) {
        this.oldRightPosition = this.rightPositions;
        this.rightPositions = rightPositions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getOldLeftPosition() {
        return oldLeftPosition;
    }

    public int[] getOldRightPosition() {
        return oldRightPosition;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getWaits() {
        return waits;
    }

    public void setWaits(int waits) {
        this.waits = waits;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isOnGatheringPoint() {
        return onGatheringPoint;
    }

    public void setOnGatheringPoint(boolean madeItToGatheringPoint) {
        this.onGatheringPoint = madeItToGatheringPoint;
    }
}
