/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;


//import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


import view.pokedex.PokedexScreen;
import view.pokeparty.PokePartyPanel;

import data.DataFetch;

/**
 * Main JFrame application container
 * @author James Ford
 *
 */
@SuppressWarnings("serial")
public class GUIEntryPoint extends JFrame {

	private static String user;
	private static String pass;

	public static final String TITLE = "PokeMonitor";
	private static final String T1_TITLE = "Home";
	private static final String T2_TITLE = "Pokedex";
	private static final String T3_TITLE = "Pokemetrics";
	private static final String T4_TITLE = "Pokevolve";
	private static final String T5_TITLE = "Pokeparty";
	private static final String T6_TITLE = "Help";
	private final DataFetch df;
	private JTabbedPane jtp;


	public GUIEntryPoint(DataFetch df) throws SQLException {
		this(df,TITLE);
	}

	public GUIEntryPoint(DataFetch df, String title) throws SQLException {
		super(title);
		this.df = df;
		this.df.setListener(this);
		this.df.connectToRIT(user, pass);
		user = null;
		pass = null;
		initComponents();
		fillComponents();
	}

	private void initComponents() {
		jtp = new JTabbedPane();

	}


	public static void showError(String msg, String title) {
		JOptionPane.showMessageDialog(null, msg, title,
				JOptionPane.ERROR_MESSAGE, null);
	}

	private void fillComponents() {
		jtp.addTab(T1_TITLE, new PokeSearchPanel());
		jtp.addTab(T2_TITLE, new PokedexScreen());
		jtp.addTab(T3_TITLE, new JPanel());
		jtp.addTab(T4_TITLE, new JPanel());
		jtp.addTab(T5_TITLE, new PokePartyPanel());
		jtp.addTab(T6_TITLE, new JPanel());
		jtp.setMnemonicAt(0, KeyEvent.VK_1);
		jtp.setMnemonicAt(1, KeyEvent.VK_2);
		jtp.setMnemonicAt(2, KeyEvent.VK_3);
		jtp.setMnemonicAt(3, KeyEvent.VK_4);
		jtp.setMnemonicAt(4, KeyEvent.VK_5);
		jtp.setMnemonicAt(5, KeyEvent.VK_6);
		this.add(jtp, BorderLayout.CENTER);
	}

	protected static void createAndShowGUI() {
		JFrame f = null;
		try {
			f = new GUIEntryPoint(DataFetch.getInstance());
		} catch (SQLException e) {
			showError(e.getMessage(), "SQLException");
		}


		// Setup JFrame deets.
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setPreferredSize(new Dimension(700,600));
		f.pack(); // Pack before setting location (this determines size)

		// Get the current screen's size
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		// Compute and set the location so the frame is centered
		int x = screen.width/2-f.getSize().width/2;
		int y = screen.height/2-f.getSize().height/2;
		f.setLocation(x, y);
		f.setVisible(true);
	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		if (!System.getProperty("java.version").startsWith("1.7")) {
			showError("Please install Java version 7", "Error");
			return;
		}

		// Set the Nimbus look and feel because it's new and cool looking
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// Will be set to default LAF
		}

		if(args.length < 2) {
			showError("Usage: args[0] = username | args[1] = password", "Error");
			return;
		}
		user = args[0];
		pass = args[1];

		Runnable doCreateAndShowGUI = new Runnable() {

			@Override
			public void run() {
				createAndShowGUI();
			}
		};
		SwingUtilities.invokeAndWait(doCreateAndShowGUI);
	}


}
