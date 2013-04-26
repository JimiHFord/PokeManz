/**
 * 
 */
package object;

/**
 * @author jimiford
 *
 */
public class Trainer {

	private int id;
	private String name;
	
	public Trainer(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
}
