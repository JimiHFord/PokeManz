/**
 * PokemetricsPanel.java
 */
package view.pokemetrics;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import data.DataFetch;

import net.miginfocom.swing.MigLayout;

/**
 * Class represents a panel object for the pokemetrics view.
 * 
 * @author Ryan Castner rrc9704
 * @contributor Jimi Ford jhf3617
 */
@SuppressWarnings("serial")
public class PokemetricsPanel extends JPanel {
	private static final String DEFAULT =  "Search Pokemon...";
	public static final String DEFAULT_POKEMON = "Bulbasaur";
	private static final int MAX_HEIGHT = 250;
	private DataFetch df;
	private JTextArea jta;
	private JTable table;
	private JScrollPane jsp;
	private JScrollPane metJsp;
	private JScrollPane movJsp;
	private JTable metrics;
	private JTable moves;
	private JLabel idLbl;
	private JLabel id;
	private JLabel nameLbl;
	private JLabel expGrpLbl;
	private JLabel expGrp;
	private JLabel expLvlLbl;
	private JLabel expLvl;
	private JLabel imageLbl;
	public JLabel name;
	public JButton baseBtn;
	public JButton thruBtn;
	private ArrayList<String> metricsData;
	private ArrayList<String> expData;
	
	/**
	 * Constructor
	 */
	public PokemetricsPanel(){
		super(new MigLayout());		// uses MigLayout
		this.df = DataFetch.getInstance();
		initComponents();
		updatePokemoves(DEFAULT_POKEMON);	// updates panel with the default
		updatePokemetrics(DEFAULT_POKEMON);	// updates panel with the default
	}
	
	/**
	 * Initializes GUI components
	 */
	private void initComponents(){
		jta = new JTextArea(DEFAULT);
		jta.setPreferredSize(new Dimension(160,25));
		table = new JTable();
		metrics = new JTable();
		moves = new JTable();
		movJsp = new JScrollPane(moves);
		movJsp.setPreferredSize(new Dimension(300,170));
		metJsp = new JScrollPane(metrics);
		metJsp.setPreferredSize(new Dimension(500,45));
		jsp = new JScrollPane(table);
		table.getTableHeader().setReorderingAllowed(false);
		jsp.setPreferredSize(new Dimension(160,200));
		idLbl = new JLabel("");
		id = new JLabel("");
		nameLbl = new JLabel("");
		name = new JLabel("");
		expGrpLbl = new JLabel("");
		expGrp = new JLabel("");
		expLvlLbl = new JLabel("");
		expLvl = new JLabel("");
		imageLbl = new JLabel("");
		baseBtn = new JButton("Base Stats");
		baseBtn.setVisible(false);
		thruBtn = new JButton("Through the Generations");
		thruBtn.setVisible(false);
		initializeActions();
		JPanel westPanel = new JPanel(new MigLayout());
		westPanel.add(jta, "wrap");
		westPanel.add(jsp);
		this.add(westPanel, "west");
		JPanel northPanel = new JPanel(new MigLayout());
		northPanel.add(baseBtn);
		northPanel.add(thruBtn);
		this.add(northPanel, "north");
		JPanel centerWestPanel = new JPanel(new MigLayout());
		centerWestPanel.add(idLbl);
		centerWestPanel.add(id, "wrap");
		centerWestPanel.add(nameLbl);
		centerWestPanel.add(name, "wrap");
		centerWestPanel.add(expGrpLbl);
		centerWestPanel.add(expGrp, "wrap");
		centerWestPanel.add(expLvlLbl);
		centerWestPanel.add(expLvl, "wrap");
		centerWestPanel.add(imageLbl, "south");
		centerWestPanel.setBorder(BorderFactory.createEtchedBorder());
		this.add(centerWestPanel, "west");
		JPanel metricsPanel = new JPanel(new MigLayout());
		metricsPanel.add(metJsp);
		this.add(metricsPanel, "north");
		JPanel movesPanel = new JPanel(new MigLayout());
		movesPanel.add(movJsp);
		this.add(movesPanel, "south");
		updateTable();
	}
	
	/**
	 * Updates the pokemoves table based off
	 * a table model in DataFetch and the data
	 * in the result set.
	 * @param pokemon - the name of the pokemon
	 */
	public void updatePokemoves(String pokemon){
		moves.setModel(df.getMovesPokemonModel(pokemon));
		moves.getColumnModel().getColumn(0).setHeaderValue("Level");
		moves.getColumnModel().getColumn(1).setHeaderValue("Move Learned");
	}
	
	/**
	 * Updates the metrics panel based off the table model
	 * provided by DataFetch. Also deals with image processing
	 * based on data provided by teh result set.
	 * 
	 * @param pokemon - the name of the pokemon
	 */
	public void updatePokemetrics(String pokemon){
		metricsData = df.getMetricsQuery(pokemon);
		expData = df.getExpQuery(pokemon);
		if(metricsData.size() == 0) {
			return;
		}
		// Below deals with image processing
		String ID = metricsData.get(0);
		if(ID.length() < 3){	// pads ID number so 3 becomes 003
			if(ID.length() == 1){
				ID = "00" + ID;
			}else{
				ID = "0" + ID;
			}
		}
		//absolute url to work universally
		java.net.URL path = this.getClass().getResource("/data/resources/images/"+ID+".png");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.createImage(path);
		BufferedImage imgs = null;
		try {
			imgs = ImageIO.read(path);
		} catch (IOException e) {
		}
		int newWidth = MAX_HEIGHT*imgs.getWidth()/imgs.getHeight();
		Image resizeImg = imgs != null ? 
				img.getScaledInstance(newWidth, MAX_HEIGHT, Image.SCALE_SMOOTH) :
				img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		imageLbl.setIcon(new ImageIcon(resizeImg));
		id.setText(ID);
		name.setText(metricsData.get(1));
		expGrpLbl.setText("EXP Group:");
		expGrp.setText(expData.get(1));
		expLvlLbl.setText("EXP To 100:");
		expLvl.setText(expData.get(2));
		metrics.setModel(df.getMetricsPokemonModel(pokemon));
		metrics.getColumnModel().getColumn(0).setHeaderValue("HP");
		metrics.getColumnModel().getColumn(1).setHeaderValue("ATK");
		metrics.getColumnModel().getColumn(2).setHeaderValue("DEF");
		metrics.getColumnModel().getColumn(3).setHeaderValue("SP.ATK");
		metrics.getColumnModel().getColumn(4).setHeaderValue("SP.DEF");
		metrics.getColumnModel().getColumn(5).setHeaderValue("SPD");
		thruBtn.setVisible(true);
		baseBtn.setVisible(true);
		idLbl.setText("ID:");
		nameLbl.setText("Name:");		
	}
	
	/**
	 * Adds action listeners and defines action performed methos
	 */
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
		this.table.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = table.getSelectedRow();
				if(index != -1){		
					String pokemon = (String) table.getValueAt(index, 1);
					updatePokemetrics(pokemon);
					updatePokemoves(pokemon);
				}				
			}
		});
		
	}
	
	/**
	 * Update search table based on input
	 */
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
