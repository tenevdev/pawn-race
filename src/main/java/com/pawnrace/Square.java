package com.pawnrace;

public class Square {
    private int x;
    private int y;
    private Color occupier;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;

        // Initialise all squares as unoccupied
        this.occupier = Color.NONE;
    }

    /**
     * The row index occupied by this square
     */
    public int getX() {
        return x;
    }

    /**
     * The column index occupied by this square
     */
    public int getY() {
        return y;
    }

    public char getYCharacter() {
        return (char) ((int) 'a' + y);
    }

    public Color occupiedBy() {
        return occupier;
    }

    public void setOccupier(Color color) {
        occupier = color;
    }

    /**
     * A string consisting of the column letter and row number
     */
    @Override public String toString() {
        char l = getYCharacter();
        return String.valueOf(l) + (x + 1);
    }

    @Override public boolean equals(Object obj) {
        Square square = (Square) obj;
        if (square.getX() == x && square.getY() == y
            && square.occupiedBy() == occupier) {
            return true;
        }
        return false;
    }

    public char getDisplayChar() {
        switch (occupier) {
            case NONE:
                return '.';
            case WHITE:
                return 'W';
            case BLACK:
                return 'B';
        }
        return ' ';
    }

    public static Square fromString(String san) {
        int y = (int) san.charAt(0) - (int) 'a';
        int x = (int) san.charAt(1) - (int) '1';
        return new Square(x, y);
    }
}
