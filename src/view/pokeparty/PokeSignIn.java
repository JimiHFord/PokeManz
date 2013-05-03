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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import view.PokeListener;

import data.DataFetch;
import data.PokeUtils;

/**
 * @author James
 *
 */
@SuppressWarnings("serial")
public class PokeSignIn extends JPanel {

	private String msg = "Password incorrect.";
	private String title = "Authentication Error";
	
	private DataFetch df;
	private Integer userID;
	protected JPasswordField pass;
	private PokeListener listener;
	private JButton back;
	private JPanel center;
	private GridBagConstraints c;
	
	
	public PokeSignIn(PokeListener listener) {
		super(new BorderLayout());
		this.listener = listener;
		center = new JPanel(new GridBagLayout());
		userID = new Integer(0);
		pass = new JPasswordField(8);
		back = new JButton("Back");
		c = new GridBagConstraints();
		df = DataFetch.getInstance();
		fillComponents();
		initActions();
	}
	
	private void fillComponents() {
		this.pass.setSize(new Dimension(110, 25));
		this.setGridBagConstraints(0, 0, 0, 0, 0, 3, 1);
		this.center.add(pass, c);
		this.setGridBagConstraints(1, 1, 0, 0, 0, 1, 1);
		this.center.add(back, c);
		this.add(center, BorderLayout.CENTER);
	}
	
	public void setID(Integer id) {
		this.userID = id;
	}
	
	private void initActions() {
		this.back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.showView(PokePartyPanel.MANAGE_TRAINERS_PANEL);
			}
			
		});
		this.pass.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
//					System.out.println(PokeUtils.doPass(pass.getPassword()));
					if(df.login(userID, PokeUtils.doPass(pass.getPassword()))) {
						listener.showIndividualTrainerView(userID);
					} else {
						JOptionPane.showMessageDialog(null, msg, title,
								JOptionPane.ERROR_MESSAGE, null);
					}
					pass.setText("");
				}
			}
			
		});
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
}
