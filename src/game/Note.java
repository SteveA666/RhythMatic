package game;

enum NoteType{
	TAP,
	HOLD,
	DRAG;
}

public class Note {

    public NoteType type;      // tap / hold / drag
    public int lane;      // which of the 4 corners (0 - 3)
    public long time;     // time in ms when the note should be hit
    public long endTime;  // only used for hold notes

    // Tap and Drag constructor
    public Note(NoteType type, int lane, long time) {
        this.type = type;
        this.lane = lane;
        this.time = time;
        this.endTime = time;
    }

    // Hold constructor
    public Note(NoteType type, int lane, long time, long endTime) {
        this.type = type;
        this.lane = lane;
        this.time = time;
        this.endTime = endTime;
    }
}
