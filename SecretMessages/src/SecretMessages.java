import java.util.Scanner;

public class SecretMessages {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a message to encode or decode:");
		String message = scan.nextLine();  // Gets the user input
		String output = "";
		char key;
		int keyVal;
		System.out.println("Enter a secret key (-25 to 25): ");
		while (true) {
			try {
				keyVal = Integer.parseInt(scan.nextLine());
				if (keyVal >= -25 && keyVal <= 25) {
					key = (char) keyVal;
					break;
				}
				else {
					System.out.println("The number is out of the set boundary, try again:");
				}
			} catch (Exception e) {
				System.out.println("Invalid input, try again:");
			}
		}

		for (int i = 0; i < message.length(); i++) {
			char input = message.charAt(i);
			message.indexOf(i);
			if (input >= 'A' && input <= 'Z') {
				input = applyKey(input, 'A', 'Z', key, 26);
			}
			else if (input >= 'a' && input <= 'z') {
				input = applyKey(input, 'a', 'z', key, 26);
			}
			else if (input >= '0' && input <= '9') {
				input = applyKey(input, '0', '9', key, 10);
			}
			output += input;
		}
		System.out.println(output);

		scan.close();
	}

	private static char applyKey(char input, char cond1, char cond2, char key, int foo) {
		if (input >= cond1 && input <= cond2) {
			input += key;
			if (input > cond2)
				input -= foo;
			if (input < cond1)
				input += foo;
		}
		return input;
	}

}