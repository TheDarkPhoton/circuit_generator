package ai;

import java.util.ArrayList;

/**
 * Created by dovydas on 27/02/17.
 */
public class AI {
    private AStar _root;

    private ArrayList<State> _goalStates = new ArrayList<>();
    private ArrayList<AStar> _checked = new ArrayList<>();
    private ArrayList<AStar> _states = null;
    private State best = null;

    public AI(AStar root) {
        _root = root;
    }

    private void expandStates() {
        if (_states == null) {
            _states = _root.expand();
        } else {
            if (best.state == null) {
                System.out.println();
            }
            ArrayList<AStar> newStates = best.state.expand();

            for (AStar newState : newStates) {
                boolean discard = false;
                ArrayList<AStar> temp = new ArrayList<>(_states);
                temp.addAll(_checked);
                for (AStar state : temp) {
                    boolean result = newState.isSame(state);
                    if (result) {
                        discard = true;
                        break;
                    }
                }

                if (!discard)
                    _states.add(newState);
            }
        }
    }

    private void findBest() {
        best = null;
        for (int i = 0; i < _states.size(); i++) {
            AStar state = _states.get(i);

            Heuristic heuristic = state.heuristic();
            if (heuristic.value == 0) {
                _goalStates.add(new State(state, heuristic));
                _states.remove(state);
                --i;
                continue;
            }

            if (best == null) {
                best = new State(state, heuristic);
                continue;
            }

            if (heuristic.sum() < best.heuristic.sum()) {
                best = new State(state, heuristic);
            }
        }

        for (State goalState : _goalStates) {
            if (goalState.heuristic.distance < best.heuristic.distance) {
                best = goalState;
            }
        }
    }

    public void findSolution(){
        do {
            expandStates();
            findBest();

            if (best != null) {
                _root.updateState(best.state);

                if (best.heuristic.value > 0) {
                    _checked.add(best.state);
                    _states.remove(best.state);
                }
            }
        } while (best == null || best.heuristic.value > 0);
    }
}
