import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class contains all the info to connect to the DB.
 * @author Christy and Thinh and Jason
 */

public class Database {
	
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	   //Default port number
	   //static final String DB_URL = "jdbc:mysql://www.sjsu-cs.org/cs160/2014spring/sec2group2/phpmyadmin:3306/";
	   //static final String DB_URL = "jdbc:mysql://69.89.31.134:3306/";
	   static final String DB_URL = "jdbc:mysql://localhost:3306/";
	   
	   //  Database credentials
	   static final String DATABASE = "moocs160";
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
					   "INSERT INTO course_data "
					 + "(title, short_desc, long_desc, course_link, video_link, start_date, "
					 + "course_length, course_image, category, site, course_fee, language, "
					 + "certificate, university, time_scraped) "
					 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 
			 try {
				 st = Database.getConnection().prepareStatement(sqlQuery);
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
			 
			 st.execute();	
			 
			 
			 // For each professor in the course, add the name and image to the database.
			 HashMap<String, String> profList = course.getProfessors();
			 for (String key : profList.keySet()){			 
				 
				 PreparedStatement st2 = null;
				 String sqlQuery2 = 
						 "INSERT INTO coursedetails "
								 + "(profname, profimage) "
								 + "VALUES (?, ?)";
				 
				 try {
					 st2 = Database.getConnection().prepareStatement(sqlQuery2);
				 } catch (Exception e) {
					 e.printStackTrace();
					 return;
				 }
				 
				 if (key.length() > 30)
					 st2.setString(1, key.substring(0, 29));
				 else
					 st2.setString(1, key);
				 st2.setString(2, profList.get(key));
				 
				 st2.execute();
			 }
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
			/* ****************** */
			/*                    */
			/* Course_data Table  */
			/*                    */
			/* ****************** */
			
			StringBuilder sb = new StringBuilder();
			sb.append("<html>");
			sb.append("<body>");
			
			sb.append("<title>course_data</title>");
			
			sb.append("<table border=\"1\">");
			
			sb.append("<tr>");
			sb.append("<td>Title</td>");
			sb.append("<td>Course Link</td>");
			sb.append("<td>Start Date</td>");
			sb.append("<td>Course Length</td>");
			sb.append("<td>University</td>");
			sb.append("<td>Course Image</td>");
			sb.append("<td>Short Description</td>");
			sb.append("<td>Long Description</td>");
			sb.append("<td>Video Link</td>");
			sb.append("<td>Category</td>");
			sb.append("<td>Site</td>");
			sb.append("<td>Course Fee</td>");
			sb.append("<td>Language</td>");
			sb.append("<td>Certificate</td>");
			sb.append("<td>Time Scraped</td>");
			sb.append("</tr>\n");
			
			PreparedStatement query = null;
			
			try{
				query = Database.getConnection().prepareStatement("SELECT * FROM course_data");
				ResultSet rs = query.executeQuery();
				while(rs.next()) 
				{					 
					 String title = rs.getString("title");
					 String course_link = rs.getString("course_link");
					 Date start_date = rs.getDate("start_date");
					 int course_length = rs.getInt("course_length");
					 String university = rs.getString("university");
					 String short_desc = rs.getString("short_desc").substring(0, 30) + "...";
					 String long_desc = rs.getString("long_desc").substring(0, 30) + "...";
					 String video_link = rs.getString("video_link");
					 String course_image = rs.getString("course_image");
					 String category = rs.getString("category");
					 String site = rs.getString("site");
					 int course_fee = rs.getInt("course_fee");
					 String language = rs.getString("language");
					 String certificate = rs.getString("certificate");
					 Date time_scraped = rs.getDate("time_scraped");
					 
					 sb.append("<tr>");
					 sb.append("<td>" + title + "</td>");
					 sb.append("<td><a href='" + course_link + "'>" + course_link + "</a></td>");
					 sb.append("<td>" + start_date + "</td>");
					 sb.append("<td>" + course_length + " weeks</td>");
					 sb.append("<td>" + university + "</td>");
					 sb.append("<td><img src='" + course_image + "' height='60' width='70'></td>");
					 sb.append("<td>" + short_desc + "</td>");
					 sb.append("<td>" + long_desc + "</td>");
					 // Check if video_link should be a link or N/A
					 if (video_link.equalsIgnoreCase("N/A"))
						 sb.append("<td>" + video_link + "</td>");
					 else
						 sb.append("<td><a href='" + video_link + "'>" + video_link + "</a></td>");
					 sb.append("<td>" + category + "</td>");
					 sb.append("<td><a href='" + site + "'>" + site + "</a></td>");
					 sb.append("<td>" + course_fee + "</td>");
					 sb.append("<td>" + language + "</td>");
					 sb.append("<td>" + certificate + "</td>");
					 sb.append("<td>" + time_scraped + "</td>");
					 sb.append("</tr>\n");
				}
			} catch (Exception e) {
				 e.printStackTrace();
				 return;
			}
			sb.append("</table>");
			sb.append("</body>");
			sb.append("</html>");
			
			/* ******************* */
			/*                     */
			/* Coursedetails Table */
			/*                     */
			/* ******************* */
			
			StringBuilder sb2 = new StringBuilder();
			sb2.append("<html>");
			sb2.append("<body>");
			
			sb2.append("<title>course_data</title>");
			
			sb2.append("<table border=\"1\">");
			
			sb2.append("<tr>");
			sb2.append("<td>Instructor</td>");
			sb2.append("<td>Instructor Image</td>");
			sb2.append("</tr>\n");
			
			PreparedStatement query2 = null;
			
			try{
				query2 = Database.getConnection().prepareStatement("SELECT * FROM coursedetails");
				ResultSet rs2 = query2.executeQuery();
				while(rs2.next()) 
				{					 
					 String profName = rs2.getString("profname");
					 String profImage = rs2.getString("profimage");
					 
					 sb2.append("<tr>");
					 sb2.append("<td>" + profName + "</td>");
					 sb2.append("<td><img src='" + profImage + "' height='60' width='70'></td>");
					 sb2.append("</tr>\n");
				}
			} catch (Exception e) {
				 e.printStackTrace();
				 return;
			}
			sb2.append("</table>");
			sb2.append("</body>");
			sb2.append("</html>");			
			
			
			
			FileWriter fstream = new FileWriter("course_data.html");
			FileWriter fstream2 = new FileWriter("coursedetails.html");
			BufferedWriter out = new BufferedWriter(fstream);
			BufferedWriter out2 = new BufferedWriter(fstream2);
			out.write(sb.toString());
			out2.write(sb2.toString());
			out.close();
			out2.close();
		}
		
		/**
		  Clears the entire table.
		 */
		public static void clearTable() throws SQLException
		{
			 PreparedStatement st = null;
			 String sqlStatement1 = "DELETE FROM course_data WHERE id > -1";
			 String sqlStatement2 = "DELETE FROM coursedetails WHERE id > -1";
			 try {
				 st = Database.getConnection().prepareStatement(sqlStatement1);
				 st.execute();
				 st = Database.getConnection().prepareStatement(sqlStatement2);
				 st.execute();
			 } catch (Exception e) {
				 e.printStackTrace();
				 return;
			 }		
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
		
		public static boolean testConnection() {
			try {
				 Database.getConnection();
				 return true;
			 } catch (Exception e) {
				 e.printStackTrace();
				 return false;
			 }
		}
}