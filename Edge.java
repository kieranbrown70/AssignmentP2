public class Edge {
    
    private int toVertex;
    private int travelTime;

    /**
     * Constructor to the edge
     * @param connectedTo Vertex the edge is connected to
     * @param time Weight of the edge
     */
    public Edge(int connectedTo, int time){
        this.toVertex = connectedTo;
        this.travelTime = time;
    }

    // return the vector value
    public int getToVertex() {
        return this.toVertex;
    }

    /**
     * Return the travel time (weight)
     * @param removeNegative whether or not a -1 weight needs to be converted to 90 seconds
     * @return weight of the edge
     */
    public int getTravelTime(boolean removeNegative){
        if (removeNegative == true && this.travelTime == -1) {
            return 90;
        } else {
            return this.travelTime;
        }
    }

}
