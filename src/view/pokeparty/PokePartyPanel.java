/**
 * 
 */
package view.pokeparty;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;

import view.PokeListener;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
@SuppressWarnings({ "serial", "unused" })
public class PokePartyPanel extends JPanel implements PokeListener {

	public static final String TEAM_PANEL = "team";
	public static final String MANAGE_TRAINERS_PANEL = "manage trainers";
	public static final String PASS_PANEL = "authenticate";
	
//	private DataFetch df;

	private GridBagConstraints c;
	private JPanel cards;
	private TeamPanel teamPanel;
	private ManageTrainersPanel manageTrainersPanel;
	private PokeSignIn pass;
	private CardLayout cardlayout;
	
	public PokePartyPanel() {
		super(new BorderLayout());
//		this.df = DataFetch.getInstance();
		this.cardlayout = new CardLayout();
		this.cards = new JPanel(cardlayout);
		fillComponents();
		initializeActions();
		act(MANAGE_TRAINERS_PANEL, MANAGE_TRAINERS_PANEL);
	}

//	public void showTeamPanel(Integer user) {
//		this.teamPanel.setUser(user);
//		showView(TEAM_PANEL);
//	}

	private void fillComponents() {
//		this.setBorder(new CompoundBorder(new EmptyBorder(4,0,0,0),BorderFactory.createLineBorder(Color.white)));
		cards.add(teamPanel = new TeamPanel(this), TEAM_PANEL);
		cards.add(manageTrainersPanel = new ManageTrainersPanel(this), MANAGE_TRAINERS_PANEL);
		cards.add(pass = new PokeSignIn(this), PASS_PANEL);
		this.add(cards, BorderLayout.CENTER);
	}

	private void initializeActions() {
		
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

	public void showIndividualTrainerView(Integer user) {
		this.teamPanel.setUser(user);
		this.cardlayout.show(cards, TEAM_PANEL);
	}
	

	public void act(String command, String argument) {
		switch(command) {
		case MANAGE_TRAINERS_PANEL:
			pass.clearText();
			manageTrainersPanel.updateTable();
			break;
			
		case PASS_PANEL:
			try {
			this.pass.setID(Integer.parseInt(argument));
			this.pass.pass.requestFocus();
			} catch (NumberFormatException e) {}
			break;
			
		case TEAM_PANEL:
			try {
			this.teamPanel.setUser(Integer.parseInt(argument));
			} catch (NumberFormatException e) {}
			break;
		}
		this.cardlayout.show(cards, command);
	}
	


}


