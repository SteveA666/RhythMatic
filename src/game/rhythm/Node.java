package game.rhythm;

enum State { IDLE, PRESSED; }
enum Pos { BL, BR, TR, TL; }

public class Node {

    private final int index;
    private final Pos pos;
    private State state;

    // Hit point location (for rendering + judging)
    private final double x;
    private final double y;

    public Node(int index, Pos pos, double x, double y) {
        this.index = index;
        this.pos = pos;
        this.x = x;
        this.y = y;
        this.state = State.IDLE;
    }

    public void setPressed(boolean pressed) {
        state = pressed ? State.PRESSED : State.IDLE;
    }

    public int getIndex() { return index; }
    public Pos getPos() { return pos; }
    public State getState() { return state; }

    public double getX() { return x; }
    public double getY() { return y; }
    
    public void setState(State s) {
    	this.state = s;
    }
}
