/**
 * 
 */
package data;

//import org.apache.commons.codec.digest.DigestUtils;

/**
 * This class provides basic utility functions for the GUI
 * so that some post processing can be separated
 * @author jimiford
 *
 */
public class PokeUtils {

	/*
	 * All weaknesses of each type of Pokemon
	 */
	public static final String NORMAL = "Normal";
	public static final String FIRE = "Fire";
	public static final String WATER = "Water";
	public static final String ELECTRIC = "Electric";
	public static final String GRASS = "Grass";
	public static final String ICE = "Ice";
	public static final String FIGHTING = "Fighting";
	public static final String POISON = "Poison";
	public static final String GROUND = "Ground";
	public static final String FLYING = "Flying";
	public static final String PSYCHIC = "Psychic";
	public static final String BUG = "Bug";
	public static final String ROCK = "Rock";
	public static final String GHOST = "Ghost";
	public static final String DRAGON = "Dragon";
	public static final String DARK = "Dark";
	public static final String STEEL = "Steel";
	private static final String normal[] = { FIGHTING};
	private static final String fire[] = { WATER, GROUND, ROCK };
	private static final String water[] = { ELECTRIC, GRASS };
	private static final String electric[] = { GROUND };
	private static final String grass[] = { FIRE, ICE, POISON, FLYING, BUG };
	private static final String ice[] = { FIRE, FIGHTING, ROCK, GHOST };
	private static final String fighting[] = { FLYING, PSYCHIC };
	private static final String poison[] = { GROUND, PSYCHIC };
	private static final String ground[] = { WATER, GRASS, ICE };
	private static final String flying[] = { ELECTRIC, ICE, ROCK };
	private static final String psychic[] = { BUG, GHOST, DARK };
	private static final String bug[] = { FIRE, FLYING, ROCK };
	private static final String rock[] = { WATER, GRASS, FIGHTING, GROUND, GHOST };
	private static final String ghost[] = { GHOST, DARK };
	private static final String dragon[] = { ICE, DRAGON };
	private static final String dark[] = { FIGHTING, BUG };
	private static final String steel[] = { FIRE, FIGHTING, GROUND };

	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	////////////////////DON'T TOUCH THIS///////////////////////////
	/*//////////*/private static final int[] keyChain = {//////////
	/*//////////*/3, -1, 2, -2, 3, 1, -3, 2, 1, -3, 1  };//////////
	////////////////////DON'T TOUCH THIS///////////////////////////
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	private static final int PRIME = 977;
	private static final int INT = 429857;
	private static final int MODLENGTH = 1000000;

	/**
	 * Hashes password
	 *
	 * @param pass the password to be hashed
	 * @return integer representing the password in hash form
	 */
	public static int doPass(char[] pass) {
		int hashed = 0;
		for (int i = 0; i < pass.length; i++){
			hashed = hashed + ((pass[i] * PRIME) + INT);
		}
		return hashed % MODLENGTH;
	}

	/**
	 * @param str the string to be encrypted
	 * @return an encryped version of the passed in string
	 */
	public static String encrypt(String str) {
		String encrypted = ""; 
		str = str.trim().replaceAll("( )+", " ");
		int length = str.length();
		int keyChainLength = keyChain.length;
		char eachChar;
		int keyIndex = 0;
		for(int i = 0; i < length; i++) {
			if(keyIndex >= keyChainLength) keyIndex = 0;
			eachChar = str.charAt(i);
			if(eachChar != '\n') {
				eachChar += keyChain[keyIndex++];
				encrypted += eachChar;
			} else {
				encrypted += '\n';
			}
		}

		return encrypted;
	}

	/**
	 * @param str the string to be "decrypted"
	 * @return the "decrypted" version of the string
	 */
	public static String decrypt(String str) {
		String encrypted = "";
		int length = str.length();
		int keyChainLength = keyChain.length;
		char eachChar;
		int keyIndex = 0;
		for(int i = 0; i < length; i++) {
			if(keyIndex >= keyChainLength) keyIndex = 0;
			eachChar =  str.charAt(i);
			if(eachChar != '\n') {
				eachChar -= keyChain[keyIndex++];
				encrypted += eachChar;
			} else {
				encrypted += '\n';
			}
		}

		return encrypted;
	}

//	/*
//	 * All the different types of Pokemon
//	 */
//	public enum Type {
//		Normal,
//		Fire,
//		Water,
//		Electric,
//		Grass,
//		Ice,
//		Fighting,
//		Poison,
//		Ground,
//		Flying,
//		Psychic,
//		Bug,
//		Rock,
//		Ghost,
//		Dragon,
//		Dark,
//		Steel
//	}

	/**
	 * This returns all the weaknesses of the type that is passed into this function
	 * @param t the type to calculate the weaknesses of
	 * @return weaknesses associated with the type t
	 */
	public static String[] getWeakness(String t) {
		switch(t) {
		case NORMAL:
			return normal;
		case FIRE:
			return fire;
		case WATER:
			return water;
		case ELECTRIC:
			return electric;
		case GRASS:
			return grass;
		case ICE:
			return ice;
		case FIGHTING:
			return fighting;
		case POISON:
			return poison;
		case GROUND:
			return ground;
		case FLYING:
			return flying;
		case PSYCHIC:
			return psychic;
		case BUG:
			return bug;
		case ROCK:
			return rock;
		case GHOST:
			return ghost;
		case DRAGON:
			return dragon;
		case DARK:
			return dark;
		case STEEL:
			return steel;
		default:
			System.err.println("Internal error on get weakness");
			return null;
		}
	}


	/**
	 * This counts the number of weaknesses of the passed in type
	 * @param t	the type to count the weaknesses of
	 * @return	the number of weaknesses associated with type t
	 */
	public static int countWeakness(String t) {
		switch(t) {
		case NORMAL:
			return normal.length;
		case FIRE:
			return fire.length;
		case WATER:
			return water.length;
		case ELECTRIC:
			return electric.length;
		case GRASS:
			return grass.length;
		case ICE:
			return ice.length;
		case FIGHTING:
			return fighting.length;
		case POISON:
			return poison.length;
		case GROUND:
			return ground.length;
		case FLYING:
			return flying.length;
		case PSYCHIC:
			return psychic.length;
		case BUG:
			return bug.length;
		case ROCK:
			return rock.length;
		case GHOST:
			return ghost.length;
		case DRAGON:
			return dragon.length;
		case DARK:
			return dark.length;
		case STEEL:
			return steel.length;
		default:
			System.err.println("Internal error on count weakness");
			return 0;
		}
	}
	//for testing
	public static void main(String[] args) {
		System.out.println(PokeUtils.doPass(new char[]{'a'}));
		System.out.println(PokeUtils.doPass(new char[]{'j'}));
		System.out.println(PokeUtils.doPass(new char[]{'d'}));
		System.out.println(PokeUtils.doPass(new char[]{'b'}));
		System.out.println(PokeUtils.doPass(new char[]{'a'}));
		
	}

	/**
	 * @param password one of the char[] to be compared
	 * @param password2 the other char[] to be compared
	 */
	public static boolean equals(char[] password, char[] password2) {
		boolean same = true;
		if(password.length == password2.length) {
			for(int i = 0; i < password.length && same; i++) {
				same = password[i] == password2[i];
			}
		} else {
			same = false;
		}
		return same;
	}
}
