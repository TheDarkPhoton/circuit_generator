package logic.cells;

import logic.Cell;

import java.awt.*;

/**
 * Created by dovydas on 26/02/17.
 */
public abstract class Node extends Cell {
    protected boolean _connected = false;
    private int _prev_x;
    private int _prev_y;
    private int _next_x;
    private int _next_y;

    public Node(int x, int y) {
        super(x, y);
        setNext(x, y);
        setPrev(x, y);

        type = "N";
    }

    public void paint(Graphics2D g, int offset_x, int offset_y) {
        g.setColor(Color.YELLOW);
        g.setStroke(new BasicStroke(3));
        g.drawOval(
                4 + offset_x + (getX() * width),
                4 + offset_y + (getY() * height),
                width - 8,
                height - 8
        );
        g.setStroke(new BasicStroke(1));
    }

    public void setNext(int x, int y) {
        _next_x = x;
        _next_y = y;
    }

    public void setPrev(int x, int y) {
        _prev_x = x;
        _prev_y = y;
    }

    public int getNextX(){
        return _next_x;
    }

    public int getNextY(){
        return _next_y;
    }

    public boolean hasNext(){
        return !(_next_x == getX() && _next_y == getY());
    }

    public int getPrevX(){
        return _prev_x;
    }

    public int getPrevY(){
        return _prev_y;
    }

    public boolean hasPrev(){
        return !(_prev_x == getX() && _prev_x == getY());
    }

    public void setConnected(boolean connected) {
        _connected = connected;
    }

    public abstract Node clone();

    @Override
    public String toString() {
        return "x: " + getX() + ", y: " + getY();
    }
}
