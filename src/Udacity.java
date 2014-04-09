import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Udacity {
	
	public static String URL = "https://www.udacity.com";
	public static String CATALOG = "https://www.udacity.com/courses#!/All";
	public static String courseURL = "http://www.udacity.com/course/";
	public static String JSON_URL = "https://www.udacity.com/api/nodes?depth=2&fresh=false&keys[]=course_catalog&projection=catalog&required_behavior=find";
	public static String YOUTUBE = "https://www.youtube.com/watch?v=";
	
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
			
			// site.
			course.setSite(URL);
			
			// title.
			Elements title = doc.select("title");
			course.setTitle(title.text().replaceAll(" - Udacity", ""));
			
			// long_desc.
			// <!-- Left Column With Enrollment Buttons and Course Information -->
			Elements courseInfo = doc.select("div[class=col-md-8 col-md-offset-2]");
			Elements courseSummary = courseInfo.select("div[class=pretty-format]");
			course.setLongDescription(courseSummary.text());
			
			// profname and university.
			// TODO update this for new model
			Elements instructor = doc
					.select("div[class=row row-gap-medium instructor-information-entry]")
					.select("div[class=col-md-6 instructor-information pull-left]");
			
			Pattern p = Pattern.compile("(?<=').*(?=')");
			Matcher m;
			String instructors = "", instructor_comma = "", university = "", university_comma = "", image_link;
			Elements teacherTest;		
			for (Element e : instructor) {
				teacherTest = e.children().select("div[class=pretty-format]");
				// Test if the instructor is a teacher or a university
				if (teacherTest.size()>0) {
					instructors += instructor_comma + e.children().select("h3[class=h-slim]").text();
					instructor_comma = ", ";
					
					// profimage
					image_link = e.children().select("img[class=img-circle instructor-picture]").attr("data-ng-src");
					m = p.matcher(image_link);
					if (m.find()) {
						course.setProfImage(m.group());
					}
					
				} else {
					university += university_comma + e.children().select("h3[class=h-slim]").text();
					university_comma = ", ";
				}
			}
			course.setProfName(instructors);
			course.setUniversity(university);
			
			// course_length.
			// <!-- Duration -->
			Elements duration = doc
					.select("div[class=col-md-3 duration-information]")
					.select("p");
			String text = duration.get(0).text();
			
			// Use regex to find the approx. months
			p = Pattern.compile("[0-9]");
			m = p.matcher(text);
			if (m.find()) {
				course.setCourseLength(Integer.parseInt(m.group()));
			}

			// video_link.
			// <!-- View trailer modal -->
			Elements trailerInfo = doc
					.select("div[class=scale-media]")
					.select("div");
			course.setVideoLink(YOUTUBE + trailerInfo.attr("data-video-id"));

			// time_scraped.
			course.setTimeScraped((java.sql.Date)new Date());
			
			/* ****************** */
			/*                    */
			/* Data not available */
			/*                    */
			/* ****************** */

			// (N/A) startDate   Udacity has only self-paced courses
			// (N/A) category    Udacity has no categories other than tracks
			// (N/A) course_fee  Udacity provides all free courses
			// (N/A) language    Udacity does not provide language information presumably only English
			// (N/A) certificate Udacity does not have any certificates
			
			course.cleanseData();
			//System.out.println(course);
		}
		return courseList;
	}
	
	/**
	 * initializeCourses uses the JSON file to find course links, course image, and short description
	 * @return The list of courses
	 */
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
	        String image, desc;
	        for (String s : set) {
		        course = new Course();
		        
		        /* **** */
		        /* JSON */
		        /* **** */
		        
		        // course_link.
				course.setCourseLink(courseURL + s);
				
				// course_image.
				try {
					image = (String)((Map)((Map)((Map)map.get(s)).get("catalog_entry")).get("_image")).get("serving_url");
					course.setCourseImage(image);
				} catch (Exception e) {
					// Do nothing
				}
				
				// short_desc.
				try {
					desc = (String)((Map)((Map)map.get(s)).get("catalog_entry")).get("short_summary");
					course.setShortDescription(desc);
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