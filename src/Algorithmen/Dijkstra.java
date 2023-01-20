package Algorithmen;

import java.util.ArrayList;

public class Dijkstra {
    static int numNodes ;
    static int[] path;
    static Boolean[] visited;
    static int[][] outputMatrix;

    public static int[][] startDijkstra(ArrayList<Integer>[] graph, int numModes, int startNode) {
        numNodes = numModes;
        path = new int[numNodes];
        visited = new Boolean[numNodes];
        outputMatrix = new int[numNodes][numNodes];

        //Matrix erstellen:
        for (int i = 0; i < numNodes; i++) {
            path[i] = Integer.MAX_VALUE;            //Wege erstmal alle auf MaxValue setzen
            visited[i] = false;                     //Und sind noch alle unbesucht
        }
        dijkstraAlgo(graph, startNode);
        return outputMatrix;
    }

    static int getMinPath()   {
        int min = Integer.MAX_VALUE, min_index = -1;
        for (int i = 0; i < numNodes; i++)
            if (!visited[i] && path[i] <= min) {    //Wenn noch nicht besucht und Pfad besser >> neues Min setzen
                min = path[i];
                min_index = i;
            }
        return min_index;
    }

    static void dijkstraAlgo(ArrayList<Integer>[] graph, int sourceNode)  {
        path[sourceNode] = 0;
        for (int i = 0; i < numNodes - 1; i++) {
            int shortestNode = getMinPath();
            visited[shortestNode] = true;

            for (int j = 0; j < numNodes; j++)
                if (!visited[j] &&
                        graph[shortestNode].get(j) != 0 &&
                        path[shortestNode] != Integer.MAX_VALUE &&
                        path[shortestNode] + graph[shortestNode].get(j) < path[j]) {
                        path[j] = path[shortestNode] + graph[shortestNode].get(j);
                    outputMatrix[shortestNode][j] = path[j];
                }

        }
    }
}