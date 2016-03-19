package tian.hongzeng;

import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class DFS3 {
    private Stack<Node> nodeStack;
    private Node startNode;
    private Node endNode;
    private int minPathLength;
    private Stack<Node> min_path;
    private Map<Integer, Vector<Node>> graph;
    public int sum = 0;
    private int depthLimit = 0;
    private int currentDepth = 0;

    public DFS3(Map<Integer, Vector<Node>> graph, Node _startNode,
                           Node _endNode, int depthLimit) {
        this.depthLimit = depthLimit;
        this.graph = graph;
        minPathLength = -1;
        nodeStack = new Stack<>();
        min_path = new Stack<>();
        this.startNode = _startNode;
        this.endNode = _endNode;
    }

    public void getAvailablePath() {
        DFSImplementation();
    }

    public int getMinLength(){
        return this.minPathLength;
    }

    private Node getUnvisitedChildNode(Node parent) {
        Node result = null;
        Vector<Node> vector = graph.get(parent.getVal());
        int i = 0;
        for(Node node1: vector){
            if((visited[node1.getVal()] != 1) && (!node1.getIsVisited())){
                result = node1;
                result.setVisited(true);
                visited[node1.getVal()] = 1;
                break;
            }
            if(i >= 2){
                break;
            }
            i++;
        }
        return result;
    }
    private void setChildUnVisited(Node parent){
        Vector<Node> vector = graph.get(parent.getVal());
        for(Node node1: vector){
            node1.setVisited(false);
        }
    }

    int[] visited = new int[600];
    private void DFSImplementation() {
        visited[startNode.getVal()] = 1;
        nodeStack.add(startNode);
        while (!nodeStack.isEmpty()) {
            Node node = nodeStack.peek();
            Node childNode = getUnvisitedChildNode(node);
            if (childNode != null) {
                nodeStack.add(childNode);
                currentDepth++;
                if(childNode.getVal() == endNode.getVal()){
                    reachEndNode();
//                    printPath(nodeStack);
//                    printMinPath();
//                    if(System.currentTimeMillis() - Main.time > 9000)
//                        return;
                }
                else if(Main.mustContainNodes.contains(childNode.getVal())){
                    currentDepth = 0;
                }
                else if(currentDepth > depthLimit){
                    backToParent();
                }
            } else {
                popFromStack();
            }
        }
    }

    public void printPath(Stack<Node> stack){
        for(Node node : stack){
            System.out.print(node.getVal()+" ");
        }
        System.out.println();
    }

    private void reachEndNode(){
        currentDepth = 0;
        if (getPathNodeVal(nodeStack).containsAll(Main.mustContainNodes)) {
            sum++;
            int tmpPathLength = getPathLength(nodeStack);
            if (minPathLength == -1) {
                minPathLength = tmpPathLength;
                stackToVector(min_path, nodeStack);
            } else if (minPathLength > tmpPathLength) {
                minPathLength = tmpPathLength;
                stackToVector(min_path, nodeStack);
            }
        }
        visited[nodeStack.peek().getVal()] = 0;
        setChildUnVisited(nodeStack.peek());
        nodeStack.pop();
    }

    private void popFromStack(){
        visited[nodeStack.peek().getVal()] = 0;
        setChildUnVisited(nodeStack.peek());
        nodeStack.pop();
    }

    private void backToParent(){
        visited[nodeStack.peek().getVal()] = 0;
        nodeStack.pop();
        currentDepth--;
    }

    public void printMinPath(){
        for(Node node : min_path){
            System.out.print(node.getVal()+" ");
        }
        System.out.println();
    }

    public Stack getMinPath(){
        return min_path;
    }

    private int getPathLength(Stack<Node> path) {
        int sum = 0;
        for (Node node : path) {
            sum += node.getWeight();
        }
        return sum;
    }

    private Vector<Integer> getPathNodeVal(Stack<Node> path) {
        Vector<Integer> vector = new Vector<>();
        for (Node node : path) {
            vector.add(node.getVal());
        }
        return vector;
    }

    private void stackToVector(Vector<Node> vector, Stack<Node> stack){
        vector.clear();
        for(Node node: stack){
            vector.add(node);
        }
    }
}
