package de.traviadan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import java.io.InputStreamReader;

public class MainWindow extends JFrame implements WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPopupMenu popupMenu;
	private List<BufferedImage> cards = new ArrayList<BufferedImage>();
	
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
		
		try {
			this.loadCards();
		} catch (IOException e) {
			System.out.println("Fehler beim Laden des Kartendecks: " + e.getMessage());
		}
		
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
	    
	    this.createMenuBar();
	    this.createPopupMenu();
	    
	    JLabel card = new JLabel(new ImageIcon(this.cards.get(0)));
	    card.setBounds(200, 300, 67, 100);
	    add(card);
	    
	    // Size of the window
	    setSize(800, 650);
	    
	    // Locate Window in the middle of the screen
	    setLocationRelativeTo(null);
	    
	    // set layout
	    setLayout(null);
	    
	    // Listen to window events
	    addWindowListener(this);
	    
	    // Show window
	    setVisible(true);
	    
	    this.loadFile();
	}

	private void loadCards() throws IOException {
        this.cards.add(ImageIO.read(getClass().getResource("/de/traviadan/resources/card_a_s.png")));
	}
	
	private void loadFile() {
		InputStream input = getClass().getResourceAsStream("/de/traviadan/resources/test.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (IOException e) {
			System.out.println("I/O Exception: " + e);
		}
		System.out.println(sb.toString());
	}
	
	private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        BufferedImage img = null;
        try {
			img = ImageIO.read(getClass().getResource("/de/traviadan/resources/exit.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ImageIcon exitIcon = new ImageIcon(img);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit", exitIcon);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener((event) -> System.exit(0));

        fileMenu.add(eMenuItem);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }
	
	private void createPopupMenu() {

        popupMenu = new JPopupMenu();

        JMenuItem maximizeMenuItem = new JMenuItem("Maximize");
        maximizeMenuItem.addActionListener((e) -> {
            if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                maximizeMenuItem.setEnabled(false);
            }
        });

        popupMenu.add(maximizeMenuItem);

        JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener((e) -> System.exit(0));

        popupMenu.add(quitMenuItem);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {

                if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    maximizeMenuItem.setEnabled(true);
                }

                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
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
