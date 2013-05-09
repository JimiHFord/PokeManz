/**
 * PokevolvePanel.java
 */
package view.pokevolve;

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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import data.DataFetch;

/**
 * This class represents the Pokevolve Panel and contains the algorithms
 * for searching through the result set and extracting data to then add
 * it to its panel.
 * 
 * @author Ryan Castner rrc9704
 * @contributor Jimi Ford jhf3617
 * @contributor Felipe Cossa fmc3147
 */

@SuppressWarnings("serial")
public class PokevolvePanel extends JPanel{
	private static final String DEFAULT = "Search Pokemon...";
	private static final int MAX_HEIGHT = 250;
	private DataFetch df;
	private JTextArea jta;
	private JTable table;
	private JScrollPane jsp;
	private ArrayList<JLabel> dataLbls;
	private ArrayList<String> evoData;
	private JScrollPane pictureScroller;
	private JPanel picturePanel;
	private JPanel westPanel;
	private boolean branching = false;

	/**
	 * Constructor for the class
	 */
	public PokevolvePanel(){
		super(new MigLayout());				// using MigLayout
		this.df = DataFetch.getInstance();
		dataLbls = new ArrayList<JLabel>();	// Array List for data to be parsed into panels
		initComponents();
		fillComponents();
		updateTable();
	}

	/**
	 * Initializes components
	 */
	private void initComponents() {
		jta = new JTextArea(DEFAULT);
		jta.setPreferredSize(new Dimension(160,25));
		table = new JTable();
		picturePanel = new JPanel(new MigLayout());
		pictureScroller = new JScrollPane(picturePanel);
		jsp = new JScrollPane(table);
		table.getTableHeader().setReorderingAllowed(false);
		jsp.setPreferredSize(new Dimension(160,200));
		jsp.setMinimumSize(new Dimension(160,200));
		initializeActions();
		westPanel = new JPanel(new MigLayout());
	}
	
	/**
	 * Fills west panel with search panel which is persistent through
	 * the entire panel life time
	 */
	private void fillComponents() {
		westPanel.add(jta, "wrap");
		westPanel.add(jsp);
		this.add(westPanel, "west");
		this.add(pictureScroller);
	}

	/**
	 * Updates the evolution display panel based on the new data inserted
	 * into the dataLbls ArrayList.
	 */
	private void updateComponents(){
		this.picturePanel.removeAll();	// remove old pictures and labels
		int j = 0;	// counter
		for(int i = 0; i < (dataLbls.size()/3); i++){	// go through ArrayList
			JPanel panel = new JPanel(new MigLayout());	// anonymous jpanel using mig layout
			panel.add(dataLbls.get(j++), "center, wrap");	// get the next 3 components 
			panel.add(dataLbls.get(j++), "center, wrap");	// data lbl contains sets of 3 pieces
			panel.add(dataLbls.get(j++), "center");			// the name, the picture, and how it evolves
			if(dataLbls.size()/3 == 5){		// these next if statements deal with wrapping components around
				if((i+1)%3 == 0){			// to correctly display evolution chains of pokemon
					this.picturePanel.add(panel, "wrap");
				}else{
					this.picturePanel.add(panel);
				}
			}else if(dataLbls.size()/3 == 4){
				if((i+1)%2 == 0){
					this.picturePanel.add(panel, "wrap");
				}else{
					this.picturePanel.add(panel);
				}
			}else if(dataLbls.size()/3 == 8){
				if((i+1)%4 == 0){
					this.picturePanel.add(panel, "wrap");
				}else{
					this.picturePanel.add(panel);
				}
			}else if(dataLbls.size()/3 == 6){
				if((i+1)%3 == 0){
					this.picturePanel.add(panel, "wrap");
				}else{
					this.picturePanel.add(panel);
				}
			}else{
				this.picturePanel.add(panel);
			}
		}
		this.revalidate();	// updates gui
		this.repaint();	
		if(!dataLbls.isEmpty()){	// clear datalbls to ready for the next selection
			dataLbls.clear();
		}
	}
	
