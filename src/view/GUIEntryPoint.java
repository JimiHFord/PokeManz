/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import view.pokemetrics.PokeCardPanel;
import view.pokemetrics.PokemetricsScreen;
import view.pokeparty.PokePartyPanel;

import data.DataFetch;

/**
 * Main JFrame application container
 * @author James Ford
 *
 */
@SuppressWarnings("serial")
public class GUIEntryPoint extends JFrame implements PokeListener, ActionListener {

	private static String user;
	private static String pass;

	public static final String TITLE = "PokeMonitor";
	private static final String T1_TITLE = "PokeHome";
	private static final String T2_TITLE = "Pokedex";
	private static final String T3_TITLE = "Pokemetrics";
	private static final String T4_TITLE = "Pokevolve";
	private static final String T5_TITLE = "Pokeparty";
	private static final String T6_TITLE = "PokeHelp";
	private final DataFetch df;
	private JTabbedPane jtp;

	private PokeSearchPanel psp;
	private PokedexScreen ps;
	private PokePartyPanel ppp;
	private PokeCardPanel pcp;

	

	public GUIEntryPoint(String title) throws SQLException {
		super(title);
		this.df = DataFetch.getInstance();
		this.df.setListener(this);
		this.df.connectToRIT(user, pass);
		user = null;
		pass = null;
		initComponents();
		fillComponents();
	}

	private void initComponents() {
		jtp = new JTabbedPane();
		pcp = new PokeCardPanel();
		pcp.pgp.metricsBtn.addActionListener(this);
		pcp.pmp.thruBtn.addActionListener(this);
	}


	public static void showError(String msg, String title) {
		JOptionPane.showMessageDialog(null, msg, title,
				JOptionPane.ERROR_MESSAGE, null);
	}

	private void fillComponents() {
		jtp.addTab(T1_TITLE, psp = new PokeSearchPanel(this));
		jtp.addTab(T2_TITLE, ps = new PokedexScreen());
		jtp.addTab(T3_TITLE, pcp);
		jtp.addTab(T4_TITLE, new JPanel());
		jtp.addTab(T5_TITLE, ppp = new PokePartyPanel());
		jtp.addTab(T6_TITLE, new JPanel());
		jtp.setMnemonicAt(0, KeyEvent.VK_1);
		jtp.setMnemonicAt(1, KeyEvent.VK_2);
		jtp.setMnemonicAt(2, KeyEvent.VK_3);
		jtp.setMnemonicAt(3, KeyEvent.VK_4);
		jtp.setMnemonicAt(4, KeyEvent.VK_5);
		jtp.setMnemonicAt(5, KeyEvent.VK_6);
		this.add(jtp, BorderLayout.CENTER);
	}

	

	@Override
	public void showView(String view) {
		if(view.equals(T1_TITLE)) {
			jtp.setSelectedIndex(0);
		} else if (view.equals(T2_TITLE)) {
			jtp.setSelectedIndex(1);
		} else if (view.equals(T3_TITLE)) {
			jtp.setSelectedIndex(2);
		} else if (view.equals(T4_TITLE)) {
			jtp.setSelectedIndex(3);
		} else if (view.equals(T5_TITLE)) {
			jtp.setSelectedIndex(4);
		}
	}

	
	protected static void createAndShowGUI() {
		JFrame f = null;
		try {
			f = new GUIEntryPoint(TITLE);
		} catch (SQLException e) {
			showError(e.getMessage(), "SQLException");
		}


		// Setup JFrame deets.
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setPreferredSize(new Dimension(940,600));
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

	@Override
	public void showIndividualTrainerView(Integer user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == pcp.pgp.metricsBtn){
			((CardLayout)(pcp.getLayout())).show(pcp, PokeCardPanel.METRICS_CARD);
			if(!pcp.pgp.name.getText().equals("")){
				pcp.pmp.updatePokemetrics(pcp.pgp.name.getText());
				pcp.pmp.updatePokemoves(pcp.pgp.name.getText());
			}
		}else if(e.getSource() == pcp.pmp.thruBtn){
			((CardLayout)(pcp.getLayout())).show(pcp, PokeCardPanel.GEN_CARD);
			if(!pcp.pmp.name.getText().equals("")){
				pcp.pgp.updatePokeGenerations(pcp.pmp.name.getText());
			}
		}
		
	}

	@Override
	public void showLogin(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
