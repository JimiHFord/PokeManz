/**
 * 
 */
package view.pokeparty;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import view.PokeListener;

import data.DataFetch;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class ManageTrainersPanel extends JPanel {

	private static final String DEFAULT = "Name (or #)";
	
	private GridBagConstraints c;
	private JTextArea trainerName;
	private JPanel left;
	private JPanel right;
	private JButton add;
	private JButton remove;
	private DataFetch df;
	private JTable table;
	private JScrollPane jsp;
	private PokeListener parent;
	
	
	public ManageTrainersPanel(PokeListener p) {
		super(new BorderLayout());
		this.parent = p;
		this.df = DataFetch.getInstance();
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
		c = new GridBagConstraints();
		this.add = new JButton("Add Trainer");
		this.remove = new JButton("Remove Trainer #");
	}
	
	public void updateTable() {
		this.table.setModel(df.getMainTrainerTableModel());
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
					parent.showIndividualTrainerView(user);
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
				df.addTrainer(trainerName.getText());
				trainerName.setText(DEFAULT);
				updateTable();
			}
		});
		this.remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!trainerName.getText().equals(DEFAULT)) {
					df.removeTrainer(trainerName.getText());
					trainerName.setText(DEFAULT);
					updateTable();
				}
			}
		});
	}

	private void fillComponents() {
		this.setGridBagConstraints(0, 0, 0, 0, 0, 3, 1);
		this.right.add(trainerName, c);
		this.setGridBagConstraints(1, 0, 0, 0, 0, 1, 1);
		this.right.add(add, c);
		this.setGridBagConstraints(1, 1, 0, 0, 0, 1, 1);
		this.right.add(remove, c);
		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.EAST);
		this.left.add(jsp, BorderLayout.CENTER);
	}
}
