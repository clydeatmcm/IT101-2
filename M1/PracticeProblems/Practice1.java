import java.util.Scanner;

public class Practice1 { // EvenSum
  
    public static void main(String[] args) {
        // 1. Use the Scanner class to accept user input.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int sum = 0;

        // 2. Use a for loop to iterate from 1 to N.
        for (int i = 1; i <= n; i++) {
            // 3. Inside the loop, add only the even numbers to the sum.
            if (i % 2 == 0) {
                sum = sum + i;
            }
        }

        // 4. Print the final result using System.out.println().
        System.out.println("Sum of even numbers from 1 to " + n + " is " + sum);
    }
}
