package view;

public interface PokeListener {

	
	public static final String TAB_VIEW = "TAB_VIEW";
	public static final String TRAINER_VIEW = "TRAINER_VIEW";	
	
	/**
	 * Child actors call this method with instructions
	 * for parent to follow to update itself
	 * @param command
	 * @param argument
	 */
	public void act(String command, String argument);

}
