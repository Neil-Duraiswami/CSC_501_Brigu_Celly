import java.util.Scanner;

public class Knapsack {

	
    public static int solve(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        
//Used the Pseudo code that was provided
        
        int[][] dp = new int[n + 1][capacity + 1]; //  will be the max value of the first i items for a knapsack of capacity w
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i-1][w], values[i-1] + dp[i-1][w - weights[i-1]]);
                } else {
                    dp[i][w] = dp[i-1][w];
                }
            }
        }

        return dp[n][capacity];
    }

    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of items:");
        int n = sc.nextInt();

        int[] weights = new int[n];
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Enter weight for item " + (i + 1) + ":");
            weights[i] = sc.nextInt();
            System.out.println("Enter value for item " + (i + 1) + ":");
            values[i] = sc.nextInt();
        }

        System.out.println("Enter the knapsack capacity:");
        int capacity = sc.nextInt();

        int maxValue = solve(weights, values, capacity);
        System.out.println("Maximum value: " + maxValue);

        sc.close();
    }
}

/*
 * Output:
 *
Test 1:
Enter the number of items:
4
Enter weight for item 1:
1
Enter value for item 1:
10
Enter weight for item 2:
3
Enter value for item 2:
40
Enter weight for item 3:
3
Enter value for item 3:
50
Enter weight for item 4:
5
Enter value for item 4:
70
Enter the knapsack capacity:
8
Maximum value: 120

Test 2:

Enter the number of items:
5
Enter weight for item 1:
2
Enter value for item 1:
3
Enter weight for item 2:
3
Enter value for item 2:
4
Enter weight for item 3:
4
Enter value for item 3:
5
Enter weight for item 4:
5
Enter value for item 4:
6
Enter weight for item 5:
6
Enter value for item 5:
8
Enter the knapsack capacity:
10
Maximum value: 13
 * */

