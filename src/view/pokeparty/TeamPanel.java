/**
 * 
 */
package view.pokeparty;

import javax.swing.JPanel;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class TeamPanel extends JPanel {

	private DataFetch df;
	
	public TeamPanel(DataFetch df) {
		this.df = df;
	}
}
