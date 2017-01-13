package de.ninjo.puzzlebrute.gui;

import de.ninjo.puzzlebrute.domain.PlayField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

public class PlayfieldDrawer extends JFrame {

    PlayField playField;

    public PlayfieldDrawer(PlayField playField) {
        this.playField = playField;
        setSize(new Dimension(800, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void paint(Graphics g) {
        g.clearRect(0,0, 800, 800);
        g.setColor(Color.BLACK);

        playField.getHexfields().stream().forEach(field -> {
            int xPos = (int) Math.round(field.getXYPosition().getX());
            int yPos = (int) Math.round(field.getXYPosition().getY());
            if (field.getOccupant() != null) {
                g.setColor(field.getOccupant().getColor());
            } else {
                g.setColor(Color.white);
            }

            if (field.isOccupied()) {
                g.fillOval(xPos + 400, yPos + 400, 20,20);
            } else {
                g.drawOval(xPos + 400, yPos + 400, 20,20);
            }

            String positionString = "(" + field.getPosition().getX() + "," + field.getPosition().getY() + ")";

            g.drawString(positionString, xPos + 380, yPos + 380);
        });
    }

    public static void main(String arg[]) {


    }
}