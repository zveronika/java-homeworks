package search;

import util.CostNode;
import util.HeuristicNode;

import java.util.*;
//TODO

public class SearchAlgorithms<S> {

    private boolean foundSolution;
    private int statesVisited;
    private int pathLength;
    private double totalCost;
    private String path;

    public SearchAlgorithms() {
        this.foundSolution = false;
        this.statesVisited = 0;
        this.pathLength = 1;
        this.totalCost = 0.0;
        this.path = new String();
    }

    public boolean isFoundSolution() {
        return foundSolution;
    }

    public int getStatesVisited() {
        return statesVisited;
    }

    public int getPathLength() {
        return pathLength;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getPath() {
        return path;
    }

    private <S> List<CostNode<S>> expand(CostNode<S> node, Map<S, Map<S, Double>> succ) {
        List<CostNode<S>> transitions = new LinkedList<>();
        for (Map.Entry<S, Double> entry : succ.get(node.getState()).entrySet()) {
            CostNode<S> value = new CostNode(entry.getKey(), node, entry.getValue());
            transitions.add(value);
        }
        return transitions;
    }

    private <S> String nodePathRecursive(CostNode<S> node, StringBuilder sb) {
        if (node.getParent() != null) {
            this.pathLength++;
            this.totalCost += node.getCost();
            nodePathRecursive(node.getParent(), sb);
            sb.append(" => ");
        }
        sb.append(node.getState().toString());
        return sb.toString();
    }

    /**
     * @param s0
     * @param succ
     * @param goal
     * @param <S>
     * @return
     */
    public <S> Optional<CostNode<S>> breadthFirstSearch(S s0, Map<S, Map<S, Double>> succ, Set<S> goal) {
        Deque<CostNode<S>> open = new LinkedList<>();
        Set<S> visited = new HashSet<>();
        CostNode<S> n0 = new CostNode<>(s0, null, 0.0);
        open.add(n0);
        while (!open.isEmpty()) {
            CostNode<S> n = open.removeFirst();
            visited.add(n.getState());
            if (goal.contains(n.getState())) {
                StringBuilder sb = new StringBuilder();
                this.path = nodePathRecursive(n, sb);
                this.foundSolution = true;
                this.statesVisited = visited.size();
                return Optional.of(n);
            }
            List<CostNode<S>> children = expand(n, succ);
            children.sort(CostNode.COMPARE_BY_STATE);
            for (CostNode<S> child : children) {
                if (visited.contains(child))
                    continue;
                open.addLast(child);
            }
        }
        return Optional.empty();
    }

    /**
     * @param s0
     * @param succ
     * @param goal
     * @param <S>
     * @return
     */
    public <S> Optional<CostNode<S>> uniformCostSearch(S s0, Map<S, Map<S, Double>> succ, Set<S> goal) {
        Queue<CostNode<S>> open = new PriorityQueue<>(CostNode.COMPARE_BY_COST.thenComparing(CostNode.COMPARE_BY_STATE));
        Set<S> visited = new HashSet<>();
        open.add(new CostNode<>(s0, null, 0.0));
        while (!open.isEmpty()) {
            CostNode<S> n = open.remove();
            visited.add(n.getState());
            if (goal.contains(n.getState())) {
                StringBuilder sb = new StringBuilder();
                this.path = nodePathRecursive(n, sb);
                this.foundSolution = true;
                this.statesVisited = visited.size();
                this.totalCost = n.getCost();
                return Optional.of(n);
            }

            List<CostNode<S>> children = expand(n, succ);
            for (CostNode<S> child : children) {
                S childState = child.getState();
                if (visited.contains(childState))
                    continue;
                double childPathCost = child.getCost() + n.getCost();
                boolean openHasCheaper = false;
                Iterator<CostNode<S>> it = open.iterator();
                while (it.hasNext()) {
                    CostNode<S> m = it.next();
                    if (!m.getState().equals(childState)) continue;
                    if (m.getCost() <= childPathCost) {
                        openHasCheaper = true;
                    } else {
                        it.remove();
                    }
                    break;
                }
                if (!openHasCheaper) {
                    CostNode<S> childNode = new CostNode<>(childState, n, childPathCost);
                    open.add(childNode);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * @param s0
     * @param succ
     * @param goal
     * @param heuristic
     * @param <S>
     * @return
     */
    public <S> Optional<HeuristicNode<S>> aStarSearch(S s0, Map<S, Map<S, Double>> succ, Set<S> goal, Map<S, Double> heuristic) {
        Queue<HeuristicNode<S>> open = new PriorityQueue<>(HeuristicNode.COMPARE_BY_TOTAL.thenComparing(CostNode.COMPARE_BY_STATE));
        open.add(new HeuristicNode<>(s0, null, 0.0, heuristic.get(s0)));
        Set<S> visited = new HashSet<>();
        while (!open.isEmpty()) {
            HeuristicNode<S> n = open.remove();
            visited.add(n.getState());
            if (goal.contains(n.getState())) {
                StringBuilder sb = new StringBuilder();
                this.path = nodePathRecursive(n, sb);
                this.foundSolution = true;
                this.statesVisited = visited.size();
                this.totalCost = n.getCost();
                return Optional.of(n);
            }
            for (CostNode<S> child : expand(n, succ)) {
                S childState = child.getState();
                if (visited.contains(childState))
                    continue;
                double cost = n.getCost() + child.getCost();
                double total = cost + heuristic.get(childState);
                boolean openHasCheaper = false;
                Iterator<HeuristicNode<S>> it = open.iterator();
                while (it.hasNext()) {
                    HeuristicNode<S> m = it.next();
                    if (!m.getState().equals(childState)) continue;
                    if (m.getTotalEstimatedCost() <= total) {
                        openHasCheaper = true;
                    } else {
                        it.remove();
                    }
                    break;
                }
                if (!openHasCheaper) {
                    HeuristicNode<S> childNode = new HeuristicNode<>(childState, n, cost, total);
                    open.add(childNode);
                }
            }
        }
        return Optional.empty();
    }

}

