package de.traviadan;

import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class DialogWindowClosingListener extends WindowAdapter {
	@Override public void windowClosing( WindowEvent event ) {
		int choice = JOptionPane.showOptionDialog(
				event.getWindow(),
				"Wirklich schliessen?",
				"Quit?",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, null, null);
		if(choice == JOptionPane.YES_OPTION) {
			event.getWindow().dispose();
		}
	}
}
