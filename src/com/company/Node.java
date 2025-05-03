package com.company;

import java.awt.*;

public class Node implements Comparable<Node>{
    private String puzzle;
    private double cost;
    public int depth;
    public Node parent;

    public Node(String puzzle,Node parent, int depth, int heuristicTyep){
        this.puzzle = puzzle;
        this.depth = depth;
        this.parent = parent;
        if(heuristicTyep == 0)  cost = depth + manhattanHeuristic(puzzle);
        else cost = depth + euclideanHeuristic(puzzle);
    }

    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPuzzle() {
        return puzzle;
    }
    public void setPuzzle(String puzzle) {
        this.puzzle = puzzle;
    }

    public double manhattanHeuristic(String state){
        double heuristic = 0;
        for(int i = 0; i < 9; ++i){
            if(state.charAt(i) - '0' == 0) continue;
            heuristic += manhattan(getPos(i), getPos(state.charAt(i) - '0'));
        }
        return heuristic;
    }
    public int manhattan(Point current, Point goal){
        return Math.abs(current.x - goal.x) + Math.abs(current.y - goal.y);
    }
    public double euclideanHeuristic(String state){
        double heuristic = 0;
        for(int i = 0; i < 9; ++i){
            if(state.charAt(i) - '0' == 0) continue;
            heuristic += euclidean(getPos(i), getPos(state.charAt(i) - '0'));
        }
        return heuristic;
    }
    public double euclidean(Point current, Point goal){
        return Math.sqrt(Math.pow(current.x - goal.x, 2) + Math.pow(current.y - goal.y, 2));
    }
    private Point getPos(int i) {
        return new Point(i / 3, i % 3);
    }

    @Override
    public int compareTo(Node o) {
        if(this.cost < o.getCost()) return -1;
        else if(this.cost > o.getCost()) return 1;
        else return 0;
    }
}
