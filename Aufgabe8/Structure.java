import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Structure {
  private final Set<Cube> cubes;

  /**
   * Adds a new cube to the structure in a functional manner, ensuring immutability of the original structure.
   * @param cube The cube to be added.
   * @return A new Structure instance containing the added cube.
   */
  public Structure addCube(Cube cube) {
    return Stream.concat(cubes.stream(), Stream.of(cube))
        .collect(Collectors.collectingAndThen(Collectors.toSet(), Structure::new));
  }

  /**
   * Creates a copy of the current structure with the same cubes.
   * @return A new Structure instance identical to the current one.
   */
  public Structure copy() {
    Structure copy = new Structure(new HashSet<>());
    copy.cubes.addAll(this.cubes);
    return copy;
  }

  /**
   * Calculates valid positions for placing new cubes based on constraints like height and adjacency.
   * @param maxHeight The maximum height allowed for the structure.
   * @return A set of valid positions represented as integer arrays [x, y, z].
   */
  public Set<int[]> getValidPositions(int maxHeight) {
    Set<int[]> positions = new HashSet<>();
    for (Cube cube : cubes) {
      int[][] deltas = {{0, 0, 1}, {1, 0, 0}, {0, 1, 0}, {-1, 0, 0}, {0, -1, 0}};
      for (int[] delta : deltas) {
        int newX = cube.x + delta[0];
        int newY = cube.y + delta[1];
        int newZ = cube.z + delta[2];
        if (newZ < maxHeight && isValidPosition(newX, newY, newZ)) {
          positions.add(new int[]{newX, newY, newZ});
        }
      }
    }
    return positions;
  }

  /**
   * Validates whether a cube can be placed at the given coordinates based on structural rules.
   * @param x The x-coordinate.
   * @param y The y-coordinate.
   * @param z The z-coordinate.
   * @return True if the position is valid, false otherwise.
   */
  private boolean isValidPosition(int x, int y, int z) {
    if (cubes.contains(new Cube(x, y, z))) return false;

    // Ensure the cube is supported and not floating.
    if (z > 0 && !cubes.contains(new Cube(x, y, z - 1))) return false;

    int[][] sideDeltas = {{1, 0, 0}, {0, 1, 0}, {-1, 0, 0}, {0, -1, 0}, {0, 0, -1}};
    int numberOfNeighbours;
    int remainingFreeSides = z <= 0 ? 4 : 5;

    for (int[] delta : sideDeltas) {
      Cube neighbor = new Cube(x + delta[0], y + delta[1], z + delta[2]);
      if (cubes.contains(neighbor)) {
        numberOfNeighbours = countNeighbors(neighbor);
        if (z == neighbor.z) {
          numberOfNeighbours++;
        }
        if (neighbor.z <= 0) {
          numberOfNeighbours++;
        }
        if (numberOfNeighbours >= 5) return false;
        remainingFreeSides--;
      }
    }

    return remainingFreeSides > 0 && remainingFreeSides < 5;
  }

  /**
   * Counts the neighboring cubes for a given cube.
   * @param cube The cube whose neighbors are to be counted.
   * @return The number of neighboring cubes.
   */
  public int countNeighbors(Cube cube) {
    int[][] directions = {{1, 0, 0}, {0, 1, 0}, {-1, 0, 0}, {0, -1, 0}, {0, 0, -1}};
    return (int) Arrays.stream(directions)
        .map(dir -> new Cube(cube.x + dir[0], cube.y + dir[1], cube.z + dir[2]))
        .filter(cubes::contains)
        .count();
  }

  /**
   * Evaluates the thermal quality of the structure based on cube exposure.
   * @return A score representing the thermal quality.
   */
  public double evaluateThermalQuality() {
    return cubes.stream().mapToDouble(this::evaluateCubeThermal).sum();
  }

  /**
   * Calculates the thermal contribution of an individual cube based on its surroundings.
   * @param cube The cube to evaluate.
   * @return A score representing the cube's thermal contribution.
   */
  private double evaluateCubeThermal(Cube cube) {
    int[][] directions = {
        {1, 0, 0}, {-1, 0, 0},
        {0, 1, 0}, {0, -1, 0},
        {0, 0, 1}, {0, 0, -1}
    };

    return Arrays.stream(directions)
        .mapToDouble(dir -> {
          Cube neighbor = new Cube(cube.x + dir[0], cube.y + dir[1], cube.z + dir[2]);
          if (cubes.contains(neighbor)) {
            return 1.0; // Fully blocked direction contributes maximally to thermal quality.
          } else if (dir[2] == 0 && isSunlit(cube, dir)) {
            // Adjust scores based on sunlit exposure.
            if (dir[0] == 1) return 0.2;
            if (dir[0] == -1) return 0.1;
            if (dir[1] == -1) return 0.5;
          }
          return 0.0;
        })
        .sum();
  }

  /**
   * Checks if a cube face is sunlit in a given direction.
   * @param cube The cube to check.
   * @param direction The direction to check.
   * @return True if the face is sunlit, false otherwise.
   */
  private boolean isSunlit(Cube cube, int[] direction) {
    int xDir = direction[0];
    int yDir = direction[1];

    return IntStream.rangeClosed(1, 25)
        .mapToObj(distance -> new Cube(cube.x + xDir * distance, cube.y + yDir * distance, cube.z))
        .noneMatch(cubes::contains);
  }

  /**
   * Evaluates the view quality of the structure based on the visibility of cubes.
   * @return A score representing the view quality.
   */
  public double evaluateViewQuality() {
    return cubes.stream().mapToDouble(this::evaluateCubeView).sum();
  }

  /**
   * Evaluates the view quality of an individual cube based on its visibility.
   * @param cube The cube to evaluate.
   * @return A score representing the cube's view quality.
   */
  private double evaluateCubeView(Cube cube) {
    // Richtungsvektoren für die vier Hauptbewegungsrichtungen
    int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    // Funktionaler Teil: Verarbeitung der Richtungsvektoren mittels Streams
    return Arrays.stream(directions)
        .mapToDouble(direction -> {
          // Imperativer Teil: Berechnung der Distanz zum nächsten Cube in der angegebenen Richtung
          int distance = 0;
          while (distance < 25) {
            Cube front = new Cube(
                cube.x + direction[0] * (distance + 1),
                cube.y + direction[1] * (distance + 1),
                cube.z
            );
            if (cubes.contains(front)) {
              break;
            }
            distance++;
          }

          // Berechnung des ersten Teils der Punktzahl (sideScore) basierend auf der Distanz
          double sideScore = Math.min(distance / 25.0, 1);

          // Imperativer Teil: Zählen der angrenzenden Cubes (unterhalb, links und rechts)
          int cubeCount = 0;
          Cube bottom = new Cube(
              cube.x + direction[0],
              cube.y + direction[1],
              cube.z - 1
          );
          if (cubes.contains(bottom)) {
            cubeCount++;
          }

          Cube left, right;
          if (direction[0] != 0) {
            // Berechnung der seitlichen Nachbarn bei horizontaler Bewegung
            left = new Cube(
                cube.x + direction[0],
                cube.y + direction[1] + 1,
                cube.z
            );
            if (cubes.contains(left)) {
              cubeCount++;
            }
            right = new Cube(
                cube.x + direction[0],
                cube.y + direction[1] - 1,
                cube.z
            );
            if (cubes.contains(right)) {
              cubeCount++;
            }
          } else {
            // Berechnung der seitlichen Nachbarn bei vertikaler Bewegung
            left = new Cube(
                cube.x + direction[0] + 1,
                cube.y + direction[1],
                cube.z
            );
            if (cubes.contains(left)) {
              cubeCount++;
            }
            right = new Cube(
                cube.x + direction[0] - 1,
                cube.y + direction[1],
                cube.z
            );
            if (cubes.contains(right)) {
              cubeCount++;
            }
          }

          // Anpassung der Punktzahl basierend auf der Anzahl angrenzender Cubes
          sideScore *= 1 / Math.pow(2, cubeCount);

          // Rückgabe der berechneten Punktzahl für die aktuelle Richtung
          return sideScore;
        })
        // Summierung der Punktzahlen aus allen Richtungen
        .sum();
  }

  /**
   * Outputs the structure to the console.
   * This method involves side effects and bridges the functional and imperative layers.
   * It has been refactored to utilize streams for better alignment with functional programming.
   */
  public void print() {
    // Map to store the highest cube at each x, y position
    Map<String, Integer> positions = cubes.stream()
        .collect(Collectors.toMap(
            cube -> cube.x + "," + cube.y,
            cube -> cube.z + 1,
            Math::max
        ));

    // Determine the bounds of the structure
    int minX = cubes.stream().mapToInt(c -> c.x).min().orElse(0);
    int minY = cubes.stream().mapToInt(c -> c.y).min().orElse(0);
    int maxX = cubes.stream().mapToInt(c -> c.x).max().orElse(0);
    int maxY = cubes.stream().mapToInt(c -> c.y).max().orElse(0);

    // Stream through rows and columns to generate the output
    IntStream.rangeClosed(minY, maxY).forEach(y -> {
      String row = IntStream.rangeClosed(minX, maxX)
          .mapToObj(x -> {
            String key = x + "," + y;
            int height = positions.getOrDefault(key, 0);
            if (height == 0) {
              return " ";
            } else if (height < 10) {
              return String.valueOf(height);
            } else {
              return String.valueOf((char) ('A' + height - 10));
            }
          })
          .collect(Collectors.joining());
      System.out.println(row);
    });
  }

  public double customEvaluation() {
    return Math.random();
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

  Structure(Set<Cube> cubes) {
    this.cubes = cubes;
  }
}
