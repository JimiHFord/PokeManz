package view.pokedex;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import net.miginfocom.swing.MigLayout;

import data.DataFetch;

/**
 * @author Ryan Castner, Felipe Cossa
 *
 */
@SuppressWarnings("serial")
public class PokedexScreen extends JPanel {

	private static final String DEFAULT = "Search Pokemon...";
	private static final String EXT = ".mp3";
	private static final String SOUND_DIR = "resources/sounds/";
	private static final String IMG_PATH = "resources/images/";
	
	private String pokemon;	
	private DataFetch df;
	private JTextArea jta;
	private CryButton cry;
	private JTable table;
	private JLabel nameLbl;
	private JLabel imageLbl;
	private JLabel htLbl;
	private JLabel height;
	private JLabel wtLbl;
	private JLabel weight;
	private JLabel colorLbl;
	private JLabel color;
	private JLabel spcsLbl;
	private JLabel species;
	private JLabel typesLbl;
	private JLabel type1;
	private JLabel type2;
	private JLabel abilitiesLbl;
	private JLabel ability1;
	private JLabel ability2;
	private JPanel southPanel;
	private JButton pokemetricsBtn;
	private JButton pokevolveBtn;
	private JScrollPane jsp;
	private ArrayList<String> dexData;
	
	public PokedexScreen(){
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
		nameLbl = new JLabel("");
		htLbl = new JLabel("");
		wtLbl = new JLabel("");
		colorLbl = new JLabel("");
		spcsLbl = new JLabel("");
		typesLbl = new JLabel("");
		abilitiesLbl = new JLabel("");
		height = new JLabel("");
		weight = new JLabel("");
		species = new JLabel("");
		color = new JLabel("");
		type1 = new JLabel("");
		type2 = new JLabel("");
		cry = new CryButton(0);
		ability1 = new JLabel("");
		ability2 = new JLabel("");
		imageLbl = new JLabel("");
		pokemetricsBtn = new JButton();
		pokemetricsBtn.setVisible(false);
		pokevolveBtn = new JButton();
		pokevolveBtn.setVisible(false);
		this.initializeActions();
		JPanel westPanel = new JPanel(new MigLayout());
		westPanel.add(jta, "width 40:90:90");
		westPanel.add(jta, "height 30:30:30");
		westPanel.add(jta, "wrap");
		westPanel.add(jsp, "width 50:50:50");
		westPanel.add(jsp, "height 200:300:400");	
		this.add(westPanel, "west");
		this.updateTable();
		JPanel centerPanel = new JPanel(new MigLayout());
		centerPanel.add(nameLbl, "center, gapbottom 15, wrap");
		centerPanel.add(imageLbl, "center, gapbottom 20, wrap");
		this.add(centerPanel, "center");
		southPanel = new JPanel(new MigLayout());
		southPanel.add(htLbl, "align label");
		southPanel.add(height, "wrap");
		southPanel.add(wtLbl, "align label");
		southPanel.add(weight, "wrap");
		southPanel.add(spcsLbl);
		southPanel.add(species, "wrap");
		southPanel.add(colorLbl);
		southPanel.add(color, "wrap");
		southPanel.add(typesLbl);
		southPanel.add(type1);
		southPanel.add(type2, "wrap");
		southPanel.add(abilitiesLbl);
		southPanel.add(ability1);
		southPanel.add(ability2, "wrap");
		southPanel.add(pokemetricsBtn, "gaptop 50");
		southPanel.add(pokevolveBtn, "gaptop 50");
//		southPanel.setBackground(Color.red);
		this.add(southPanel, "south");
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
	
	private void updatePokedexName(){
		String search = "" + pokemon;
		dexData = df.getPokedexQuery(search);
		String path = dexData.get(0);
		if(path.length() < 3){
			if(path.length() == 1){
				path = "resources/images/00" + path + ".png";
			}else{
				path = "resources/images/0" + path + ".png";
			}
		}else{
			path = "resources/images/" + path + ".png";
		}
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.createImage(path);
		Image resizeImg = img.getScaledInstance(200, 200, 0);
		imageLbl.setIcon(new ImageIcon(resizeImg));
		nameLbl.setText(dexData.get(1));
		nameLbl.setFont(new Font("Dialog", Font.BOLD, 26));
		height.setText(dexData.get(2) + " m");
		weight.setText(dexData.get(3) + " kg");
		species.setText(dexData.get(4));
		color.setText(dexData.get(5));
		type1.setText(dexData.get(6));
		type2.setText(dexData.get(7));
		ability1.setText(dexData.get(8));
		ability2.setText(dexData.get(9));
		htLbl.setText("Height:");
		wtLbl.setText("Weight:");
		spcsLbl.setText("Species:");
		colorLbl.setText("Color:");
		if(type1.getText().equals(type2.getText())){
			type2.setText("");
			typesLbl.setText("Type:");
		}else{
			typesLbl.setText("Types:");
		}
		if(ability2.getText().equals("None")){
			ability2.setText("");
			abilitiesLbl.setText("Ability:");
		}else{
			abilitiesLbl.setText("Abilities:");
		}
		pokemetricsBtn.setText("Pokemetrics");
		pokemetricsBtn.setVisible(true);
		pokevolveBtn.setText("Pokevolve");
		pokevolveBtn.setVisible(true);
		southPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.revalidate();
	}
	
	public void setPokedexEntry(String pokemon){
		this.pokemon = pokemon;
		updatePokedexName();
	}
	
	private void setPokedexEntry(String pokemon, int national_id) {
		this.pokemon = pokemon;
		this.cry.setNationalID(national_id);
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
		this.pokemetricsBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO switch to pokemetrics panel
				
			}
		});
		this.pokevolveBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO switch to pokevolve panel
				
			}
			
		});
		this.table.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = table.getSelectedRow();
				if(index != -1){		
					String pokemon = (String) table.getValueAt(index, 1);
					int national_id = (int) table.getValueAt(index, 0);
					setPokedexEntry(pokemon, national_id);
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
		this.cry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SoundJLayer(SOUND_DIR + cry.getNationalID() + EXT).play();
			}
		});
	}
	
	
}
