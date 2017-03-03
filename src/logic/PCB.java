package logic;

import ai.AStar;
import ai.Heuristic;
import javafx.util.Pair;
import logic.cells.nodes.Conduit;
import logic.cells.Node;
import logic.cells.Wall;
import logic.cells.nodes.EndNode;
import logic.cells.nodes.StartNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by dovydas on 26/02/17.
 */
public class PCB extends Observable implements AStar {
    private ArrayList<StartNode> _startNodes = new ArrayList<>();
    private Cell[][] _cells;
    private Point _position = new Point(0, 0);

    public PCB(int width, int height) {
        _cells = new Cell[height][width];

        for (int i = 1; i < height; i++) {
            addCell(new Wall(5, i));
        }
    }

    public PCB(PCB pcb){
        _cells = new Cell[pcb.getHeight()][pcb.getWidth()];

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Cell cell = pcb.getCell(x, y);
                if (cell instanceof Node) {
                    Node node = ((Node) cell);
                    addCell(node.clone());
                } else {
                    _cells[y][x] = pcb.getCell(x, y);
                }
            }
        }
    }

    public void paint(Graphics2D g) {
        int offset_x = getX();
        int offset_y = getY();

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Cell cell = _cells[y][x];
                if (cell != null) {
                    cell.paint(g, offset_x, offset_y);
                } else {
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(
                            offset_x + (x * Cell.width),
                            offset_y + (y * Cell.height),
                            Cell.width,
                            Cell.height
                    );
                }
            }
        }
    }

    public void addCell(Cell c) {
        _cells[c.getY()][c.getX()] = c;
        setChanged();
        notifyObservers();

        if (c instanceof StartNode) {
            _startNodes.add(((StartNode) c));
        }
    }

    public ArrayList<StartNode> getStartNodes() {
        return _startNodes;
    }

    public void addConduit(Pair<Point, Point> c) {
        Cell node_a = _cells[c.getKey().y][c.getKey().x];
        Cell node_b = _cells[c.getValue().y][c.getValue().x];

        if (node_a instanceof StartNode && node_b instanceof EndNode) {
            StartNode start = ((StartNode) node_a);
            EndNode end = ((EndNode) node_b);

            start.setNext(end.getX(), end.getY());
            end.setPrev(start.getX(), start.getY());
        } else if (node_a instanceof Conduit && node_b instanceof EndNode) {
            Conduit conduit = ((Conduit) node_a);
            EndNode end = ((EndNode) node_b);

            conduit.setNext(end.getX(), end.getY());
            end.setPrev(conduit.getX(), conduit.getY());
            end.setConnected(true);
        } else if (node_a instanceof StartNode) {
            StartNode start = ((StartNode) node_a);
            Conduit conduit = new Conduit(start, c.getValue().x, c.getValue().y);

            start.setNext(conduit.getX(), conduit.getY());
            conduit.setPrev(start.getX(), start.getY());
            addCell(conduit);
        } else if (node_a instanceof Conduit){
            Conduit conduit1 = ((Conduit) node_a);
            Conduit conduit2 = new Conduit(conduit1, c.getValue().x, c.getValue().y);

            conduit1.setNext(conduit2.getX(), conduit2.getY());
            conduit2.setPrev(conduit1.getX(), conduit1.getY());
            addCell(conduit2);
        }
    }

    public void removeCell(Cell c) {
        _cells[c.getY()][c.getX()] = null;
        setChanged();
        notifyObservers();

        if (c instanceof StartNode) {
            _startNodes.remove(c);
        }
    }

    public Cell getCell(int x, int y) {
        return _cells[y][x];
    }

    public int getWidth(){
        return _cells[0].length;
    }

    public int getHeight(){
        return _cells.length;
    }

    public int getX(){
        return _position.x;
    }

    public int getY(){
        return _position.y;
    }

    public void setPosition(Point position) {
        _position = position;
        setChanged();
        notifyObservers();
    }

    public Point getPosition() {
        return _position;
    }

    private Node findTrailingNode(Node node) {
        if (node.hasNext()) {
            return findTrailingNode((Node)_cells[node.getNextY()][node.getNextX()]);
        }

        return node;
    }

    private double trailHypotenuse(StartNode start) {
        Node trail = findTrailingNode(start);
        Point end = new Point(start.getEndX(), start.getEndY());

        if (end.equals(new Point(trail.getX(), trail.getY())))
            return 0;

        return Math.hypot(
                Math.abs(trail.getX() - end.getX()),
                Math.abs(trail.getY() - end.getY())
        );
    }

    private double pathLength(Node node) {
        if (node.hasNext()){
            Node next = (Node)_cells[node.getNextY()][node.getNextX()];
            return Math.hypot(
                    Math.abs(node.getX() - next.getX()),
                    Math.abs(node.getY() - next.getY())
            ) + pathLength(next);
        }

        return 0;
    }

    private boolean isConnected(Point a, Point b){
        Cell cell_a = _cells[a.y][a.x];
        Cell cell_b = _cells[b.y][b.x];

        if (cell_a instanceof Node && cell_b instanceof Node) {
            Node node_a = ((Node) cell_a);
            Node node_b = ((Node) cell_b);
            if (node_a.getNextX() == cell_b.getX() && node_a.getNextY() == cell_b.getY() && node_b.getPrevX() == cell_a.getX() && node_b.getPrevY() == cell_a.getY()) {
                return true;
            }

            if (node_b.getNextX() == cell_a.getX() && node_b.getNextY() == cell_a.getY() && node_a.getPrevX() == cell_b.getX() && node_a.getPrevY() == cell_b.getY()) {
                return true;
            }
        }

        return false;
    }

    public PCB reset() {
        PCB pcb = new PCB(getWidth(), getHeight());
        for (StartNode startNode : _startNodes) {
            StartNode copy = new StartNode(startNode.getX(), startNode.getY());
            EndNode end = new EndNode(startNode.getEndX(), startNode.getEndY(), copy);
            pcb.addCell(copy);
            pcb.addCell(end);
        }
        updateState(pcb);

        return this;
    }

    @Override
    public Heuristic heuristic() {
        double value = Double.MAX_VALUE;
        double distance = 0;

        if (!_startNodes.isEmpty()) value = 0;
        for (StartNode startNode : _startNodes) {
            value += trailHypotenuse(startNode);
            distance += pathLength(startNode);
        }

        return new Heuristic(value, distance);
    }

    @Override
    public ArrayList<AStar> expand() {
        ArrayList<AStar> states = new ArrayList<>();
        ArrayList<Pair<Point, Point>> moves = new ArrayList<>();

        for (StartNode start : _startNodes) {
            Node node = findTrailingNode(start);
            for (int y = node.getY() - 1; y < node.getY() + 2; ++y) {
                for (int x = node.getX() - 1; x < node.getX() + 2; ++x) {
                    Point current = new Point(node.getX(), node.getY());

                    if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight() &&
                            (_cells[y][x] == null || _cells[y][x] instanceof EndNode) &&
                            !current.equals(new Point(x, y))) {

                        if (_cells[y][x] instanceof EndNode) {
                            EndNode endNode = ((EndNode) _cells[y][x]);
                            if (endNode.isConnected())
                                continue;
                        }

                        if (isConnected(new Point(current.x, y), new Point(x, current.y))){
                            continue;
                        }

                        moves.add(new Pair<>(current, new Point(x, y)));
                    }
                }
            }
        }

        for (Pair<Point, Point> move : moves) {
            PCB copy = new PCB(this);
            copy.addConduit(move);
            states.add(copy);
        }

        return states;
    }

    @Override
    public void updateState(AStar state) {
        if (state instanceof PCB) {
            PCB pcb = (PCB)state;

            _cells = new Cell[pcb.getHeight()][pcb.getWidth()];

            for (int y = 0; y < getHeight(); y++) {
                for (int x = 0; x < getWidth(); x++) {
                    _cells[y][x] = pcb.getCell(x, y);
                }
            }

            setChanged();
            notifyObservers();
        }
    }

    @Override
    public boolean isSame(AStar state) {
        return toString().equals(state.toString());
    }

    @Override
    public String toString() {
        ArrayList<Node> trails = new ArrayList<>();
        for (StartNode startNode : _startNodes) {
            trails.add(findTrailingNode(startNode));
        }

        System.out.println(trails);
        return trails.toString();
    }
}
