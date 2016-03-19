package tian.hongzeng;


public class Node {

    private int val;
    private boolean visited;
    private int weight;
    private boolean isVisited;

    public int getVal(){
        return this.val;
    }

    public int getWeight(){
        return weight;
    }

    public void setVisited(boolean isVisited){
        this.isVisited = isVisited;
    }

    public boolean getIsVisited(){
        return isVisited;
    }

    public Node(int val) {
        this.val = val;
    }

    public Node(int _val, int weight) {
        this.val = _val;
        this.visited = false;
        this.weight = weight;
    }

}