	/**
	 * Updates the Pokevolve panel by getting result data from DataFetch
	 * class and then correctly populating dataLbls array list fields
	 * with a recursive algorithm that searches through pokemon evolutions.
	 * This is due to some pokemon having divergent evolution paths.
	 * @param pokemon - the name of the pokemon to search for
	 * @param branchNo - the ith branch of a pokemon's evolution chain, counted by 7's
	 */
	public void updatePokevolve(String pokemon, int branchNo){
		evoData = df.getPokevolveQuery(pokemon);
		if(evoData.size() == 0) {		// base case
			return;
		}
		if(evoData.get(0).equals("133")){	// special handling for eevee
			updateEevee();
			return;
		}
		if(evoData.get(0).equals("236")){	// special handling for tyrogue
			updateTyrogue();
			return;
		}
		int records = evoData.size()/7;		// determines number of records returned by DF
		if(records > 1){					// if more than 1 record this pokemon has branching evolutions
			branching = true;
		}
		for(int i = 0; i < records; i++){	// iterate through each record and go down its evolution branch
			if(i > 0){
				branchNo += 7;
			}
			dataLbls.add(new JLabel(evoData.get(branchNo+1)));
			String ID = evoData.get(branchNo);
			addImage(ID);
			if(evoData.get(branchNo+3).endsWith("does not evolve")){
				dataLbls.add(new JLabel("Does Not Evolve"));
				if(branching == false){
					updateComponents();
				}
				branching = false;
			}
			if(evoData.get(branchNo+3).endsWith("at level")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3) + " " + evoData.get(branchNo+4)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).equals("when leveled up with high friendship")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).equals("when leveled up with high friendship during the day")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).equals("when leveled up with high friendship during the night")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).equals("when traded")){
				dataLbls.add(new JLabel("Evovles " + evoData.get(branchNo+3)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).endsWith("while holding")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3) + " " + evoData.get(branchNo+5)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).endsWith("when exposed to")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3) + " " + evoData.get(branchNo+5)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).equals("when leveled up with high Beauty")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).endsWith("while knowing")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3) + " " + evoData.get(branchNo+6)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).equals("when leveled up with Remoraid in the party")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).equals("when leveled up at Mt. Coronet")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).equals("when leveled up near a Moss Rock")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).equals("when leveled up near an Ice Rock")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}else if(evoData.get(branchNo+3).equals("when traded while holding ")){
				dataLbls.add(new JLabel("Evolves " + evoData.get(branchNo+3) + " " + evoData.get(branchNo+5)));
				updatePokevolve(evoData.get(branchNo+2), 0);
				evoData = df.getPokevolveQuery(pokemon);
				records = evoData.size()/7;
			}
		}
	}

	/**
	 * The normal evolution algorithm can't represent this pokemon
	 * correctly so this function deals with this special case
	 */
	private void updateTyrogue() {
		evoData = df.getPokevolveQuery("236");
		dataLbls.add(new JLabel(evoData.get(1)));
		addImage("236");
		dataLbls.add(new JLabel(""));
		for(int i = 0; i < 3; i++){
			dataLbls.add(new JLabel(evoData.get(i*7+2)));
			ArrayList<String> temp = df.getPokedexQuery(evoData.get(i*7+2));
			String ID = temp.get(0);
			addImage(ID);
			if(evoData.get(i*7+3).endsWith("at level")){
				dataLbls.add(new JLabel("Evolves into " + evoData.get(i*7+3)+" "+evoData.get(i*7+4)));
			}
		}
		updateComponents();
	}

	/**
	 * The normal evolution algorithm can't represent this pokemon
	 * correctly so this function deals with this special case
	 */
	private void updateEevee() {
		evoData = df.getPokevolveQuery("133");
		dataLbls.add(new JLabel(evoData.get(1)));
		addImage("133");
		dataLbls.add(new JLabel(""));
		for(int i = 0; i < 7; i++){
			dataLbls.add(new JLabel(evoData.get(i*7+2)));
			ArrayList<String> temp = df.getPokedexQuery(evoData.get(i*7+2));
			String ID = temp.get(0);
			addImage(ID);
			if(evoData.get(i*7+3).equals("when exposed to")){
				dataLbls.add(new JLabel("Evolves into " + evoData.get(i*7+3) + " " + evoData.get(i*7+5)));
			}else if(evoData.get(i*7+3).startsWith("when leveled up")){
				dataLbls.add(new JLabel("Evolves into " + evoData.get(i*7+3)));
			}
		}
		updateComponents();
	}

	/**
	 * Method deals with extracting the image based on the
	 * supplied pokemon id from the local directory
	 * @param ID - the National ID of the pokemon
	 */
	private void addImage(String ID){
		if(ID.length() < 3){
			if(ID.length() == 1){
				ID = "00" + ID;
			}else{
				ID = "0" + ID;
			}
		}
//		String path = "resources/images/" + ID + ".png";
		java.net.URL path = this.getClass().getResource("/data/resources/images/"+ID+".png");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.createImage(path);
		BufferedImage imgs = null;
		try {
			imgs = ImageIO.read(path);
		} catch (IOException e) { }
		int newWidth = MAX_HEIGHT*imgs.getWidth()/imgs.getHeight();
		Image resizeImg = img.getScaledInstance(
				newWidth, MAX_HEIGHT, Image.SCALE_SMOOTH);
		dataLbls.add(new JLabel(""));
		dataLbls.get(dataLbls.size()-1).setIcon(new ImageIcon(resizeImg));
	}
	
	/**
	 * Method deals with updating the search panel
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

	/**
	 * Initializes action listeners and implements actionPerformed methods
	 * for the different components of the panel
	 */
	private void initializeActions() {
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
					updatePokevolve(pokemon, 0);
				}				
			}

		});
	}



}
