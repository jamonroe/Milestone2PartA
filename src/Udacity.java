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
		Set urls = fetchURLs();
		for (Object s : urls)
			System.out.println(s);
	}
	
	public static Set fetchURLs() {
		URL url;
	    InputStream is = null;

	    try {
	        url = new URL("https://www.udacity.com/api/nodes?depth=2&fresh=false&keys[]=course_catalog&projection=catalog&required_behavior=find");

			is = url.openStream();
	        String index = IOUtils.toString(is);

	        Pattern p = Pattern.compile("(?s)\\{.*");
	        Matcher m = p.matcher(index);
	        
	    	Gson gson = new Gson();
	        Map map = (Map)((Map)gson.fromJson(index, Map.class).get("references")).get("Node");
	        Set set = map.keySet();

	        return set;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return null;
	}
}