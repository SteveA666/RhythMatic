package game;

import java.util.ArrayList;

enum Difficulties {
    EASY,
    NORMAL,
    HARD,
    INSANE;
}

public class Chart {

    private static int nextChartID = 1;

    public double internalDifficulty;        // internal difficulty e.g., 12.9
    public int displayedDifficulty;          // displayed difficulty e.g., 12
    public Difficulties difficultyLevel;     // EASY / NORMAL / HARD / INSANE
    public int chartID;                      // unique
    public String chartMaker;                // Username of who authored the chart
    public Song parent;                      // the Song this chart belongs to
    public ArrayList<Note> notes;            // list of notes

    public Chart(Difficulties level, double internalDifficulty, String chartMaker) {
        this.chartID = nextChartID++;
        this.difficultyLevel = level;
        this.internalDifficulty = internalDifficulty;
        this.displayedDifficulty = (int) internalDifficulty;   // truncate
        this.chartMaker = chartMaker;
        this.notes = new ArrayList<>();
        this.parent = null;
    }

    // Link chart to its Song
    public void setParentSong(Song song) {
        this.parent = song;
    }

    // Add notes
    public void addNote(Note note) {
        notes.add(note);
    }

    // Get note count, helper method
    public int getNoteCount() {
        return notes.size();
    }

    @Override
    public String toString() {
        return "Chart{" +
                "difficulty=" + difficultyLevel +
                ", internal=" + internalDifficulty +
                ", displayed=" + displayedDifficulty +
                ", notes=" + notes.size() +
                ", maker='" + chartMaker + '\'' +
                ", parent='" + parent + '\'' +
                '}';
    }

	public int getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Difficulties getDifficultyLevel() {
		return difficultyLevel;
	}
	
	public int getDisplayedDifficulty() {
		return displayedDifficulty;
	}

	public int getTotalNotes() {
		return notes.size();
	}

	public void consumeNoteForLane(int laneIndex) {
		// TODO Auto-generated method stub
		
	}

	public long getNextNoteTimeForLane(int laneIndex) {
		// TODO Auto-generated method stub
		return 0;
	}
    
    
    
}
