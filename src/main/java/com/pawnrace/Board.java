package com.pawnrace;

public class Board {
    public static final int SIZE = 8;

    private Square[][] squares;

    public Board(char whiteGap, char blackGap) {
        squares = new Square[SIZE][SIZE];

        int whiteGapIndex = gapPositionToIndex(whiteGap);
        int blackGapIndex = gapPositionToIndex(blackGap);

        populateBoard(whiteGapIndex, blackGapIndex);
    }

    public Square getSquare(int x, int y) {
        return squares[x][y];
    }

    public int getStartingRow(Color color) {
        return color == Color.WHITE ? 1 : Board.SIZE - 2;
    }

    /**
     * Returns the movement direction of a player
     * White moves up the board so its direction is -1
     * Black moves down and the index increases by 1
     *
     * @return -1 for WHITE; 1 for BLACK; 0 for NONE
     */
    public int getDirection(Color color) {
        return color.ordinal() - 1;
    }

    public void applyMove(Move move) {
        Square from = move.getFrom();
        Square to = move.getTo();

        // Check for capture
        if (move.isCapture()) {
            // Remove captured pawn
            to.setOccupier(Color.NONE);

            // Row delta handles en passant captures such as b5xc5 where b5
            // must move to c6. c5 is the actual position of the captured pawn.
            // It can be deduced by movement directions of each color
            int deltaX = (from.occupiedBy() == Color.WHITE) ? 1 : -1;

            // Place from on its new position
            squares[from.getX() + deltaX][to.getY()].setOccupier(from.occupiedBy());


        } else {
            // Place from on its new position
            to.setOccupier(from.occupiedBy());
        }

        from.setOccupier(Color.NONE);
    }

    public void unapplyMove(Move move) {

    }

    /**
     * Prints the board to the console.
     */
    public void display() {
        String line;

        System.out.println("   A B C D E F G H\n");
        for (int i = SIZE - 1; i >= 0; i--) {
            line = (i + 1) + "  ";
            for (int j = 0; j < SIZE; j++) {
                line += squares[i][j].getDisplayChar() + " ";
            }
            line += " " + (i + 1);
            System.out.println(line);
        }
        System.out.println("\n   A B C D E F G H");
    }

    public boolean isPassed(Square square) {
        if (square.occupiedBy() == Color.NONE) {
            // Empty squares cannot be passed
            return false;
        }

        int direction = square.occupiedBy().getDirection();
        int centreColumn = square.getY();
        if (direction == 1) {
            // Check each row in the movement direction
            for (int i = square.getX() + direction; i < SIZE; i += direction) {
                if (!checkRow(i, centreColumn)) {
                    return false;
                }
            }
            // All checks passed
            return true;
        } else {
            for (int i = square.getX(); i >= 0; i--) {
                if (!checkRow(i, centreColumn)) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean checkRow(int rowIndex, int centreColumn) {
        if (centreColumn != 0) {
            // Check to the left
            if (squares[rowIndex][centreColumn - 1].occupiedBy()
                != Color.NONE) {
                return false;
            }
        }
        if (centreColumn != SIZE - 1) {
            // Check to the right
            if (squares[rowIndex][centreColumn + 1].occupiedBy()
                != Color.NONE) {
                return false;
            }
        }
        if (squares[rowIndex][centreColumn].occupiedBy() != Color.NONE) {
            // Check centre
            return false;
        }

        return true;
    }

    private static int gapPositionToIndex(char gap) {
        return (int) gap - (int) 'a';
    }

    private void populateBoard(int whiteGapIndex, int blackGapIndex) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                squares[i][j] = new Square(i, j);
                if (i == 1 && whiteGapIndex != j) {
                    squares[i][j].setOccupier(Color.WHITE);
                } else if (i == SIZE - 2 && blackGapIndex != j) {
                    squares[i][j].setOccupier(Color.BLACK);
                }
            }
        }
    }
}
