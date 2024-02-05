package CSC_501_01_Assignments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Naive_salesman {

    public static int[] findShortestRoute(int[][] M) {
        int n = M.length;
        int startCity = 0;
        List<Integer> otherCities = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (i != startCity) {
                otherCities.add(i);
            }
        }

        int minDistance = Integer.MAX_VALUE;
        int[] bestRoute = new int[n + 1]; 

        for (List<Integer> permutation : generatePermutations(otherCities)) {
            int[] route = new int[n + 1]; 
            route[0] = startCity;

            for (int i = 0; i < n - 1; i++) {
                route[i + 1] = permutation.get(i);
            }
            
            route[n] = startCity; // Return to the starting city

            int currentDistance = calculateTotalDistance(route, M);

            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                bestRoute = route.clone();
            }
        }

        return bestRoute;
    }

    public static int calculateTotalDistance(int[] route, int[][] M) {
        int totalDistance = 0;

        for (int i = 0; i < route.length - 1; i++) {
            int currentCity = route[i];
            int nextCity = route[i + 1];
            totalDistance += M[currentCity][nextCity];
        }

        return totalDistance;
    }

    public static List<List<Integer>> generatePermutations(List<Integer> elements) {
        List<List<Integer>> permutations = new ArrayList<>();
        generatePermutationsHelper(elements, new ArrayList<>(), permutations);
        return permutations;
    }

    private static void generatePermutationsHelper(List<Integer> elements, List<Integer> permutation, List<List<Integer>> permutations) {
        if (elements.isEmpty()) {
            permutations.add(new ArrayList<>(permutation));
            return;
        }

        for (int i = 0; i < elements.size(); i++) {
            int element = elements.get(i);
            permutation.add(element);
            List<Integer> remaining = new ArrayList<>(elements);
            remaining.remove(i);
            generatePermutationsHelper(remaining, permutation, permutations);
            permutation.remove(permutation.size() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of cities: ");
        int n = scanner.nextInt();
        int[][] M = new int[n][n];

        System.out.println("Enter the distance matrix (M[i][j]):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                M[i][j] = scanner.nextInt();
            }
        }

        int[] shortestRoute = findShortestRoute(M);
        System.out.println("Shortest Route: " + Arrays.toString(shortestRoute));
        System.out.println("Shortest Distance: " + calculateTotalDistance(shortestRoute, M));

        scanner.close();
    }
}

/*output:
Enter the number of cities: 4
Enter the distance matrix (M[i][j]):
0 10 15 20
10 0 35 25
15 35 0 30
20 25 30 0
Shortest Route: [0, 1, 3, 2, 0]
Shortest Distance: 80 */
