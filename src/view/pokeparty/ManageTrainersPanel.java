/**
 * 
 */
package view.pokeparty;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import view.PokeListener;

import data.DataFetch;
import data.PokeUtils;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class ManageTrainersPanel extends JPanel {

	private static final String DEFAULT = "Name";
	protected static final String errorMsg = "Passwords do not match";
	protected static final String errorTitle = "Retype Password";

	
	
	private GridBagConstraints c;
	private JTextArea trainerName;
	private JPanel left;
	private JPanel right;
	private JButton add;
	private DataFetch df;
	private JTable table;
	private JScrollPane jsp;
	private JPasswordField passOne;
	private JPasswordField passTwo;
	private PokeListener listener;

		
	
	public ManageTrainersPanel(PokeListener p) {
		super(new GridLayout(1,2));
		this.listener = p;
		this.df = DataFetch.getInstance();
		this.setFocusable(true);
		createComponents();
		actionInitialization();
		updateTable();
		fillComponents();
	}
	
	
	private void createComponents() {
		this.left = new JPanel(new BorderLayout());
		this.right = new JPanel(new GridBagLayout());
		this.table = new JTable();
		this.table.getTableHeader().setReorderingAllowed(false);
		this.jsp = new JScrollPane(table);
		this.trainerName = new JTextArea(DEFAULT);
		this.trainerName.setPreferredSize(new Dimension(160, 25));
		this.passOne = new JPasswordField(10);
		this.passTwo = new JPasswordField(10);
		c = new GridBagConstraints();
		this.add = new JButton("Add Trainer");
		this.right.setFocusable(true);
//		this.remove = new JButton("Remove Trainer #");
	}
	
	public void updateTable() {
		this.table.setModel(df.getMainTrainerTableModel());

		table.getColumnModel().getColumn(0).setHeaderValue("Trainer ID");
		table.getColumnModel().getColumn(1).setHeaderValue("Trainer Name");
	}
	
	private void setGridBagConstraints(int row, int col, int fill
			, double weightx, double weighty, int width, int height) {
		c.weightx = weightx;
		c.weighty = weighty;
		c.fill = fill;
		c.gridx = col;
		c.gridy = row;
		c.gridwidth = width;
		c.gridheight = height;
		// Reset the insets each time, we use it a few times so this
		//  will undo any times we do it.
		c.insets = new Insets(0, 0, 0, 0);
	}
	
	private void actionInitialization() {
		this.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					Integer user = (Integer)target.getValueAt(row,0);
					listener.act(PokePartyPanel.PASS_PANEL, String.valueOf(user));
				}
			}
		});
		this.trainerName.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(trainerName.getText().equals(DEFAULT)) {
					trainerName.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(trainerName.getText().equals("")) {
					trainerName.setText(DEFAULT);
				}
			}
		});
		this.add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createUser();
			}
		});
//		this.remove.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(!trainerName.getText().equals(DEFAULT)) {
//					df.removeTrainer(trainerName.getText());
//					trainerName.setText(DEFAULT);
//					updateTable();
//				}
//			}
//		});
		this.passOne.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					passTwo.requestFocus();
				}
			}
			
		});
		this.passTwo.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					createUser();
				}
			}
			
		});
	}

	private void createUser() {
		if(PokeUtils.equals(passOne.getPassword(),passTwo.getPassword())) {
			df.addTrainer(trainerName.getText(), PokeUtils.doPass(passOne.getPassword()));
			passOne.setText("");
			passTwo.setText("");
			trainerName.setText(DEFAULT);
			updateTable();
		} else {
			passOne.setText("");
			passTwo.setText("");
			JOptionPane.showMessageDialog(null, errorMsg, errorTitle,
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
	
	private void fillComponents() {
		this.setGridBagConstraints(0, 1, 0, 0, 0, 3, 1);
		this.right.add(trainerName, c);
		this.setGridBagConstraints(1, 0, 0, 0, 0, 1, 1);
		this.right.add(new JLabel("Password"), c);
		this.setGridBagConstraints(1, 1, 0, 0, 0, 3, 1);
		this.right.add(passOne, c);
		this.setGridBagConstraints(2, 0, 0, 0, 0, 1, 1);
		this.right.add(new JLabel("Confirm"), c);
		this.setGridBagConstraints(2, 1, 0, 0, 0, 3, 1);
		this.right.add(passTwo, c);
		this.setGridBagConstraints(3, 1, 0, 0, 0, 1, 1);
		this.right.add(add, c);
		this.setGridBagConstraints(3, 1, 0, 0, 0, 1, 1);
//		this.right.add(remove, c);
		this.add(left);
		this.add(right);
		this.left.add(jsp, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		char[] j = {'j'};
		System.out.println(j.hashCode());
	}
}
