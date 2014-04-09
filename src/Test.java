import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This program creates a course object and tests that it can be added to the DB.
 * @author: Christy and Thinh
 */

public class Test {
	
	

	public static void main(String[] args) throws SQLException, IOException {
		
//		ArrayList<Course> udacity = Udacity.fetchCourses();
//		ArrayList<Course> futurelearn = FutureLearn.fetchCourses();
//		
//		Database.clearTable();
//
//		for (Course c : udacity)
//			Database.insertCourse(c);
//		for (Course c : futurelearn)
//			Database.insertCourse(c);
//		
//		Database.toHtml();
//		Database.close();
		
		
		Date date = new Date(System.currentTimeMillis());				 

		Course newClass = new Course("a", "b", "c", "d", "e", "f", "g", date, 7, "Computer Science", "Stanford", "Ronald MacDonald", 7, "a", Course.Certificate.NO, "f", date);

		Database.insertCourse(newClass);	
	}
}