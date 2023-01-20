package Algorithmen;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class FordFulkerson {
    static int numNodes;
    static ArrayList<Integer>[] graph;
    static int[][] residualGraph;
    static int[] parentNode;

    public static int[][] startFordFulker(ArrayList<Integer>[] graphArray, int numNodes, int sourceNode, int sinkNode) {
        FordFulkerson.numNodes = numNodes;
        graph = graphArray;

        residualGraph = new int[numNodes][numNodes];
        //ResidualGraph in
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                residualGraph[i][j] = graph[i].get(j);
            }
        }
        parentNode = new int[numNodes];

        getFlow(sourceNode, sinkNode);
        return residualGraph;
    }

    public static void getFlow(int sourceNode, int sinkNode) {
        while (pathExistsCheck(residualGraph, sourceNode, sinkNode, parentNode)) {
            int sink = sinkNode;
            int capacity = Integer.MAX_VALUE;
            //Maximale Kapazität finden:
            while (sink != sourceNode) {                //Solange Quelle ungleich Senke:
                int curNode = parentNode[sink];         //Nächsten Knoten auswählen, und prüfen obs passt
                capacity = Math.min(capacity, residualGraph[curNode][sink]);
                sink = curNode;                         //Wenn ja: Neuen Knoten gefunden
            }

            sink = sinkNode;
            while (sink != sourceNode) {
                int currentNode = parentNode[sink];
                residualGraph[currentNode][sink] -= capacity;
                residualGraph[sink][currentNode] += capacity;
                sink = currentNode;                     //Senke wird "verlegt"; das vorher braucht man ja nicht mehr
            }
        }
    }

    public static boolean pathExistsCheck(int[][] residualGraph, int source, int sink, int[] parentNode) {
        boolean pathExists;
        boolean[] visited = new boolean[numNodes];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        parentNode[source] = -1;
        visited[source] = true;

        while (!queue.isEmpty()) {              //Solange queue nicht leer ist:
            int currentNode = queue.poll();
            for (int i = 0; i < numNodes; i++) {
                if (visited[i] == false && residualGraph[currentNode][i] > 0) {
                    queue.add(i);
                    visited[i] = true;
                    parentNode[i] = currentNode;
                }
            }
        }
        pathExists = visited[sink];
        return pathExists;

    }
}