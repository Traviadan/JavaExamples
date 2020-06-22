package de.traviadan;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

	private Properties props = new Properties();
	private Log log;
	
	public MainWindow() {
		super();
		init();
	}
	
	public MainWindow(String title, Log logger) {
		super(title);
		if (log != null) {
			log = logger;
		} else {
			log = new Log();
			log.lvl = Log.Level.Debug;
			log.out = Log.Out.System;
			log.start();
		}
		loadProperties();
		log.msg("Initialisierung");
		init();
	}
	
	private void init() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    
	    createMenuBar();
	    createPopupMenu();

	    // Create Widgets
	    
	    
	    // set layout
	    setLayout(new BorderLayout(5, 5));
	    // Size of the window
	    setSize(800, 650);
	    // Locate Window in the middle of the screen
	    setLocationRelativeTo(null);
	    // Listen to window events
	    addWindowListener(this);
	    // Show window
	    setVisible(true);
	    
	    log.msg(this.loadFileToString());
	}

	private void createFile(String name) {
		URL resourceUrl = getClass().getResource("/de/traviadan/resources/" + name);
		File file = null;
		try {
			file = new File(resourceUrl.toURI());
		} catch (URISyntaxException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(file != null) {
			try {
				if(file.createNewFile()) {
					System.out.println("File created: " + name);
				} else {
					System.out.println("File already exists: " + name);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	private String loadFileToString() {
		InputStream input = getClass().getResourceAsStream("/de/traviadan/resources/test.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			log.msg("I/O-exception:" + e.getMessage(), Log.Level.Failure);
		}
		return sb.toString();
	}
	
	private void loadProperties() {
		URL resourceUrl = getClass().getResource("/de/traviadan/resources/properties.ini");
		try {
			//Reader reader = new FileReader(new File(resourceUrl.toURI()));
			InputStream input = new FileInputStream(new File(resourceUrl.toURI()));
			this.props.load(input);
			input.close();
		} catch (URISyntaxException e) {
			log.msg("URISyntaxException: " + e.getMessage(), Log.Level.Failure);
		} catch (FileNotFoundException e) {
			log.msg("File not found: " + e.getMessage(), Log.Level.Failure);
		} catch (IOException e) {
			log.msg("I/O-Exception: " + e.getMessage(), Log.Level.Failure);
		}
	}
	
	private void saveProperties() {
		URL resourceUrl = getClass().getResource("/de/traviadan/resources/properties.ini");
		File file = null;
		try {
			file = new File(resourceUrl.toURI());
			OutputStream output = new FileOutputStream(file);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
			if(file != null) {
				try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
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
