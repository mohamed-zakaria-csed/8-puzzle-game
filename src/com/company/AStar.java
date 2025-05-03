package com.company;

import java.awt.*;
import java.util.*;
import java.util.List;

public class AStar {
    private int HeuristicType;
    public List<String> path = new ArrayList<>();
    public int nodesExpanded;
    public int depth;
    public int pathCost;

    public AStar(int HeuristicType) {
        this.HeuristicType = HeuristicType;
    }

    public Boolean AStarSearch(String initialState){
        Set<String> explored = new HashSet<>();
        PriorityQueue<Node> frontier = new PriorityQueue<>();
        Map<String, Node> frontierMap = new HashMap<>();

        Node root = new Node(initialState, null, 0, HeuristicType);
        frontier.add(root);
        frontierMap.put(initialState, root);

        nodesExpanded = 0;
        depth = 0;
        while (!frontier.isEmpty()){
            Node state = frontier.remove();
            String statePuzzle = state.getPuzzle();
            frontierMap.remove(statePuzzle);
            explored.add(statePuzzle);
//            System.out.println(statePuzzle);

            if(statePuzzle.equals("012345678")){
                getPath(state);
//                this.depth = state.depth;
                this.pathCost = state.depth;
                return true;
            }
            nodesExpanded ++;

            for(String neighbor: getNeighbors(statePuzzle)){
                if(!explored.contains(neighbor) && !frontierMap.keySet().contains(neighbor)){
                    Node child = new Node(neighbor,state, state.depth + 1, HeuristicType);
                    frontier.add(child);
                    frontierMap.put(neighbor, child);
                    if(state.depth + 1 > depth) depth = state.depth + 1;
                }else if(frontierMap.keySet().contains(neighbor)){
                    Node child = new Node(neighbor,state, state.depth + 1, HeuristicType);
                    if(child.getCost() < frontierMap.get(neighbor).getCost())
                        frontierMap.put(neighbor, child);
                }
            }

        }
        return false;
//        this.depth = -1;
//        return new Node("000000000", null, -1, HeuristicType);
    }

    public void getPath(Node goal){
        Stack<String> invertedPath = new Stack<>();
        Node currentNode = goal;
        while(true){
            invertedPath.push(currentNode.getPuzzle());
            currentNode = currentNode.parent;
            if(currentNode == null) break;
        }
        int size = invertedPath.size();
        for(int i = 0; i < size; ++i){
            this.path.add(invertedPath.pop());
        }
    }
    public List<String> getNeighbors(String puzzle){
        List<String> neighbors = new ArrayList<>();
        if(puzzle.charAt(0) == '0'){
            neighbors.add(swapChars(puzzle, 0, 1));
            neighbors.add(swapChars(puzzle, 0, 3));
        }else if(puzzle.charAt(1) == '0'){
            neighbors.add(swapChars(puzzle, 1, 0));
            neighbors.add(swapChars(puzzle, 1, 2));
            neighbors.add(swapChars(puzzle, 1, 4));
        }else if(puzzle.charAt(2) == '0'){
            neighbors.add(swapChars(puzzle, 2, 1));
            neighbors.add(swapChars(puzzle, 2, 5));
        }else if(puzzle.charAt(3) == '0'){
            neighbors.add(swapChars(puzzle, 3, 0));
            neighbors.add(swapChars(puzzle, 3, 4));
            neighbors.add(swapChars(puzzle, 3, 6));
        }else if(puzzle.charAt(4) == '0'){
            neighbors.add(swapChars(puzzle, 4, 1));
            neighbors.add(swapChars(puzzle, 4, 5));
            neighbors.add(swapChars(puzzle, 4, 7));
            neighbors.add(swapChars(puzzle, 4, 3));
        }else if(puzzle.charAt(5) == '0'){
            neighbors.add(swapChars(puzzle, 5, 2));
            neighbors.add(swapChars(puzzle, 5, 8));
            neighbors.add(swapChars(puzzle, 5, 4));
        }else if(puzzle.charAt(6) == '0'){
            neighbors.add(swapChars(puzzle, 6, 3));
            neighbors.add(swapChars(puzzle, 6, 7));
        }else if(puzzle.charAt(7) == '0'){
            neighbors.add(swapChars(puzzle, 7, 6));
            neighbors.add(swapChars(puzzle, 7, 4));
            neighbors.add(swapChars(puzzle, 7, 8));
        }else if(puzzle.charAt(8) == '0'){
            neighbors.add(swapChars(puzzle, 8, 5));
            neighbors.add(swapChars(puzzle, 8, 7));
        }

        return neighbors;
    }

    public String swapChars(String word, int index1, int index2){
        char[] c = word.toCharArray();
        char temp = c[index1];
        c[index1] = c[index2];
        c[index2] = temp;
        return new String(c);
    }

}
