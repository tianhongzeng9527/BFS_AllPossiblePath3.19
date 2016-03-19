package tian.hongzeng;

import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class DFS {
    private Stack<Node> nodeStack;
    private Node startNode;
    private Node endNode;
    private int minPathLength;
    private Stack<Node> min_path;
    private Map<Integer, Vector<Node>> graph;
    public int sum = 0;

    public DFS(Map<Integer, Vector<Node>> graph, Node _startNode,
               Node _endNode) {
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
            i++;
        }
        return result;
    }
    private void setChildUnVisited(Node parent){
        Vector<Node> vector = graph.get(parent.getVal());
        for(Node node1: vector){
            node1.setVisited(false);
            node1.setVisited(false);
        }
    }

    public Stack getMinPath(){
        return this.min_path;
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
                if(childNode.getVal() == endNode.getVal()){
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
                        printMinPath();
                    }
                    visited[nodeStack.peek().getVal()] = 0;
                    setChildUnVisited(nodeStack.peek());
                    nodeStack.pop();
                }
            } else {
                visited[nodeStack.peek().getVal()] = 0;
                setChildUnVisited(nodeStack.peek());
                nodeStack.pop();
            }
        }
    }

    public void printPath(Stack<Node> nodeStack){
        for(Node node : nodeStack){
            System.out.print(node.getVal()+"  ");
        }
        System.out.println();
    }

    public void printMinPath(){
        for(Node node : min_path){
            System.out.print(node.getVal()+" ");
        }
        System.out.println();
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
