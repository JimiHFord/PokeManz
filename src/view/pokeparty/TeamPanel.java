/**
 * 
 */
package view.pokeparty;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import view.PokeListener;
import view.PokeSearchPanel;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class TeamPanel extends JPanel implements PokeListener {

	private JButton back;
	private JButton delete;
	private JPanel buttonPanel;
	private JPanel right;
	private JPanel left;
	private PokeListener listener;
	private PokeSearchPanel search;
	private DataFetch df;
	private JTable table;
	@SuppressWarnings("unused")
	private Integer user;
	private JScrollPane jsp;
	
	
	public TeamPanel(PokeListener p) {
		super(new BorderLayout());
		this.buttonPanel = new JPanel(new GridLayout(2,1));
		this.delete = new JButton("Remove Selected");
		this.listener = p;
		this.df = DataFetch.getInstance();
		this.table = new JTable();
		this.table.getTableHeader().setReorderingAllowed(false);
		this.jsp = new JScrollPane(table);
		this.right = new JPanel(new BorderLayout());
		this.left = new JPanel(new BorderLayout());
		this.search = new PokeSearchPanel(this);
		this.back = new JButton("Back");
		this.initComponents();
		this.fillComponents();
	}

	private void initComponents() {
		this.back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.showView(PokePartyPanel.MANAGE_TRAINERS_PANEL);
			}
			
		});
		
		this.search.getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					if(row >= 0) {
						Integer national_id = (Integer)target.getValueAt(row, 0);
						df.addPokemonToTrainer(national_id, user);
						System.err.println("National ID: " + national_id);
						setUser(user);
					}
				}
			}
		});
		
		this.delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				Integer party_id = (Integer)table.getValueAt(row, 0);
				df.removePartyEntry(party_id);
				setUser(user);
			}
		});
	}
	
	private void fillComponents() {

		right.add(buttonPanel, BorderLayout.CENTER);
		right.add(jsp, BorderLayout.SOUTH);
		right.add(new JPanel(), BorderLayout.NORTH);
		this.buttonPanel.add(back);
		this.buttonPanel.add(delete);
		this.left.add(search, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);
		this.add(left, BorderLayout.WEST);
		
	}
	
	public void setUser(Integer user) {
		this.user = user;
		this.table.setModel(df.getTeamPanelModel(user));
		
	}

	@Override
	public void showView(String view) {
		return;
	}

	@Override
	public void showIndividualTrainerView(Integer user) {
		// TODO Auto-generated method stub
		
	}
}
