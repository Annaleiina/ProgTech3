package Algorithmen;

import java.util.ArrayList;

public class Prim {
    int parentNode = 0;
    static ArrayList<Integer> visitedNodes = new ArrayList();
    static int[][] outputMatrix;
    static ArrayList<Integer>[] graphArray;
    static Boolean[] visited;

    public Prim() {
    }

    public int[][] startPrim(ArrayList<Integer>[] graph, int numNodes, int startNode, ArrayList<String> nodeNames) {
        int i;
        int j;
        graphArray = graph;
        visited = new Boolean[graphArray.length];
        outputMatrix = new int[numNodes][numNodes];
        //Graph erstellen und alles unverbundene auf Max setzen:
        for(i = 0; i < graphArray.length; ++i) {
            for(j = 0; j < graphArray.length; ++j) {
                if ((Integer)graphArray[i].get(j) == 0) {
                    graphArray[i].set(j, Integer.MAX_VALUE);
                }
            }
        }
        //Knoten = unbesucht:
        for(i = 0; i < visited.length; ++i) {
            visited[i] = false;
        }

        for(i = 0; i < outputMatrix.length; ++i) {
            for(j = 0; j < outputMatrix.length; ++j) {
                outputMatrix[i][j] = 0;
            }
        }
        //Nachdem der Graph erstellt wurde Prim benutzen:
        this.prim(startNode);
        return outputMatrix;
    }

    public void prim(int start) {
        visited[start] = true;
        visitedNodes.add(start);

        for(int i = 0; i < graphArray.length - 1; ++i) {
            int nextNode = this.getNextVertex();
            if (nextNode != -1) {
                visited[nextNode] = true;
            }
            visitedNodes.add(nextNode);
        }

    }

    public int getNextVertex() {
        int bestNode = -1;
        int bestWeight = Integer.MAX_VALUE;

        for(int i = 0; i < visitedNodes.size(); ++i) {
            for(int curNode = 0; curNode < graphArray.length; ++curNode) {
                int curWeight = (Integer)graphArray[(Integer)visitedNodes.get(i)].get(curNode);
                if (curWeight < bestWeight && !visited[curNode] && !this.isFull(curNode)) {
                    this.parentNode = (Integer)visitedNodes.get(i);
                    bestNode = curNode;
                    bestWeight = curWeight;
                }
            }
        }
        outputMatrix[this.parentNode][bestNode] = bestWeight;
        outputMatrix[bestNode][this.parentNode] = bestWeight;
        return bestNode;
    }

    public boolean isFull(int curNode) {
        boolean isFull = false;
        int currentAmount = 0;

        for(int i = 0; i < outputMatrix.length; ++i) {
            if (outputMatrix[curNode][i] == 1) {
                ++currentAmount;
            }
        }
        if (currentAmount >= 5) {
            isFull = true;
        }
        return isFull;
    }
}
