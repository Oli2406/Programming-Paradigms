// Aufgabe8/Cube.java
import java.util.Objects;

public class Cube {
  public final int x, y, z;

  public Cube(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Cube cube)) return false;
    return x == cube.x && y == cube.y && z == cube.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }
}