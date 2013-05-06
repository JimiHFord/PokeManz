/**
 * 
 */
package view.pokeparty;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import view.PokeListener;
import view.PokeSearchPanel;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class TeamPanel extends JPanel implements PokeListener {

	private static final String DEFAULT = "new name...";
	private static final String UPDATE_MSG = "processed";
	
	private JButton back;
	private JButton removePokemon;
	private JButton update;
	private JButton deleteAccount;
	private JTextArea nameArea;
	private JPanel buttonPanel;
	private JPanel right;
	private JPanel left;
	private PokeListener listener;
	private PokeSearchPanel search;
	private PokePI pi;
	private DataFetch df;
	private JTable table;
	private Integer ID;
	private String userName;
	private JScrollPane jsp;
	private GridBagConstraints c;
	
	
	public TeamPanel(PokeListener p) {
		super(new BorderLayout());
		this.setFocusable(true);
		this.c = new GridBagConstraints();
		this.buttonPanel = new JPanel(new GridBagLayout());
		this.removePokemon = new JButton("Remove Selected");
		this.deleteAccount = new JButton("Delete Account");
		this.update = new JButton("Update Name");
		this.nameArea = new JTextArea();
		this.listener = p;
		this.pi = new PokePI(this);
		this.df = DataFetch.getInstance();
		this.table = new JTable();
		this.table.getTableHeader().setReorderingAllowed(false);
		this.jsp = new JScrollPane(table);
		this.right = new JPanel(new GridLayout(3,1));
		this.left = new JPanel(new BorderLayout());
		this.search = new PokeSearchPanel(this);
		this.back = new JButton("Sign Out");
		this.initActions();
		this.fillComponents();
	}

	private void initActions() {
		this.back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.act(PokePartyPanel.MANAGE_TRAINERS_PANEL, PokePartyPanel.MANAGE_TRAINERS_PANEL);
			}
			
		});
		this.deleteAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					df.removeTrainer(String.valueOf(ID));
					listener.act(PokePartyPanel.MANAGE_TRAINERS_PANEL, PokePartyPanel.MANAGE_TRAINERS_PANEL);
			}
		});
		this.search.getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					if(row >= 0) {
						Integer national_id = (Integer)target.getValueAt(row, 0);
						df.addPokemonToTrainer(national_id, ID);
//						System.err.println("National ID: " + national_id);
						setUser(ID);
					}
				}
			}
		});
		
		this.removePokemon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row >= 0) {
					Integer party_id = (Integer)table.getValueAt(row, 0);
					df.removePartyEntry(party_id);
					setUser(ID);
				}
			}
		});
		
		this.update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				df.updateTrainerNameWithID(ID, nameArea.getText());
				nameArea.setText(UPDATE_MSG);
				new Thread() {
					@Override
					public void run() {
						try {
							sleep(3000);
						} catch (InterruptedException e) {
							
						}
//						nameArea.setText(userName);
					}
					
				}.start();
				
				setUser(ID);
			}
		});
		
		this.nameArea.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if(nameArea.getText().equals(userName)) {
					nameArea.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(nameArea.getText().equals("")) {
					nameArea.setText(userName);
				}
			}
			
		});
	}
	
	private void fillComponents() {

		this.nameArea.setPreferredSize(new Dimension(110, 25));
		right.add(buttonPanel);
		right.add(new JPanel());//THIS WILL BE THE PIE CHART
		right.add(jsp);
		this.setGridBagConstraints(0, 0, 0, 0, 0, 2, 1);
		this.buttonPanel.add(back, c);
		this.setGridBagConstraints(0, 2, 0, 0, 0, 2, 1);
		this.buttonPanel.add(removePokemon, c);
		this.setGridBagConstraints(1, 0, 0, 0, 0, 1, 1);
		this.buttonPanel.add(update, c);
		this.setGridBagConstraints(1, 1, 0, 0, 0, 3, 1);
		this.buttonPanel.add(nameArea, c);
		this.setGridBagConstraints(2, 0, 0, 0, 0, 2, 1);
		this.buttonPanel.add(deleteAccount, c);
		this.left.add(search, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);
		this.add(left, BorderLayout.WEST);
		
	}
	
	public void setUser(Integer user) {
		this.ID = user;
		this.table.setModel(df.getTeamPanelModel(user));
		this.userName = df.getTrainerNameFromID(user);
		this.nameArea.setText(userName);
		System.out.println(df.getTypesOnTeam(user));
		this.pi.setTypeDataSet(df.getTypesOnTeam(user));
	}

	public void setGridBagConstraints(int row, int col, int fill
			, double weightx, double weighty, int width, int height) {
		c.weightx = weightx;
		c.weighty = weighty;
		c.fill = fill;
		c.gridx = col;
		c.gridy = row;
		c.gridwidth = width;
		c.gridheight = height;
		// Reset the insets each time, we use it a few times so this
		//  will undo any times we do it.
		c.insets = new Insets(0, 0, 0, 0);
	}

	@Override
	public void act(String command, String argument) {
		
	}

	
}
