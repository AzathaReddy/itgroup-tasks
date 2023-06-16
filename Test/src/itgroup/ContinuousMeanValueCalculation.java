package itgroup;

/*Pseudo code
 * 
	1. Initialize sum to 0.0
	2. Initialize count to 0
	3. Display program title and instructions to the user
	4. Read user input
	5. If input is a floating-point number:
	     a. Parse the number
	     b. Add the number to sum
	     c. Increment count by 1
	     d. Calculate the mean as sum divided by count
	     e. Display the mean value
	6. If input is 'x' (or any other condition to end the program):
	     a. Exit the loop
	7. If input is invalid:
	     a. Display an error message
	8. Repeat steps 4-7 until the program is terminated
	9. Display a program end message

 * 
 */

import java.util.Scanner;

public class ContinuousMeanValueCalculation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double sum = 0.0;  // Total sum of entered numbers
        int count = 0;     // Number of entered numbers

        System.out.println("Continuous Mean Value Calculation Program");
        System.out.println("Enter a floating-point number or 'x' to end:");

        while (scanner.hasNext()) {
            if (scanner.hasNextDouble()) {
                double number = scanner.nextDouble();
                sum += number;
                count++;
                double mean = sum / count;
                System.out.println("Mean value: " + mean);
            } else {
                String input = scanner.next();
                if (input.equalsIgnoreCase("x")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a floating-point number or 'x' to end.");
                }
            }
        }

        System.out.println("Program ended.");
        scanner.close();
    }
}

