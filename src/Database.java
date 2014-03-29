import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	    * @throws ClassNotFoundException 
	    */
	   protected static Connection getConnection() throws SQLException, ClassNotFoundException
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
	    	  System.out.println("You likely need to add the MySQL JDBC Connector jar to the build path");
	    	  throw e;
	      }
	      return conn;
	   }

		/**
		  Inserts a course into the database.
		  @param course the course to be added (course id not needed)
		 */
		public static void insertCourse(Course course) throws SQLException
		{
			 PreparedStatement st = null;
			 String sqlQuery =
					   "INSERT INTO course_details "
					 + "(title, description, course_link, start_date, duration, category, university, instructor) "
					 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			 try {
				 st = Database.getConnection().prepareStatement(sqlQuery);
			 } catch (Exception e) {
				 e.printStackTrace();
				 return;
			 }

			 st.setString(1, course.getTitle());
			 st.setString(2, course.getDescription());
			 st.setString(3, course.getCourseLink());
			 st.setDate(4, course.getStartDate());
			 st.setInt(5, course.getDuration());
			 st.setString(6, course.getCategory());
			 st.setString(7, course.getUniversity());
			 st.setString(8, course.getInstructor());
			 st.execute();		
		}
		
		/**
		  Clears the entire table.
		 */
		public static void clearTable() throws SQLException
		{
			 PreparedStatement st = null;
			 String sqlStatement = "DELETE FROM course_details WHERE course_id > -1";
			 try {
				 st = Database.getConnection().prepareStatement(sqlStatement);
			 } catch (Exception e) {
				 e.printStackTrace();
				 return;
			 }
			 st.execute();		
		}
		
		/**
		 * Closes the connection.
		 */
		public static void close() throws SQLException
		{
			try {
				Database.getConnection().close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
}