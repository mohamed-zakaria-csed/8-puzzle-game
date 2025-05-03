package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void drawStates(List<int[][]> path){
        SwingUtilities.invokeLater(() -> {
            ArrayPrinterApp app = new ArrayPrinterApp(path);
            app.setVisible(true);
        });
    }
    public static List<int[][]> converter(List<String> stringList) {
        List<int[][]> matrixList = new ArrayList<>();

        for (String str : stringList) {
            if (str.length() != 9) {
                throw new IllegalArgumentException("Each string in the list must have exactly 9 characters.");
            }

            int[][] matrix = new int[3][3];
            for (int i = 0; i < 9; i++) {
                int row = i / 3;
                int col = i % 3;
                matrix[row][col] = Character.getNumericValue(str.charAt(i));
            }

            matrixList.add(matrix);
        }

        return matrixList;
    }
    public static void main(String[] args) {
        State state = State.AddingState;
        Scanner scanner = new Scanner(System.in);
        while (state != State.End){
            System.out.println("Enter the initial state (ex: 124567038)");
            String initialState = scanner.next();
            state = State.ChoosingAlgorithm;
            while (state == State.ChoosingAlgorithm){
                System.out.println("Enter 0 for DFS, 1 for BFS, 2 for A* Manhattan , else for A* Euclidean");
                int algorithm = scanner.nextInt();
                List path = new ArrayList();
                Long start, end = System.currentTimeMillis();
                boolean isSolvable = true;
                if(algorithm == 0){
                    var solver = new Algorithms();
                    start = System.currentTimeMillis();
                    HashMap solve = solver.DFS(solver.fromStringToLong(initialState));
                    if(!solver.found){
                        System.out.println("Not solvable");
                        isSolvable = false;
                    }else {
                        path = solver.getPath(solve);
                        end = System.currentTimeMillis();
                        System.out.println("Cost = " + solver.cost);
                        System.out.println("Nodes expanded = " + solver.nodesExpanded);
                        System.out.println("Search depth = " + solver.maxDepth);
                    }

                }else if(algorithm == 1){
                    var solver = new Algorithms();
                    start = System.currentTimeMillis();
                    HashMap solve = solver.BFS(solver.fromStringToLong(initialState));
                    if(!solver.found){
                        System.out.println("Not solvable");
                        isSolvable = false;
                    }else{
                        path = solver.getPath(solve);
                        end = System.currentTimeMillis();
                        System.out.println("Cost = " + solver.cost);
                        System.out.println("Nodes expanded = " + solver.nodesExpanded);
                        System.out.println("Search depth = " + solver.maxDepth);
                    }

                }else if(algorithm == 2){
                    var solver = new AStar(0);
                    start = System.currentTimeMillis();
                    Boolean solvable = solver.AStarSearch(initialState);
                    if(!solvable){
                        System.out.println("Not solvable");
                        isSolvable = false;
                    }else{
                        end = System.currentTimeMillis();
                        System.out.println("Cost = " + solver.pathCost);
                        System.out.println("Nodes expanded = " + solver.nodesExpanded);
                        System.out.println("Search depth = " + solver.depth);
                        path = converter(solver.path);
                    }

                }else {
                    var solver = new AStar(1);
                    start = System.currentTimeMillis();
                    Boolean solvable = solver.AStarSearch(initialState);
                    if(!solvable){
                        System.out.println("Not solvable");
                        isSolvable = false;
                    }else {
                        end = System.currentTimeMillis();
                        System.out.println("Cost = " + solver.pathCost);
                        System.out.println("Nodes expanded = " + solver.nodesExpanded);
                        System.out.println("Search depth = " + solver.depth);
                        path = converter(solver.path);
                    }
                }
                if(isSolvable){
                    System.out.println("Running time = " + (end-start) + "ms");
                    Scanner changeAlgorithm = new Scanner(System.in);
                    System.out.println("Enter 0 to visualize the path, else no visualization");
                    int isVisualize = changeAlgorithm.nextInt();
                    if(isVisualize == 0){
                        drawStates(path);
                    }
                }
                System.out.println("Enter 0 to continue with same initial state, else to change initial state");
                Scanner changeAlgorithm = new Scanner(System.in);
                int isChange = changeAlgorithm.nextInt();
                if(isChange != 0){
                    state = State.AddingState;
                }
            }
            Scanner exit = new Scanner(System.in);
            System.out.println("Enter 0 to continue with different state, else to exit the program");
            int isExit = exit.nextInt();
            if(isExit != 0){
                state = State.End;
            }
        }
//        // Astar parameter:
//        // (0) for Manhattan Hearistic
//        // (any other integer) for Euclidean Hearistic
//        AStar a = new AStar(0);
//        Node finalNode = a.AStarSearch("768243105");
//        System.out.println();
//
//        System.out.println(a.depth);
//        System.out.println(a.pathCost);
//        System.out.println(a.nodesExpanded);
//        System.out.println(a.path);sou
    }
