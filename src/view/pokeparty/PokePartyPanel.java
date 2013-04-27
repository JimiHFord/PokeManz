/**
 * 
 */
package view.pokeparty;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
public class PokePartyPanel extends JPanel {

	private DataFetch df;

	private GridBagConstraints c;

	public PokePartyPanel(DataFetch df) {
		super(new GridBagLayout());
		this.df = df;
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
}
