/**
 * CryButton.java
 */
package view.pokedex;

import javax.swing.JButton;

/**
 * Implements a custom cry button for the pokedex panel
 * @author jimiford
 */
@SuppressWarnings("serial")
public class CryButton extends JButton {

	private int national_id;
	
	public CryButton(int national_id) {
		super("Play Sound");
		setNationalID(national_id);
	}
	
	public void setNationalID(int national_id) {
		this.national_id = national_id;
	}
	
	public int getNationalID() {
		return this.national_id;
	}
}
