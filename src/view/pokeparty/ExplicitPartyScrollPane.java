/**
 * 
 */
package view.pokeparty;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import data.DataFetch;

/**
 * @author JimiHFord
 *
 */
@SuppressWarnings("serial")
public class ExplicitPartyScrollPane extends JScrollPane {

	private DataFetch df;
	private JTable table;
	private String user;
	
	public ExplicitPartyScrollPane(DataFetch df, String user) {
		this(df, null, user);
	}
	
	public ExplicitPartyScrollPane() {
		this(null, null);
	}
	
	public ExplicitPartyScrollPane(DataFetch df, JTable table, String user) {
		super(table = new JTable());
		this.table = table;
		this.user = user;
		this.table.getTableHeader().setReorderingAllowed(false);
		this.df = df;
	}
	
	public void addNewData(DataFetch df, String user) {
		this.df = df;
		this.user = user;
		refresh();
	}
	
	public void refresh() {
		this.table.setModel(df.getExplicitPartyTAbleModel(user));
	}
}
