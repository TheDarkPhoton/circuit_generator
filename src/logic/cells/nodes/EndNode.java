package logic.cells.nodes;

import logic.cells.Node;

import java.awt.*;

/**
 * Created by dovydas on 27/02/17.
 */
public class EndNode extends Node {
    private int _start_x;
    private int _start_y;

    public EndNode(int x, int y, StartNode node) {
        this(x, y, node.getX(), node.getY());
        node.setEndNode(getX(), getY());
    }

    private EndNode(int x, int y, int start_x, int start_y) {
        super(x, y);
        _start_x = start_x;
        _start_y = start_y;
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
                offset_x + (getPrevX() * width) + (width / 2),
                offset_y + (getPrevY() * height) + (height / 2),
                offset_x + (getX() * width) + (width / 2),
                offset_y + (getY() * height) + (height / 2));

        g.setStroke(new BasicStroke(1));

        super.paint(g, offset_x, offset_y);
    }

    public boolean isConnected(){
        return _connected;
    }

    public Node clone() {
        EndNode node = new EndNode(getX(), getY(), _start_x, _start_y);
        node.setNext(getNextX(), getNextY());
        node.setPrev(getPrevX(), getPrevY());
        node.setConnected(_connected);
        return node;
    }
}
