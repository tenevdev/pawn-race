package com.pawnrace;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SquareTest {
    private Square square;

    @Before public void setUp() throws Exception {
        square = new Square(2, 3); //d3
    }

    @Test public void testGetX() throws Exception {
        assertTrue(2 == square.getX());
    }

    @Test public void testGetY() throws Exception {
        assertTrue(3 == square.getY());
    }

    @Test public void testGetYCharacter() throws Exception {
        assertEquals('d', square.getYCharacter());
    }

    @Test public void testOccupiedBy() throws Exception {
        assertEquals(Color.NONE, square.occupiedBy());
    }

    @Test public void testSetOccupier() throws Exception {
        square.setOccupier(Color.WHITE);
        assertEquals(Color.WHITE, square.occupiedBy());
    }

    @Test public void testToString() throws Exception {
        assertEquals("d3", square.toString());
    }

    @Test public void testGetDisplayChar() throws Exception {
        assertEquals('.', square.getDisplayChar());
        square.setOccupier(Color.BLACK);
        assertEquals('B', square.getDisplayChar());
    }

    @Test public void testFromString() throws Exception {
        Square a1 = new Square(1, 0);
        assertEquals(a1, Square.fromString("a2"));
    }
}
