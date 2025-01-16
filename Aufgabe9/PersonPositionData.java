public class PersonPositionData {
  private int id;
  private int[] leftPositions;
  private int[] rightPositions;
  
  public PersonPositionData(int id, int[] leftPositions, int[] rightPositions) {
    this.id = id;
    this.leftPositions = leftPositions;
    this.rightPositions = rightPositions;
  }
  
  public int getId() {
    return id;
  }
  
  public int[] getLeftPositions() {
    return leftPositions;
  }
  
  public void setLeftPositions(int[] leftPositions) {
    this.leftPositions = leftPositions;
  }
  
  public int[] getRightPositions() {
    return rightPositions;
  }
  
  public void setRightPositions(int[] rightPositions) {
    this.rightPositions = rightPositions;
  }
  
  public void setId(int id) {
    this.id = id;
  }
}
