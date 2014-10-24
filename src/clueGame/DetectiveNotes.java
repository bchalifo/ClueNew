package clueGame;

import javax.swing.*;

public class DetectiveNotes extends JDialog {
	
	public DetectiveNotes() {
		// stuff
		setTitle("Detective Notes");
		setSize(300, 200);
		// create components
		JLabel testLabel = new JLabel("TEST");
		JTextField testField = new JTextField("test");
		add(testLabel);
		add(testField);
	}
}
