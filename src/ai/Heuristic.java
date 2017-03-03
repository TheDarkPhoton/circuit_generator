package ai;

/**
 * Created by dovydas on 01/03/17.
 */
public class Heuristic {
    public double value = Double.MAX_VALUE;
    public double distance = 0;

    public Heuristic(double value, double distance) {
        this.value = value;
        this.distance = distance;
    }

    public double sum(){
        return value + distance;
    }
}
