/**
 * 
 */
package view.pokedex;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	private int national_id;
	private String pokemon;
	private DataFetch df;
	private JTextArea jta;
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
		JPanel westPanel = new JPanel(new MigLayout());
		westPanel.add(jta, "width 40:90:90");
		westPanel.add(jta, "height 30:30:30");
		westPanel.add(jta, "wrap");
		westPanel.add(jsp, "width 50:50:50");
		westPanel.add(jsp, "height 200:300:400");	
		this.add(westPanel, BorderLayout.WEST);
		this.updateTable();
	}
	
	private void initComponents(){
		JPanel eastPanel = new JPanel(new MigLayout());
		eastPanel.add(nameLbl, "span, center, gapright 450, gapbottom 15, wrap");
		eastPanel.add(htLbl);
		eastPanel.add(height, "gapright 600");
		eastPanel.add(imageLbl, "wrap");
		eastPanel.add(wtLbl);
		eastPanel.add(weight, "gapright 600, wrap");
		this.add(eastPanel, BorderLayout.EAST);
		this.revalidate();
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
	
	private void updatePokedexId(){
		dexData = df.getPokedexQuery(national_id);
		nameLbl.setText(dexData.get(1));
		height = new JLabel(dexData.get(2));
		weight = new JLabel(dexData.get(3));
		species = new JLabel(dexData.get(4));
		color = new JLabel(dexData.get(5));
		type1 = new JLabel(dexData.get(6));
		type2 = new JLabel(dexData.get(7));
		ability1 = new JLabel(dexData.get(8));
		ability2 = new JLabel(dexData.get(9));
		initComponents();
		
	}
	
	private void updatePokedexName(){
		String search = "" + pokemon;
		dexData = df.getPokedexQuery(search);
		System.out.println(dexData);
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
		System.out.println(path);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.createImage(path);
		Image resizeImg = img.getScaledInstance(100, 100, 0);
		imageLbl = new JLabel("");
		imageLbl.setIcon(new ImageIcon(resizeImg));
		nameLbl.setText(dexData.get(1));
		height = new JLabel(dexData.get(2));
		weight = new JLabel(dexData.get(3));
		species = new JLabel(dexData.get(4));
		color = new JLabel(dexData.get(5));
		type1 = new JLabel(dexData.get(6));
		type2 = new JLabel(dexData.get(7));
		ability1 = new JLabel(dexData.get(8));
		ability2 = new JLabel(dexData.get(9));
		initComponents();
	}
	
	private void setPokedexEntry(int national_id){
		this.national_id = national_id;
		updatePokedexId();
	}
	
	private void setPokedexEntry(String pokemon){
		this.pokemon = pokemon;
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
		this.table.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				boolean flag = false;
				int index = table.getSelectedRow();
				if(index != -1){		
					
					int id = (int) table.getValueAt(index, 0);
					int uid = 0;
					try{
						uid = Integer.parseInt(jta.getText());
					}catch(NumberFormatException e){
						flag = true;
					}
					if(flag && id == uid){
						setPokedexEntry(id);
					}else if(jta.getText().equals(table.getValueAt(index, 1))){
						setPokedexEntry(jta.getText());
					}
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
