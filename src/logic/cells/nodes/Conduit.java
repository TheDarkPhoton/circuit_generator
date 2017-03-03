package logic.cells.nodes;

import logic.cells.Node;

import java.awt.*;

/**
 * Created by dovydas on 27/02/17.
 */
public class Conduit extends Node {

    public Conduit(Node prev, int x, int y) {
        this(prev.getX(), prev.getY(), x, y);
    }

    private Conduit(int prev_x, int prev_y, int x, int y) {
        super(x, y);
        setPrev(prev_x, prev_y);
        setNext(x, y);

        type = "C";
    }

    public void paint(Graphics2D g, int offset_x, int offset_y){
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
            offset_x + (getPrevX() * width) + (width / 2),
            offset_y + (getPrevY() * height) + (height / 2),
            offset_x + (getX() * width) + (width / 2),
            offset_y + (getY() * height) + (height / 2));

        g.drawLine(
                offset_x + (getX() * width) + (width / 2),
                offset_y + (getY() * height) + (height / 2),
                offset_x + (getNextX() * width) + (width / 2),
                offset_y + (getNextY() * height) + (height / 2));

        g.setStroke(new BasicStroke(1));
    }

    public Node clone() {
        Conduit node = new Conduit(getPrevX(), getPrevY(), getX(), getY());
        node.setNext(getNextX(), getNextY());
        node.setConnected(_connected);
        return node;
    }
}
