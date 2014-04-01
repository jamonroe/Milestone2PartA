import org.apache.commons.io.IOUtils;
import org.jsoup.*;
import org.jsoup.nodes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Udacity {

	public static void main(String[] args) {
		ArrayList<String> urls = fetchURLs();
		for (String s : urls)
			System.out.println(s);
	}
	
	public static ArrayList<String> fetchURLs() {
		URL url;
	    InputStream is = null;

	    try {
	        url = new URL("https://www.udacity.com/api/nodes?depth=2&fresh=false&keys[]=course_catalog&projection=catalog&required_behavior=find");

			System.out.println("Attempting to fetch course links from source: " + url);

			is = url.openStream();
	        String index = IOUtils.toString(is);
	        
	        //System.out.println(index);
	        
	        String[] references = index.split("ref");
	        
	        Pattern keyPattern = Pattern.compile("(?i)\"key\":.\"([a-z]|[0-9])*\"");
	        Matcher m;
	        
	        ArrayList<String> urls = new ArrayList<String>();
	        
	        for (String s : references) {
	        	m = keyPattern.matcher(s);
	        	if (m.find()) {
	        		urls.add("https://www.udacity.com/course/" + m.group().replaceAll("(\"|\\s)", "").split(":")[1]);
	        	} else {
	        		//System.out.println("No URL found" + s);
	        	}
	        }
	        sortUniques(urls);
	        return urls;
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	         if (is != null) IOUtils.closeQuietly(is);
	    }
	    return null;
	}
	
	public static void sortUniques(ArrayList<? extends String> list) {
        Collections.sort(list);
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i).equals(list.get(i+1))) {
				list.remove(i);
				i--;
			}
		}
	}
}
