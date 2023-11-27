import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Graph {
    
    ArrayList<LinkedList<Edge>> adjList;
    ArrayList<Integer> visitedVertices;

    /**
     * Initializes the graph by creating an adjacency list
     * @param vertices Int representing the number of vertices of the graph to create
     */
    public Graph(int vertices){
        // create an array for the whole graph 
        adjList = new ArrayList<LinkedList<Edge>>();
        
        // populate the array with lists for each vertex
        for (int i = 0; i < vertices; i++) {
            adjList.add(new LinkedList<Edge>());
        }
        
        // create a new array for all of the used integers when searching for paths
        visitedVertices = new ArrayList<Integer>();
    }

    /**
     * Add a new edge to the graph
     * @param from Vertex moving from
     * @param to Vertex moving to
     * @param time Time of travel in between the two
     */
    public void addToGraph(int from, int to, int time){
        // create a new object to store the next vertex and time together
        Edge currentEdge = new Edge(to, time);

        // add the edge to the graph at the desired index
        adjList.get(from).add(currentEdge);
    }

    /**
     * A basic depth first search algorithm
     * @param start 
     */
    public void DFS(int start) { 
        // add the starting vertex
        visitedVertices.add(start);

        // initialize iterator to go through the graph
        Iterator<Edge> iterator = adjList.get(start).listIterator();
        while (iterator.hasNext()) {
            Edge currentEdge = iterator.next();
            
            // if it isn't the same station connection, continue
            if (currentEdge.getTravelTime(false) != -1){
                int nextVertex = currentEdge.getToVertex();
                
                // if the array of visited vertices doesn't include the vertex, add it
                if (!visitedVertices.contains(nextVertex)){
                    // recursively continue the algorithm 
                    DFS(nextVertex);
                }
            }      
        }
    }

    /**
     * Returns the list of visited vertices after DFS is run
     * @return List of connected vertices
     */
    public ArrayList<Integer> getVisitedVertices() {
        return visitedVertices;
    }

    /**
     * Dijkstra's algorithm that finds the fastest time to each vertex
     * Code is modified from https://www.geeksforgeeks.org/dijkstras-algorithm-for-adjacency-list-representation-greedy-algo-8/
     * @param from Starting node
     * @param to Ending node
     * @return Time it takes for the node of value to
     */
    public int dijkstra(int from, int to) {
        // initialize arrays
        int[] distance = new int[adjList.size()];
        int[] parent = new int[adjList.size()];
        
        // fill the arrays with values to be changed
        Arrays.fill(distance, Integer.MAX_VALUE); // max value since we're looking for the minimum
        Arrays.fill(parent, -1); // empty values to be replace when forming the parent tree
        distance[from] = 0;

        // create new priorityqueue to store the vertices
        PriorityQueue<Edge> distanceQueue = new PriorityQueue<>((v1, v2) -> v1.getTravelTime(true) - v2.getTravelTime(true));
        // add the starting vertex
        distanceQueue.add(new Edge(from, 0));

        // run while there are still vertices to check
        while (distanceQueue.size() > 0) { 
            // retrieve the next edge to check
            Edge current = distanceQueue.poll();
 
            // check all of the connected vertices to the current vertex
            for (Edge nextEdge : adjList.get(current.getToVertex())) {
                // calculate the new distance between the two vertices
                int newDistance = distance[current.getToVertex()] + nextEdge.getTravelTime(true);
                
                // if it's quicker than the current one replace it
                if (newDistance < distance[nextEdge.getToVertex()]) {
                    // replace the value
                    distance[nextEdge.getToVertex()] = newDistance;
                    // add the current location to the parent tree
                    parent[nextEdge.getToVertex()] = current.getToVertex();
                    // add the next vertex to be checked to the priorityqueue
                    distanceQueue.add(new Edge(nextEdge.getToVertex(), distance[nextEdge.getToVertex()]));
                }
            }
        }

        // create an array to store the path the route follows
        ArrayList<Integer> path = new ArrayList<Integer>();
        
        // create an int to track the location
        int traversalPos = to;
        
        // loop to traverse the parent tree until the initial location is found
        while (traversalPos != from) {
            // add to the current path
            path.add(traversalPos);
            // update the current position by reading from the parent
            traversalPos = parent[traversalPos];
        }
        path.add(from);

        // reverse the path to display in the correct order
        Collections.reverse(path);
        System.out.println("Path: " + path.toString().replaceAll("[\\[\\]]", ""));

        // return the time the path takes
        return distance[to];
    }

    /**
     * Method to delete the last visited path
     */
    public void deleteVisitedPath(){

        // iterate through the path forwards
        for (int i = 0; i < visitedVertices.size() - 1; i++) {
            
            // initialize iterator to go through the graph
            Iterator<Edge> iterator = adjList.get(visitedVertices.get(i)).listIterator();
            
            // run while the edge hasn't been found
            while (iterator.hasNext()) {
                Edge currentEdge = iterator.next();
                
                // if the edge connecting the current vertex we're looking for and the next match, delete the node
                if (currentEdge.getToVertex() == visitedVertices.get(i+1)){
                    iterator.remove();
                    break;
                }
            }
        }
        
        // iterate through the path backwards since the list is linked forwards and backwards
        for (int i = visitedVertices.size() - 1; i > 0; i--) {
            
            // initialize iterator to go through the graph
            Iterator<Edge> iterator = adjList.get(visitedVertices.get(i)).listIterator();
            
            // run while the edge hasn't been found
            while (iterator.hasNext()) {
                Edge currentEdge = iterator.next();
                
                // if the edge connecting the current vertex we're looking for and the next match, delete the node
                if (currentEdge.getToVertex() == visitedVertices.get(i-1)){
                    iterator.remove();
                    break;
                }
            }
        }
    }
}
