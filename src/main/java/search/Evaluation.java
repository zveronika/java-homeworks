package search;

import util.CostNode;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Evaluation<S> {

    private boolean consistent;
    private boolean optimistic;

    public Evaluation() {
        this.consistent = true;
        this.optimistic = true;
    }

    public <S> void checkConsistence(Map<S, Map<S, Double>> succ, Set<S> goal, Map<S, Double> heuristic) {
        for (Map.Entry<S, Map<S, Double>> outerEntry : succ.entrySet()) {
            S key = outerEntry.getKey();
            for (Map.Entry<S, Double> innerEntry : succ.get(key).entrySet()) {
                Double parentHeuristic = heuristic.get(key);
                Double childHeuristic = heuristic.get(innerEntry.getKey());
                Double costValue = innerEntry.getValue();
                System.out.print("[CONDITION]: ");
                if (parentHeuristic <= childHeuristic + costValue) {
                    System.out.print("[OK] ");
                } else {
                    this.consistent = false;
                    System.out.print("[ERR] ");
                }
                System.out.print("h(" + key.toString() + ") <= h(" + innerEntry.getKey() + ") + c: " + parentHeuristic + " <= " + childHeuristic + " + " + costValue + "\n");
            }
        }
        System.out.println("[CONCLUSION]: Heuristic is " + (consistent ? "" : "not") + " consistent.");
    }

    public <S> void checkOptimistic(Map<S, Map<S, Double>> succ, Set<S> goal, Map<S, Double> heuristic) {
        Optional<CostNode<String>> finish;
        SearchAlgorithms search = new SearchAlgorithms();
        for (Map.Entry<S, Map<S, Double>> outerEntry : succ.entrySet()) {
            S key = outerEntry.getKey();
            finish = search.uniformCostSearch(key, succ, goal);
            double heuristicVal = heuristic.get(key);
            double totalCost = search.getTotalCost();
            System.out.print("[CONDITION]: ");
            if (heuristicVal <= totalCost) {
                System.out.print("[OK] ");
            } else {
                this.optimistic = false;
                System.out.print("[ERR] ");
            }
            System.out.print("h(" + key.toString() + ") <= h*: " + heuristicVal + " <= " + totalCost + "\n");
        }
        System.out.println("[CONCLUSION]: Heuristic is " + (optimistic ? "" : "not") + " optimistic.");
    }

}
