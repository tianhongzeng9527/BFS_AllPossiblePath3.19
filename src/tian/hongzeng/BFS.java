package tian.hongzeng;


import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class BFS {

    private MyQueue q;
    private Vector<Node> temp_path, tmpPath;
    private Map<Integer, Vector<Node>> graph;
    public Vector<Node> all_linkNodes;
    private Node startNode;
    private Node endNode;
    private int minPathLength;
    private Vector<Node> minPath;
    public int sum = 0;

    public BFS(Map<Integer, Vector<Node>> graph2, Node _startNode,
               Node _endNode) {
        minPathLength = -1;
        minPath = new Vector<Node>();
        q = new MyQueue();
        temp_path = new Vector<Node>();
        tmpPath = new Vector<Node>();
        graph = new HashMap<Integer, Vector<Node>>();
        all_linkNodes = new Vector<Node>();
        this.graph = graph2;
        this.startNode = _startNode;
        this.endNode = _endNode;
    }

    public void getAvailablePath() {
        bfsImplementation();
    }


    private void bfsImplementation() {
        temp_path.add(startNode);
        q.enQueue(temp_path);
        while (!q.isEmpty()) {
            tmpPath = q.deQueue();
            Node last_node = q.getLastNode(tmpPath);
            if (last_node.getVal() == endNode.getVal()) {
                if (getPathNodeVal(tmpPath).containsAll(Main.mustContainNodes)) {
                    sum++;
                    int tmpPathLength = 0;
                    for (Node node : tmpPath) {
                        tmpPathLength += node.getWeight();
                    }
                    if (minPathLength == -1) {
                        minPathLength = tmpPathLength;
                        minPath = tmpPath;
                    } else if (minPathLength > tmpPathLength) {
                        minPathLength = tmpPathLength;
                        minPath = tmpPath;
                    }
                }
                continue;
            }
            Vector<Node> allLinkNodes = graph.get(last_node.getVal());
            for (int i = 0; i < allLinkNodes.size(); i++) {
                Node linkNode = allLinkNodes.get(i);
                if (!isIn_tmp_path(linkNode)) {
                    Vector<Node> newPath = new Vector<Node>();
                    newPath.addAll(tmpPath);
                    newPath.add(linkNode);
                    q.enQueue(newPath);
                }
            }
        }
    }


    private void printPath(Vector<Node> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.elementAt(i).getVal() + " ");
        }
        System.out.print("\n");
    }


    private boolean isIn_tmp_path(Node linkNode) {
        for (int j = 0; j < tmpPath.size(); j++) {
            Node n = tmpPath.get(j);
            if (linkNode.getVal() == n.getVal())
                return true;
        }
        return false;
    }

    public void getMinPath() {
        System.out.println("------------------");
        printPath(minPath);
    }

    private Vector<Integer> getPathNodeVal(Vector<Node> path) {
        Vector<Integer> vector = new Vector<>();
        for (Node node : path) {
            vector.add(node.getVal());
        }
        return vector;
    }
}
