package logic.cells.nodes;

import logic.cells.Node;

import java.awt.*;

/**
 * Created by dovydas on 27/02/17.
 */
public class StartNode extends Node {
    private int _end_x;
    private int _end_y;

    public StartNode(int x, int y) {
        super(x, y);
    }

    @Override
    public void paint(Graphics2D g, int offset_x, int offset_y) {
        g.setColor(_color);
        g.fillRect(
                offset_x + (getX() * width),
                offset_y + (getY() * height),
                width,
                height
        );

        g.setColor(Color.YELLOW);
        g.setStroke(new BasicStroke(3));
        g.drawLine(
                offset_x + (getX() * width) + (width / 2),
                offset_y + (getY() * height) + (height / 2),
                offset_x + (getNextX() * width) + (width / 2),
                offset_y + (getNextY() * height) + (height / 2));

        g.setStroke(new BasicStroke(1));

        super.paint(g, offset_x, offset_y);
    }

    public void setEndNode(int x, int y) {
        _end_x = x;
        _end_y = y;
    }

    public int getEndX(){
        return _end_x;
    }

    public int getEndY(){
        return _end_y;
    }

    public double trailHypotenuse(Point trail) {
        return Math.hypot(
                Math.abs(trail.x - _end_x),
                Math.abs(trail.y - _end_y)
        );
    }

    public Node clone() {
        StartNode node = new StartNode(getX(), getY());
        node.setNext(getNextX(), getNextY());
        node.setPrev(getPrevX(), getPrevY());
        node.setConnected(_connected);
        node.setEndNode(_end_x, _end_y);
        return node;
    }
}
