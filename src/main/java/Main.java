import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Main {

	public static void main (String[] args) {
		System.out.println(getMovieTitle("spiderman"));
	}
	
	public static List<String> getMovieTitle(String title) {
		List<String> titles = new ArrayList<String>();
		
		JSONParser parser = new JSONParser();
		String url = "https://jsonmock.hackerrank.com/api/movies/search/?Title=" + title;
		
		try {
			JSONObject a = new JSONObject();
			int currentPage = 1;
			do {
				URL oracle = new URL(url + "&page=" + currentPage);
				URLConnection uc = oracle.openConnection();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
				System.out.println(currentPage);
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					a = (JSONObject) parser.parse(inputLine);
										
					JSONArray array = (JSONArray) a.get("data");
					
					for (Object o : array) {
						JSONObject json = (JSONObject) o;
						titles.add(json.get("Title").toString());
					}
				}
				currentPage++;
			} while (currentPage <= Integer.parseInt(a.get("total_pages").toString()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return titles.stream().sorted((s1, s2) -> s1.compareTo(s2)).collect(Collectors.toList());
	}
}
