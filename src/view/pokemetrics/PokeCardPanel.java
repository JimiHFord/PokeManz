/**
 * PokeCardPanel.java
 */
package view.pokemetrics;

import java.awt.CardLayout;

import javax.swing.JPanel;

/**
 * Creates a card layout to switch between the
 * base stats metrics and through the generations panel
 * 
 * @author Ryan Castner rrc9704
 */
@SuppressWarnings("serial")
public class PokeCardPanel extends JPanel{
	public PokemetricsPanel pmp;
	public PokeGenerationsPanel pgp;
	public final static String METRICS_CARD = "Metrics";
	public final static String GEN_CARD = "Generations";
	
	/**
	 * Constructor
	 */
	public PokeCardPanel(){
		super(new CardLayout());
		pmp = new PokemetricsPanel();
		pgp = new PokeGenerationsPanel();
		this.add(pmp, METRICS_CARD);
		this.add(pgp, GEN_CARD);
	}
}

