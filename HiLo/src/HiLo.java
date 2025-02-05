import java.util.Scanner;

public class HiLo {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String playAgain = "";
		do {
			float highNumber = 100;
			int guess = 0;
			int noTries = 0;
			int theNumber = (int)(Math.random() * (float)(highNumber) + 1); // random er mellem 0.0 og 1.0 (teknisk set 0.999) derfor * 100
			// System.out.println(theNumber);
			System.out.println("Guess a number between 1 and " + highNumber + ": ");
			while (guess != theNumber) {
				guess = scan.nextInt();
				if (guess < theNumber) System.out.println(guess + " is too low. Try again."); 
				else if (guess > theNumber) System.out.println(guess + " is too high. Try again.");				
				else {
					System.out.println("That's right!");
					System.out.println("You used " + noTries + " guesses.");
					continue;
				}
				noTries++;
			}
			System.out.println("Would you like to play again (y/n)?");
			playAgain = scan.next();
		} while (playAgain.equalsIgnoreCase("y"));
		scan.close();
	}
}