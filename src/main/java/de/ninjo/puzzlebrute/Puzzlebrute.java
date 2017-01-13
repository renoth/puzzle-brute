package de.ninjo.puzzlebrute;

import de.ninjo.puzzlebrute.domain.Hexfield;
import de.ninjo.puzzlebrute.domain.PlayField;
import de.ninjo.puzzlebrute.domain.Tile;
import de.ninjo.puzzlebrute.domain.TileGroup;
import de.ninjo.puzzlebrute.gui.PlayfieldDrawer;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Puzzlebrute {
    public static void main(String[] args) {
        PlayField playField = new PlayField(4);

        playField.getHexfields().parallelStream().forEach(field -> {
            System.out.println(field.getPosition());

            try {
                tryToFitTiles(new PlayField(4), createTiles(), field);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    private static void tryToFitTiles(PlayField playField, List<TileGroup> tiles, Hexfield startField) throws InvocationTargetException, InterruptedException {
        if (tiles.size() == 0) {
            System.out.println("WOW WOW WOW WOW WOW WOW");
            SwingUtilities.invokeAndWait(() -> new PlayfieldDrawer(playField));
        } else {
            TileGroup attemptedTile = tiles.get(0);

            for (Tile rotatedTile : attemptedTile.getRotations()) {

                if (tryToMatchTile(playField, startField, rotatedTile)) {
                    TileGroup usedTile = tiles.remove(0);

                    playField.occupyFields(startField, rotatedTile);

                    for (Hexfield newStartfield : playField.getUnoccupiedFields()) {
                        tryToFitTiles(playField, tiles, newStartfield);
                    }

                    playField.unOccupyFields(startField, rotatedTile);
                    tiles.add(0, usedTile);
                }
            }
        }
    }

    private static List<TileGroup> createTiles() {
        List<TileGroup> tiles = new ArrayList<>();

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(0, 1), new Hexfield(0, 2), new Hexfield(1, 2), new Hexfield(2, 2)
        ), Color.BLUE));

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(0, 1), new Hexfield(-1, 1), new Hexfield(1, 1), new Hexfield(1, 2)
        ), Color.cyan));

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(0, 1), new Hexfield(1, 0), new Hexfield(1, 1), new Hexfield(1, 2)
        ), Color.red));

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(0, 1), new Hexfield(0, 2), new Hexfield(-1, 2), new Hexfield(1, 2)
        ), Color.LIGHT_GRAY));

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(-1, 0), new Hexfield(1, -1), new Hexfield(0, 1), new Hexfield(0, 2)
        ), Color.yellow));

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(1, 0), new Hexfield(0, 1), new Hexfield(0, 2), new Hexfield(0, 3)
        ), Color.DARK_GRAY));

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(0, 1), new Hexfield(0, 2), new Hexfield(1, 1), new Hexfield(1, 2)
        ), Color.black));

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(0, 1), new Hexfield(1, 1), new Hexfield(1, 2), new Hexfield(1, 3)
        ), Color.MAGENTA));

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(0, 1), new Hexfield(1, 1), new Hexfield(1, 2), new Hexfield(2, 1)
        ), new Color(255, 221, 151)));

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(1, 0), new Hexfield(2, 0), new Hexfield(1, 1), new Hexfield(1, 2)
        ), new Color(74, 255, 149)));

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(0, 1), new Hexfield(-1, 2), new Hexfield(-2, 3), new Hexfield(-3, 4)
        ), new Color(255, 109, 167)));

        tiles.add(new TileGroup(new Hexfield(0, 0), Arrays.asList(
                new Hexfield(0, 1), new Hexfield(0, 2), new Hexfield(-1, 3), new Hexfield(-1, 1)
        ), new Color(116, 233, 255)));

        return tiles;
    }

    private static boolean tryToMatchTile(PlayField playField, Hexfield field, Tile tile) {
        if (field.isOccupied()) {
            return false;
        }

        for (Hexfield tileField : tile.getOccupiedTilesRelativeToRoot()) {
            Hexfield fieldToOccupy = playField.getFieldAt(field.getPosition().add(tileField.getPosition()));
            if (fieldToOccupy == null || fieldToOccupy.isOccupied()) {
                return false;
            }
        }

        return true;
    }
}
