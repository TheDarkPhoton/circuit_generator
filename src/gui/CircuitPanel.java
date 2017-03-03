package gui;

import logic.PCB;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by dovydas on 26/02/17.
 */
public class CircuitPanel extends JPanel implements Observer {
    private PCB _pcb;

    public CircuitPanel(PCB pcb){
        _pcb = pcb;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        _pcb.paint(g2);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
