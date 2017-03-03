package ai;

import java.util.ArrayList;

/**
 * Created by dovydas on 26/02/17.
 */
public interface AStar {
    Heuristic heuristic();
    ArrayList<AStar> expand();
    void updateState(AStar state);
    boolean isSame(AStar state);
}
