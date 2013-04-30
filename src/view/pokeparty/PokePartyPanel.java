/**
 * 
 */
package view.pokeparty;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
@SuppressWarnings({ "serial", "unused" })
public class PokePartyPanel extends JPanel {

	public static final String TEAM_PANEL = "team";
	public static final String MANAGE_TRAINERS_PANEL = "manage trainers";
	
//	private DataFetch df;

	private GridBagConstraints c;
	private JPanel cards;
	private TeamPanel teamPanel;
	private ManageTrainersPanel manageTrainersPanel;
	private CardLayout cardlayout;
	
	public PokePartyPanel() {
		super(new BorderLayout());
//		this.df = DataFetch.getInstance();
		this.cardlayout = new CardLayout();
		this.cards = new JPanel(cardlayout);
		fillComponents();
		show(MANAGE_TRAINERS_PANEL);
	}

	public void showTeamPanel(String user) {
		this.teamPanel.setUser(user);
		show(TEAM_PANEL);
	}

	private void fillComponents() {
//		this.setBorder(new CompoundBorder(new EmptyBorder(4,0,0,0),BorderFactory.createLineBorder(Color.white)));
		cards.add(teamPanel = new TeamPanel(this), TEAM_PANEL);
		cards.add(manageTrainersPanel = new ManageTrainersPanel(this), MANAGE_TRAINERS_PANEL);
		this.add(cards, BorderLayout.CENTER);
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

	public void showIndividualTrainerView(String user) {
		this.teamPanel.setUser(user);
		this.cardlayout.show(cards, TEAM_PANEL);
	}
	
	
	
	public void show(String key) {
		this.cardlayout.show(cards, key);
	}
	

}


