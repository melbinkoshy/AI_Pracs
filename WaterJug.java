import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class WaterJug {
  static class WaterJugState {
    int jug1;
    int jug2;
    String steps;

    public WaterJugState(int jug1, int jug2, String steps) {
      this.jug1 = jug1;
      this.jug2 = jug2;
      this.steps = steps;
    }
  }

  public static WaterJugState WaterJugSol(int[] capacities, int target) {
    Deque<WaterJugState> queue = new ArrayDeque<>();
    Set<String> visited = new HashSet<>();
    queue.offer(new WaterJugState(0, 0, ""));
    while (!queue.isEmpty()) {
      WaterJugState currentState = queue.poll();
      if (visited.contains(currentState.jug1 + "-" + currentState.jug2))
        continue;
      visited.add(currentState.jug1 + "-" + currentState.jug2);
      if (currentState.jug1 == target || currentState.jug2 == target)
        return currentState;

      queue.offer(new WaterJugState(0, currentState.jug2, currentState.steps + "empty jug1 \n"));
      queue.offer(new WaterJugState(currentState.jug1, 0, currentState.steps + "empty jug2 \n"));
      queue.offer(new WaterJugState(capacities[0], currentState.jug2, currentState.steps + "Fill jug1 \n"));
      queue.offer(new WaterJugState(currentState.jug1, capacities[1], currentState.steps + "Fill jug2 \n"));
      int pourQuantity = Math.min(currentState.jug1, capacities[1] - currentState.jug2);
      queue.offer(new WaterJugState(currentState.jug1 - pourQuantity, currentState.jug2 + pourQuantity,
          currentState.steps + "Pour from jug1 to jug2 \n"));
      queue.offer(new WaterJugState(currentState.jug1 + pourQuantity, currentState.jug2 - pourQuantity,
          currentState.steps + "Pour from jug2 to jug1 \n"));

    }
    return new WaterJugState(0, 0, "Cannot achieve this quantity");
  }

  public static void main(String[] args) {
    int[] capacities = { 5, 3 };
    int target = 4;
    WaterJugState solution = WaterJugSol(capacities, target);
    System.out.println(solution.steps);
  }
}