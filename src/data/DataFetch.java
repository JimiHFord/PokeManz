/**
 * 
 */
package data;

import java.io.*;
import java.sql.*;
import java.lang.*;

/**
 * This class will be used for the majority of Database IO
 * @author jimiford
 */
public class DataFetch {

	
	private static final String url = "jdbc:postgresql://reddwarf.cs.rit.edu/p48501h";
	
	/**
	 * Singleton Wrapper class 
	 * @author jimiford
	 *
	 */
	private static class SingletonWrapper {
		public static DataFetch INSTANCE = new DataFetch();
	}
	
	private DataFetch() {	}
	
	public static DataFetch getInstance() {
		return SingletonWrapper.INSTANCE;
	}
	
	
	
	public static void main(String[] args) {
		String user = "";
		String pass = "";
		
		
		try {
			if(args.length < 2) {	
				InputStreamReader inReader = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(inReader);
				System.out.print("Enter user id: ");
				user = br.readLine();
				System.out.print("This is bad, but may I have your password? - ");
				pass = br.readLine();

			} else {
				user = args[0];
				pass = args[1];
			}
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			Connection con;
			Statement stmt;
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, user, pass);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from pokemon_name;");
			while(rs.next()) {
				System.out.println("Pokemon ID: " + rs.getInt("national_id") + '\t' + rs.getString("english"));
			}
		} catch (ClassNotFoundException e) {
			System.err.println("PSQL Driver is not properly set up");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("This should compile.");
		//or should it?
		/*yes, it should*/
		//ryan commit test xD
		//bananarama
	}
}
