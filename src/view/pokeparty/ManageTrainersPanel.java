/**
 * 
 */
package view.pokeparty;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
public class ManageTrainersPanel extends JScrollPane {

	public ManageTrainersPanel(DataFetch df) {
		super(new JTable(df.getMainTrainerTableModel()));
	}

}
