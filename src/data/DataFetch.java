/**
 * 
 */
package data;

/**
 * @author jimiford
 *
 */
public class DataFetch {

	
	
	private static class SingletonWrapper {
		public static DataFetch INSTANCE = new DataFetch();
	}
	
	private DataFetch() {	}
	
	public static DataFetch getInstance() {
		return SingletonWrapper.INSTANCE;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("This should compile.");
	}
}