import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class SecretMessagesGUI extends JFrame {
	private JTextArea txtIn;
	private JTextArea txtOut;
	private JTextField txtKey;
	private JSlider encodeSlider;

	public String encode(String message, int keyVal) {
		String output = "";
		char key = (char) keyVal;
		try {
			if (keyVal >= -25 && keyVal <= 25) {
				key = (char) keyVal;
			}
		} catch (Exception e) {
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
		return output;
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


	public SecretMessagesGUI() {
		setTitle("Secret Message App");
		getContentPane().setLayout(null);

		txtOut = new JTextArea();
		txtOut.setWrapStyleWord(true);
		txtOut.setLineWrap(true);
		txtOut.setBounds(10, 210, 564, 140);
		getContentPane().add(txtOut);

		txtIn = new JTextArea();
		txtIn.setWrapStyleWord(true);
		txtIn.setLineWrap(true);
		txtIn.setBounds(10, 11, 564, 140);
		getContentPane().add(txtIn);

		txtKey = new JTextField();
		txtKey.setHorizontalAlignment(SwingConstants.RIGHT);
		txtKey.setBounds(406, 172, 43, 20);
		getContentPane().add(txtKey);
		txtKey.setColumns(10);

		JButton btnEncode = new JButton("Encode/Decode");
		btnEncode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String message = txtIn.getText();
					int key = Integer.parseInt(txtKey.getText());
					String output = encode(message, key);
					txtOut.setText(output);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Please enter a whole number value.");
					txtKey.requestFocus();
					txtKey.selectAll();
				}
			}
		});
		btnEncode.setBounds(459, 171, 115, 23);
		getContentPane().add(btnEncode);

		encodeSlider = new JSlider();
		encodeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				txtKey.setText("" + encodeSlider.getValue());
				String message = txtIn.getText();
				int key = encodeSlider.getValue();
				txtOut.setText(encode(message, key));
			}
		});
		encodeSlider.setMinorTickSpacing(1);
		encodeSlider.setMajorTickSpacing(13);
		encodeSlider.setPaintTicks(true);
		encodeSlider.setPaintLabels(true);
		encodeSlider.setValue(0);
		encodeSlider.setMinimum(-26);
		encodeSlider.setMaximum(26);
		encodeSlider.setBounds(10, 162, 386, 37);
		getContentPane().add(encodeSlider);
	}

	public static void main(String[] args) {
		SecretMessagesGUI theApp = new SecretMessagesGUI();
		theApp.setSize(new java.awt.Dimension(600,400));
		theApp.setVisible(true);
	}
}
