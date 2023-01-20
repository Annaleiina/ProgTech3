//Anfang unser Zeug
import Algorithmen.BipartiteMatching;
import Algorithmen.Dijkstra;
import Algorithmen.FordFulkerson;
import Algorithmen.Hierholzer;
import Algorithmen.Prim;
//Ende unser Zeug
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Planungstool{
    static Scanner scan;
    static int numNodes;
    static String path;
    static List<String> input;
    static ArrayList<String> nodeRow;
    static ArrayList<String> nodeColumn;
    static ArrayList<Integer>[] matrix;
    static int[][] resultMatrix;

    public static void main(String[] args) {
        System.out.println("Problem waehlen:\nBitte eine Zahl von 1-7 angeben!");
        System.out.println("1: Strassenbau");
        System.out.println("2: Wasserversorgung");
        System.out.println("3: Stromversorgung");
        System.out.println("4: Historische Funde");
        System.out.println("5: Einladungen verteilen");
        System.out.println("6: Ampelverkehr");
        System.out.println("7: Mitarbeiterkompetenzen\n\n");
        int problem = scan.nextInt();

        System.out.println("Bitte den Dateipfad angeben:");
        System.out.println("Achtung! Datei muss im Projektordner liegen.");
        path = scan.next();
        try {
            input = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Bitte Anzahl der Knoten angeben:");
        numNodes = scan.nextInt();

        String strNew = input.get(0).replace("  ", "");
        nodeRow = new ArrayList(Arrays.asList(strNew.split(" ")));
        matrix = new ArrayList[numNodes];

        int i;
        for(i = 0; i < numNodes; ++i) {
            matrix[i] = new ArrayList();
        }

        for(i = 0; i < numNodes; ++i) {
            ArrayList<String> line = new ArrayList(Arrays.asList(input.get(i + 1).split(" ")));
            nodeColumn.add(line.remove(0));
            int[] arr = line.stream().mapToInt((j) -> {
                return Integer.parseInt(j);
            }).toArray();

            for(int k = 0; k < numNodes; ++k) {
                matrix[i].add(arr[k]);
            }
        }

        switch (problem) {
            case 1 -> {
                System.out.println("Strassenbau ausgewaehlt!");
                startDijkstra();
                break;
            }
            case 2 -> {
                System.out.println("Wasserversorgung ausgewaehlt!");
                startFordFulkerson();
                break;
            }
            case 3 -> {
                System.out.println("Stromversorgung ausgewaehlt!");
                startPrim();
                break;
            }
            case 4 -> {
                System.out.println("Historische Funde ausgewaehlt!");
                startDijkstra();
                break;
            }
            case 5 -> {
                System.out.println("Einladungen verteilen ausgewaehlt!");
                startHierholzer();
                break;
            }
            case 6 -> {
                System.out.println("Ampelverkehr ausgewaehlt!");
                startFordFulkerson();
                break;
            }
            case 7 -> {
                System.out.println("Mitarbeiterkompetenzen ausgewaehlt!");
                startBipartiteMatching();
                break;
            }
            default -> {
                System.out.println("Ungueltige Zahl!");
                break;
            }
        }
        printOutput();
    }

    public static void startDijkstra() {
        System.out.println("Bitte den Index des Startknoten angeben: ");
        System.out.println("Achtung! Knoten starten mit dem Index 0");
        int startNode = scan.nextInt();
        resultMatrix = Dijkstra.startDijkstra(matrix, numNodes, startNode);
    }

    public static void startFordFulkerson() {
        System.out.println("Bitte den Index der Quelle angeben: ");
        System.out.println("Achtung! Knoten starten mit dem Index 0");
        int source = scan.nextInt();
        System.out.println("Bitte den Index der Senke angeben: ");
        int destination = scan.nextInt();
        //FordFulkerson fordFulker = new FordFulkerson();
        resultMatrix= FordFulkerson.startFordFulker(matrix, numNodes, source, destination);
    }

    public static void startPrim() {
        Prim prim = new Prim();
        resultMatrix = prim.startPrim(matrix, numNodes, 0, nodeRow);
    }

    public static void startHierholzer() {
        resultMatrix = Hierholzer.startHierholzer(matrix, numNodes);
    }

    public static void startBipartiteMatching() {
        String[] names = nodeRow.toArray(new String[0]);
        String[] jobs = nodeColumn.toArray(new String[1]);
        resultMatrix = BipartiteMatching.start(numNodes, names, jobs, matrix);
    }

    public static void printOutput() {
        System.out.println("\n\nErgebnismatrix:");

        int i;
        for(i = 0; i < nodeRow.size(); ++i) {
            if(i < 1){
                System.out.print("  ");                         //Nicht so elegant, macht aber was es soll...
            }
            System.out.print((String) nodeRow.get(i) + " ");
        }

        System.out.println();

        for(i = 0; i < resultMatrix.length; ++i) {
            System.out.print(nodeColumn.get(i) + " ");

            for(int j = 0; j < resultMatrix.length; ++j) {
                System.out.print(resultMatrix[i][j] + " ");
            }

            System.out.println();
        }

    }

    static {
        scan = new Scanner(System.in);
        nodeColumn = new ArrayList();
    }
}