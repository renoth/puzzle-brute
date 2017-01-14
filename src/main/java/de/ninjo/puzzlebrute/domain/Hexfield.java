package de.ninjo.puzzlebrute.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.geometry.Point2D;

public class Hexfield {
    private static final int GRID_SIZE = 40;
    Coordinate position;
    boolean isOccupied = false;
    private Tile occupant;
    private int id;

    public Hexfield(int x, int y) {
        position = new Coordinate(x, y);
    }

    public Hexfield(int x, int y, int id) {
        position = new Coordinate(x, y);
        this.id = id;
    }

    @JsonIgnore
    public Point2D getXYPosition() {
        double xPos = 1.5d * GRID_SIZE * position.x;
        double yPos = Math.sqrt(3) * GRID_SIZE * (position.x / 2d + position.y);

        return new Point2D(xPos, yPos);
    }

    public Coordinate getPosition() {
        return position;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Tile getOccupant() {
        return occupant;
    }

    public void setOccupant(Tile occupant) {
        this.occupant = occupant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
