/**
 * 
 */
package view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class PokeNoEditTableModel extends AbstractTableModel {

	private String[] headers = { "ID", "Name", "Type 1", "Type 2" };
	private ArrayList<ArrayList<String>> list;
	
	public PokeNoEditTableModel(ArrayList<ArrayList<String>> list) {
		this.list = list;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return list.size() == 0 ? 0 : list.get(0).size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		return list.get(rowIndex).get(columnIndex);
	}

	public String getColumnName(int col) {
		return headers[col];
	}
	
}
