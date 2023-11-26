//package CSI2110.AssignmentP2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ParisMetro {
    
    private static Graph metroGraph;

    /**
     * Read the metro edges from the file and create the graph for the metro
     * @param filename String of the file to read
     */
    private static void readMetro(String filename){     
        try {
            // initialize the file reader with the given filename
            FileReader metroFile = new FileReader(filename);
            BufferedReader metroReader = new BufferedReader(metroFile);
            
            // read the first line to determine the number of vertices 
            String line = metroReader.readLine();
            StringTokenizer st = new StringTokenizer(line);
            
            // initialize the graph with the size indicated in the file
            int vertices = Integer.parseInt(st.nextToken());
            metroGraph = new Graph(vertices);
            //int edges = Integer.parseInt(st.nextToken());
            
            // skip the names of each of the stations
            while ((line = metroReader.readLine()) != null) {
                if (line.startsWith("$")){
                    break;
                }
            }

            // read each of the edges and assign each in the graph
            while ((line = metroReader.readLine()) != null) {
                st = new StringTokenizer(line);
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());
                metroGraph.addToGraph(from, to, time);
            }

            // close the file reader
            metroReader.close();
        } catch (IOException e) {
            System.out.println("Unable to read metro file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        // run the method to set up the graph from the file
        readMetro("metro.txt");
        
        switch (args.length) {
            case 1:
                System.out.println("Every station on the line with: " + args[0]);
            
                // read the given parameter and use a dfs to find the route
                metroGraph.DFS(Integer.parseInt(args[0]));

                // display the route for the user
                System.out.println("Line: " + 
                    metroGraph.getVisitedVertices().toString().replaceAll("[\\[\\]]", ""));
                
            case 2:
                System.out.println("Shortest path between: " + args[0] + " and " + args[1]);

                int result = metroGraph.dijkstra(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

                System.out.println(result);
            case 3:
                break;
            default:
                System.out.println("Incorrect parameters provided. Please use the format java ParisMetro N1 N2 N3.");
        }

    }

}
