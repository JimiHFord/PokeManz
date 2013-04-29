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
	private String user;
	
	
	public TeamPanel(DataFetch df) {
		this.df = df;
	}

	public void setUser(String user) {
		this.user = user;		
	}
	
	
}
