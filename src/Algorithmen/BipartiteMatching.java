package Algorithmen;

import java.util.ArrayList;

public class BipartiteMatching {
    static int tasks;
    static String[] task;
    static int employees;
    static String[] employee;
    static int[][] outputMatrix;
    static ArrayList<Integer>[] adjMatrix;

    public BipartiteMatching() {
    }

    public static int[][] start(int numNodes, String[] employeeArray, String[] taskArray, ArrayList<Integer>[] matrix) {
        employees = numNodes;
        employee = employeeArray;
        tasks = numNodes;
        task = taskArray;
        adjMatrix = matrix;

        outputMatrix = new int[employeeArray.length][taskArray.length];
        bipartiteMatching();

        return outputMatrix;
    }

    public static void bipartiteMatching() {
        int[] assign = new int[tasks];

        int i;
        for(i = 0; i < tasks; ++i) {
            assign[i] = -1;
        }

        for(i = 0; i < employees; ++i) {
            boolean[] visited = new boolean[tasks];
            match(i, visited, assign);
        }

        for(i = 0; i < employee.length; ++i) {
            for(int j = 0; j < task.length; ++j) {
                if (i == assign[j]) {
                    outputMatrix[i][j] = 1;
                }
            }
        }
    }

    static boolean match(int employee, boolean[] visited, int[] assign) {
        for(int task = 0; task < tasks; ++task) {
            if ((adjMatrix[employee].get(task) == 1) && !visited[task]) {
                visited[task] = true;
                int matchedEmployee = assign[task];
                if (matchedEmployee < 0 || match(matchedEmployee, visited, assign)) {
                    assign[task] = employee;
                    return true;
                }
            }
        }
        return false;
    }
}
