package util;

import java.util.Comparator;

public class CostNode<S> implements Comparable<CostNode<S>> {

    public static final Comparator<CostNode<?>> COMPARE_BY_COST =
            (n1, n2) -> Double.compare(n1.getCost(), n2.getCost());

    public static final Comparator<CostNode<?>> COMPARE_BY_STATE =
            (n1, n2) -> n1.getState().toString().compareTo(n2.getState().toString());

    protected S state;
    protected CostNode<S> parent;
    protected double cost;

    public CostNode(S state, CostNode<S> parent, double cost) {
        this.parent = parent;
        this.state = state;
        this.cost = cost;
    }

    public S getState() {
        return state;
    }

    public CostNode<S> getParent() {
        return parent;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("(%s,%.1f)", state, cost);
    }

    @Override
    public int compareTo(CostNode<S> o) {
        return Double.compare(this.cost, o.cost);
    }
}
