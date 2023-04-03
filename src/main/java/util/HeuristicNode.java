package util;

import java.util.Comparator;

public class HeuristicNode<S> extends CostNode<S> {

    public static final Comparator<HeuristicNode<?>> COMPARE_BY_TOTAL =
            (n1, n2) -> Double.compare(n1.getTotalEstimatedCost(), n2.getTotalEstimatedCost());

    private double totalEstimatedCost;

    public HeuristicNode(S state, CostNode<S> parent, double cost, double totalEstimatedCost) {
        super(state, parent, cost);
        this.totalEstimatedCost = totalEstimatedCost;
    }

    public double getTotalEstimatedCost() {
        return totalEstimatedCost;
    }

    @Override
    public HeuristicNode<S> getParent() {
        return (HeuristicNode<S>) super.getParent();
    }

    @Override
    public String toString() {
        return String.format("(%s,%.1f,%.1f)", state, cost,
                totalEstimatedCost);
    }
}

