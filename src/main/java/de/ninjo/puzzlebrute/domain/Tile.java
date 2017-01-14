package de.ninjo.puzzlebrute.domain;


import java.util.List;

public class Tile {
    public int id;
    Hexfield rootTile;
    List<Hexfield> occupiedTilesRelativeToRoot;

    public Tile(int id, Hexfield rootTile, List<Hexfield> occupiedTilesRelativeToRoot) {
        this.id = id;
        this.rootTile = rootTile;
        this.occupiedTilesRelativeToRoot = occupiedTilesRelativeToRoot;
    }

    public List<Hexfield> getOccupiedTilesRelativeToRoot() {
        return occupiedTilesRelativeToRoot;
    }

    public int getId() {
        return id;
    }
}
