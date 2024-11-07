package Resident;
// STYLE: Objektorientiertes Paradigma
// Nominale Abstraktion (Klasse Resident)
public class Resident {
  private int age;
  private float satisfaction;
  private boolean movingOut;
  private float chanceOfMovingOut = 0.0f;
  private float chanceOfDeath = 0.0f;
  private boolean isDead = false;
  private boolean livesWithParents;
  private boolean canHaveChildren;

  /**
   * Constructor for Resident
   * @param age The age of the resident
   * @param satisfaction The satisfaction of the resident
   * @param livesWithParents Whether the resident lives with their parents
   * @param movingOut Whether the resident is moving out
   * @param canHaveChildren Whether the resident can have children
   */
  public Resident(int age, float satisfaction, boolean livesWithParents, boolean movingOut, boolean canHaveChildren) {
    this.age = age;
    this.satisfaction = satisfaction;
    this.livesWithParents = livesWithParents;
    this.movingOut = movingOut;
    this.canHaveChildren = canHaveChildren;
  }

  /**
   * Getter for age
   * @return The age of the resident
   */
  public int getAge() {
    return age;
  }

  /**
   * Setter for age
   * @param age The age of the resident
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * Getter for satisfaction
   * @return The satisfaction of the resident
   */
  public float getSatisfaction() {
    return satisfaction;
  }

  /**
   * Setter for satisfaction
   * @param satisfaction The satisfaction of the resident
   */
  public void setSatisfaction(float satisfaction) {
    this.satisfaction = satisfaction;
  }

  /**
   * Getter for livesWithParents
   * @return Whether the resident lives with their parents
   */
  public boolean isLivesWithParents() {
    return livesWithParents;
  }

  /**
   * Setter for livesWithParents
   * @param livesWithParents Whether the resident lives with their parents
   */
  public void setLivesWithParents(boolean livesWithParents) {
    this.livesWithParents = livesWithParents;
  }

  /**
   * Getter for movingOut
   * @return Whether the resident is moving out
   */
  public boolean isMovingOut() {
    return movingOut;
  }

  /**
   * Setter for movingOut
   * @param movingOut Whether the resident is moving out
   */
  public void setMovingOut(boolean movingOut) {
    this.movingOut = movingOut;
  }

  /**
   * Getter for chanceOfMovingOut
   * @return The chance of the resident moving out
   */
  public boolean isDead() {
    return isDead;
  }

  /**
   * Setter for isDead
   * @param isDead Whether the resident is dead
   */
  public void setDead(boolean isDead) {
    this.isDead = isDead;
  }

  /**
   * Getter for canHaveChildren
   * @return Whether the resident can have children
   */
  public boolean isCanHaveChildren() {
    return canHaveChildren;
  }

  /**
   * Setter for canHaveChildren
   * @param canHaveChildren Whether the resident can have children
   */
  public void setCanHaveChildren(boolean canHaveChildren) {
    this.canHaveChildren = canHaveChildren;
  }

  /**
   * Ages the resident
   */
  public void age() {
    age++;
    checkIfDead();
    checkIfCanHaveChildren();
    checkIfMoveOut();
    satisfactionDecrease();
  }

  /**
   * Checks if the resident is moving out
   */
  private void checkIfMoveOut() {
    if (livesWithParents && age > 18) {
      if (chanceOfMovingOut < 0.9f) {
        chanceOfMovingOut += 0.1f;
      }
      if (Math.random() < chanceOfMovingOut) {
        movingOut = true;
      }
    }
  }

  /**
   * Checks if the resident is dead
   */
  private void checkIfDead() {
    if (age > 80) {
      if (chanceOfDeath < 0.95f) {
        chanceOfDeath += 0.05f;
      }
      if (Math.random() < chanceOfDeath) {
        isDead = true;
      }
    }
  }

    /**
     * Checks if the resident can have children
     */
  private void checkIfCanHaveChildren() {
    if (age > 50) {
      canHaveChildren = false;
    }
  }

    /**
     * Decreases the satisfaction of the resident
     */
  private void satisfactionDecrease() {
    if (satisfaction - 0.012f < 0) {
      satisfaction = 0;
    } else {
      satisfaction -= 0.012f;
    }
  }
}
