package view.pokevolve;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import data.DataFetch;

public class PokevolvePanel extends JPanel{
	private static final String DEFAULT = "Search Pokemon...";
	private DataFetch df;
	private JTextArea jta;
	private JTable table;
	private JScrollPane jsp;
	private JLabel levelLbl;
	private JLabel itemLbl;
	private JLabel moveLbl;
	private int size;
	private ArrayList<JLabel> dataLbls;
	private ArrayList<String> evoData;
	private JPanel centerPanel;
	private JTable eggMoves;
	
	public PokevolvePanel(){
		super(new MigLayout());
		this.df = DataFetch.getInstance();
		initComponents();
	}

	private void initComponents() {
		jta = new JTextArea(DEFAULT);
		jta.setPreferredSize(new Dimension(90,30));
		table = new JTable();
		jsp = new JScrollPane(table);
		table.getTableHeader().setReorderingAllowed(false);
		jsp.setPreferredSize(new Dimension(160,200));
		levelLbl = new JLabel("");
		itemLbl = new JLabel("");
		moveLbl = new JLabel("");
		dataLbls = new ArrayList<JLabel>();
		centerPanel = new JPanel(new MigLayout());
		initializeActions();
		JPanel westPanel = new JPanel(new MigLayout());
		westPanel.add(jta, "wrap");
		westPanel.add(jsp);
		updateTable();
		this.add(westPanel, "west");
	}
	
	public void updatePokevolve(String pokemon){
		evoData = df.getPokevolveQuery(pokemon);
		int i = 1;
		while(!evoData.get(4).equals("0") || i == 1){
			int num = Integer.parseInt(evoData.get(0));
			num++;
			String ID = "" + num;
			if(i==1){
				ID = evoData.get(0);
			}
			evoData = df.getPokevolveQuery(ID);
			System.out.println(evoData);
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
			size = dataLbls.size();
			dataLbls.add(new JLabel(""));
			dataLbls.get(size).setIcon(new ImageIcon(resizeImg));
			String temp = evoData.get(3);
			if(temp.endsWith("at level")){
				size = dataLbls.size();
				levelLbl.setText("Level:");
				dataLbls.add(new JLabel("Level:"));
				dataLbls.add(new JLabel(evoData.get(4)));
				centerPanel.add(dataLbls.get(size), "wrap");
				centerPanel.add(levelLbl);
				centerPanel.add(dataLbls.get(size+1), "wrap");
			}
			i++;
		}
		this.add(centerPanel, "center");
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
		this.table.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = table.getSelectedRow();
				if(index != -1){		
					String pokemon = (String) table.getValueAt(index, 1);
					updatePokevolve(pokemon);
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
