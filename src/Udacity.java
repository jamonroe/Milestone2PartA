import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Udacity {
	
	public static String URL = "https://www.udacity.com/courses#!/All";
	public static String courseURL = "http://www.udacity.com/course/";
	public static String JSON_URL = "https://www.udacity.com/api/nodes?depth=2&fresh=false&keys[]=course_catalog&projection=catalog&required_behavior=find";
	
	public static void main(String[] args) {
		fetchCourses();
	}
	
	public static ArrayList<Course> fetchCourses() {
		ArrayList<Course> courseList = initializeCourses();
		Iterator<Course> iterator = courseList.iterator();
		
		while (iterator.hasNext()) {
			Course course = iterator.next();
			Document doc;
			try {
				doc = Jsoup.connect(course.getCourseLink()).get();
			} catch (Exception e) {
				System.out.println("Could not connect to " + courseURL + course.getCourseLink());
				iterator.remove();
				continue;
			}
			
			// Fetch title.
			Elements title = doc.select("title");
			course.setTitle(title.text().replaceAll(" - Udacity", ""));
			
			// Fetch description.
			// <!-- Left Column With Enrollment Buttons and Course Information -->
			Elements courseInfo = doc.select("div[class=col-md-8 col-md-offset-2]");
			Elements courseSummary = courseInfo.select("div[class=pretty-format]");
			course.setDescription(courseSummary.text());
			
			// Fetch instructor. Fetch University.
			Elements instructorList = doc.select("div[class=row row-gap-medium instructor-information-entry]");
			Elements instructor = instructorList.select("div[class=col-md-6 instructor-information pull-left]");
			
			String instructors = "", instructor_comma = "", university = "", university_comma = "";
			Elements teacherTest;		
			for (Element e : instructor) {
				teacherTest = e.children().select("div[class=pretty-format]");
				// Test if the instructor is a teacher or a university
				if (teacherTest.size()>0) {
					instructors += instructor_comma + e.children().select("h3[class=h-slim]").text();
					instructor_comma = ", ";
				} else {
					university += university_comma + e.children().select("h3[class=h-slim]").text();
					university_comma = ", ";
				}
			}
			course.setInstructor(instructors);
			course.setUniversity(university);
			
			// Fetch duration.
			// <!-- Duration -->
			Elements durationInfo = doc.select("div[class=col-md-3 duration-information]");
			Elements duration = durationInfo.select("p");
			String text = duration.get(0).text();
			
			// Use regex to find the approx. months
			Pattern p = Pattern.compile("[0-9]");
			Matcher m = p.matcher(text);
			if (m.find()) {
				course.setDuration(Integer.parseInt(m.group()));
			}
			
			// Fetch category.  
			// Using the "Tracks" they have.  "Data Science Track" "Web Development Track"
			// <!-- Course Info Bar -->
			/* Elements courseInfoBar = doc.select("div[class=row course-info-bar row-gap-medium]");
			Elements tracks = courseInfoBar.select("div[class=col-md-10]");
			Elements trackLinks = tracks.select("a");
			String category = "", comma = "";
			for (Element e : trackLinks) {
				category += comma + e.text();
				comma = ", ";
			}
			course.setCategory(category.replaceAll(" Track", "")); */

			// TODO Fetch startDate.  They all seem to be self paced courses.

			course.cleanseData();
			System.out.println(course);
		}
		return courseList;
	}
	
	public static ArrayList<Course> initializeCourses() {
		URL url;
	    InputStream is = null;
	    ArrayList<Course> courseList = new ArrayList<Course>();
	    try {
	        url = new URL(JSON_URL);

			is = url.openStream();
	        String index = IOUtils.toString(is);

	        Pattern p = Pattern.compile("(?s)\\{.*");
	        Matcher m = p.matcher(index);
	        
	    	Gson gson = new Gson();
	        Map<String, Object> map = (Map)((Map)gson.fromJson(index, Map.class).get("references")).get("Node");
	        Set<String> set = map.keySet();
	        
	        Course course;
	        String image;
	        for (String s : set) {
		        course = new Course();
		        
		        // Set the course link
				course.setCourseLink(courseURL + s);
				
				// Get the image from the object
				try {
					image = (String)((Map)((Map)((Map)map.get(s)).get("catalog_entry")).get("_image")).get("serving_url");
					course.setImage(image);
				} catch (Exception e) {
					// Do nothing
				}
				
				courseList.add(course);
	        }
	        return courseList;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return null;
	}
}