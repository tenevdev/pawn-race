package com.pawnrace;

public class Game {
    private static final int MAX_MOVES = 4096;
    private Board board;
    private Move[] moves;
    private int nextMoveIndex = 0;
    private boolean finished = false;

    public Game(Board board) {
        this.board = board;
        this.moves = new Move[MAX_MOVES];
        this.nextMoveIndex = 0;
    }

    public Color getCurrentPlayer() {
        // White always starts
        return nextMoveIndex % 2 == 0 ? Color.WHITE : Color.BLACK;
    }

    public Move getLastMove() {
        if (nextMoveIndex != 0) {
            return moves[nextMoveIndex - 1];
        }
        return null;
    }

    public void applyMove(Move move) {
        if (!finished) {
            moves[nextMoveIndex++] = move;
            board.applyMove(move);
            checkFinished(move.getTo());
        }
    }

    public void unapplyMove(Move move) {
        moves[--nextMoveIndex] = null;
        board.unapplyMove(move);
    }

    public boolean isFinished() {
        return finished;
    }

    public Color getGameResult() {
        if (finished) {
            return getLastMove().getTo().occupiedBy();
        }
        return null;
    }

    /**
     * Parses a move from short algebraic notation
     *
     * @param san a valid move in short algebraic notation
     * @return the parsed move object if valid or null otherwise
     */
    public Move parseMove(String san) {
        // Check length
        if (san.length() != 2 && san.length() != 4) {
            return null;
        }

        Square from, to;
        Color player = getCurrentPlayer();
        boolean isCapture = false;

        if (san.contains("x")) {
            // A capture move (bxc4)
            isCapture = true;
            char fromColumn = san.charAt(0);
            int column = columnCharToInt(fromColumn);
            int delta = player.ordinal() - 1;

            to = Square.fromString(san.substring(2));
            if (to.getX() >= 0 && to.getX() < Board.SIZE && to.getY() >= 0
                && to.getY() < Board.SIZE) {
                to = board.getSquare(to.getX(), to.getY());
                from = board.getSquare(delta + to.getX(), column);
            } else {
                return null;
            }
        } else {
            // A forward move (b4)
            to = Square.fromString(san);
            if (to.getX() >= 0 && to.getX() < Board.SIZE && to.getY() >= 0
                && to.getY() < Board.SIZE) {
                to = board.getSquare(to.getX(), to.getY());
                from = deduceMovedSquare(to);
            } else {
                return null;
            }

            // Check if valid forward move
            if (from == null) {
                return null;
            }
        }
        Move move = new Move(from, to, isCapture);
        if (!isValidMove(move)) {
            return null;
        }

        return move;
    }

    private Square deduceMovedSquare(Square to) {
        Color player = getCurrentPlayer();

        int direction = board.getDirection(player);
        Square movedSquare = board.getSquare(to.getX() + direction, to.getY());

        if (movedSquare.occupiedBy() != player) {
            movedSquare =
                board.getSquare(movedSquare.getX() + direction, to.getY());
            if (movedSquare.occupiedBy() != player) {
                // Long jumps validation
                return null;
            }
            return movedSquare;
        }

        return movedSquare;
    }

    private void checkFinished(Square to) {
        if (to.getX() == 0 || to.getX() == Board.SIZE - 1) {
            finished = true;
        }
    }

    private boolean isValidMove(Move move) {
        Square from = move.getFrom(),
            to = move.getTo();
        Color player = getCurrentPlayer(),
            other = player == Color.BLACK ? Color.WHITE : Color.BLACK;
        if (from.occupiedBy() == player) {
            if (move.isCapture()) {
                // Check for valid capture
                if (to.occupiedBy() == Color.NONE) {
                    // Check en passant
                    int direction = board.getDirection(player);
                    if (board.getSquare(to.getX() + direction, to.getY())
                        .occupiedBy() != other || !getLastMove().isDouble()) {
                        return false;
                    }
                    return true;
                } else {
                    // Normal capture
                    // Check if capturing a rival pawn
                    if (to.occupiedBy().ordinal() == player
                        .getOpponentOrdinal()) {
                        return true;
                    }
                    return false;
                }
            } else {
                // Check number of steps
                if (to.occupiedBy() != Color.NONE) {
                    return false;
                }

                int steps = Math.abs(from.getX() - to.getX());
                if (steps == 1 || (steps == 2
                    && board.getStartingRow(player) == from.getX())) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private int columnCharToInt(char column) {
        return (int) column - (int) 'a';
    }
}
