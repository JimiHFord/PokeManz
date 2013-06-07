/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import view.pokedex.PokedexScreen;

import data.DataFetch;

/**
 * The front page search screen for view
 * @author James Ford
 *
 */
@SuppressWarnings("serial")
public class PokeSearchPanel extends JPanel {

	
	private static final String DEFAULT = "Search Pokemon...";
	public static final String SPLITTER = "_";
	private boolean override;
	private DataFetch df;
	private JTextArea jta;
	private JScrollPane jsp;
	private JTable table;
	private PokeListener listen;
	
	/**
	 * constructor
	 * @param listen the parent component to report back to
	 */
	public PokeSearchPanel(PokeListener listen) {
		super(new BorderLayout());
		this.setFocusable(true);
		this.listen = listen;
		this.df = DataFetch.getInstance();
		this.table = new JTable();
		this.table.getTableHeader().setReorderingAllowed(false);
		this.jsp = new JScrollPane(table);
		this.jta = new JTextArea(DEFAULT);
		this.initActions();
		this.add(jsp, BorderLayout.CENTER);
		this.add(jta, BorderLayout.NORTH);

		this.updateModel();
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	/**
	 * updates the table model with either search data or every row in the database
	 */
	public void updateModel() {
		if(jta.getText().equals(DEFAULT)) {			
			this.table.setModel(df.getDefaultPokemonModel());
			
		} else {
			this.table.setModel(df.getSearchPokemonModel(jta.getText()));
		}
		if(table.getColumnModel().getColumnCount() == 0) {
			return;
		}
		table.getColumnModel().getColumn(0).setHeaderValue("National ID");
		table.getColumnModel().getColumn(1).setHeaderValue("Name");
		table.getColumnModel().getColumn(2).setHeaderValue("Type 1");
		table.getColumnModel().getColumn(3).setHeaderValue("Type 2");
	}
	
	/**
	 * initializes actions of action components
	 */
	private void initActions() {
		this.jta.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(jta.getText().equals(DEFAULT)) {
					jta.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(jta.getText().equals("")) {
					jta.setText(DEFAULT);
					updateModel();
				}
			}
		});
		this.jta.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					jta.setText(DEFAULT);
				}
				updateModel();
			}
			
		});
		this.table.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				if(index != -1){		
					String pokemon = (String) table.getValueAt(index, 1);
					int national_id = (int) table.getValueAt(index, 0);
					listen.act(GUIEntryPoint.POKEDEX, national_id + SPLITTER+ pokemon);
				}				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
}
