package gui;

import logic.Cell;
import logic.PCB;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dovydas on 26/02/17.
 */
public class Window extends JFrame {
    private PCB _pcb = new PCB(10, 10);

    public Window() {
        super("Circuit");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);

        CircuitPanel area = new CircuitPanel(_pcb);
        _pcb.addObserver(area);
        add(area);

        Controls controls = new Controls(_pcb);
        area.addMouseListener(controls);
        area.addMouseMotionListener(controls);
        addKeyListener(controls);

        setVisible(true);

        centerBoard();
    }

    private void centerBoard(){
        _pcb.setPosition(new Point(
                (getWidth() / 2) - (_pcb.getWidth() * Cell.width / 2),
                (getHeight() / 2) - (_pcb.getHeight() * Cell.height / 2)
        ));
    }
}
