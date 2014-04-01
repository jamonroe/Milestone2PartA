import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/**
 * This program creates a course object and tests that it can be added to the DB.
 * @author: Christy and Thinh
 */

public class Test {

	public static void main(String[] args) throws SQLException, IOException {
		
		Date date = new Date(System.currentTimeMillis());
		 
		Course class1 = new Course("Computer Science", "Computer sciency things will be taught.", "www.udacity.org", 
				date, 7, "Computer Science", "Stanford", "Ronald MacDonald");
		
		Course class2 = new Course("Statistics", "Probability will be taught.", "www.futurelearn.com", 
				date, 5, "Math", "UC Berkeley", "Mathew Goodies");

		Database.clearTable();
		Database.insertCourse(class1);
		Database.insertCourse(class2);
		System.out.println(Database.printCourses());
		Database.toHtml();
		Database.close();

	}
}