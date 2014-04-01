import org.jsoup.*;
import org.jsoup.nodes.*;
import java.io.IOException;

public class Udacity {

	public static void main(String[] args) throws IOException {

		String udacity = "https://www.udacity.com/courses#!/All";

		System.out.println("Attempting to fetch course links from source: " + udacity);

		Document doc = Jsoup.connect(udacity).get();
		System.out.println(doc);
		
	}

}
