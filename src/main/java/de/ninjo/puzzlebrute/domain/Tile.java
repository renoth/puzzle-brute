package de.ninjo.puzzlebrute.domain;


import java.awt.*;
import java.util.List;

public class Tile {
    Hexfield rootTile;

    List<Hexfield> occupiedTilesRelativeToRoot;

    Coordinate positionOnPlayField;
    private Color color;

    public Tile(Hexfield rootTile, List<Hexfield> occupiedTilesRelativeToRoot, Color color) {
        this.rootTile = rootTile;
        this.occupiedTilesRelativeToRoot = occupiedTilesRelativeToRoot;
        this.color = color;
    }

    public Hexfield getRootTile() {
        return rootTile;
    }

    public void setRootTile(Hexfield rootTile) {
        this.rootTile = rootTile;
    }

    public List<Hexfield> getOccupiedTilesRelativeToRoot() {
        return occupiedTilesRelativeToRoot;
    }

    public void setOccupiedTilesRelativeToRoot(List<Hexfield> occupiedTilesRelativeToRoot) {
        this.occupiedTilesRelativeToRoot = occupiedTilesRelativeToRoot;
    }

    public Coordinate getPositionOnPlayField() {
        return positionOnPlayField;
    }

    public void setPositionOnPlayField(Coordinate positionOnPlayField) {
        this.positionOnPlayField = positionOnPlayField;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
