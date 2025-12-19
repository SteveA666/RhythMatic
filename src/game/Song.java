package game;

import java.util.ArrayList;

public class Song {
	
	private static int NextSongID = 1;
	public double length;
	public double bpm;
	public int songID;
	public String title;
	public String artist;
	public ArrayList<Chart> charts;
	
	public Song(String title, String artist, double bpm, double length) {
		
		// Automatically set a song ID
		this.songID = NextSongID++;
		
		// Fill in metadata
        this.title = title;
        this.artist = artist;
        this.bpm = bpm;
        this.length = length;
        this.charts = new ArrayList<>();
	}
	
	// Operations for adding a new chart
	public void addChart(Chart newChart) {
		
		newChart.setParentSong(this);
		charts.add(newChart);
		
	}
	
	@Override
    public String toString() {
        return title + " - " + artist;
    }

}
