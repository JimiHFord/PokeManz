/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author James
 *
 */
public class PokeWelcome extends JPanel {

	private URL url = PokeWelcome.class.getResource("pokewelcome.jpg");
	private JButton enter;
	private PokeListener listen;
	private JPanel buttonPanel;
	private GridBagConstraints c;
	
	public PokeWelcome(PokeListener listen) {
		super(new BorderLayout());
		this.listen = listen;
		c = new GridBagConstraints();
		this.createComponents();
		this.fillComponents();
		this.initActions();
	}
	
	private void createComponents() {
		enter = new JButton("Enter");
		buttonPanel = new JPanel(new GridBagLayout());
	}
	
	private void fillComponents() {
		this.setGridBagConstraints(5, 0, 0, 0, 0, 1, 1);
		buttonPanel.add(enter, c);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void initActions() {
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listen.act(GUIEntryPoint.ENTER, null);
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
