/**
 * 
 */
package view.pokedex;

import javax.swing.JPanel;

import data.DataFetch;

/**
 * @author Felipe
 *
 */
@SuppressWarnings("serial")
public class PokedexScreen extends JPanel {
	
	private DataFetch df;
	
	public PokedexScreen(){
		this.df = DataFetch.getInstance();
	}
	
}
