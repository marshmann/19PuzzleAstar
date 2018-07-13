public class Node implements Denumerable, Comparable<Node>{
    Node parent;
    int[][] state;
    int zerox;
    int zeroy;
    int g; //how deep (sum of edge, or the total number of moves in this case)
    int h; //heuristic cost
        
    int pos;    
    boolean inFrontier;

    public Node(int[][] b, int x, int y, Node parent) {
        this.state = b;
        this.zerox = x;
        this.zeroy = y;
        this.parent = parent;
    }
    public String toString() {
        return "( " +zerox + ", " +zeroy +" ) H: " + h + " G: " + g + " F: "+(h+g);
    }
    public int compareTo(Node n) {
        int nf = n.g + n.h;
        int f = g + h;
        if(nf == f) return 0;
        else if(f>nf) return 1;
        else return -1;
    }
    public int getNumber() {
        return pos;
    }
    public void setNumber(int x) {
        pos = x;        
    }
}
