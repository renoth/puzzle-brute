package de.ninjo.puzzlebrute.domain;

import java.util.ArrayList;
import java.util.List;

public class TileGroup {
    List<Tile> rotations = new ArrayList<>();

    public TileGroup(int id, Hexfield rootTile, List<Hexfield> occupiedTilesRelativeToRoot) {
        rotations.add(new Tile(id, rootTile, occupiedTilesRelativeToRoot));

        List<Hexfield> rotatedHexfields = new ArrayList<>();

        for (Hexfield tileToRotate : occupiedTilesRelativeToRoot) {
            Coordinate oldPos = tileToRotate.position;
            rotatedHexfields.add(new Hexfield(-oldPos.x - oldPos.y, oldPos.x));
        }

        rotations.add(new Tile(id, new Hexfield(0, 0), rotatedHexfields));

        List<Hexfield> rotatedHexfields2 = new ArrayList<>();

        for (Hexfield tileToRotate : rotatedHexfields) {
            Coordinate oldPos = tileToRotate.position;
            rotatedHexfields2.add(new Hexfield(-oldPos.x - oldPos.y, oldPos.x));
        }

        rotations.add(new Tile(id, new Hexfield(0, 0), rotatedHexfields2));
    }

    public List<Tile> getRotations() {
        return rotations;
    }
}
