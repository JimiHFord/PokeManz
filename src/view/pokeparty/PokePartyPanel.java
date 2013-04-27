/**
 * 
 */
package view.pokeparty;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class PokePartyPanel extends JPanel {

	private DataFetch df;

	private GridBagConstraints c;

	public PokePartyPanel(DataFetch df) {
		super(new CardLayout());
		this.df = df;
		initComponents();
	}


	private void initComponents() {
		this.setBorder(new CompoundBorder(new EmptyBorder(4,0,0,0),BorderFactory.createLineBorder(Color.blue)));
	}

	private void setGridBagConstraints(int row, int col, int fill
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
	
	
	
	
	
	
	
	
	
	private class TrainerPanel extends JPanel {
		private TrainerPanel() {
			
		}
	}
	
	
	private class ManageTrainersPanel extends JPanel {
	
	}
}


