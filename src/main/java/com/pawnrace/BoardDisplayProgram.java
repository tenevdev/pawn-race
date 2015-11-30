package com.pawnrace;

public class BoardDisplayProgram {
    public static void main(String[] args){
        Board board = new Board('c', 'f');
        board.display();
        Square w2 = board.getSquare(1,1);
        Square e2 = board.getSquare(3,1);
        Move move = new Move(w2, e2, false);
        board.applyMove(move);
        board.display();
    }
}
