import org.jsoup.*;

import java.sql.Date;

import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class FutureLearn {

	
	public FutureLearn() { }
	
	@SuppressWarnings("deprecation")
	private static Date convertDate(String date) {

		String day = date.substring(0,2);
		String month = date.substring(3);
		int monthNum = 0;
		switch (month) {
			case "January": 	monthNum = 1;
								break;
			case "February": 	monthNum = 2;
							 	break;
			case "March":		monthNum = 3;
								break;
			case "April":		monthNum = 4;
								break;
			case "May":			monthNum = 5;
								break;
			case "June":		monthNum = 6;
								break;
			case "July":		monthNum = 7;
								break;
			case "August":		monthNum = 8;
								break;
			case "September":	monthNum = 9;
								break;
			case "October":		monthNum = 10;
								break;
			case "November":	monthNum = 11;
								break;
			case "December":	monthNum = 12;
								break;
			default:			monthNum = -1; 	
								break;
		}
		int dayNum = Integer.parseInt(day);
		
		Date returnDate = new Date(2014, monthNum, dayNum);
		
		return returnDate;
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		FutureLearn test = new FutureLearn();
		ArrayList<Course> courseList = new ArrayList<Course>();

		String futurelearn = "https://www.futurelearn.com/courses";

		System.out.println("Attempting to fetch course links from source: " + futurelearn);

		Document doc = Jsoup.connect(futurelearn).get();
		Elements ele = doc.select("ul[class=list course-index]");
		Elements li = ele.select("li[class=media media-large clearfix]");
		Elements div = li.select("div[class=media_body]");
		Elements header = div.select("header[class=header header-medium]");
		Elements h2 = header.select("a[class=title]");
		Elements h3 = header.select("h3[class=organisation headline headline-secondary]");
		Elements section = div.select("section[class=media_description]");
		Elements descriptions = section.select("p[class=introduction]");
		Elements dates = div.select("dates[class=media_details clearfix]").select("span[class=media_attributes small]");


		Elements link = h2.select("[href]");


		System.out.println("Printing fetched course links...");

		for (int i = 0; i < link.size(); i++) {
			Course new_course = new Course();

			// Scrape course URL
			String course_url = "https://www.futurelearn.com" + link.get(i).attr("href");
			new_course.setCourseLink(course_url);

			// Scrape description out of Elements descriptions
			String description = descriptions.get(i).text();
			new_course.setDescription(description);

			// Scrape instructor name from subsequent course page
			Document course_page = Jsoup.connect(course_url).get();
			Elements edu = course_page.select("div[class=course-educators clearfix]");
			Elements ed = edu.select("div[class=small]");
			Elements teachers = ed.select("a");
			String instructor = "";
			if (i < teachers.size())
				instructor = teachers.get(i).text();
			new_course.setInstructor(instructor);

			// Scrape course title from h2 heading on original HTML page
			String course_title = h2.select("[title]").get(i).text();
			new_course.setTitle(course_title);

			// Scrape university name from h3 heading on original HTML page
			Element universityLink = h3.get(i).select("a").first();
			String university = universityLink.text();
			System.out.println(university);
			new_course.setUniversity(university);

			// Scrape start date and duration of course from original HTML page
			String startDate = "";
			String duration = "";
			
			if (i < dates.size())
			if (dates.get(i).select("time").hasAttr("datetime")) {
				Element date = dates.get(i);
				String dateInfo = date.text();
				int j = 0;
				while (dateInfo.charAt(j) != ',') j++;
				int k = j+1;
				while (dateInfo.charAt(k) != ',') k++;
				startDate = dateInfo.substring(0, j);
				new_course.setStartDate(convertDate(startDate));
				
				
				duration = dateInfo.substring(j+2,k);
				int durationLength = Integer.parseInt(duration.substring(0,3));
				new_course.setDuration(durationLength);
				
				
			}
			else {
				startDate = dates.get(i).text();
				new_course.setStartDate(new Date(2014, 12, 1));
				new_course.setDuration(-1);
			}
			
			// FutureLearn does not categorize their courses
			new_course.setCategory("null");

			courseList.add(new_course);
		}
		for (Course c : courseList) {
			System.out.println(c.getTitle());
			System.out.println(c.getUniversity());
			System.out.println(c.getCategory());
			System.out.println(c.getCourseLink());
			System.out.println(c.getInstructor());
			System.out.println(c.getDescription());
			System.out.println(c.getStartDate());
			System.out.println(c.getDuration());
			System.out.println("======================");
		}

	}

}
