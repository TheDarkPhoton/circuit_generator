package logic;

import java.awt.*;

/**
 * Created by dovydas on 26/02/17.
 */
public abstract class Cell {
    public static final int width = 20;
    public static final int height = 20;

    private int _x;
    private int _y;
    protected Color _color = Color.GREEN;
    public String type = " ";

    public Cell(int x, int y) {
        _x = x;
        _y = y;
    }

    public void paint(Graphics2D g, int offset_x, int offset_y) {
        g.setColor(_color);
        g.fillRect(
                offset_x + (getX() * width),
                offset_y + (getY() * height),
                width,
                height
        );

    }

    public Point getPosition() {
        return new Point(_x, _y);
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cell) {
            Cell cell = (Cell)obj;
            return getPosition().equals(cell.getPosition());
        }

        return false;
    }
}
