package clueGame;

import java.awt.GridLayout;
import javax.swing.*;

public class DetectiveNotes extends JDialog {
	
	public DetectiveNotes() {
		// stuff
		setTitle("Detective Notes");
		setSize(300, 200);
		setLayout(new GridLayout(3, 2));
		// create components
		JLabel testLabel = new JLabel("TEST");
		JTextField testField = new JTextField("test");
		add(testLabel);
		add(testField);
	}
}
