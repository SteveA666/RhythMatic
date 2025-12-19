package game.rhythm;

import game.Note;

enum JudgementLevels{
	EASY,
	MEDIUM,
	HARD;
}

public class Judgement {

    // windows from YOUR documentation (ms → ns)
    private static final long EX_PLUS = 30L * 1_000_000L;
    private static final long EX      = 50L * 1_000_000L;
    private static final long FAB     = 80L * 1_000_000L;
    private static final long NEAT    = 120L * 1_000_000L;

    private final GameStatus status;
    private final PlayZone playZone;
    private final ChartPlayer player;

    public Judgement(GameStatus status, PlayZone pz, ChartPlayer player) {
        this.status = status;
        this.playZone = pz;
        this.player = player;
    }

    /**
     * Called once per frame from Rhythm.update()
     */
    public void update(long timeNs) {

        Node[] nodes = playZone.getNodes();

        for (int lane = 0; lane < nodes.length; lane++) {
            if (nodes[lane].getState() == State.PRESSED) {
                judgeLane(lane, timeNs);
                nodes[lane].setState(State.IDLE);
            }
        }

        // ChartPlayer handles auto-miss internally
        player.update(timeNs);

        status.update();
    }

    private void judgeLane(int lane, long pressTime) {

        Note note = player.peekNextNote(lane);

        if (note == null) {
            applyMiss();
            return;
        }

        long diff = Math.abs(pressTime - note.time);

        if (diff <= EX_PLUS) {
            applyExPlus();
        } else if (diff <= EX) {
            applyEx();
        } else if (diff <= FAB) {
            applyFab();
        } else if (diff <= NEAT) {
            applyNeat();
        } else {
            applyMiss();
        }

        player.consumeNextNote(lane);
    }

    /* ---------- Effects ---------- */

    private void applyExPlus() {
        status.CntExPlus++;
        status.CurrentCombo++;
        addScore(1.1);
        healHP(2.0);
    }

    private void applyEx() {
        status.CntEx++;
        status.CurrentCombo++;
        addScore(1.0);
        healHP(1.0);
    }

    private void applyFab() {
        status.CntFab++;
        status.CurrentCombo++;
        addScore(0.7);
    }

    private void applyNeat() {
        status.CntNeat++;
        status.CurrentCombo++;
        addScore(0.4);
        damageHP(1.0);
    }

    private void applyMiss() {
        status.CntMiss++;
        status.CurrentCombo = 0;
        damageHP(5.0);
    }

    private void addScore(double units) {
        int totalNotes = player.getTotalNotes();
        double unit = 1_000_000.0 / totalNotes;
        status.score += (int) Math.round(unit * units);
    }

    private void healHP(double amount) {
        if (status.HP == null) return;
        status.HP = Math.min(100.0, status.HP + amount);
    }

    private void damageHP(double amount) {
        if (status.HP == null) return;
        status.HP -= amount;
        if (status.HP <= 0.0) {
            status.HP = 0.0;
            status.isFinished = true;
            status.isClear = false;
        }
    }
}