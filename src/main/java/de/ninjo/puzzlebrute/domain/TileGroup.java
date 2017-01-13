package de.ninjo.puzzlebrute.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TileGroup {


    List<Tile> rotations = new ArrayList<>();



    public TileGroup(Hexfield rootTile, List<Hexfield> occupiedTilesRelativeToRoot, Color color) {
        rotations.add(new Tile(rootTile, occupiedTilesRelativeToRoot, color));

        List<Hexfield> rotatedHexfields = new ArrayList<>();

        for (Hexfield tileToRotate : occupiedTilesRelativeToRoot) {
            Coordinate oldPos = tileToRotate.position;
            rotatedHexfields.add(new Hexfield(-oldPos.x - oldPos.y, oldPos.x));
        }

        rotations.add(new Tile(new Hexfield(0,0), rotatedHexfields, color));

        List<Hexfield> rotatedHexfields2 = new ArrayList<>();

        for (Hexfield tileToRotate : rotatedHexfields) {
            Coordinate oldPos = tileToRotate.position;
            rotatedHexfields2.add(new Hexfield(-oldPos.x - oldPos.y, oldPos.x));
        }

        rotations.add(new Tile(new Hexfield(0,0), rotatedHexfields2, color));

    }

    public List<Tile> getRotations() {
        return rotations;
    }
}
