import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This program creates a course object and tests that it can be added to the DB.
 * @author: Christy and Thinh and Jason
 */

public class Test {
	
	

	public static void main(String[] args) throws SQLException, IOException {
		Database.clearTable();
		
		ArrayList<Course> udacity = Udacity.fetchCourses();
		ArrayList<Course> futurelearn = FutureLearn.fetchCourses();

		for (Course c : udacity)
			Database.insertCourse(c);
		for (Course c : futurelearn)
			Database.insertCourse(c);
		
		Database.toHtml();
	}
}