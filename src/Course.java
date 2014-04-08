/**
 * This program models a course object, which holds all of its descriptive information.
 * @author: Christy
 */

public class Course {

	private int courseId;
	private String title;
	private String description;
	private String courseLink;
	private String startDate;
	private int duration;
	private String university;
	private String instructor;
	private String image;
	
	/**
	 * Constructs a course object. For the DB, the course_courseId is omitted in
	 * the construction of the object since the DB will automatically create
	 * a course courseId for each course added.
	 * 
	 * @param title the title of the course
	 * @param description the course description
	 * @param courseLink the link to the original course webpage
	 * @param startDate the String the course begins
	 * @param duration the duration of the course (in weeks)
	 * @param university the university which offers the course
	 * @param instructor the instructor which teaches the course
	 * @param image the image url for the course
	 */
	public Course(String title, String description,
			String courseLink, String startDate, int duration,
			String university, String instructor, String image){
		this.courseId = 0;
		this.title = title;
		this.description = description;
		this.courseLink = courseLink;
		this.startDate = startDate;
		this.duration = duration;
		this.university = university;
		this.instructor = instructor;
		this.image = image;
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
	 * @param startDate the String the course begins
	 * @param duration the duration of the course (in weeks)
	 * @param university the university which offers the course
	 * @param instructor the instructor which teaches the course
	 * @param image the image url for the course
	 */
	public Course(int courseId, String title, String description,
			String courseLink, String startDate, int duration,
			String university, String instructor, String image){
		this.courseId = courseId;
		this.title = title;
		this.description = description;
		this.courseLink = courseLink;
		this.startDate = startDate;
		this.duration = duration;
		this.university = university;
		this.image = image;
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
	  Sets the start String of the course.
	  @param newString the start String.
	 */
	public void setStartDate(String newString){
		startDate = newString;
	}
	
	/**
	  Returns the course start String.
	 */
	public String getStartDate(){
		return startDate;
	}
	
	/**
	  Sets the duration of the course (in weeks).
	  @param newDuration the number of weeks the course lasts
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
	
	/**
	  Sets the course ID
	  @param courseId the id
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	/**
	 * Sets the course image address
	 * @param image the image url
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * Returns the course image address
	 * @return the image url
	 */
	public String getImage() {
		return image;
	}
	
	/**
	  @return the course ID
	 */
	public int getCourseId() {
		return courseId;
	}
	
	/**
	  Reverts important values back to null.
	 */
	public void cleanseData() {
		if (title == null)
			title = "";
		
		if (description == null)
			description = "";
		
		if (image == null)
			image = "";
		
		if (university == null)
			university = "";
		
		if (instructor == null)
			instructor = "";
		
		if (startDate == null)
			startDate = "";
	}
	
	/**
	 * Displays the course as a list of its components.
	 */
	public String toString() {
		String result = "Title: " + title + "\n";
		result += "University: " + university + "\n";
		result += "Instructor: " + instructor + "\n";
		result += "Course Link: " + courseLink + "\n";
		result += "Start Date: " + startDate + "\n";
		result += "Duration: " + duration + " Week(s)\n";
		result += "Description: " + description + "\n";
		result += "Image: " + image + "\n";
		return result;
	}
}
