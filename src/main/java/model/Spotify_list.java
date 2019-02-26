package model;

import interfaces.Interface_model;

public class Spotify_list implements Interface_model{

	@Override
	public String toString() {
		return "Spotify_list [title=" + title + ", artist=" + artist + ", duration=" + duration + "]";
	}

	String title;
	String artist;
	String duration;

	public Spotify_list(String title,String artist,String duration)
	{
		this.title=title;
		this.artist=artist;
		this.duration=duration;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public void print() {
		// TODO Auto-generated method stub
		System.out.println("Spotify API Title : "+this.title+" Artist : "+this.artist+" Duration : "+this.duration);
	}

}
