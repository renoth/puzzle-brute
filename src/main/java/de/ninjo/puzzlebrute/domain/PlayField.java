package de.ninjo.puzzlebrute.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayField {

    Map<Coordinate, Hexfield> hexfieldMap = new HashMap<>();

    public PlayField() {
    }

    public PlayField(int radius) {
        IntStream.rangeClosed(-radius, radius).forEach(x -> {
            IntStream.rangeClosed(-radius, radius).forEach(y -> {
                if (Math.abs(x + y) <= radius) {
                    hexfieldMap.put(new Coordinate(x ,y) ,new Hexfield(x, y));
                }
            });
        });
    }

    public Collection<Hexfield> getHexfields() {
        return hexfieldMap.values();
    }

    public Hexfield getFieldAt(Coordinate coordinate) {
        return hexfieldMap.get(coordinate);

    }

    public void occupyFields(Hexfield field, Tile tile) {
        field.setOccupied(true);
        field.setOccupant(tile);

        for (Hexfield tileField : tile.getOccupiedTilesRelativeToRoot()) {
           getFieldAt(field.getPosition().add(tileField.getPosition())).setOccupied(true);
           getFieldAt(field.getPosition().add(tileField.getPosition())).setOccupant(tile);
        }
    }

    public void unOccupyFields(Hexfield field, Tile tile) {
        field.setOccupied(false);
        field.setOccupant(null);

        for (Hexfield tileField : tile.getOccupiedTilesRelativeToRoot()) {
           getFieldAt(field.getPosition().add(tileField.getPosition())).setOccupied(false);
           getFieldAt(field.getPosition().add(tileField.getPosition())).setOccupant(null);
        }
    }

    public List<Hexfield> getUnoccupiedFields() {
        return hexfieldMap.values().stream().filter(field -> !field.isOccupied()).collect(Collectors.toList());
    }
}
