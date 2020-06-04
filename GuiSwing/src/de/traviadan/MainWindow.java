package de.traviadan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MainWindow extends JFrame implements WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainWindow() {
		super();
		this.init();
	}
	
	public MainWindow(String title) {
		super(title);
		this.init();
	}
	
	private void init() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		final JTextField tf1=new JTextField();  
	    tf1.setBounds(50, 50, 150, 20);  
	    add(tf1);  
	    
	    final JButton b1 = new JButton("Click Here");  
	    b1.setBounds(50, 100, 120, 30);  
	    b1.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	            tf1.setText("Button clicked");  
	        }  
	    }); 
	    add(b1);
	    
	    final JButton b2 = new JButton("Show options");  
	    b2.setBounds(50, 200, 120, 30);  
	    b2.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	            showOptionDialog();  
	        }  
	    }); 
	    add(b2);
	    
	    setSize(400, 400);  
	    setLayout(null);  
	    addWindowListener(this);
	    setVisible(true);
	}

	private void showOptionDialog() {
	    final JCheckBox check = new JCheckBox("Tick me");
        final Object[] options = {'e', 2, 3.14, 4, 5, "TURTLES!", check};
        int x = JOptionPane.showOptionDialog(
        		this,
        		"So many options using Object[]",
                "Don't forget to Tick it!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null, options, options[0]);

        if (check.isSelected() && x != -1) {
            System.out.println("Your choice was " + options[x]);
        } else {
            System.out.println(":( no choice");
        }
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		int choice = JOptionPane.showOptionDialog(
				this,
				"Wirklich schliessen?",
				"Quit?",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, null, null);
		if(choice == JOptionPane.YES_OPTION) {
			dispose();
		}
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
