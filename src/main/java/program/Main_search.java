package program;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import api.ApiItunes;
import api.ApiSpotify;
import interfaces.Interface_model;

public class Main_search {
	
	public static List<Interface_model>mergeItem(List<Interface_model> list1,List<Interface_model> list2)
	{
		List<Interface_model> merge=new ArrayList<Interface_model>();
		for(Interface_model model:list1)
		{
			merge.add(model);
		}
		for(Interface_model model:list2)
		{
			merge.add(model);
		}
		return merge;
	}
	
	public static void main(String[] args){
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a key word : ");
		String query = reader.nextLine();
		reader.close();
		//System.out.println(query);
		
		ApiItunes itunesApi=new ApiItunes();
		ApiSpotify spotifyApi=new ApiSpotify();
		List<Interface_model> queryResultSpotify=spotifyApi.search(query);
		List<Interface_model> queryResultiTunes=itunesApi.search(query);
		
		List<Interface_model> queryResult=mergeItem(queryResultSpotify,queryResultiTunes);
		
		for(Interface_model model:queryResult)
		{
			System.out.println(model);
			//model.print();
		}
	}

}
