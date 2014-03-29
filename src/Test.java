import java.sql.Date;
import java.sql.SQLException;

/**
 * This program creates a course object and tests that it can be added to the DB.
 * @author: Christy
 */

public class Test {

	public static void main(String[] args) throws SQLException {
		
		Date date = new Date(System.currentTimeMillis());
		 
		Course newClass = new Course("Computer Science", "Computer sciency things will be taught.", "www.udacity.org", 
				date, 7, "Computer Science", "Stanford", "Ronald MacDonald");
		
		Course.insertCourse(newClass);	
	}
}