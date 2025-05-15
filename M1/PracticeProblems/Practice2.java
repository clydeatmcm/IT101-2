import java.util.Scanner;

public class Practice2 { // ReverseRange
  
    public static void main(String[] args) {
        // 1. Use the Scanner class to accept 2 integer inputs
        Scanner sc = new Scanner(System.in);

        int a = sc.nextInt();
        int b = sc.nextInt();

        System.out.print("Numbers in reverse: ");

        // 2. Use if-else to determine which number is higher
        if (a >= b) {
            // 3. Use a for loop to display numbers from a to b in reverse
            for (int i = a; i >= b; i--) {
                System.out.print(i + " ");
            }
        } else {
            // 4. If b is greater, loop from b to a in reverse
            for (int i = b; i >= a; i--) {
                System.out.print(i + " ");
            }
        }
    }
}
