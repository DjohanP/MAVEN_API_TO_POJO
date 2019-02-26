package api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import interfaces.Interface_api;
import interfaces.Interface_model;
import model.Itune_list;

public class ApiItunes implements Interface_api{
	public List<Interface_model> search(String query) {
		query=query.replace(" ", "+");
		try {
			String response=requestGet(new URL("https://itunes.apple.com/search?term="+query));
			JSONParser parse = new JSONParser();
			JSONObject jobj = (JSONObject)parse.parse(response); 
			
			List<Interface_model> convert=convertToModel(jobj);
			return convert;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Interface_model> convertToModel(JSONObject results) {
		List<Interface_model> iTunesModels=new ArrayList<Interface_model>();
		JSONArray arrayResults = (JSONArray) results.get("results");
		
		for(int i=0;i<arrayResults.size();i++)
		{
			JSONObject object1 = (JSONObject)arrayResults.get(i);
			
			String track=String.valueOf(object1.get("trackName"));
			String artist=String.valueOf(object1.get("artistName"));
			//String genre=String.valueOf(object1.get("primaryGenreName"));
			String duration=String.valueOf(object1.get("trackTimeMillis"));
			
			Itune_list itune=new Itune_list(track,artist,duration);
			iTunesModels.add(itune);
			
		}
		return iTunesModels;
	}

	public String requestGet(URL url) {
		HttpURLConnection conn;
		try {	
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			Scanner sc = new Scanner(url.openStream());
			String inline="";
			while(sc.hasNext())
			{
				inline+=sc.nextLine();
			}
			
			sc.close();
			return inline;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}