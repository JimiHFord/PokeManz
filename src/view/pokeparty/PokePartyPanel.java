/**
 * 
 */
package view.pokeparty;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import view.GUIEntryPoint;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class PokePartyPanel extends JPanel {

	public static final String TEAM_PANEL = "team";
	public static final String MANAGE_TRAINERS_PANEL = "manage trainers";
	
	private DataFetch df;

	private GridBagConstraints c;
	private JPanel cards;
	private CardLayout cardlayout;
	
	
	public PokePartyPanel(DataFetch df) {
		super(new BorderLayout());
		this.df = df;
		this.cardlayout = new CardLayout();
		this.cards = new JPanel(cardlayout);
		fillComponents();
		show(MANAGE_TRAINERS_PANEL);
	}


	private void fillComponents() {
//		this.setBorder(new CompoundBorder(new EmptyBorder(4,0,0,0),BorderFactory.createLineBorder(Color.white)));
		cards.add(new TeamPanel(df), TEAM_PANEL);
		cards.add(new ManageTrainersPanel(df), MANAGE_TRAINERS_PANEL);
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

	
	
	public void show(String key) {
		cardlayout.show(cards, key);
	}
	
	
	private class TeamPanel extends JPanel {
		private TeamPanel(DataFetch df) {
		    // It creates and displays the table
			
		}
	}

}


