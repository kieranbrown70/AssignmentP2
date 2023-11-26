//package CSI2110.AssignmentP2;

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
        visitedVertices.add(start);

        Iterator<Edge> iterator = adjList.get(start).listIterator();
        while (iterator.hasNext()) {
            
            Edge currentEdge = iterator.next();
            if (currentEdge.getTravelTime(false) != -1){
                
                int nextVertex = currentEdge.getToVertex();
                if (!visitedVertices.contains(nextVertex)){
                    DFS(nextVertex);
                }
            }      
        }
    }

    public ArrayList<Integer> getVisitedVertices() {
        return visitedVertices;
    }

    public int dijkstra(int from, int to){
        int[] distance = new int[adjList.size()];
        int[] parent = new int[adjList.size()];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        distance[from] = 0;

        PriorityQueue<Edge> distanceQueue = new PriorityQueue<>((v1, v2) -> v1.getTravelTime(true) - v2.getTravelTime(true));
        distanceQueue.add(new Edge(from, 0));

        while (distanceQueue.size() > 0) { 
            Edge current = distanceQueue.poll();
 
            for (Edge nextEdge : adjList.get(current.getToVertex())) {
                int newDistance = distance[current.getToVertex()] + nextEdge.getTravelTime(true);
                if (newDistance < distance[nextEdge.getToVertex()]) {
                    distance[nextEdge.getToVertex()] = newDistance;
                    parent[nextEdge.getToVertex()] = current.getToVertex();
                    distanceQueue.add(new Edge(nextEdge.getToVertex(), distance[nextEdge.getToVertex()]));
                }
            }
        }

        ArrayList<Integer> path = new ArrayList<Integer>();
        int traversalPos = to;
        while (traversalPos != from) {
            path.add(traversalPos);
            traversalPos = parent[traversalPos];
        }
        Collections.reverse(path);
        System.out.println(path.toString());

        return distance[to];
    }

}
