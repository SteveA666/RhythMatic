package game.rhythm;

public class GameClock {

    private boolean running;
    private boolean paused;

    private long startNano;      // System.nanoTime() when (re)started/resumed
    private long elapsedNano;    // accumulated elapsed time (ns), excluding current running segment

    public GameClock() {
        running = false;
        paused = false;
        startNano = 0L;
        elapsedNano = 0L;
    }

    /**
     * Starts the clock from 0. If already running, it restarts from 0.
     */
    public void start() {
        running = true;
        paused = false;
        elapsedNano = 0L;
        startNano = System.nanoTime();
    }

    /**
     * Returns elapsed time in nanoseconds since start, excluding paused time.
     */
    public long currentTime() {
        if (!running) {
            return 0L;
        }
        if (paused) {
            return elapsedNano;
        }
        long now = System.nanoTime();
        return elapsedNano + (now - startNano);
    }

    /**
     * Pauses the clock (time stops increasing).
     */
    public void pause() {
        if (!running || paused) {
            return;
        }
        elapsedNano = currentTime();
        paused = true;
    }

    /**
     * Resumes after a pause.
     */
    public void resume() {
        if (!running || !paused) {
            return;
        }
        paused = false;
        startNano = System.nanoTime();
    }

    /**
     * Stops and resets to 0.
     */
    public void reset() {
        running = false;
        paused = false;
        startNano = 0L;
        elapsedNano = 0L;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }
    
    /**
     * Synchronize the clock so that currentTime() immediately reports the given elapsed value.
     * Example: syncTo(2_000_000_000L) makes the clock think 2.0 seconds have already passed.
     *
     * This is useful for:
     * - countdowns / preroll
     * - seeking in a song
     * - starting a chart at an offset
     *
     * @param elapsedNs elapsed time in nanoseconds (can be 0 or more; negative treated as 0)
     */
    public void syncTo(long elapsedNs) {
        if (elapsedNs < 0L) {
            elapsedNs = 0L;
        }

        running = true;
        paused = false;

        elapsedNano = elapsedNs;
        startNano = System.nanoTime();
    }

}
