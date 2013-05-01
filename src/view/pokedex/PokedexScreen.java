/**
 * 
 */
package view.pokedex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import data.DataFetch;

/**
 * @author Felipe
 *
 */
@SuppressWarnings("serial")
public class PokedexScreen extends JPanel {
	
	@SuppressWarnings("unused")
	private static final String DEFAULT = "Search Pokemon...";
	private DataFetch df;
	private JTextArea jta;
	private JTable table;
	private JScrollPane jsp;
	
	public PokedexScreen(){
		super(new BorderLayout());
		JPanel west = new JPanel();
		this.df = DataFetch.getInstance();
		this.jta = new JTextArea(DEFAULT);
		this.table = new JTable();
		this.jsp = new JScrollPane(table);
//		this.jsp.setPreferredSize(new Dimension(160, 300));
		this.initializeActions();
		west.add(jta, BorderLayout.NORTH);
		west.add(jsp, BorderLayout.CENTER);
		this.add(west, BorderLayout.WEST);
		this.updateTable();
	}
	
	private void updateTable(){
		if(jta.getText().equals(DEFAULT)){
			this.table.setModel(df.getSimplifiedDefaultPokemonModel());
			this.table.getColumnModel().getColumn(0).setHeaderValue("ID");
			this.table.getColumnModel().getColumn(1).setHeaderValue("Name");
			this.table.getColumnModel().getColumn(0).setMaxWidth(40);
			this.table.getColumnModel().getColumn(1).setMaxWidth(100);
		}else{
			this.table.setModel(df.getSimplifiedSearchPokemonModel(jta.getText()));
			this.table.getColumnModel().getColumn(0).setHeaderValue("ID");
			this.table.getColumnModel().getColumn(1).setHeaderValue("Name");
			this.table.getColumnModel().getColumn(0).setMaxWidth(40);
			this.table.getColumnModel().getColumn(1).setMaxWidth(100);
		}
	}
	
	private void initializeActions(){
		this.jta.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				if(jta.getText().equals(DEFAULT)){
					jta.setText("");
				}
			}
			public void focusLost(FocusEvent e){
				if(jta.getText().equals("")){
					jta.setText(DEFAULT);
				}
				updateTable();
			}
		});
		this.jta.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					jta.setText(DEFAULT);
				}
				updateTable();
			}
			
		});
	}
	
	
}
