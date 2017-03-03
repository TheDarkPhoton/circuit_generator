package gui;

import ai.AI;
import logic.Cell;
import logic.cells.Node;
import logic.PCB;
import logic.cells.Selected;
import logic.cells.nodes.EndNode;
import logic.cells.nodes.StartNode;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by dovydas on 26/02/17.
 */
public class Controls implements MouseListener, MouseMotionListener, KeyListener {
    private PCB _pcb;
    private Point _origin;
    private Point _start;
    private boolean _b1Pressed = false;

    private StartNode _startNode = null;

    public Controls(PCB pcb) {
        _pcb = pcb;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Rectangle board = new Rectangle(
                    _pcb.getX(), _pcb.getY(),
                    _pcb.getWidth() * Cell.width,
                    _pcb.getHeight() * Cell.height
            );

            if (board.contains(e.getPoint())) {
                Point real = new Point(
                        (int)Math.floor((e.getX() - board.getX()) / Cell.width),
                        (int)Math.floor((e.getY() - board.getY()) / Cell.height)
                );

                if (_startNode == null) {
                    _startNode = new StartNode(real.x, real.y);
                    _pcb.addCell(new Selected(_startNode));
                } else {
                    EndNode endNode = new EndNode(real.x, real.y, _startNode);
                    if (!_startNode.getPosition().equals(endNode.getPosition())){
                        _pcb.addCell(_startNode);
                        _pcb.addCell(endNode);
                        _startNode = null;
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            _origin = _pcb.getPosition();
            _start = e.getPoint();
            _b1Pressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            _b1Pressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (_b1Pressed) {
            Point newPosition = new Point(
                    _origin.x + (e.getX() - _start.x),
                    _origin.y + (e.getY() - _start.y)
            );

            _pcb.setPosition(newPosition);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (_startNode != null) {
                _pcb.removeCell(_startNode);
                _startNode = null;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Looking for solution");
                    AI test = new AI(_pcb.reset());
                    test.findSolution();
                    System.out.println("Solution found!");
                }
            });

            t.start();
        }
    }
}
