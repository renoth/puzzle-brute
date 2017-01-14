package de.ninjo.puzzlebrute;

import de.ninjo.puzzlebrute.domain.Hexfield;
import de.ninjo.puzzlebrute.domain.PlayField;
import de.ninjo.puzzlebrute.domain.Tile;
import de.ninjo.puzzlebrute.domain.TileGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Puzzlebrute {
    static long sequence = 0;

    public static void main(String[] args) throws IOException, InvocationTargetException, InterruptedException {
        PlayField playField = new PlayField(4);

        playField.getHexfields().parallelStream().forEach(field -> {
            System.out.println(field.getPosition());

            PlayField newPlayfield = new PlayField(4);

            try {
                tryToFitTiles(newPlayfield, createTiles(), newPlayfield.getFieldAt(field.getPosition()));
            } catch (InvocationTargetException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });

    }

    private static void tryToFitTiles(PlayField playField, List<TileGroup> tiles, Hexfield startField) throws InvocationTargetException, InterruptedException, IOException {
        if (tiles.size() == 0) {
            //we found a solution, save for later
            writeSolutionToFile(++sequence, playField);
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

    private static void writeSolutionToFile(long counter, PlayField playField) {
        try (PrintWriter out = new PrintWriter(new File("target/" + counter + ".csv"))) {
            playField.getHexfields().stream()
                    .forEach(hexfield -> {
                        out.write(hexfield.getPosition().getX() + "," + hexfield.getPosition().getY() + "," + (hexfield.getOccupant() != null ? hexfield.getOccupant().getId() : "X") + "\n");
                    });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<TileGroup> createTiles() {
        List<TileGroup> tiles = new ArrayList<>();

        tiles.add(new TileGroup(1, new Hexfield(0, 0), Arrays.asList(new Hexfield(0, 1), new Hexfield(0, 2), new Hexfield(1, 2), new Hexfield(2, 2))));
        tiles.add(new TileGroup(2, new Hexfield(0, 0), Arrays.asList(new Hexfield(0, 1), new Hexfield(-1, 1), new Hexfield(1, 1), new Hexfield(1, 2))));
        tiles.add(new TileGroup(3, new Hexfield(0, 0), Arrays.asList(new Hexfield(0, 1), new Hexfield(1, 0), new Hexfield(1, 1), new Hexfield(1, 2))));
        tiles.add(new TileGroup(4, new Hexfield(0, 0), Arrays.asList(new Hexfield(0, 1), new Hexfield(0, 2), new Hexfield(-1, 2), new Hexfield(1, 2))));
        tiles.add(new TileGroup(5, new Hexfield(0, 0), Arrays.asList(new Hexfield(-1, 0), new Hexfield(1, -1), new Hexfield(0, 1), new Hexfield(0, 2))));
        tiles.add(new TileGroup(6, new Hexfield(0, 0), Arrays.asList(new Hexfield(1, 0), new Hexfield(0, 1), new Hexfield(0, 2), new Hexfield(0, 3))));
        tiles.add(new TileGroup(7, new Hexfield(0, 0), Arrays.asList(new Hexfield(0, 1), new Hexfield(0, 2), new Hexfield(1, 1), new Hexfield(1, 2))));
        tiles.add(new TileGroup(8, new Hexfield(0, 0), Arrays.asList(new Hexfield(0, 1), new Hexfield(1, 1), new Hexfield(1, 2), new Hexfield(1, 3))));
        tiles.add(new TileGroup(9, new Hexfield(0, 0), Arrays.asList(new Hexfield(0, 1), new Hexfield(1, 1), new Hexfield(1, 2), new Hexfield(2, 1))));
        tiles.add(new TileGroup(10, new Hexfield(0, 0), Arrays.asList(new Hexfield(1, 0), new Hexfield(2, 0), new Hexfield(1, 1), new Hexfield(1, 2))));
        tiles.add(new TileGroup(11, new Hexfield(0, 0), Arrays.asList(new Hexfield(0, 1), new Hexfield(-1, 2), new Hexfield(-2, 3), new Hexfield(-3, 4))));
        tiles.add(new TileGroup(12, new Hexfield(0, 0), Arrays.asList(new Hexfield(0, 1), new Hexfield(0, 2), new Hexfield(-1, 3), new Hexfield(-1, 1))));

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
