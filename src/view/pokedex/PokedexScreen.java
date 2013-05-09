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
import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.Port.Info;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.GUIEntryPoint;
import view.PokeListener;

import net.miginfocom.swing.MigLayout;

import data.DataFetch;

/**
 * @author Ryan Castner, Felipe Cossa
 *
 */
@SuppressWarnings("serial")
public class PokedexScreen extends JPanel implements PokeListener {

	private static final String DEFAULT = "Search Pokemon...";
	private static final String EXT = ".mp3";
	
	private static final String RESOURCE_DIR = "/data/resources/";
	private static final String SOUND_DIR = RESOURCE_DIR + "sounds/";
	private static final String IMG_PATH = RESOURCE_DIR + "images/";
	public static final String DISABLE = "DISABLE";
	public static final String ENABLE = "ENABLE";
	private static final int MAX_HEIGHT = 250;

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
	private JLabel cryLabel;
	private JPanel southPanel;
	private JButton pokemetricsBtn;
	private JButton pokevolveBtn;
	private JScrollPane jsp;
	private JSlider volume;
	private ArrayList<String> dexData;
	private PokeListener listener;
	private PokeListener thisListener = this;

	public PokedexScreen(PokeListener listener){
		super(new MigLayout());
		this.listener = listener;
		this.df = DataFetch.getInstance();
		initComponents();
	}

	private void initComponents(){
		jta = new JTextArea(DEFAULT);
		jta.setPreferredSize(new Dimension(160,25));
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
		cryLabel = new JLabel("Cry:");
		cry = new CryButton(0);
		ability1 = new JLabel("");
		ability2 = new JLabel("");
		imageLbl = new JLabel("");
		volume = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
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
		southPanel.add(cryLabel);
		southPanel.add(cry, "wrap");
		southPanel.add(volume);
		southPanel.add(pokemetricsBtn, "gaptop 50");
		southPanel.add(pokevolveBtn, "gaptop 50");
		this.add(southPanel, "south");
		this.setPokedexEntry("Bulbasaur", 1);
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
				path = IMG_PATH + "00" + path + ".png";
			}else{
				path = IMG_PATH + "0" + path + ".png";
			}
		}else{
			path = IMG_PATH + path + ".png";
		}
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = this.getClass().getResource(path);
		Image img = tk.createImage(url);
		java.awt.image.BufferedImage imgs = null;
		try {
			imgs = ImageIO.read(url);
		} catch (IOException e) {	}
		int newWidth = MAX_HEIGHT*imgs.getWidth()/imgs.getHeight();
		Image resizeImg = imgs == null ? 
				img.getScaledInstance(200, 200, Image.SCALE_SMOOTH) :
					img.getScaledInstance(newWidth, MAX_HEIGHT, Image.SCALE_SMOOTH);
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

	public void setPokedexEntry(String pokemon, int national_id) {
		this.pokemon = pokemon;
		this.cry.setNationalID(national_id);
		updatePokedexName();
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
				listener.act(GUIEntryPoint.POKEMETRICS, pokemon);
			}
		});
		this.pokevolveBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				listener.act(GUIEntryPoint.POKEVOLVE, pokemon);
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
				new SoundJLayer(cry.getNationalID() + EXT, thisListener).play();
			}
		});
		this.volume.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(!volume.getValueIsAdjusting()) {
					Info source = Port.Info.SPEAKER;
					if (AudioSystem.isLineSupported(source)) {
						try {
							Port outline = (Port) AudioSystem.getLine(source);
							outline.open();                
							FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);                
//							System.out.println("       volume: " + volumeControl.getValue() );
							float v = volume.getValue()/100f;
							volumeControl.setValue(v);
//							System.out.println("   new volume: " + volumeControl.getValue() );
							outline.close();
						} catch (LineUnavailableException ex) {
							System.err.println("Audio system not supported.");
							volume.setEnabled(false);
						} 
					}
				} 
			}			
		});
	}


	@Override
	public void act(String command, String argument) {
		switch(command) {
		case ENABLE:
			this.cry.setEnabled(true);
			break;
		case DISABLE:
			this.cry.setEnabled(false);
			break;
		default:
			System.err.println("Internal error. Wrong message sent.");
		}
	}

	public static void main(String[] args) {
		new SoundJLayer(SOUND_DIR + 490 + EXT).play();
	}


}
