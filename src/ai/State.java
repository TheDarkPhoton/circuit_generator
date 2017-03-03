package ai;

/**
 * Created by dovydas on 01/03/17.
 */
public class State {
    public AStar state;
    public Heuristic heuristic;

    public State(AStar state, Heuristic heuristic) {
        this.state = state;
        this.heuristic = heuristic;
    }
}