//    public static double manhattanHeuristic(String state){
//        double heuristic = 0;
//        for(int i = 0; i < 9; ++i){
//            if(state.charAt(i) - '0' == 0) continue;
//            heuristic += manhattan(getPos(i), getPos(state.charAt(i) - '0'));
//        }
//        return heuristic;
//    }
//    public static int manhattan(Point current, Point goal){
//        return Math.abs(current.x - goal.x) + Math.abs(current.y - goal.y);
//    }
//    private static Point getPos(int i) {
//        return new Point(i / 3, i % 3);
//    }
//
//    public static List<String> getNeighbors(String puzzle){
//        List<String> neighbors = new ArrayList<>();
//        if(puzzle.charAt(0) == '0'){
//            neighbors.add(swapChars(puzzle, 0, 1));
//            neighbors.add(swapChars(puzzle, 0, 3));
//        }else if(puzzle.charAt(1) == '0'){
//            neighbors.add(swapChars(puzzle, 1, 0));
//            neighbors.add(swapChars(puzzle, 1, 2));
//            neighbors.add(swapChars(puzzle, 1, 4));
//        }else if(puzzle.charAt(2) == '0'){
//            neighbors.add(swapChars(puzzle, 2, 1));
//            neighbors.add(swapChars(puzzle, 2, 5));
//        }else if(puzzle.charAt(3) == '0'){
//            neighbors.add(swapChars(puzzle, 3, 0));
//            neighbors.add(swapChars(puzzle, 3, 4));
//            neighbors.add(swapChars(puzzle, 3, 6));
//        }else if(puzzle.charAt(4) == '0'){
//            neighbors.add(swapChars(puzzle, 4, 1));
//            neighbors.add(swapChars(puzzle, 4, 5));
//            neighbors.add(swapChars(puzzle, 4, 7));
//            neighbors.add(swapChars(puzzle, 4, 3));
//        }else if(puzzle.charAt(5) == '0'){
//            neighbors.add(swapChars(puzzle, 5, 2));
//            neighbors.add(swapChars(puzzle, 5, 8));
//            neighbors.add(swapChars(puzzle, 5, 4));
//        }else if(puzzle.charAt(6) == '0'){
//            neighbors.add(swapChars(puzzle, 6, 3));
//            neighbors.add(swapChars(puzzle, 6, 7));
//        }else if(puzzle.charAt(7) == '0'){
//            neighbors.add(swapChars(puzzle, 7, 6));
//            neighbors.add(swapChars(puzzle, 7, 4));
//            neighbors.add(swapChars(puzzle, 7, 8));
//        }else if(puzzle.charAt(8) == '0'){
//            neighbors.add(swapChars(puzzle, 8, 5));
//            neighbors.add(swapChars(puzzle, 8, 7));
//        }
//
//        return neighbors;
//    }
//    public static String swapChars(String word, int index1, int index2){
//        char[] c = word.toCharArray();
//        char temp = c[index1];
//        c[index1] = c[index2];
//        c[index2] = temp;
//        return new String(c);
//    }
}
