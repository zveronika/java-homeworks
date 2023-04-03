package ui;

import search.Evaluation;
import util.Arguments;
import search.SearchAlgorithms;
import util.CostNode;
import util.HeuristicNode;
import util.Input;

import java.util.*;

public class Solution {

    public static void main(String... args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args)
            sb.append(s + " ");
        String argsString = sb.toString().strip();

        Arguments arguments = new Arguments(argsString);
        Input input = new Input();

        input.readStates(arguments.getStates());

        String alg = arguments.getAlgorithm();
        String s0 = input.getS0();
        Set<String> goals = input.getGoal();
        Map<String, Map<String, Double>> succ = input.getTransitions();
        String heuristicName = arguments.getHeuristic();
        Map<String, Double> heuristic = new HashMap<>();

        if (heuristicName != null) {
            input.readHeuristic(heuristicName);
            heuristic = input.getHeuristic();
        }

        SearchAlgorithms<String> search = new SearchAlgorithms<>();
        Evaluation<String> evaluation = new Evaluation<>();

        if (alg != null) {
            if (alg.equals("bfs")) {
                System.out.println("# BFS");
                Optional<CostNode<String>> goal = search.breadthFirstSearch(s0, succ, goals);
                printResult(search);
            } else if (alg.equals("ucs")) {
                System.out.println("# UCS");
                Optional<CostNode<String>> goal = search.uniformCostSearch(s0, succ, goals);
                printResult(search);
            } else if (alg.equals("astar")) {
                System.out.println("# A-STAR " + heuristicName);
                Optional<HeuristicNode<String>> goal = search.aStarSearch(s0, succ, goals, heuristic);
                printResult(search);
            }
        }

        if (arguments.getConsistent()) {
            System.out.println("#  HEURISTIC-CONSISTENT " + heuristicName);
            evaluation.checkConsistence(succ, goals, heuristic);
        }
        if (arguments.getOptimistic()) {
            System.out.println("#  HEURISTIC-OPTIMISTIC " + heuristicName);
            evaluation.checkOptimistic(succ, goals, heuristic);
        }

    }

    private static void printResult(SearchAlgorithms search) {
        if (search.isFoundSolution()) {
            System.out.println("[FOUND_SOLUTION]: yes");
            System.out.println("[STATES_VISITED]: " + search.getStatesVisited());
            System.out.println("[PATH_LENGTH]: " + search.getPathLength());
            System.out.println("[TOTAL_COST]: " + search.getTotalCost());
            System.out.println("[PATH]: " + search.getPath());
        } else {
            System.out.println("[FOUND_SOLUTION]: no");
        }
    }
}
