import org.jsoup.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FutureLearn {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, SQLException {

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
		Elements dates = div.select("footer[class=media_details clearfix]").select("span[class=media_attributes small]");

		Elements link = h2.select("[href]");

		System.out.println("Printing fetched course links...");

		for (int i = 0; i < link.size(); i++) {
			Course new_course = new Course();

			// Scrape course URL
			String course_url = "https://www.futurelearn.com" + link.get(i).attr("href");
			new_course.setCourseLink(course_url);
			
			// Scrape university name from h3 heading on original HTML page
			Element universityLink = h3.get(i).select("a").first();
			String university = universityLink.text();
			new_course.setUniversity(university);
			
			// Scrape description out of elements descriptions on original HTML page
			String description = descriptions.get(i).text();
			new_course.setDescription(description);

			// Scrape instructor name from subsequent course page
			Document course_page = Jsoup.connect(course_url).get();
			Elements educator_list = course_page.select("div[class=course-educators clearfix]");
			Elements educators = educator_list.select("div[class=small]");
			Elements teachers = educators.select("a");
			
			String instructors = "";
			for (int j = 0; j < teachers.size(); j++) {
				instructors += teachers.get(j).text();
				if (j != teachers.size() - 1)
					instructors += ", ";
			}
			new_course.setInstructor(instructors);
			
			// Scrape course title from course details on course page
			String course_title = h2.select("[title]").get(i).text();
			new_course.setTitle(course_title);

			// Scrape start date and duration from course details on course page
			if (dates.get(i).select("time").hasAttr("datetime")) {
				Element date = dates.get(i);
				String dateInfo = date.select("time").attr("datetime");
				
				new_course.setStartDate(Date.valueOf(dateInfo));
				String[] components = date.text().split(",");
				Pattern p = Pattern.compile("[0-9]+");
				Matcher m = p.matcher(components[1]);
				if (m.find()) {
					new_course.setDuration(Integer.parseInt(m.group()));
				}
			}
			
			courseList.add(new_course);
			System.out.println(new_course);
		}
		/*
		Database.clearTable();
		for (Course c : courseList)
			Database.insertCourse(c);
		System.out.println(Database.printCourses());
		Database.toHtml();
		Database.close();*/
	}
}
