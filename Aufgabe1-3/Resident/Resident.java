package Resident;

public class Resident {
  private int age;
  private float satisfaction;
  private boolean movingOut;
  private float chanceOfMovingOut = 0.0f;

  private float chanceOfDeath = 0.0f;

  private boolean isDead = false;



  private boolean livesWithParents;

  private boolean canHaveChildren;

  public Resident(int age, float satisfaction, boolean livesWithParents, boolean movingOut, boolean canHaveChildren) {
    this.age = age;
    this.satisfaction = satisfaction;
    this.livesWithParents = livesWithParents;
    this.movingOut = movingOut;
    this.canHaveChildren = canHaveChildren;
  }


  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public float getSatisfaction() {
    return satisfaction;
  }

  public void setSatisfaction(float satisfaction) {
    this.satisfaction = satisfaction;
  }

  public boolean isLivesWithParents() {
    return livesWithParents;
  }

  public void setLivesWithParents(boolean livesWithParents) {
    this.livesWithParents = livesWithParents;
  }

  public boolean isMovingOut() {
    return movingOut;
  }

  public void setMovingOut(boolean movingOut) {
    this.movingOut = movingOut;
  }

  public boolean isDead() {
    return isDead;
  }

  public void setDead(boolean isDead) {
    this.isDead = isDead;
  }

  public boolean isCanHaveChildren() {
    return canHaveChildren;
  }

  public void setCanHaveChildren(boolean canHaveChildren) {
    this.canHaveChildren = canHaveChildren;
  }

  public void age() {
    age++;
    checkIfDead();
    checkIfCanHaveChildren();
    checkIfMoveOut();
    satisfactionDecrease();
  }

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

  private void checkIfCanHaveChildren() {
    if (age > 50) {
      canHaveChildren = false;
    }
  }

  private void satisfactionDecrease() {
    if (satisfaction - 0.012f < 0) {
      satisfaction = 0;
    } else {
      satisfaction -= 0.012f;
    }
  }
}
