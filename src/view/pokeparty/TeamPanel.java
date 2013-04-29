/**
 * 
 */
package view.pokeparty;

import java.awt.BorderLayout;
import java.awt.Graphics;

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
	private String user = "Trudy";
	private ExplicitPartyScrollPane partyScrollPane;
	
	public TeamPanel(DataFetch df) {
		super(new BorderLayout());
		this.df = df;
//		this.user = "";
		this.partyScrollPane = new ExplicitPartyScrollPane();
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
		//this.partyScrollPane = new ExplicitPartyScrollPane(df,user);
		partyScrollPane.addNewData(df, user);
		//this.repaint();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
