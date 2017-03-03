package logic.cells;

import logic.Cell;

import java.awt.*;

/**
 * Created by dovydas on 28/02/17.
 */
public class Wall extends Cell {

    public Wall(int x, int y) {
        super(x, y);

        type = "W";
        _color = Color.RED;
    }
}
