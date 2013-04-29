/**
 * 
 */
package view.pokeparty;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class TeamPanel extends JPanel {

	private JPanel right;
	private DataFetch df;
	private String user;
	private ExplicitPartyScrollPane partyScrollPane;
	
	public TeamPanel(DataFetch df) {
		super(new BorderLayout());
		this.df = df;
		this.user = "";
		this.refresh();
		this.right = new JPanel(new BorderLayout());
		this.fillComponents();
	}

	public void fillComponents() {
		right.add(partyScrollPane, BorderLayout.SOUTH);
		this.add(right, BorderLayout.EAST);
	}
	
	public void setUser(String user) {
		this.user = user;
		this.refresh();
	}
	
	public void refresh() {
		this.partyScrollPane = new ExplicitPartyScrollPane(df,user);
	}
	
	
}
