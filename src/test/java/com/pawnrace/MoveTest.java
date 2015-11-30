package com.pawnrace;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoveTest {
    private Square from;
    private Square to;
    private Move move;
    private Move captureMove;

    @Before public void setUp() throws Exception {
        from = new Square(1,1); //b2
        to = new Square(2,1); //b3

        move = new Move(from, to, false);
        captureMove = new Move(from, to, true);
    }

    @Test public void testGetTo() throws Exception {
        assertEquals(to, move.getTo());
    }

    @Test public void testIsCapture() throws Exception {
        assertEquals(false, move.isCapture());
        assertEquals(true, captureMove.isCapture());
    }

    @Test public void testGetFrom() throws Exception {
        assertEquals(from, move.getFrom());
    }

    @Test public void testGetSAN() throws Exception {
        assertEquals("b3", move.getSAN());
        assertEquals("bxb3", captureMove.getSAN());
    }

    @Test public void testIsDouble() throws Exception {
        assertEquals(false, move.isDouble());
        move = new Move(move.getFrom(), new Square(to.getX() + 1, to.getY()),
            false);
        assertEquals(true, move.isDouble());
    }
}
