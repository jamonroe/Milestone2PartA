import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class contains all the info to connect to the DB.
 * @author Christy
 */

public class Database {
	
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	   //Default port number
	   static final String DB_URL = "jdbc:mysql://localhost:3306/";
	   
	   //  Database credentials
	   static final String DATABASE = "coursecamp";
	   static final String USER = "demo";
	   static final String PASS = "passwort";
	   private static Connection conn = null;

	   /**
	    * Requires that MySQL is running, the database exists, the user exists,
	    * the user has access, and that the port is the default port.
	    * 
	    * @return The database Connection object
	    * @throws SQLException
	    */
	   public static Connection getConnection() throws SQLException
	   {
	      try
	      {
	    	  if (conn == null)
		      {
		         Class.forName(JDBC_DRIVER); //Register JDBC Driver
		         conn = DriverManager.getConnection(DB_URL + DATABASE, USER, PASS);
		      }
	      }
	      catch(ClassNotFoundException e)
	      {
	    	  System.out.println("Error with JDBC Driver!");
	      }
	      return conn;
	   }
}