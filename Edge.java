//package CSI2110.AssignmentP2;

public class Edge {
    
    private int toVertex;
    private int travelTime;

    public Edge(int connectedTo, int time){
        this.toVertex = connectedTo;
        this.travelTime = time;
    }

    public int getToVertex() {
        return this.toVertex;
    }

    public int getTravelTime(boolean removeNegative){
        if (removeNegative == true && this.travelTime == -1) {
            return 90;
        } else {
            return this.travelTime;
        }
    }

}
