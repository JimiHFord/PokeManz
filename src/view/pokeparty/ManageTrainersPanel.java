/**
 * 
 */
package view.pokeparty;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
public class ManageTrainersPanel extends JScrollPane {

	private DataFetch df;
	private JTable table;
	private PokePartyPanel parent = null;
	
	public ManageTrainersPanel(DataFetch df) {
		this(df, null);
	}
	
	public ManageTrainersPanel(DataFetch df, JTable table) {
		super(table = new JTable(df.getMainTrainerTableModel()));
		this.table = table;
		this.table.getTableHeader().setReorderingAllowed(false);
		this.df = df;
		initComponents();
		actionInitialization();
	}
	
	public ManageTrainersPanel setParent(PokePartyPanel p) {
		this.parent = p;
		return this;
	}
	
	private void initComponents() {
		
	}
	
	public void updateTable() {
		this.table = new JTable(df.getMainTrainerTableModel());
	}
	
	private void actionInitialization() {
		this.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
//					String user = target.
					System.err.println("Row " + row + " selected.");
					String user = (String)target.getValueAt(row,1);
					System.err.println(user);
					parent.showIndividualTrainerView(user);
					//GET RID OF THIS NEXT LINE
					//JUST FOR TESTING!!!!!
					updateTable();
				}
			}
		});
	}

}
