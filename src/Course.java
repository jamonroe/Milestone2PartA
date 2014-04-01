import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This program models a course object, which holds all of its descriptive information.
 * @author: Christy
 */

public class Course {

	private int courseId;
	private String title;
	private String description;
	private String courseLink;
	private Date startDate;
	private int duration;
	private String category;
	private String university;
	private String instructor;
	// Course image?
	
	/**
	 * Constructs a course object. For the DB, the course_courseId is omitted in
	 * the construction of the object since the DB will automatically create
	 * a course courseId for each course added.
	 * 
	 * @param title the title of the course
	 * @param description the course description
	 * @param courseLink the link to the original course webpage
	 * @param startDate the date the course begins
	 * @param duration the duration of the course (in days)
	 * @param category the category of the course, i.e., "computer science"
	 * @param university the university which offers the course
	 * @param instructor the instructor which teaches the course
	 */
	public Course(String title, String description,
			String courseLink, Date startDate, int duration, String category,
			String university, String instructor){
		this.courseId = 0;
		this.title = title;
		this.description = description;
		this.courseLink = courseLink;
		this.startDate = startDate;
		this.duration = duration;
		this.category = category;
		this.university = university;
		this.instructor = instructor;
	}
	
	/**
	 * Constructs a course object. For the DB, the course_courseId is omitted in
	 * the construction of the object since the DB will automatically create
	 * a course courseId for each course added.
	 * 
	 * @param courseId the table id
	 * @param title the title of the course
	 * @param description the course description
	 * @param courseLink the link to the original course webpage
	 * @param startDate the date the course begins
	 * @param duration the duration of the course (in days)
	 * @param category the category of the course, i.e., "computer science"
	 * @param university the university which offers the course
	 * @param instructor the instructor which teaches the course
	 */
	public Course(int courseId, String title, String description,
			String courseLink, Date startDate, int duration, String category,
			String university, String instructor){
		this.courseId = courseId;
		this.title = title;
		this.description = description;
		this.courseLink = courseLink;
		this.startDate = startDate;
		this.duration = duration;
		this.category = category;
		this.university = university;
		this.instructor = instructor;
	}
	
	public Course() {
		// empty constructor in order to add data to a course incrementally in other classes
		
	}
	
	/**
	  Sets the title of the course.
	  @param newTitle the title to be added
	 */
	public void setTitle(String newTitle){
		title = newTitle;
	}
	
	/**
	  Returns the title of the course.
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	  Sets the course description.
	  @param newDescription the course description
	 */
	public void setDescription(String newDescription){
		description = newDescription;
	}
	
	/**
	  Returns the course description.
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	  Sets the link to the original course webpage.
	  @param link the link to the page
	 */
	public void setCourseLink(String link){
		courseLink = link;
	}
	
	/**
	  Returns the course link.
	 */
	public String getCourseLink(){
		return courseLink;
	}
	
	/**
	  Sets the start date of the course.
	  @param newDate the start date.
	 */
	public void setStartDate(Date newDate){
		startDate = newDate;
	}
	
	/**
	  Returns the course start date.
	 */
	public Date getStartDate(){
		return startDate;
	}
	
	/**
	  Sets the duration of the course (in days).
	  @param newDuration the number of days the course lasts
	 */
	public void setDuration(int newDuration){
		duration = newDuration;
	}
	
	/**
	  Returns the duration of the course.
	 */
	public int getDuration(){
		return duration;
	}
	
	/**	
	  Sets the course category.
	  @param newCategory the category
	 */
	public void setCategory(String newCategory){
		category = newCategory;
	}
	
	/**
	  Returns the course category.
	 */
	public String getCategory(){
		return category;
	}
	
	/**
	  Sets the university which offers the course.
	  @param newUni the university
	 */
	public void setUniversity(String newUni){
		university = newUni;
	}
	
	/**
	  Returns the university which offers the course.
	 */
	public String getUniversity(){
		return university;
	}
	
	/**
	  Sets the instructor of the course.
	  @param name the name of the instructor
	 */
	public void setInstructor(String name){
		instructor = name;
	}
	
	/**
	  Returns the instructor of the course.
	 */
	public String getInstructor(){
		return instructor;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	public void cleanseData() {
		if (title != null)
			title = title.replaceAll("&#39;", "'");
		else
			title = "";
		
		if (description != null)
			description = description.replaceAll("&#39;", "'");
		else
			description = "";
		
		if (category != null)
			category = category.replaceAll("&#39;", "'");
		else
			category = "";
		
		if (university != null)
			university = university.replaceAll("&#39;", "'");
		else
			university = "";
		
		if (instructor != null)
			instructor = instructor.replaceAll("&#39;", "'");
		else
			instructor = "";
	}
	
	public String toString() {
		String result = "Title: " + title + "\n";
		result += "University: " + university + "\n";
		result += "Instructor: " + instructor + "\n";
		result += "Course Link: " + courseLink + "\n";
		result += "Start Date: " + startDate + "\n";
		result += "Duration: " + duration + "\n";
		result += "Category: " + category + "\n";
		result += "Description: " + description + "\n";
		return result;
	}
}
