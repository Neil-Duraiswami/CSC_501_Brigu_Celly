

import java.util.Scanner;

public class PrimsAlgorithm {
   
    public static int primMST(char[] vertices, int[][] graph) {
        int numVertices = vertices.length;
        int[] parent = new int[numVertices];
        int[] key = new int[numVertices];
        boolean[] mstSet = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < numVertices - 1; count++) {
            int u = minKey(key, mstSet);
            mstSet[u] = true;

            for (int v = 0; v < numVertices; v++) {
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        System.out.println("Minimum Spanning Tree:");
        int totalWeight = 0;

        for (int i = 1; i < numVertices; i++) {
            int u = parent[i];
            int v = i;
            int weight = graph[u][v];
            totalWeight += weight;
            System.out.println(vertices[u] + " - " + vertices[v] + ": " + weight);
        }

        return totalWeight;
    }

    public static int minKey(int[] key, boolean[] mstSet) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < key.length; v++) {
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    public static int indexOf(char[] arr, char target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of vertices: ");
        int numVertices = scanner.nextInt();
        scanner.nextLine(); 

        char[] vertices = new char[numVertices];
        int[][] graph = new int[numVertices][numVertices];

        // Initialize the graph with maximum values
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                graph[i][j] = Integer.MAX_VALUE;
            }
        }

        // Adding vertices
        for (int i = 0; i < numVertices; i++) {
            System.out.print("Enter vertex's name " + (i + 1) + ": ");
            vertices[i] = scanner.next().charAt(0);
        }

        System.out.print("Enter the number of edges: ");
        int numEdges = scanner.nextInt();
        scanner.nextLine(); 

        
        System.out.println("Enter edges (e.g., 'A B' for an edge between A and B):");

        
        for (int i = 0; i < numEdges; i++) {
            System.out.print("Edge " + (i + 1) + ": ");
            String edgeInput = scanner.nextLine().trim(); 

            
            if (edgeInput.isEmpty()) {
                System.out.println("Empty input line. Please re-enter.");
                i--;
                continue;
            }

            String[] edgeParts = edgeInput.split(" ");
            if (edgeParts.length != 2) {
                System.out.println("Invalid input format. Please use 'A B' format.");
                i--;
                continue;
            }

            char vertex1 = edgeParts[0].charAt(0);
            char vertex2 = edgeParts[1].charAt(0);

            int index1 = indexOf(vertices, vertex1);
            int index2 = indexOf(vertices, vertex2);

            if (index1 != -1 && index2 != -1) {
                System.out.print("Enter weight for the edge " + vertex1 + " - " + vertex2 + ": ");
                int weight = scanner.nextInt();
                graph[index1][index2] = weight;
                graph[index2][index1] = weight;
                scanner.nextLine(); 
            } else {
                System.out.println("Invalid vertices. Edge not added.");
            }
        }

        // Run Prim's algorithm 
        int totalWeight = primMST(vertices, graph);

        System.out.println("Total Weight of MST: " + totalWeight);

        scanner.close();
    }
}

/*Output:
**Test 1:A graph where all edges have unique weights.

Enter the number of vertices: 5
Enter vertex's name  1: A
Enter vertex's name  2: B
Enter vertex's name  3: C
Enter vertex's name  4: D
Enter vertex's name  5: E
Enter the number of edges: 7
Enter edges (e.g., 'A B' for an edge between A and B):
Edge 1: A B
Enter weight for the edge A - B: 2
Edge 2: A C
Enter weight for the edge A - C: 3
Edge 3: B C
Enter weight for the edge B - C: 4
Edge 4: B D
Enter weight for the edge B - D: 1
Edge 5: C D
Enter weight for the edge C - D: 5
Edge 6: C E
Enter weight for the edge C - E: 2
Edge 7: D E
Enter weight for the edge D - E: 3
Minimum Spanning Tree:
A - B: 2
B - D: 1
C - A: 3
C - E: 2
Total Weight of MST: 8

**Test 2:A graph where some edges have the same weight.

Enter the number of vertices: 6
Enter vertex's name  1: A
Enter vertex's name  2: B
Enter vertex's name  3: C
Enter vertex's name  4: D
Enter vertex's name  5: E
Enter vertex's name  6: F
Enter the number of edges: 10
Enter edges (e.g., 'A B' for an edge between A and B):
Edge 1: A B
Enter weight for the edge A - B: 2
Edge 2: A C
Enter weight for the edge A - C: 3
Edge 3: B C
Enter weight for the edge B - C: 2
Edge 4: B D
Enter weight for the edge B - D: 1
Edge 5: C D
Enter weight for the edge C - D: 3
Edge 6: C E
Enter weight for the edge C - E: 4
Edge 7: D E
Enter weight for the edge D - E: 2
Edge 8: D F
Enter weight for the edge D - F: 1
Edge 9: E F
Enter weight for the edge E - F: 2
Edge 10: A F
Enter weight for the edge A - F: 4
Minimum Spanning Tree:
B - D: 1
D - F: 1
E - D: 2
A - B: 2
C - B: 2
Total Weight of MST: 8

**Test 3:A graph that's not fully connected.

Enter the number of vertices: 5
Enter vertex's name  1: A
Enter vertex's name  2: B
Enter vertex's name  3: C
Enter vertex's name  4: D
Enter vertex's name  5: E
Enter the number of edges: 6
Enter edges (e.g., 'A B' for an edge between A and B):
Edge 1: A B
Enter weight for the edge A - B: 2
Edge 2: A C
Enter weight for the edge A - C: 3
Edge 3: B C
Enter weight for the edge B - C: 2
Edge 4: B D
Enter weight for the edge B - D: 1
Edge 5: C D
Enter weight for the edge C - D: 3
Edge 6: E D
Enter weight for the edge E - D: 2
Minimum Spanning Tree:
B - D: 1
A - B: 2
C - B: 2
E - D: 2
Total Weight of MST: 7

 */





