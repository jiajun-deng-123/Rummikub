package main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class tileTest{
    @Test
    public void testTile(){
        tile t = new tile("O1");
        assertEquals('O', t.color);

        assertEquals(1, t.point);

        t = new tile("R13");
        assertEquals('R', t.color);

        assertEquals(13, t.point);

        t = new tile('B', 12);
        assertEquals('B', t.color);

        assertEquals(12, t.point);

        assertFalse(t.isJoker);

        t = new tile("JR");
        assertTrue(t.isJoker);
    }

    @Test
    public void testSameColor(){
        tile t1 = new tile("R6");
        tile t2 = new tile("R10");
        tile t3 = new tile("B6");

        assertTrue(t1.sameColor(t2));

        assertFalse(t1.sameColor(t3));
    }

    @Test
    public void testSamePoint(){
        tile t1 = new tile("R6");
        tile t2 = new tile("R10");
        tile t3 = new tile("B6");

        assertTrue(t1.samePoint(t3));

        assertFalse(t1.samePoint(t2));
    }

    @Test
    public void testToString(){
        tile t1 = new tile('G', 11);
        tile t2 = new tile("R5");

        assertEquals("G11", t1.toString());

        assertEquals("R5", t2.toString());
    }
}