package main;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class gameServerTest {
    @Test
    public void testPrintTurn(){
        gameServer gs = new gameServer();

        assertEquals("Player 1's turn", gs.printTurn());

        gs.handindex = 1;

        assertEquals("Player 2's turn", gs.printTurn());
    }

    @Test
    public void testPrintTable(){
        gameServer gs = new gameServer();

        assertEquals("Melds on the table:\n", gs.printTable());

        meld m1 = new meld();
        m1.addTile(new tile("R1"));
        m1.addTile(new tile("R2"));
        m1.addTile(new tile("R3"));

        gs.shareTable.add(m1);

        assertEquals("Melds on the table:\nR1 R2 R3 ", gs.printTable());

        meld m2 = new meld();
        m2.addTile(new tile("B2"));
        m2.addTile(new tile("R2"));
        m2.addTile(new tile("G2"));

        gs.shareTable.add(m2);

        assertEquals("Melds on the table:\nR1 R2 R3 and B2 G2 R2 ", gs.printTable());

        gs.shareTable.add(m1);

        assertEquals("Melds on the table:\nR1 R2 R3 and B2 G2 R2 and R1 R2 R3 ", gs.printTable());
    }

    @Test
    public void testGoNext(){
        gameServer gs = new gameServer();
        gs.playerCount = 3;
        gs.handindex = 0;

        assertEquals(0, gs.handindex);

        gs.goNext();

        assertEquals(1, gs.handindex);

        gs.goNext();

        assertEquals(2, gs.handindex);

        gs.goNext();

        assertEquals(0, gs.handindex);
    }

    @Test
    public void testDrawHandCard(){
        gameServer gs = new gameServer();
        gs.playerCount = 1;
        tile t = gs.p.get(14);

        gs.firstDraw();

        assertEquals(t.toString(), gs.p.get(0).toString());
    }

    @Test
    public void testRemoveFromHand(){
        gameServer gs = new gameServer();
        LinkedList<tile> ts = new LinkedList<tile>();
        ts.add(new tile("R5"));
        ts.add(new tile("R2"));
        ts.add(new tile("R5"));
        ts.add(new tile("R1"));
        ts.add(new tile("R6"));
        gs.handindex = 0;
        gs.handcard.add(ts);

        assertEquals("Your hand card:\n" +
                "R5 R2 R5 R1 R6 ", gs.printHandCard());

        gs.removeFromHand(new tile("R2"));

        assertEquals("Your hand card:\n" +
                "R5 R5 R1 R6 ", gs.printHandCard());

        gs.removeFromHand(new tile("R5"));

        assertEquals("Your hand card:\n" +
                "R5 R1 R6 ", gs.printHandCard());
    }

    @Test
    public void testAddMeld(){
        gameServer gs = new gameServer();
        LinkedList<tile> ts = new LinkedList<tile>();
        ts.add(new tile("R5"));
        ts.add(new tile("R2"));
        ts.add(new tile("R5"));
        ts.add(new tile("R1"));
        ts.add(new tile("R6"));
        ts.add(new tile("R3"));
        gs.handindex = 0;
        gs.handcard.add(ts);

        String s = "R1 R2 R3";

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "R5 R2 R5 R1 R6 R3 ", gs.printHandCard());

        gs.addNewMeld(s);

        assertEquals("Melds on the table:\n" +
                "*R1 *R2 *R3 ", gs.printTable());
        assertEquals("Your hand card:\n" +
                "R5 R5 R6 ", gs.printHandCard());

        s = "R4 R5 R6";
        gs.addNewMeld(s);

        assertEquals("Melds on the table:\n" +
                "*R1 *R2 *R3 ", gs.printTable());
        assertEquals("Your hand card:\n" +
                "R5 R5 R6 ", gs.printHandCard());
    }

    @Test
    public void testRefreshTable(){
        gameServer gs = new gameServer();
        LinkedList<tile> ts = new LinkedList<tile>();
        ts.add(new tile("R5"));
        ts.add(new tile("R2"));
        ts.add(new tile("R5"));
        ts.add(new tile("R1"));
        ts.add(new tile("R6"));
        ts.add(new tile("R3"));
        gs.handindex = 0;
        gs.handcard.add(ts);

        String s = "R1 R2 R3";

        assertEquals("Melds on the table:\n", gs.printTable());

        gs.addNewMeld(s);

        assertEquals("Melds on the table:\n" +
                "*R1 *R2 *R3 ", gs.printTable());

        gs.refreshTable();
        assertEquals("Melds on the table:\n" +
                "R1 R2 R3 ", gs.printTable());
    }

    @Test
    public void testMoveMeld(){
        gameServer gs = new gameServer();
        meld m1 = new meld();
        meld m2 = new meld();

        m1.addTile(new tile("R1"));
        m1.addTile(new tile("R2"));
        m1.addTile(new tile("R3"));
        m1.addTile(new tile("R4"));

        m2.addTile(new tile("B4"));
        m2.addTile(new tile("O4"));
        m2.addTile(new tile("G4"));

        gs.shareTable.add(m1);
        gs.shareTable.add(m2);

        assertEquals("Melds on the table:\n" +
                "R1 R2 R3 R4 and B4 G4 O4 ", gs.printTable());

        gs.moveToMeld("R4", 0, 1);

        assertEquals("Melds on the table:\n" +
                "R1 R2 R3 and B4 G4 O4 !R4 ", gs.printTable());
    }
}