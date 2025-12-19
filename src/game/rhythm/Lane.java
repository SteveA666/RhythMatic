package game.rhythm;

enum LPos { BOTTOM, RIGHT, TOP, LEFT; }

public class Lane {

    private final int index;
    private final LPos pos;

    // Simple geometry for now (so you can render + place notes later)
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    public Lane(int index, LPos pos, double x, double y, double width, double height) {
        this.index = index;
        this.pos = pos;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getIndex() { return index; }
    public LPos getPos() { return pos; }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}
