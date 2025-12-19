package game.story;

public class DialogueLine {
	
	public String speaker;
    public String text;
    public String portrait;
    public String background;
    public boolean triggersBattle = false;   // placeholder
    
    public DialogueLine() {
		speaker = null;
	}
    
    
    public DialogueLine(String speaker, String text, String portrait, String background) {
		this.speaker = speaker;
		this.text = text;
		this.portrait = portrait;
		this.background = background;
	}

}
