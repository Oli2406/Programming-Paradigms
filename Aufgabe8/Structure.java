import java.util.*;

//TODO: evaluierung + custom evaluierung
//TODO: add cube überprüfen (an nachbarn anfügen)
class Structure {
  private final Set<Cube> cubes = new HashSet<>();

  public Structure addCube(Cube cube) {
    cubes.add(cube);
    return this;
  }

  public Structure copy() {
    Structure copy = new Structure();
    copy.cubes.addAll(this.cubes);
    return copy;
  }

  public Set<int[]> getValidPositions(int maxHeight) {
    Set<int[]> positions = new HashSet<>();
    for (Cube cube : cubes) {
      int[][] deltas = {{0, 0, 1}, {1, 0, 0}, {0, 1, 0}, {-1, 0, 0}, {0, -1, 0}};
      for (int[] delta : deltas) {
        int newX = cube.x + delta[0];
        int newY = cube.y + delta[1];
        int newZ = cube.z + delta[2];
        if (newZ <= maxHeight && isValidPosition(newX, newY, newZ)) {
          positions.add(new int[]{newX, newY, newZ});
        }
      }
    }
    return positions;
  }

  private boolean isValidPosition(int x, int y, int z) {
    if (cubes.contains(new Cube(x, y, z))) return false;

    if (z > 0 && !cubes.contains(new Cube(x, y, z - 1))) return false;  //Würfel kann nicht schweben

    int[][] sideDeltas = {{1, 0, 0}, {0, 1, 0}, {-1, 0, 0}, {0, -1, 0}, {0,0,-1}};
    int numberOfNeighbours;

    int remainingFreeSides = 5;

    for (int[] delta : sideDeltas) {
      Cube neighbor = new Cube(x + delta[0], y + delta[1], z+delta[2]);
      if (cubes.contains(neighbor)) {
        numberOfNeighbours = countNeighbors(neighbor);
        if(z == neighbor.z) {
            numberOfNeighbours++;
        }
        if(neighbor.z <= 0) {
          numberOfNeighbours++;
        }

        if(numberOfNeighbours >= 5) return false;
        remainingFreeSides--;
      }
    }

    if(remainingFreeSides == 5) return false;
    if(remainingFreeSides == 0) return false;

    return true;
  }

  public int countNeighbors(Cube cube) {
    int[][] directions = {{1, 0, 0}, {0, 1, 0}, {-1, 0, 0}, {0, -1, 0}, {0,0,-1}};
    int count = 0;

    for (int[] dir : directions) {
      Cube neighbor = new Cube(cube.x + dir[0], cube.y + dir[1], cube.z + dir[2]);
      if (cubes.contains(neighbor)) {
        count++;
      }
    }

    return count;
  }

  public double evaluateThermalQuality() {
    return cubes.stream().mapToDouble(this::evaluateCubeThermal).sum();
  }

  private double evaluateCubeThermal(Cube cube) {
    double score = 0.0;
    int[][] directions = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}, {0, 0, -1}};

    for (int[] dir : directions) {
      Cube neighbor = new Cube(cube.x + dir[0], cube.y + dir[1], cube.z + dir[2]);
      if (cubes.contains(neighbor)) {
        score += 1.0;
      } else if (dir[2] == 0) {
        score += 0.5;
      }
    }
    return score;
  }

  public double evaluateViewQuality() {
    return cubes.stream().mapToDouble(this::evaluateCubeView).sum();
  }


  private double evaluateCubeView(Cube cube) {
    double score = 1.0;
    for (int i = 1; i <= 25; i++) {
      Cube front = new Cube(cube.x + i, cube.y, cube.z);
      if (cubes.contains(front)) {
        score *= 0.9;
        break;
      }
    }
    return score;
  }

  public double customEvaluation() {
    return Math.random();
  }

  public void print() {
    Map<String, Integer> positions = new HashMap<>();
    for (Cube cube : cubes) {
      String key = cube.x + "," + cube.y;
      positions.put(key, Math.max(positions.getOrDefault(key, 0), cube.z));
    }
    int minX = cubes.stream().mapToInt(c -> c.x).min().orElse(0);
    int minY = cubes.stream().mapToInt(c -> c.y).min().orElse(0);
    int maxX = cubes.stream().mapToInt(c -> c.x).max().orElse(0);
    int maxY = cubes.stream().mapToInt(c -> c.y).max().orElse(0);

    for (int y = minY; y <= maxY; y++) {
      for (int x = minX; x <= maxX; x++) {
        String key = x + "," + y;
        int height = positions.getOrDefault(key, 0);
        if (height == 0) {
          System.out.print(" ");
        } else if (height < 10) {
          System.out.print(height);
        } else {
          System.out.print((char) ('A' + height - 10));
        }
      }
      System.out.println();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Structure structure)) return false;
    return Objects.equals(cubes, structure.cubes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cubes);
  }
}