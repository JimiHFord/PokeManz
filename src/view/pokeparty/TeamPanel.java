/**
 * 
 */
package view.pokeparty;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class TeamPanel extends JPanel {

	private JButton back;
	private JPanel right;
	private PokePartyPanel parent;
	private DataFetch df;
	private JTable table;
	@SuppressWarnings("unused")
	private String user;
	private JScrollPane jsp;
	
	
	public TeamPanel(PokePartyPanel p) {
		super(new BorderLayout());
		this.parent = p;
		this.df = DataFetch.getInstance();
		this.table = new JTable();
		this.table.getTableHeader().setReorderingAllowed(false);
		this.jsp = new JScrollPane(table);
		this.right = new JPanel(new BorderLayout());
		this.back = new JButton("Back");
		this.initComponents();
		this.fillComponents();
	}

	private void initComponents() {
		this.back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.show(PokePartyPanel.MANAGE_TRAINERS_PANEL);
			}
			
		});
	}
	
	private void fillComponents() {
		right.add(jsp, BorderLayout.SOUTH);
		right.add(back, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);
	}
	
	public void setUser(String user) {
		this.user = user;
		this.table.setModel(df.getTeamPanelModel(user));
	}
}
