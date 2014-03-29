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
	    */
	   protected static Connection getConnection() throws SQLException
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

		/**
		  Inserts a course into the database.
		  @param course the course to be added (course id not needed)
		 */
		public static void insertCourse(Course course) throws SQLException
		{
			 PreparedStatement st = null;
			 String sqlQuery =
					   "INSERT INTO course_details "
					 //+ "(title, description, course_link, start_date, duration, category, university, instructor) "
					 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 
			 st = Database.getConnection().prepareStatement(sqlQuery);
			 
			 st.setInt(1, course.getId());
			 st.setString(2, course.getTitle());
			 st.setString(3, course.getDescription());
			 st.setString(4, course.getCourseLink());
			 st.setDate(5, course.getStartDate());
			 st.setInt(6, course.getDuration());
			 st.setString(7, course.getCategory());
			 st.setString(8, course.getUniversity());
			 st.setString(9, course.getInstructor());
			 st.execute();		
		}
}