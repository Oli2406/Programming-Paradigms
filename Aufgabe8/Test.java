import java.util.*;
import java.util.stream.Collectors;

public class Test {

  public static void main(String[] args) {
    int n1 = 69, m1 = 12, k1 = 5;
    int n2 = 42, m2 = 15, k2 = 10;
    int n3 = 24, m3 = 8, k3 = 25;

    System.out.println("Optimierungslauf 1 (Standard-Bewertung):");
    runOptimization(n1, m1, k1, structure -> structure.evaluateThermalQuality() + structure.evaluateViewQuality());

    System.out.println("\nOptimierungslauf 2 (Standard-Bewertung):");
    runOptimization(n2, m2, k2, structure -> structure.evaluateThermalQuality() + structure.evaluateViewQuality());

    /*
    System.out.println("\nOptimierungslauf 3 (Eigene Bewertungsfunktion):");
    runOptimization(n3, m3, k3, Structure::customEvaluation);
     */
  }

  private static void runOptimization(int n, int m, int k, Evaluator evaluator) {
    List<Structure> solutions = findOptimalStructures(n, m, k, evaluator);

    Structure best = solutions.getFirst();
    Structure worst = solutions.getLast();

    System.out.println("Parameter: n=" + n + ", m=" + m + ", k=" + k);

    System.out.println("Beste Lösung:");
    best.print();
    System.out.println("Bewertung: " + evaluator.evaluate(best));

    System.out.println("\nSchlechteste Lösung:");
    worst.print();
    System.out.println("Bewertung: " + evaluator.evaluate(worst));
  }

  private static List<Structure> findOptimalStructures(int n, int m, int k, Evaluator evaluator) {
    if (n == 1) {
      return Collections.singletonList(new Structure().addCube(new Cube(0, 0, 0)));
    }

    List<Structure> previousSolutions = findOptimalStructures(n - 1, m, k, evaluator);
    Set<Structure> newSolutions = new HashSet<>();

    previousSolutions.stream()
        .flatMap(structure -> structure.getValidPositions(m).stream()
            .map(pos -> structure.copy().addCube(new Cube(pos[0], pos[1], pos[2]))))
        .forEach(newSolutions::add);

    List<Structure> sortedSolutions = newSolutions.stream()
        .sorted(Comparator.comparing(evaluator::evaluate).reversed())
        .collect(Collectors.toList());

    return weightedRandomSelection(sortedSolutions, k, evaluator);
  }

  private static List<Structure> weightedRandomSelection(List<Structure> solutions, int k, Evaluator evaluator) {
    List<Structure> selectedSolutions = new ArrayList<>();
    Random random = new Random();

    // Ensure the best-evaluated solution is included
    selectedSolutions.add(solutions.getFirst());
    solutions.removeFirst();

    while (selectedSolutions.size() < k && !solutions.isEmpty()) {
      double totalWeight = solutions.stream().mapToDouble(evaluator::evaluate).sum();
      double randomValue = random.nextDouble() * totalWeight;

      double cumulativeWeight = 0.0;
      for (Iterator<Structure> iterator = solutions.iterator(); iterator.hasNext(); ) {
        Structure solution = iterator.next();
        cumulativeWeight += evaluator.evaluate(solution);
        if (cumulativeWeight >= randomValue) {
          selectedSolutions.add(solution);
          iterator.remove();
          break;
        }
      }
    }

    return selectedSolutions;
  }
}