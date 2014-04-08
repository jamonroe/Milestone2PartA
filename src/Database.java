import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class contains all the info to connect to the DB.
 * @author Christy and Thinh and Jason
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
					 + "(title, description, course_link, start_date, duration, university, instructor, course_image) "
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
			 st.setString(4, course.getStartDate());
			 st.setInt(5, course.getDuration());
			 st.setString(6, course.getUniversity());
			 st.setString(7, course.getInstructor());
			 st.setString(8, course.getImage());
			 st.execute();		
		}
		
		/*
		 * Print results from database.
		 */
		public static String printCourses() throws SQLException
		{
			PreparedStatement query = null;
			String returnString = " ";
			
			try{
				query = Database.getConnection().prepareStatement("SELECT * FROM course_details");
				ResultSet rs = query.executeQuery();
				while(rs.next()) 
				{String title = rs.getString("title");
				 String description = rs.getString("description");
				 String course_link = rs.getString("course_link");
				 Date start_date = rs.getDate("start_date");
				 int duration = rs.getInt("duration");
				 String university = rs.getString("university");
				 String instructor = rs.getString("instructor");
				 String course_image = rs.getString("course_image");
				 
				returnString += "Title: " + title + "\n" 
						+ "Description: " + description + "\n"
						+ "Course Link: " + course_link + "\n"
						+ "Start Date: " + start_date + "\n"
						+ "Duration: " + duration + "\n"
						+ "Univeristy: " + university + "\n"
						+ "Instructor: " + instructor + "\n"
						+ "Course Image: " + course_image + "\n\n"; 
				}
			} catch (Exception e) {
				 e.printStackTrace();
				 return "";
			}
			
			return returnString;		
		}
		
		/*
		 * Set data to html table.
		 */
		public static void toHtml() throws SQLException, IOException
		{
			StringBuilder sb = new StringBuilder();
			sb.append("<html>");
			sb.append("<body>");
			
			sb.append("<title>CourseCamp</title>");
			
			sb.append("<table border=\"1\" style=\"width:2000px\">");
			
			sb.append("<tr>");
			sb.append("<td>Title</td>");
			sb.append("<td>Start Date</td>");
			sb.append("<td>Duration</td>");
			sb.append("<td>University</td>");
			sb.append("<td>Instructor</td>");
			sb.append("<td>Description</td>");
			sb.append("<td>Course Link</td>");
			sb.append("<td>Course Image</td>");
			sb.append("</tr>");
			
			PreparedStatement query = null;
			
			try{
				query = Database.getConnection().prepareStatement("SELECT * FROM course_details");
				ResultSet rs = query.executeQuery();
				while(rs.next()) 
				{String title = rs.getString("title");
				 String description = rs.getString("description").substring(0, 30) + "...";
				 String course_link = rs.getString("course_link");
				 String start_date = rs.getString("start_date");
				 int duration = rs.getInt("duration");
				 String university = rs.getString("university");
				 String instructor = rs.getString("instructor");
				 String course_image = rs.getString("course_image");
				 
				 sb.append("<tr>");
				 sb.append("<td>" + title + "</td>");
				 sb.append("<td>" + start_date + "</td>");
				 sb.append("<td>" + duration + "</td>");
				 sb.append("<td>" + university + "</td>");
				 sb.append("<td>" + instructor + "</td>");
				 sb.append("<td>" + description + "</td>");
				 sb.append("<td>" + course_link + "</td>");
				 sb.append("<td>" + course_image + "</td>");
				 sb.append("</tr>");
				}
			} catch (Exception e) {
				 e.printStackTrace();
				 return;
			}
			sb.append("</table>");
			sb.append("</body>");
			sb.append("</html>");
			
			FileWriter fstream = new FileWriter("coursecamp.html");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(sb.toString());
			out.close();
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