/**
 * 
 */
package view.pokedex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;
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
	private JLabel nameLbl;
	private JLabel htLbl;
	private JLabel wtLbl;
	private JLabel colorLbl;
	private JLabel spcsLbl;
	private JLabel typesLbl;
	private JLabel abilitiesLbl;
	private JButton pokemetricsBtn;
	private JButton pokevolveBtn;
	private JScrollPane jsp;
	private ArrayList<String> dexData;
	
	public PokedexScreen(){
		super(new BorderLayout());
		this.df = DataFetch.getInstance();
		this.jta = new JTextArea(DEFAULT);
		jta.setPreferredSize(new Dimension(90,30));
		this.table = new JTable();
		this.jsp = new JScrollPane(table);
		table.getTableHeader().setReorderingAllowed(false);
		jsp.setPreferredSize(new Dimension(160,200));
		nameLbl = new JLabel("Default Name");
		htLbl = new JLabel("Height");
		wtLbl = new JLabel("Weight");
		colorLbl = new JLabel("Color");
		spcsLbl = new JLabel("Species");
		typesLbl = new JLabel("Types");
		abilitiesLbl = new JLabel("Abilities");
		this.initializeActions();
		JPanel eastPanel = new JPanel(new MigLayout());
		eastPanel.add(jta, "width 40:90:90");
		eastPanel.add(jta, "height 30:30:30");
		eastPanel.add(jta, "wrap");
		eastPanel.add(jsp, "width 50:50:50");
		eastPanel.add(jsp, "height 200:300:400");
		
		JPanel westPanel = new JPanel(new MigLayout());
		westPanel.add(nameLbl, "gapleft 50");
		westPanel.add(nameLbl, "gapright 50");
		this.add(eastPanel, BorderLayout.WEST);
		this.add(westPanel, BorderLayout.CENTER);
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
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					jta.setText(DEFAULT);
				}
				updateTable();
			}
			
		});
	}
	
	
}
