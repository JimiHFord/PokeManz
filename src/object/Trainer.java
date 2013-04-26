/**
 * 
 */
package object;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jimiford
 *
 */
public class Trainer {

	public static final int MAX_PARTY_SIZE = 6;
	
	private int id;
	private String name;
	private List<Pokemon> party;
	
	public Trainer(int id, String name) {
		this.id = id;
		this.name = name;
		this.party = new ArrayList<Pokemon>(MAX_PARTY_SIZE);
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Pokemon> getParty() {
		return this.party;
	}
	
	public boolean add(Pokemon p) {
		boolean retval = false;
		if(party.size() < MAX_PARTY_SIZE) {
			party.add(p);
			retval = true;
		}
		return retval;
	}
}
