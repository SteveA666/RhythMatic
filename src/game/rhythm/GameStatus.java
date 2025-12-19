package game.rhythm;

import game.Chart;
import game.Song;

public class GameStatus {
	
	public double accuracy;
	public int score;
	public int CntExPlus;
	public int CntEx;
	public int CntFab;
	public int CntNeat;
	public int CntMiss;
	public int CurrentCombo;
	public Double HP;
	public boolean isFinished;
	public boolean isClear;
	public Song currentSong;
	public Chart currentChart;
	
	public GameStatus(Chart c) {
		HP = null;
		isFinished = false;
		isClear = false;
		CntExPlus = 0;
		CntEx = 0;
		CntFab = 0;
		CntNeat = 0;
		CntMiss = 0;
		currentChart = c;
		currentSong = c.parent;
	}
	
	public GameStatus(boolean isChallenge, Chart c) {
		HP = isChallenge ? 100.0 : null;
		isFinished = false;
		isClear = false;
		CntExPlus = 0;
		CntEx = 0;
		CntFab = 0;
		CntNeat = 0;
		CntMiss = 0;
		currentChart = c;
		currentSong = c.parent;
		
	}
	
	public void update() {
		if(CntExPlus == 0 && CntEx == 0 && CntFab == 0 && CntNeat == 0 && CntMiss == 0) {
			accuracy = 100;
		}else {
			accuracy = StatCalculator.calculateAcc(CntExPlus, CntEx, CntFab, CntNeat, CntMiss);
		}
	}
	

}
