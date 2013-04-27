/**
 * 
 */
package data;

import java.io.*;
import java.sql.*;
import java.lang.*;

/**
 * This class will be used for the majority of Database IO
 * @author JimiHFord jhf3617
 */
public class DataFetch {

	/*
	 * static data
	 */
	private static final String driver = "org.postgresql.Driver"; 
	private static final String url = "jdbc:postgresql://reddwarf.cs.rit.edu/";
	
	/*
	 * instance variables
	 */
	private Statement stmt;
	private Connection con;
	
	/**
	 * Singleton Wrapper class 
	 * @author JimiHFord jhf3617
	 *
	 */
	private static class SingletonWrapper {
		private static DataFetch INSTANCE = new DataFetch();
	}

	/**
	 * Use as an alternative to a constructor. This will ensure singleton
	 * behavior
	 * @return	the singleton instance of DataFetch
	 */
	public static DataFetch getInstance() {
		return SingletonWrapper.INSTANCE;
	}
	
	/**
	 * Private constructor ensures no extraneous DataFetchers will be created
	 */
	private DataFetch() {	}
	
	
	/**
	 * 
	 * @param url	full url to database including database name (not needed though)
	 * @param user	username of the psql account
	 * @param pass	that user's password
	 * @throws SQLException				if something is wrong with the connection
	 * @throws ClassNotFoundException	if the driver for psql can not be found
	 */	
	public void establishConnection(String url, String user, String pass) 
			throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		this.con = DriverManager.getConnection(url + user, user, pass);
	}
	
	/**
	 * Creates a statement for the DataFetch object to execute queries with.
	 * @throws SQLException
	 */
	public void createStatement() throws SQLException {
		this.stmt = con.createStatement();
	}
	
	/**
	 * tests queries
	 * @param args	command line arguments
	 * 		args[0]	username
	 * 		args[1] password
	 */
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
			con = DriverManager.getConnection(url + user, user, pass);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from pokemon_name;");
			while(rs.next()) {
				System.out.println("Pokemon ID: " + rs.getInt("national_id") + '\t' + rs.getString("english"));
			}
		} catch (ClassNotFoundException e) {
			System.err.println("PSQL Driver is not properly set up");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
