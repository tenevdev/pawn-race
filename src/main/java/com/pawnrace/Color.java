package com.pawnrace;

public enum Color {
    WHITE, //0
    NONE,  //1
    BLACK;  //2

    public int getDirection() {
        return 1 - this.ordinal();
    }

    public int getOpponentOrdinal() {
        return Math.abs(this.ordinal() - 2);
    }
}
