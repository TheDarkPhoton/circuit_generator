package logic.cells;

import logic.Cell;

import java.awt.*;

/**
 * Created by dovydas on 27/02/17.
 */
public class Selected extends Cell {
    protected Color _color = Color.YELLOW;

    public Selected(Cell c) {
        super(c.getX(), c.getY());
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
    }
}
