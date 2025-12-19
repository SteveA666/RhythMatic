package game;

import java.util.ArrayList;

public class SongDatabase {
	
	public static ArrayList<Song> songs = new ArrayList<Song>();

    public static void loadSongs() {
    	// TEMPORARY add test songs
        Song s1 = new Song("Test 1", "SteveA666", 120.0, 120.0);
        Song s2 = new Song("Test 2", "AkihabaraFlick", 200.0, 140.0);
        
        songs.add(s1);
        songs.add(s2);
        
        Chart testChart1 = new Chart(Difficulties.EASY, 2.0, "SteveA666");
        Chart testChart2 = new Chart(Difficulties.INSANE, 14.9, "SteveA666");
        
        s1.addChart(testChart1);
        s2.addChart(testChart2);
        // TODO add a list of songs from JSON
    }

}
