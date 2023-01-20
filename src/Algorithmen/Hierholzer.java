package Algorithmen;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Hierholzer{
    static int numNodes;
    static int[][] outputMatrix;
    static ArrayList<Integer>[] adjacencyMatrix;
    static List<Integer> eulerTour = new ArrayList<>();
    static Set<Integer> oddNodeCounter = new HashSet<>();       //Set zum überprüfen ob Graph = Eulergraph

    public static int[][] startHierholzer(ArrayList<Integer>[] matrix, int numNodes){
        Hierholzer.numNodes = numNodes;
        adjacencyMatrix = matrix;
        outputMatrix = new int[numNodes][numNodes];
        if(checkForGraphEligibility())
            tourSearch();
        System.out.println(eulerTour);
        return outputMatrix;
    }

    static void tourSearch() {
        int start;
        //Wenn man Knoten mit geradem Grad hat dort anfangen:
        if (oddNodeCounter.size() == 0) {
            start = 0;
        } else {
            start = oddNodeCounter.iterator().next();
        }
        eulerTour.add(start);
        int current = start;
        while (true) {
            List<Integer> subTour = new ArrayList<>();              //Neue Untertour anlegen
            do {
                for (int i = 0; i < numNodes; i++) {
                    // find a connected node
                    if (adjacencyMatrix[current].get(i) == 1) {
                        adjacencyMatrix[current].set(i, 0);
                        adjacencyMatrix[i].set(current, 0);
                        current = i;
                        break;
                    }
                }
                subTour.add(current);
            } while (current != start);

            eulerTour.addAll(start+1, subTour);             //Untertour zur Eulertour hinzufügen
            start = getNextNode();

            if (start == 0) {
                break;
            }
        }

        for(int i = 0; i < eulerTour.size()-1; i++){
            outputMatrix[eulerTour.get(i)][eulerTour.get(i+1)] = 1;
        }
    }

    public static boolean checkForGraphEligibility(){
        for (int i = 0; i < numNodes; i++) {
            int degree = 0;
            for (int j = 0; j < numNodes; j++) {

                if (adjacencyMatrix[i].get(j) == 1) {
                    degree++;
                }
            }
            if (degree % 2 == 1) {                  //Prüfen ob Knotengrad ungerade
                oddNodeCounter.add(i);
            }
        }

        if (oddNodeCounter.size() > 0) {            //
            System.out.println("Fehler! Es wurde kein Eulergraph gegeben;\nAlgorithmus wurde abgebrochen.");
            return false;
        }
        return true;
    }

    public static int getNextNode() {
        int nextNode = 0;
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (adjacencyMatrix[i].get(j) == 1) {
                    return i;
                }
            }
        }
        return nextNode;
    }
}