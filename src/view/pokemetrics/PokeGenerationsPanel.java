package view.pokemetrics;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import data.DataFetch;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PokeGenerationsPanel extends JPanel {
	public JButton metricsBtn;
	public JButton genBtn;
	public JLabel name;
	protected static final String DEFAULT = "Search Pokemon...";
	private ButtonGroup group;
	private DataFetch df;
	private JTextArea jta;
	private JTable table;
	private JScrollPane jsp;
	private JRadioButton redblue;
	private JRadioButton yellow;
	private JRadioButton gold;
	private JRadioButton silver;
	private JRadioButton crystal;
	private JRadioButton ruby;
	private JRadioButton sapphire;
	private JRadioButton emerald;
	private JRadioButton firered;
	private JRadioButton leafgreen;
	private JRadioButton diamond;
	private JRadioButton pearl;
	private JLabel idLbl;
	private JLabel id;
	private JLabel nameLbl;
	private JLabel imageLbl;
	private JLabel pokedexEntry;
	private int national_id;
	public PokeGenerationsPanel(){
		super(new MigLayout());
		this.df = DataFetch.getInstance();
		initComponents();
	}
	
	private void initComponents(){
		jta = new JTextArea(DEFAULT);
		jta.setPreferredSize(new Dimension(90,30));
		table = new JTable();
		jsp = new JScrollPane(table);
		table.getTableHeader().setReorderingAllowed(false);
		jsp.setPreferredSize(new Dimension(160,200));
		metricsBtn = new JButton("Base Stats");
		genBtn = new JButton("Through the Generations");
		redblue = new JRadioButton("Red/Blue");
		yellow = new JRadioButton("Yellow");
		gold = new JRadioButton("Gold");
		silver = new JRadioButton("Silver");
		crystal = new JRadioButton("Crystal");
		ruby = new JRadioButton("Ruby");
		sapphire = new JRadioButton("Sapphire");
		emerald = new JRadioButton("Emerald");
		firered = new JRadioButton("Fire Red");
		leafgreen = new JRadioButton("Leaf Green");
		diamond = new JRadioButton("Diamond");
		pearl = new JRadioButton("Pearl");
		
		ActionListener listener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("Red/Blue")){
					String query = "select red_blue from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}else if(e.getActionCommand().equals("Yellow")){
					String query = "select yellow from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}else if(e.getActionCommand().equals("Gold")){
					String query = "select gold from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}else if(e.getActionCommand().equals("Silver")){
					String query = "select silver from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}else if(e.getActionCommand().equals("Crystal")){
					String query = "select crystal from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}else if(e.getActionCommand().equals("Ruby")){
					String query = "select ruby from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}else if(e.getActionCommand().equals("Sapphire")){
					String query = "select sapphire from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}else if(e.getActionCommand().equals("Emerald")){
					String query = "select emerald from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}else if(e.getActionCommand().equals("Fire Red")){
					String query = "select fire_red from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}else if(e.getActionCommand().equals("Leaf Green")){
					String query = "select leaf_green from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}else if(e.getActionCommand().equals("Diamond")){
					String query = "select diamond from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}else if(e.getActionCommand().equals("Pearl")){
					String query = "select pearl from pokedex_description where national_id = '" + national_id + "';";
					String result = df.getDexEntry(query);
					pokedexEntry.setText(result);
				}
			}
		};
		redblue.addActionListener(listener);
		yellow.addActionListener(listener);
		gold.addActionListener(listener);
		silver.addActionListener(listener);
		crystal.addActionListener(listener);
		ruby.addActionListener(listener);
		sapphire.addActionListener(listener);
		emerald.addActionListener(listener);
		firered.addActionListener(listener);
		leafgreen.addActionListener(listener);
		diamond.addActionListener(listener);
		pearl.addActionListener(listener);
		group = new ButtonGroup();
		group.add(redblue);
		group.add(yellow);
		group.add(gold);
		group.add(silver);
		group.add(crystal);
		group.add(ruby);
		group.add(sapphire);
		group.add(emerald);
		group.add(firered);
		group.add(leafgreen);
		group.add(diamond);
		group.add(pearl);
		idLbl = new JLabel("");
		id = new JLabel("");
		nameLbl = new JLabel("");
		name = new JLabel("");
		imageLbl = new JLabel("");
		pokedexEntry = new JLabel("");
		
		JPanel western = new JPanel(new MigLayout());
		western.add(jta, "wrap");
		western.add(jsp);
		this.add(western, "west");
		
		JPanel northern = new JPanel(new MigLayout());
		northern.add(metricsBtn);
		northern.add(genBtn);
		this.add(northern, "north");
		
		JPanel west = new JPanel(new MigLayout());
		west.add(idLbl);
		west.add(id, "wrap");
		west.add(nameLbl);
		west.add(name, "wrap");
		west.add(imageLbl, "south");
		west.setBorder(BorderFactory.createEtchedBorder());
		this.add(west, "west");
		
		JPanel east = new JPanel(new MigLayout());
		east.add(pokedexEntry, "east, wrap");
		this.add(east, "center");
		
		JPanel south = new JPanel(new MigLayout());
		south.add(redblue);
		south.add(yellow);
		south.add(gold);
		south.add(silver);
		south.add(crystal);
		south.add(ruby, "wrap");
		south.add(sapphire);
		south.add(emerald);
		south.add(firered);
		south.add(leafgreen);
		south.add(diamond);
		south.add(pearl);
		south.setBorder(BorderFactory.createEtchedBorder());
		this.add(south, "south");
		initializeActions();
		updateTable();
	}
	
	private void updatePokedexEntry(String pokemon){
		for(Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();){
			AbstractButton button = buttons.nextElement();
			if(button.isSelected()){
				button.doClick();
			}
		}
	}
	
	public void updatePokeGenerations(String pokemon){
		ArrayList<String> metricsData = df.getMetricsQuery(pokemon);
		national_id = Integer.parseInt(metricsData.get(0));
		String ID = metricsData.get(0);
		if(ID.length() < 3){
			if(ID.length() == 1){
				ID = "00" + ID;
			}else{
				ID = "0" + ID;
			}
		}
		String path = "resources/images/" + ID + ".png";
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.createImage(path);
		Image resizeImg = img.getScaledInstance(150, 150, 0);
		imageLbl.setIcon(new ImageIcon(resizeImg));
		id.setText(ID);
		name.setText(metricsData.get(1));
		idLbl.setText("ID:");
		nameLbl.setText("Name:");
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
		this.table.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = table.getSelectedRow();
				if(index != -1){		
					String pokemon = (String) table.getValueAt(index, 1);
					updatePokeGenerations(pokemon);
					updatePokedexEntry(pokemon);
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
}
