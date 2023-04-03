package util;

public class Arguments {

    private int curr;
    private String text;

    private String algorithm;
    private String states;
    private String heuristic;
    private boolean optimistic;
    private boolean consistent;

    public Arguments(String text) {
        this.algorithm = null;
        this.states = null;
        this.heuristic = null;
        this.optimistic = false;
        this.consistent = false;

        this.curr = 0;
        this.text = text;
        parse();
    }

    private void parse() {
        StringBuilder sb = new StringBuilder();
        String arg, value;
        while (curr < text.length()) {
            sb.setLength(0);
            arg = read(sb);
            curr++;
            if (arg.equals("--alg")) {
                sb.setLength(0);
                value = read(sb);
                this.algorithm = value;
            } else if (arg.equals("--ss")) {
                sb.setLength(0);
                this.states = read(sb);
            } else if (arg.equals("--h")) {
                sb.setLength(0);
                this.heuristic = read(sb);
            } else if (arg.equals("--check-optimistic")) {
                this.optimistic = true;
            } else if (arg.equals("--check-consistent")) {
                this.consistent = true;
            } else {
                throw new IllegalArgumentException("Zadani pogresni argumenti!");
            }
            curr++;
        }
    }

    private String read(StringBuilder sb) {
        while (curr < text.length() && text.charAt(curr) != ' ')
            sb.append(text.charAt(curr++));
        return sb.toString();
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getStates() {
        return states;
    }

    public String getHeuristic() {
        return heuristic;
    }

    public boolean getOptimistic() {
        return optimistic;
    }

    public boolean getConsistent() {
        return consistent;
    }
}