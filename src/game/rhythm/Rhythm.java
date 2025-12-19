package game.rhythm;

import game.Chart;

public class Rhythm {
	
	public boolean challengeMode = false;
	
	// If you are unsure about definitions look for the definition and you'll jump back here
	private ChartParser parser;
	private Judgement j;
	private PlayZone pz;
	private InputManager im;
	private GameStatus status;
	private RhythmWindow window;
	private GameClock clock;
	private ChartPlayer player;
	
	
	public Rhythm(Chart c, boolean isChallengeMode) {
		
		
		System.out.println("Game Created with chart " + c);
		
		// TODO Create the game over here.
		
		// Game State
		status = new GameStatus(isChallengeMode, c);
		
		// Field (Square) for gameplay
		pz = new PlayZone(400, 50);
		
		
		// Clock for game timing
		clock = new GameClock();
				
		// Chart Player and Parser
		parser = new ChartParser(c);
		player = new ChartPlayer(parser.getNotes(), pz, clock);
		
		
		j = new Judgement(status, pz, player);
		im = new InputManager(j, clock);
		
		// Rhythm Game Window
		window = new RhythmWindow(pz, im, status, 1280, 720);
		
		// Start game loops
		clock.start();
		window.start();
		
		// set window to call back on this Rhythm object during updates
		window.setUpdateCallBack(this);
		
	}
	
	public void update() {
	    long time = clock.currentTime();

	    pz.update(time);
	    im.update(time);
	    j.update(time);

	    if(status.isFinished) {window.showResult();}
	    window.render();
	    window.renderInfoDisplay();
	}
	
	


}
