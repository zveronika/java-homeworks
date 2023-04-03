package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Input {

    private String s0;
    private Map<String, Map<String, Double>> transitions;
    private Set<String> goal;
    private Map<String, Double> heuristic;

    public Input() {
        this.s0 = null;
        this.transitions = new HashMap<>();
        this.goal = new LinkedHashSet<>();
        this.heuristic = null;
    }

    public String getS0() {
        return s0;
    }

    public Map<String, Map<String, Double>> getTransitions() {
        return transitions;
    }

    public Set<String> getGoal() {
        return goal;
    }

    public Map<String, Double> getHeuristic() {
        return heuristic;
    }

    public void readStates(String states) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(states), StandardCharsets.UTF_8);
            int pass = 0;
            for (String line : lines) {
                if (line.startsWith("#"))
                    continue;
                pass++;
                if (pass == 1) {
                    s0 = line;
                } else if (pass == 2) {
                    for (String g : line.split(" "))
                        goal.add(g);
                } else {
                    String[] trans = line.split(": ");
                    String state = trans[0];
                    if(trans.length == 1) {
                        state = state.substring(0, state.length() - 1);
                    }
                    transitions.put(state, new HashMap<>());
                    if (trans.length == 2) {
                        for (String el : trans[1].split(" ")) {
                            String[] value = el.split(",");
                            transitions.get(state).put(value[0], Double.parseDouble(value[1]));
                        }
                    }
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void readHeuristic(String heuristicFile) {
        heuristic = new HashMap<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(heuristicFile), StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line.startsWith("#"))
                    continue;
                String[] heu = line.split(": ");
                Double heuVal = Double.parseDouble(heu[1]);
                heuristic.put(heu[0], heuVal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
