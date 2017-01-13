package de.ninjo.puzzlebrute.domain;

public class Coordinate {
    int x,y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate) {
            return ((Coordinate) obj).getX() == x && ((Coordinate) obj).getY() == y;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return x * 23 + y;
    }

    public Coordinate add(Coordinate position) {
        return new Coordinate(x + position.x, y + position.y);
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y;
    }
}
