package tian.hongzeng;

import java.io.*;
import java.util.*;

public class Main {
    private static Map<Integer, Vector<Node>> graph = null;
    public static List<Integer> mustContainNodes = null;
    public static final long time = System.currentTimeMillis();
    public static int[] mustContainNodesNums = new int[600];
    public static int[] mustContainFlag = new int[600];

    public static void main(String[] args) throws IOException {
        mustContainNodes = new ArrayList<>();
        File topo = new File(args[0]);
        File demand = new File(args[1]);
        File result = new File(args[2]);
        BufferedReader bufferedReaderTopo = new BufferedReader(new FileReader(topo));
        BufferedReader bufferedReaderDemand = new BufferedReader(new FileReader(demand));
        BufferedWriter bufferedWriterResult = new BufferedWriter(new FileWriter(result));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/demand.csv")));
        String s = bufferedReaderDemand.readLine();
        String[] splits = s.split(",");
//        System.out.println(splits[2]);
        int begin = Integer.valueOf(splits[0]);
        int end = Integer.valueOf(splits[1]);
        String[] mustNodes = splits[2].split("\\|");
        for (String mustNode : mustNodes) {
            mustContainNodes.add(Integer.valueOf(mustNode));
        }
        graph = new HashMap<Integer, Vector<Node>>();
//        bufferedReaderDemand = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/topo.csv")));
        while ((s = bufferedReaderTopo.readLine()) != null) {
            splits = s.split(",");
            if (graph.containsKey(Integer.valueOf(splits[1]))) {
                Vector<Node> nodes = graph.get(Integer.valueOf(splits[1]));
                insertToGraph(nodes, new Node(Integer.valueOf(splits[2]), Integer.valueOf(splits[3])));
                graph.put(Integer.valueOf(splits[1]), nodes);
            } else {
                Vector<Node> nodes = new Vector<>();
                nodes.add(new Node(Integer.valueOf(splits[2]), Integer.valueOf(splits[3])));
                graph.put(Integer.valueOf(splits[1]), nodes);
            }
        }
        bufferedReaderTopo.close();
        bufferedReaderDemand.close();
        DFSOptimization dfsAlgo = new DFSOptimization(graph, new Node(begin), new Node(end), graph.size()/mustContainNodes.size()*10);
//        DFS dfsAlgo = new DFS(graph, new Node(begin), new Node(end));
        mustContainNodes.add(0, end);
        dfsAlgo.getAvailablePath();
        System.out.println(dfsAlgo.getMinLength());
        dfsAlgo.printMinPath();
        Stack<Node> stack = dfsAlgo.getMinPath();
        if(stack.size() > 0){
            List<Integer> resultList = new ArrayList<>();
            for(Node node : stack){
                resultList.add(node.getVal());
            }
            for(int i = 0; i < resultList.size()-1;i++){
                bufferedWriterResult.write(resultList.get(i)+"|");
            }
            bufferedWriterResult.write(String.valueOf(end));
        }
        else{
            bufferedWriterResult.write("0");
        }
        bufferedWriterResult.close();
    }

    private static int getSplitIndex(Vector<Node> vector) {
        for (int i = 0; i < vector.size(); i++) {
            if (!mustContainNodes.contains(vector.get(i).getVal())) {
                return i;
            }
        }
        return vector.size();
    }

    private static boolean handleSameNodeWithDifferentWeight(Vector<Node> vector, Node node) {
        for (Node node1 : vector) {
            if (node1.getVal() == node.getVal()) {
                if (node1.getWeight() < node.getWeight()) {
                    return true;
                } else {
                    vector.remove(node1);
                    return true;
                }
            }
        }
        return false;
    }

    private static void insertToGraph(Vector<Node> vector, Node node) {
        if(!handleSameNodeWithDifferentWeight(vector, node)){
            mustContainNodesNums[node.getVal()]++;
        }
        int splitIndex = getSplitIndex(vector);
        if (mustContainNodes.contains(node.getVal())) {
            boolean insert = false;
            for (int i = 0; i < splitIndex; i++) {
                if (node.getWeight() < vector.get(i).getWeight()) {
                    vector.add(i, node);
                    insert = true;
                    break;
                }
            }
            if (!insert) {
                vector.add(splitIndex, node);
            }

        } else {
            boolean insert = false;
            for (int i = splitIndex; i < vector.size(); i++) {
                if (node.getWeight() < vector.get(i).getWeight()) {
                    vector.add(i, node);
                    insert = true;
                    break;
                }
            }
            if (!insert)
                vector.add(node);
        }
    }
}
