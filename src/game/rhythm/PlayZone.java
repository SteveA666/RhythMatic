package game.rhythm;

public class PlayZone {

    private final Node[] nodes;
    private final Lane[] lanes;

    private final double size;
    private final double laneThickness;

    public PlayZone(double size, double laneThickness) {
        this.size = size;
        this.laneThickness = laneThickness;

        lanes = new Lane[4];
        nodes = new Node[4];

        // lanes around a local square (0,0) (size,size)
        lanes[0] = new Lane(0, LPos.TOP,    0, -laneThickness, size, laneThickness);
        lanes[1] = new Lane(1, LPos.RIGHT,  size, 0, laneThickness, size);
        lanes[2] = new Lane(2, LPos.BOTTOM, 0, size, size, laneThickness);
        lanes[3] = new Lane(3, LPos.LEFT,   -laneThickness, 0, laneThickness, size);

        // nodes at square corners
        double t = laneThickness;

        nodes[0] = new Node(0, Pos.TL, -t, -t);
        nodes[1] = new Node(1, Pos.TR, size, -t);
        nodes[2] = new Node(2, Pos.BR, size, size);
        nodes[3] = new Node(3, Pos.BL, -t, size);


    }

    public double getSize() {
        return size;
    }

    public double getLaneThickness() {
        return laneThickness;
    }

    public Lane[] getLanes() { return lanes; }
    public Node[] getNodes() { return nodes; }

    public void update(long time) {
        // later
    }
}
