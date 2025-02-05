import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuessingGame extends JFrame {
	private JTextField txtGuess;
	private JLabel lblOutput;
	private int theNumber;
	
	public void checkGuess() {
		String guessText = txtGuess.getText();
		String message = "";
		try {
			int guess = Integer.parseInt(guessText);
			if (guess < theNumber)
				message = guess + " is too low. Try again.";
			else if (guess > theNumber)
				message = guess + " is too high. Try again.";
			else {
				message = guess + " is correct. You win.";
				txtGuess.setText("");
				newGame();
			}			
		} catch (Exception e) {
			message = "Enter a whole number between 1 and 100.";
		} finally {
			lblOutput.setText(message);
			txtGuess.requestFocus();
			txtGuess.selectAll();
		}
	}
	
	public void newGame() {
		theNumber = (int)(Math.random() * 100 + 1);
	}
	
	
	public GuessingGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Hi-Lo Guessing Game");
		getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("Hi-Lo Guessing Game");
		lblTitle.setForeground(Color.GREEN);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblTitle.setBounds(10, 11, 414, 35);
		getContentPane().add(lblTitle);
		
		JLabel lblRules = new JLabel("Guess a number between 1 and 100:");
		lblRules.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRules.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblRules.setBounds(10, 71, 290, 27);
		getContentPane().add(lblRules);
		
		txtGuess = new JTextField();
		txtGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkGuess();
			}
		});
		txtGuess.setHorizontalAlignment(SwingConstants.RIGHT);
		txtGuess.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		txtGuess.setBounds(307, 70, 38, 29);
		getContentPane().add(txtGuess);
		txtGuess.setColumns(10);
		
		JButton btnGuess = new JButton("Guess!");
		btnGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkGuess();
			}
		});
		btnGuess.setBackground(Color.BLACK);
		btnGuess.setForeground(Color.GREEN);
		btnGuess.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
		btnGuess.setBounds(118, 123, 198, 56);
		getContentPane().add(btnGuess);
		
		lblOutput = new JLabel("Write a number, and press \"Guess!\"");
		lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutput.setBounds(10, 204, 414, 14);
		getContentPane().add(lblOutput);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GuessingGame theGame = new GuessingGame();
		theGame.newGame();
		theGame.setSize(new Dimension(450, 300));
		theGame.setVisible(true);
	}
}
