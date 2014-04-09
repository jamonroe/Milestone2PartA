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
	   static final String DB_URL = "jdbc:mysql://www.sjsu-cs.org/cs160/2014spring/sec2group2/phpmyadmin/";
	   
	   //  Database credentials
	   static final String DATABASE = "sjsucsor_160s2g22014s";
	   static final String USER = "sjsucsor_s2g214s";
	   static final String PASS = "CourseCampDB";
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
					   "INSERT INTO course_data "
					 + "(title, short_desc, long_desc, course_link, video_link, start_date, "
					 + "course_length, course_image, category, site, course_fee, language, "
					 + "certificate, university, time_scraped) "
					 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 
			 PreparedStatement st2 = null;
			 String sqlQuery2 = 
					 "INSERT INTO coursedetails "
							 + "(id, profname, profimage) "
							 + "VALUES (?, ?, ?)";
			 
			 try {
				 st = Database.getConnection().prepareStatement(sqlQuery);
				 st2 = Database.getConnection().prepareStatement(sqlQuery2);
			 } catch (Exception e) {
				 e.printStackTrace();
				 return;
			 }

			 st.setString(1, course.getTitle());
			 st.setString(2, course.getShortDescription());
			 st.setString(3, course.getLongDescription());
			 st.setString(4, course.getCourseLink());
			 st.setString(5, course.getVideoLink());
			 st.setDate(6, course.getStartDate());
			 st.setInt(7, course.getCourseLength());
			 st.setString(8, course.getCourseImage());
			 st.setString(9, course.getCategory());
			 st.setString(10, course.getSite());
			 st.setInt(11, course.getCourseFee());
			 st.setString(12, course.getLanguage());
			 st.setString(13, course.getCertificate().enumToString());
			 st.setString(14, course.getUniversity());
			 st.setDate(15, course.getTimeScraped());
			 
			 st2.setInt(1, course.getID());
			 st2.setString(2, course.getProfName());
			 st2.setString(3, course.getProfImage());
			 
			 st.execute();	
			 st2.execute();
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
				{					
					 String title = rs.getString("title");
					 String course_link = rs.getString("course_link");
					 Date start_date = rs.getDate("start_date");
					 String university = rs.getString("university");
					 String course_image = rs.getString("course_image");
					 String profname = rs.getString("profname");
					 String profimage = rs.getString("profimage");
					 String short_desc = rs.getString("short_desc");
					 String long_desc = rs.getString("long_desc");
					 String video_link = rs.getString("video_link");
					 int course_length = rs.getInt("course_length");
					 String category = rs.getString("category");
					 String site = rs.getString("site");
					 int course_fee = rs.getInt("course_fee");
					 String language = rs.getString("language");
					 String certificate = rs.getString("certificate");
					 Date time_scraped = rs.getDate("time_scraped");
				
				returnString += "Title: " + title + "\n"
					 + "Category: " + category + "\n"
					 + "University: " + university + "\n"
					 + "Instructor: " + profname + "\n"
					 + "Instructor Image: " + profimage + "\n"
					 + "Course Link: " + course_link + "\n"
					 + "Site: " + site + "\n"
					 + "Video Link: " + video_link + "\n"
					 + "Start Date: " + start_date.toString() + "\n"
					 + "Duration: " + course_length + " Week(s)\n"
					 + "Short Description: " + short_desc + "\n"
					 + "Long Description: " + long_desc + "\n"
					 + "Image: " + course_image + "\n"
					 + "Course Fee: " + course_fee + "\n"
					 + "Language: " + language + "\n"
					 + "Certificate: " + certificate + "\n"
					 + "Time Scraped: " + time_scraped + "\n\n";
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
			sb.append("<td>Course Length</td>");
			sb.append("<td>University</td>");
			sb.append("<td>Instructor</td>");
			sb.append("<td>Instructor Image</td>");
			sb.append("<td>Short Description</td>");
			sb.append("<td>Long Description</td>");
			sb.append("<td>Course Link</td>");
			sb.append("<td>Video Link</td>");
			sb.append("<td>Course Image</td>");
			sb.append("<td>Category</td>");
			sb.append("<td>Site</td>");
			sb.append("<td>Course Fee</td>");
			sb.append("<td>Language</td>");
			sb.append("<td>Certificate</td>");
			sb.append("<td>Time Scraped</td>");
			sb.append("</tr>");
			
			PreparedStatement query = null;
			
			try{
				query = Database.getConnection().prepareStatement("SELECT * FROM course_details");
				ResultSet rs = query.executeQuery();
				while(rs.next()) 
				{					 
					 String title = rs.getString("title");
					 String course_link = rs.getString("course_link");
					 Date start_date = rs.getDate("start_date");
					 String university = rs.getString("university");
					 String course_image = rs.getString("course_image");
					 String profname = rs.getString("profname");
					 String profimage = rs.getString("profimage");
					 String short_desc = rs.getString("short_desc").substring(0, 30) + "...";
					 String long_desc = rs.getString("long_desc").substring(0, 30) + "...";
					 String video_link = rs.getString("video_link");
					 int course_length = rs.getInt("course_length");
					 String category = rs.getString("category");
					 String site = rs.getString("site");
					 int course_fee = rs.getInt("course_fee");
					 String language = rs.getString("language");
					 String certificate = rs.getString("certificate");
					 Date time_scraped = rs.getDate("time_scraped");
					 
					 sb.append("<tr>");
					 sb.append("<td>" + title + "</td>");
					 sb.append("<td>" + course_link + "</td>");
					 sb.append("<td>" + start_date + "</td>");
					 sb.append("<td>" + university + "</td>");
					 sb.append("<td>" + course_image + "</td>");
					 sb.append("<td>" + profname + "</td>");
					 sb.append("<td>" + profimage + "</td>");
					 sb.append("<td>" + short_desc + "</td>");
					 sb.append("<td>" + long_desc + "</td>");
					 sb.append("<td>" + video_link + "</td>");
					 sb.append("<td>" + course_length + "</td>");
					 sb.append("<td>" + category + "</td>");
					 sb.append("<td>" + site + "</td>");
					 sb.append("<td>" + course_fee + "</td>");
					 sb.append("<td>" + language + "</td>");
					 sb.append("<td>" + certificate + "</td>");
					 sb.append("<td>" + time_scraped + "</td>");
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