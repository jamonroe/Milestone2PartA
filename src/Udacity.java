import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Udacity {
	
	public static void main(String[] args) {
		ArrayList<String> urls = fetchURLs();
		//for (String s : urls)
			//System.out.println(s);
	}
	
	public static ArrayList<String> fetchURLs() {
		URL url;
	    InputStream is = null;

	    try {
	        url = new URL("https://www.udacity.com/api/nodes?depth=2&fresh=false&keys[]=course_catalog&projection=catalog&required_behavior=find");

			is = url.openStream();
	        String index = IOUtils.toString(is);

	        Pattern p = Pattern.compile("(?s)\\{.*");
	        Matcher m = p.matcher(index);
	        
	    	Gson gson = new Gson();
	        printMap((Map)gson.fromJson(index, Object.class));
	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return null;
	}
	
	public static void printMap(Map m) {
		if (m == null) return;
		Set s = m.keySet();
		Object val;
		for (Object key : s) {
			val = m.get(key);
			if (val instanceof Map) {
				System.out.println(key + ":\n{");
				printMap((Map) val);
				System.out.println("}");
			}
			else
				System.out.println(key + ": " + val);
		}
	}
}