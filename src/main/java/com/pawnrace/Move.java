package com.pawnrace;

public class Move {
    private Square from;
    private Square to;
    private boolean isCapture;

    public Move(Square from, Square to, boolean isCapture) {
        this.from = from;
        this.to = to;
        this.isCapture = isCapture;
    }

    public Square getTo() {
        return to;
    }

    public boolean isCapture() {
        return isCapture;
    }

    public Square getFrom() {
        return from;
    }

    public String getSAN() {
        if(isCapture){
            return from.toString().charAt(0) + ('x' + to.toString());
        }
        return to.toString();
    }

    public boolean isDouble(){
        return (Math.abs(from.getX() - to.getX()) == 2);
    }
}
