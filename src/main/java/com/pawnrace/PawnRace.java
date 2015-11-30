package com.pawnrace;

import java.io.IOException;
import java.util.Scanner;

public class PawnRace {
    private static Scanner scan;

    public static void main(String[] args)
        throws IOException, InterruptedException {
        scan = new Scanner(System.in);

        String whitePlayer = args[0],
            blackPlayer = args[1];
        char blackGap = args[3].toLowerCase().charAt(0),
            whiteGap = args[2].toLowerCase().charAt(0);

        Board board = new Board(whiteGap, blackGap);
        Game game = new Game(board);
        Player p1 =
            new Player(game, board, Color.WHITE, whitePlayer.equals("C"));
        Player p2 =
            new Player(game, board, Color.BLACK, blackPlayer.equals("C"));

        board.display();
        start(game, board, p1, p2);
    }

    private static void start(Game game, Board board, Player p1, Player p2)
        throws IOException, InterruptedException {
        Player current = p1;
        while (!game.isFinished()) {
            // Display board
            board.display();

            // Execute move
            if (current.isComputerPlayer()) {
                current.makeMove();
            } else {
                Move move;
                do {
                    String moveInput = promptMove();
                    move = game.parseMove(moveInput);
                } while (move == null);
                game.applyMove(move);
            }

            // Switch current player
            if (game.getCurrentPlayer() == Color.BLACK) {
                current = p2;
            } else {
                current = p1;
            }
        }

        // Revert last switch player
        if (game.getCurrentPlayer() == Color.BLACK) {
            current = p1;
        } else {
            current = p2;
        }

        board.display();
        System.out.println(current.getColor() + " Player won!");
    }

    private static String promptMove() {
        System.out.println("It is your turn. Input your move : ");
        return scan.nextLine();
    }
}
