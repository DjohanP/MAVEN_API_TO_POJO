package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import interfaces.Interface_api;
import interfaces.Interface_model;
import model.Spotify_list;

public class ApiSpotify implements Interface_api{

	URL url;
	String token=" BQAYqWR3Rl_rZBeLPKxagzeHuOOmerchI0KlmghobHUGQrRNk8Zqna63rowO6_M-hskRJb1cVrFD3n-od3reLlGtcxJDP1ZpZ0oiKMt3zO7cXQUk9gf87sLhxlLTTl_PTeJ2Lyncxi7Pqn9_xywguvjZxoIF8e3Knw";
	//https://developer.spotify.com/console/get-search-item/?q=Endank&type=&market=&limit=&offset=
	public List<Interface_model> search(String query) {
		// TODO Auto-generated method stub
		query=query.replace(" ", "%20");
		String response;
		try {
			response = requestGet(new URL("https://api.spotify.com/v1/search?q="+query+"&type=track&limit=50"));
			//System.out.println(response);
	        
	        JSONParser parse = new JSONParser();
			JSONObject jobj = (JSONObject)parse.parse(response);
			JSONObject results=(JSONObject)parse.parse(jobj.get("tracks").toString());
			List<Interface_model> convert=getList(results);
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
	
	public List<Interface_model> getList(JSONObject jsonNext)
	{
		String nextUrl=String.valueOf(jsonNext.get("next"));
		List<Interface_model>listSpotify=convertToModel(jsonNext);
		if(jsonNext.get("next")!=null)
		{
			String response;
			try {
				response = requestGet(new URL(nextUrl));
				//System.out.println(response);
				
				JSONParser parse = new JSONParser();
				JSONObject jobj = (JSONObject)parse.parse(response);
				JSONObject results=(JSONObject)parse.parse(jobj.get("tracks").toString());
				
				listSpotify.addAll( getList(results));
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
		
		return listSpotify;
	}

	public List<Interface_model> convertToModel(JSONObject results) {
		List<Interface_model> spotifyModels=new ArrayList<Interface_model>();
		JSONArray arrayResults = (JSONArray) results.get("items");
		//System.out.println(arrayResults);
		for(int i=0;i<arrayResults.size();i++)
		{
			JSONObject object1 = (JSONObject)arrayResults.get(i);
			
			String track=String.valueOf(object1.get("name"));
			String artis="";
			
			JSONArray arrayArtists = (JSONArray) object1.get("artists");
			//System.out.println(arrayArtists);
			for(int j=0;j<arrayArtists.size();j++)
			{
				JSONObject artist = (JSONObject)arrayArtists.get(j);
				if(j==0)
				{
					artis=artis+String.valueOf(artist.get("name"));
				}
				else
				{
					artis=artis+","+String.valueOf(artist.get("name"));
				}
			}
			
			//String genre=String.valueOf(object1.get("primaryGenreName"));//?
			String duration=String.valueOf(object1.get("duration_ms"));
			Spotify_list itune=new Spotify_list(track,artis,duration);
			spotifyModels.add(itune);
			
		}
		return spotifyModels;
	}

	public String requestGet(URL url) {
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization","Bearer "+token);
			conn.setRequestProperty("Content-Type","application/json");
			conn.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String output;

	        StringBuffer response = new StringBuffer();
	        while ((output = in.readLine()) != null) {
	            response.append(output);
	        }

	        in.close();
	        return response.toString();			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
