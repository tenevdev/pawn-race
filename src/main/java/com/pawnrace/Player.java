package com.pawnrace;

public class Player {
    private Game game;
    private Board board;
    private Color ownColor;
    private boolean isComputer;
    private Player opponent;

    public Player(Game game, Board board, Color color,
        boolean isComputerPlayer) {
        this.game = game;
        this.board = board;
        this.ownColor = color;
        this.isComputer = isComputerPlayer;
    }

    public void setOpponent(Player opp) {
        if (opponent == null) {
            opponent = opp;
        }
    }

    public Color getColor() {
        return ownColor;
    }

    public boolean isComputerPlayer() {
        return isComputer;
    }

    public Square[] getAllPawns() {
        int ownPawnsCount = 0;
        Square[] squares = new Square[Board.SIZE];

        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                if (ownColor == board.getSquare(i, j).occupiedBy()) {
                    squares[ownPawnsCount++] = board.getSquare(i, j);
                }
            }
        }

        Square[] rightSquares = new Square[ownPawnsCount];
        for (int i = 0; i < ownPawnsCount; i++) {
            rightSquares[i] = squares[i];
        }

        return rightSquares;
    }

    public Move[] getAllValidMoves() {
        Square[] pawns = getAllPawns();
        Move[] moves = new Move[21];
        int nextMoveIndex = 0;

        for (Square pawn : pawns) {
            Square front =
                new Square(pawn.getX() + ownColor.getDirection(), pawn.getY());
            Square doubleFront =
                new Square(front.getX() + ownColor.getDirection(), pawn.getY());
            Square left = new Square(front.getX(), front.getY() - 1);
            Square right = new Square(front.getX(), front.getY() + 1);

            String forward = front.toString();
            String doubleForward = doubleFront.toString();
            String captureLeft = new Move(pawn, left, true).getSAN();
            String captureRight = new Move(pawn, right, true).getSAN();

            Move move = game.parseMove(forward);
            if (move != null) {
                moves[nextMoveIndex++] = move;
            }
            move = game.parseMove(doubleForward);
            if (move != null) {
                moves[nextMoveIndex++] = move;
            }
            move = game.parseMove(captureLeft);
            if (move != null) {
                moves[nextMoveIndex++] = move;
            }
            move = game.parseMove(captureRight);
            if (move != null) {
                moves[nextMoveIndex++] = move;
            }
        }

        Move[] rightMoves = new Move[nextMoveIndex];
        for (int i = 0; i < nextMoveIndex; i++) {
            rightMoves[i] = moves[i];
        }

        return rightMoves;
    }

    public void makeMove() {
        // Trivial move
        game.applyMove(getAllValidMoves()[0]);
    }

    public boolean hasPassedPawn() {
        Square[] pawns = getAllPawns();
        for (Square pawn : pawns) {
            if (board.isPassed(pawn)) {
                return true;
            }
        }
        return false;
    }
}
