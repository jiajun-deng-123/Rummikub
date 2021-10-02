package main;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class meldTest {
    @Test
    public void testMeld(){
        meld m1 = new meld();
        m1.addTile(new tile("R2"));
        m1.addTile(new tile("R1"));
        m1.addTile(new tile("R3"));

        assertEquals("R1 R2 R3 ", m1.toString());
    }

    @Test
    public void testAddTile(){
        meld m1 = new meld();
        m1.addTile(new tile("R2"));
        m1.addTile(new tile("R3"));
        m1.addTile(new tile("R4"));

        assertEquals("R2 R3 R4 ", m1.toString());

        m1.addTile(new tile("R1"));

        assertEquals("R1 R2 R3 R4 ", m1.toString());

        m1.addTile(new tile("R5"));

        assertEquals("R1 R2 R3 R4 R5 ", m1.toString());

        meld m2 = new meld();
        m2.addTile(new tile("B2"));
        m2.addTile(new tile("R2"));
        m2.addTile(new tile("O2"));

        assertEquals("B2 O2 R2 ", m2.toString());

        m2.addTile(new tile("G2"));

        assertEquals("B2 G2 O2 R2 ", m2.toString());
    }

    @Test
    public void testMove(){
        meld m1 = new meld();
        m1.addTile(new tile("R1"));
        m1.addTile(new tile("R2"));
        m1.addTile(new tile("R3"));
        m1.addTile(new tile("R4"));
        m1.addTile(new tile("R5"));
        m1.addTile(new tile("R6"));

        assertEquals("R1 R2 R3 R4 R5 R6 ", m1.toString());

        m1.moveHead();

        assertEquals("R2 R3 R4 R5 R6 ", m1.toString());

        m1.moveTail();

        assertEquals("R2 R3 R4 R5 ", m1.toString());

        assertTrue(m1.moveTile(new tile("R5")));

        assertEquals("R2 R3 R4 ", m1.toString());
    }

    @Test
    public void testToString(){
        meld m1 = new meld();
        m1.addTile(new tile("R2"));
        m1.addTile(new tile("R3"));
        m1.addTile(new tile("R4"));

        assertEquals("R2 R3 R4 ", m1.toString());

        meld m2 = new meld();
        m2.addTile(new tile("B2"));
        m2.addTile(new tile("R2"));
        m2.addTile(new tile("O2"));

        assertEquals("B2 O2 R2 ", m2.toString());
    }

    @Test
    public void testIsValid(){
        meld m1 = new meld();
        m1.addTile(new tile("R2"));
        m1.addTile(new tile("R3"));
        m1.addTile(new tile("R4"));

        assertTrue(m1.isValid());


        m1.addTile(new tile("B4"));

        assertFalse(m1.isValid());
    }

    @Test
    public void testClone(){
        meld m1 = new meld();
        m1.addTile(new tile("R1"));
        m1.addTile(new tile("R2"));
        m1.addTile(new tile("R3"));
        m1.addTile(new tile("R4"));

        meld m2 = m1.clone();

        assertEquals("R1 R2 R3 R4 ", m2.toString());
    }
}