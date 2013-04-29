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
public class ExplicitPartyScrollPane extends JScrollPane {

	private DataFetch df;
	private JTable table;
	
	
	public ExplicitPartyScrollPane(DataFetch df, String user) {
		this(df, null, user);
	}
	
	public ExplicitPartyScrollPane(DataFetch df, JTable table, String user) {
		super(table = new JTable(df.getExplicitPartyTAbleModel(user)));
		this.table = table;
		this.table.getTableHeader().setReorderingAllowed(false);
		this.df = df;
	}
}
