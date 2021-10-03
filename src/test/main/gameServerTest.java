package main;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class gameServerTest {
    @Test
    public void testPrintTurn(){
        gameServer gs = new gameServer();

        assertEquals("\nPlayer 1's turn\n", gs.printTurn());

        gs.handindex = 1;

        assertEquals("\nPlayer 2's turn\n", gs.printTurn());
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

        gs.moveToMeld("R3", 0, 1);

        assertEquals("Melds on the table:\n" +
                "R1 R2 R3 and B4 G4 O4 !R4 ", gs.printTable());
    }

    @Test
    public void testAddOneMeld(){
        gameServer gs = new gameServer();
        LinkedList<tile> ts = new LinkedList<tile>();
        ts.add(new tile("R5"));
        ts.add(new tile("R2"));
        ts.add(new tile("R5"));
        ts.add(new tile("R1"));
        ts.add(new tile("R6"));
        ts.add(new tile("R3"));

        meld m = new meld();

        m.addTile(new tile("R1"));
        m.addTile(new tile("R2"));
        m.addTile(new tile("R3"));
        m.addTile(new tile("R4"));

        gs.handindex = 0;
        gs.handcard.add(ts);
        gs.shareTable.add(m);

        assertEquals("Melds on the table:\n" +
                "R1 R2 R3 R4 ", gs.printTable());
        assertEquals("Your hand card:\n" +
                "R5 R2 R5 R1 R6 R3 ", gs.printHandCard());

        gs.addOneMeld("R5",0);

        assertEquals("Melds on the table:\n" +
                "R1 R2 R3 R4 *R5 ", gs.printTable());
        assertEquals("Your hand card:\n" +
                "R2 R5 R1 R6 R3 ", gs.printHandCard());

        gs.addOneMeld("R6",0);
        gs.addOneMeld("R7",0);

        assertEquals("Melds on the table:\n" +
                "R1 R2 R3 R4 *R5 *R6 ", gs.printTable());
        assertEquals("Your hand card:\n" +
                "R2 R5 R1 R3 ", gs.printHandCard());

    }

    @Test
    public void testCountScore(){
        gameServer gs = new gameServer();
        gs.playerCount = 3;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("O5"));
        hc1.add(new tile("R2"));
        hc1.add(new tile("G3"));
        gs.handcard.add(hc1);
        LinkedList<tile> hc2 = new LinkedList<tile>();
        gs.handcard.add(hc2);
        LinkedList<tile> hc3 = new LinkedList<tile>();
        hc3.add(new tile("B13"));
        hc3.add(new tile("R2"));
        hc3.add(new tile("O9"));
        hc3.add(new tile("R4"));
        hc3.add(new tile("G8"));
        hc3.add(new tile("G12"));
        hc3.add(new tile("B1"));
        hc3.add(new tile("R4"));
        hc3.add(new tile("O3"));
        gs.handcard.add(hc3);

        assertEquals("Game over. Player 2 is the winner of the game. \n" +
                "Final Score:\n" +
                "Player 1 (-10)\n" +
                "Player 2 (66)\n" +
                "Player 3 (-56)\n", gs.countScore());
    }

    @Test
    public void testUIupdate(){
        gameServer gs = new gameServer();
        gs.playerCount = 4;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("B12"));
        hc1.add(new tile("R12"));
        hc1.add(new tile("O12"));
        gs.handcard.add(hc1);
        LinkedList<tile> hc2 = new LinkedList<tile>();
        hc2.add(new tile("R11"));
        hc2.add(new tile("R12"));
        hc2.add(new tile("R13"));
        hc2.add(new tile("G11"));
        gs.handcard.add(hc2);
        LinkedList<tile> hc3 = new LinkedList<tile>();
        hc3.add(new tile("R13"));
        hc3.add(new tile("G13"));
        hc3.add(new tile("O13"));
        hc3.add(new tile("B2"));
        hc3.add(new tile("G2"));
        hc3.add(new tile("R2"));
        hc3.add(new tile("G3"));
        gs.handcard.add(hc3);

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "B12 R12 O12 ", gs.printHandCard());

        hc1.add(new tile("B1"));

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "B12 R12 O12 B1 ", gs.printHandCard());

        gs.goNext();
        gs.refreshTable();


        assertEquals("Your hand card:\n" +
                "R11 R12 R13 G11 ", gs.printHandCard());

        gs.addNewMeld("R11 R12 R13");

        assertEquals("Melds on the table:\n" +
                "*R11 *R12 *R13 ", gs.printTable());
        assertEquals("Your hand card:\n" +
                "G11 ", gs.printHandCard());

        gs.goNext();
        gs.refreshTable();

        assertEquals("Melds on the table:\n" +
                "R11 R12 R13 ", gs.printTable());
        assertEquals("Your hand card:\n" +
                "R13 G13 O13 B2 G2 R2 G3 ", gs.printHandCard());

        gs.addNewMeld("R13 G13 O13");
        gs.addNewMeld("B2 G2 R2");

        assertEquals("Melds on the table:\n" +
                "R11 R12 R13 and *G13 *O13 *R13 and *B2 *G2 *R2 ", gs.printTable());
        assertEquals("Your hand card:\n" +
                "G3 ", gs.printHandCard());

        gs.goNext();
        gs.refreshTable();

        assertEquals("Melds on the table:\n" +
                "R11 R12 R13 and G13 O13 R13 and B2 G2 R2 ", gs.printTable());
        assertEquals("Your hand card:\n" +
                "B12 R12 O12 B1 ", gs.printHandCard());

        gs.addNewMeld("B12 R12 O12");

        assertEquals("Melds on the table:\n" +
                "R11 R12 R13 and G13 O13 R13 and B2 G2 R2 and *B12 *O12 *R12 ", gs.printTable());
        assertEquals("Your hand card:\n" +
                "B1 ", gs.printHandCard());
    }

    @Test
    public void testInitial1(){
        gameServer gs = new gameServer();
        gs.playerCount = 1;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("B11"));
        hc1.add(new tile("B12"));
        hc1.add(new tile("B13"));
        gs.handcard.add(hc1);

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "B11 B12 B13 ", gs.printHandCard());

        gs.addNewMeld("B11 B12 B13");

        assertEquals("Melds on the table:\n" +
                "*B11 *B12 *B13 ", gs.printTable());
        assertEquals("Your hand card:\n", gs.printHandCard());

    }

    @Test
    public void testInitial2(){
        gameServer gs = new gameServer();
        gs.playerCount = 1;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("G12"));
        hc1.add(new tile("B12"));
        hc1.add(new tile("R12"));
        gs.handcard.add(hc1);

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "G12 B12 R12 ", gs.printHandCard());

        gs.addNewMeld("G12 B12 R12");

        assertEquals("Melds on the table:\n" +
                "*B12 *G12 *R12 ", gs.printTable());
        assertEquals("Your hand card:\n", gs.printHandCard());

    }

    @Test
    public void testInitial3(){
        gameServer gs = new gameServer();
        gs.playerCount = 1;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("B9"));
        hc1.add(new tile("B10"));
        hc1.add(new tile("B11"));
        hc1.add(new tile("B12"));
        hc1.add(new tile("B13"));
        gs.handcard.add(hc1);

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "B9 B10 B11 B12 B13 ", gs.printHandCard());

        gs.addNewMeld("B9 B10 B11 B12 B13");

        assertEquals("Melds on the table:\n" +
                "*B9 *B10 *B11 *B12 *B13 ", gs.printTable());
        assertEquals("Your hand card:\n", gs.printHandCard());

    }

    @Test
    public void testInitial4(){
        gameServer gs = new gameServer();
        gs.playerCount = 1;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("G12"));
        hc1.add(new tile("B12"));
        hc1.add(new tile("R12"));
        hc1.add(new tile("O12"));
        gs.handcard.add(hc1);

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "G12 B12 R12 O12 ", gs.printHandCard());

        gs.addNewMeld("G12 B12 R12 O12");

        assertEquals("Melds on the table:\n" +
                "*B12 *G12 *O12 *R12 ", gs.printTable());
        assertEquals("Your hand card:\n", gs.printHandCard());

    }

    @Test
    public void testInitial5(){
        gameServer gs = new gameServer();
        gs.playerCount = 1;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("R2"));
        hc1.add(new tile("R3"));
        hc1.add(new tile("R4"));
        hc1.add(new tile("B7"));
        hc1.add(new tile("B8"));
        hc1.add(new tile("B9"));
        gs.handcard.add(hc1);

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "R2 R3 R4 B7 B8 B9 ", gs.printHandCard());

        gs.addNewMeld("R2 R3 R4");
        gs.addNewMeld("B7 B8 B9");

        assertEquals("Melds on the table:\n" +
                "*R2 *R3 *R4 and *B7 *B8 *B9 ", gs.printTable());
        assertEquals("Your hand card:\n", gs.printHandCard());

    }

    @Test
    public void testInitial6(){
        gameServer gs = new gameServer();
        gs.playerCount = 1;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("G2"));
        hc1.add(new tile("B2"));
        hc1.add(new tile("R2"));
        hc1.add(new tile("O4"));
        hc1.add(new tile("G4"));
        hc1.add(new tile("B4"));
        hc1.add(new tile("R4"));
        hc1.add(new tile("O5"));
        hc1.add(new tile("G5"));
        hc1.add(new tile("B5"));
        gs.handcard.add(hc1);

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "G2 B2 R2 O4 G4 B4 R4 O5 G5 B5 ", gs.printHandCard());

        gs.addNewMeld("G2 B2 R2");
        gs.addNewMeld("O4 G4 B4 R4");
        gs.addNewMeld("O5 G5 B5");

        assertEquals("Melds on the table:\n" +
                "*B2 *G2 *R2 and *B4 *G4 *O4 *R4 and *B5 *G5 *O5 ", gs.printTable());
        assertEquals("Your hand card:\n", gs.printHandCard());

    }

    @Test
    public void testInitial7(){
        gameServer gs = new gameServer();
        gs.playerCount = 1;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("G8"));
        hc1.add(new tile("R8"));
        hc1.add(new tile("O8"));
        hc1.add(new tile("B2"));
        hc1.add(new tile("B3"));
        hc1.add(new tile("B4"));
        gs.handcard.add(hc1);

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "G8 R8 O8 B2 B3 B4 ", gs.printHandCard());

        gs.addNewMeld("G8 R8 O8");
        gs.addNewMeld("B2 B3 B4");

        assertEquals("Melds on the table:\n" +
                "*G8 *O8 *R8 and *B2 *B3 *B4 ", gs.printTable());
        assertEquals("Your hand card:\n", gs.printHandCard());

    }

    @Test
    public void testInitial8(){
        gameServer gs = new gameServer();
        gs.playerCount = 1;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("G2"));
        hc1.add(new tile("R2"));
        hc1.add(new tile("O2"));
        hc1.add(new tile("B2"));
        hc1.add(new tile("B3"));
        hc1.add(new tile("B4"));
        hc1.add(new tile("G3"));
        hc1.add(new tile("R3"));
        hc1.add(new tile("O3"));
        hc1.add(new tile("B5"));
        hc1.add(new tile("B6"));
        hc1.add(new tile("B7"));
        gs.handcard.add(hc1);

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "G2 R2 O2 B2 B3 B4 G3 R3 O3 B5 B6 B7 ", gs.printHandCard());

        gs.addNewMeld("G2 R2 O2");
        gs.addNewMeld("B2 B3 B4");
        gs.addNewMeld("G3 R3 O3");
        gs.addNewMeld("B5 B6 B7");

        assertEquals("Melds on the table:\n" +
                "*G2 *O2 *R2 and *B2 *B3 *B4 and *G3 *O3 *R3 and *B5 *B6 *B7 ", gs.printTable());
        assertEquals("Your hand card:\n", gs.printHandCard());

    }

    @Test
    public void testInitial9(){
        gameServer gs = new gameServer();
        gs.playerCount = 4;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("G2"));
        hc1.add(new tile("R2"));
        hc1.add(new tile("O2"));
        hc1.add(new tile("B2"));
        hc1.add(new tile("G3"));
        hc1.add(new tile("G4"));
        hc1.add(new tile("G5"));
        hc1.add(new tile("G6"));
        hc1.add(new tile("G7"));
        hc1.add(new tile("B4"));
        hc1.add(new tile("B5"));
        hc1.add(new tile("B6"));
        hc1.add(new tile("B7"));
        hc1.add(new tile("B8"));
        gs.handcard.add(hc1);
        LinkedList<tile> hc2 = new LinkedList<tile>();
        hc2.add(new tile("R11"));
        hc2.add(new tile("R12"));
        hc2.add(new tile("R13"));
        hc2.add(new tile("G11"));
        gs.handcard.add(hc2);
        LinkedList<tile> hc3 = new LinkedList<tile>();
        hc3.add(new tile("R13"));
        hc3.add(new tile("G13"));
        hc3.add(new tile("O13"));
        hc3.add(new tile("B2"));
        hc3.add(new tile("G2"));
        hc3.add(new tile("R2"));
        hc3.add(new tile("G3"));
        gs.handcard.add(hc3);

        assertEquals("Melds on the table:\n", gs.printTable());
        assertEquals("Your hand card:\n" +
                "G2 R2 O2 B2 G3 G4 G5 G6 G7 B4 B5 B6 B7 B8 ", gs.printHandCard());

        gs.addNewMeld("G2 R2 O2 B2");
        gs.addNewMeld("G3 G4 G5 G6 G7");
        gs.addNewMeld("B4 B5 B6 B7 B8");

        assertEquals("Melds on the table:\n" +
                "*B2 *G2 *O2 *R2 and *G3 *G4 *G5 *G6 *G7 and *B4 *B5 *B6 *B7 *B8 ", gs.printTable());
        assertEquals("Your hand card:\n", gs.printHandCard());
        assertEquals("Game over. Player 1 is the winner of the game. \n" +
                "Final Score:\n" +
                "Player 1 (95)\n" +
                "Player 2 (-47)\n" +
                "Player 3 (-48)\n", gs.countScore());

    }

    @Test
    public void testMeld1(){
        gameServer gs = new gameServer();
        gs.playerCount = 4;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("O11"));
        hc1.add(new tile("O12"));
        hc1.add(new tile("O13"));
        hc1.add(new tile("B2"));
        hc1.add(new tile("B3"));
        hc1.add(new tile("B4"));
        gs.handcard.add(hc1);
        LinkedList<tile> hc2 = new LinkedList<tile>();
        hc2.add(new tile("R11"));
        hc2.add(new tile("R12"));
        hc2.add(new tile("R13"));
        gs.handcard.add(hc2);
        LinkedList<tile> hc3 = new LinkedList<tile>();
        hc3.add(new tile("B11"));
        hc3.add(new tile("B12"));
        hc3.add(new tile("B13"));
        gs.handcard.add(hc3);

        gs.addNewMeld("O11 O12 O13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("R11 R12 R13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("B11 B12 B13");

        gs.goNext();
        gs.refreshTable();


        gs.addNewMeld("B2 B3 B4");

        assertEquals("Melds on the table:\n" +
                "O11 O12 O13 and R11 R12 R13 and B11 B12 B13 and *B2 *B3 *B4 ", gs.printTable());
    }

    @Test
    public void testMeld2(){
        gameServer gs = new gameServer();
        gs.playerCount = 4;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("O11"));
        hc1.add(new tile("O12"));
        hc1.add(new tile("O13"));
        hc1.add(new tile("B2"));
        hc1.add(new tile("B3"));
        hc1.add(new tile("B4"));
        hc1.add(new tile("G8"));
        hc1.add(new tile("G9"));
        hc1.add(new tile("G10"));
        gs.handcard.add(hc1);
        LinkedList<tile> hc2 = new LinkedList<tile>();
        hc2.add(new tile("R11"));
        hc2.add(new tile("R12"));
        hc2.add(new tile("R13"));
        gs.handcard.add(hc2);
        LinkedList<tile> hc3 = new LinkedList<tile>();
        hc3.add(new tile("B11"));
        hc3.add(new tile("B12"));
        hc3.add(new tile("B13"));
        gs.handcard.add(hc3);

        gs.addNewMeld("O11 O12 O13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("R11 R12 R13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("B11 B12 B13");

        gs.goNext();
        gs.refreshTable();


        gs.addNewMeld("B2 B3 B4");
        gs.addNewMeld("G8 G9 G10");

        assertEquals("Melds on the table:\n" +
                "O11 O12 O13 and R11 R12 R13 and B11 B12 B13 and *B2 *B3 *B4 and *G8 *G9 *G10 ", gs.printTable());
    }

    @Test
    public void testMeld3(){
        gameServer gs = new gameServer();
        gs.playerCount = 4;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("O11"));
        hc1.add(new tile("O12"));
        hc1.add(new tile("O13"));
        hc1.add(new tile("B2"));
        hc1.add(new tile("G2"));
        hc1.add(new tile("O2"));
        gs.handcard.add(hc1);
        LinkedList<tile> hc2 = new LinkedList<tile>();
        hc2.add(new tile("R11"));
        hc2.add(new tile("R12"));
        hc2.add(new tile("R13"));
        gs.handcard.add(hc2);
        LinkedList<tile> hc3 = new LinkedList<tile>();
        hc3.add(new tile("B11"));
        hc3.add(new tile("B12"));
        hc3.add(new tile("B13"));
        gs.handcard.add(hc3);

        gs.addNewMeld("O11 O12 O13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("R11 R12 R13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("B11 B12 B13");

        gs.goNext();
        gs.refreshTable();


        gs.addNewMeld("B2 G2 O2");

        assertEquals("Melds on the table:\n" +
                "O11 O12 O13 and R11 R12 R13 and B11 B12 B13 and *B2 *G2 *O2 ", gs.printTable());
    }

    @Test
    public void testMeld4(){
        gameServer gs = new gameServer();
        gs.playerCount = 4;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("O11"));
        hc1.add(new tile("O12"));
        hc1.add(new tile("O13"));
        hc1.add(new tile("B2"));
        hc1.add(new tile("G2"));
        hc1.add(new tile("O2"));
        hc1.add(new tile("B8"));
        hc1.add(new tile("G8"));
        hc1.add(new tile("O8"));
        hc1.add(new tile("R8"));
        gs.handcard.add(hc1);
        LinkedList<tile> hc2 = new LinkedList<tile>();
        hc2.add(new tile("R11"));
        hc2.add(new tile("R12"));
        hc2.add(new tile("R13"));
        gs.handcard.add(hc2);
        LinkedList<tile> hc3 = new LinkedList<tile>();
        hc3.add(new tile("B11"));
        hc3.add(new tile("B12"));
        hc3.add(new tile("B13"));
        gs.handcard.add(hc3);

        gs.addNewMeld("O11 O12 O13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("R11 R12 R13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("B11 B12 B13");

        gs.goNext();
        gs.refreshTable();


        gs.addNewMeld("B2 G2 O2");
        gs.addNewMeld("B8 G8 R8 O8");

        assertEquals("Melds on the table:\n" +
                "O11 O12 O13 and R11 R12 R13 and B11 B12 B13 and *B2 *G2 *O2 and *B8 *G8 *O8 *R8 ", gs.printTable());
    }

    @Test
    public void testMeld5(){
        gameServer gs = new gameServer();
        gs.playerCount = 4;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("O11"));
        hc1.add(new tile("O12"));
        hc1.add(new tile("O13"));
        hc1.add(new tile("B2"));
        hc1.add(new tile("G2"));
        hc1.add(new tile("O2"));
        hc1.add(new tile("B8"));
        hc1.add(new tile("B9"));
        hc1.add(new tile("B10"));
        gs.handcard.add(hc1);
        LinkedList<tile> hc2 = new LinkedList<tile>();
        hc2.add(new tile("R11"));
        hc2.add(new tile("R12"));
        hc2.add(new tile("R13"));
        gs.handcard.add(hc2);
        LinkedList<tile> hc3 = new LinkedList<tile>();
        hc3.add(new tile("B11"));
        hc3.add(new tile("B12"));
        hc3.add(new tile("B13"));
        gs.handcard.add(hc3);

        gs.addNewMeld("O11 O12 O13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("R11 R12 R13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("B11 B12 B13");

        gs.goNext();
        gs.refreshTable();


        gs.addNewMeld("B2 G2 O2");
        gs.addNewMeld("B8 B9 B10");

        assertEquals("Melds on the table:\n" +
                "O11 O12 O13 and R11 R12 R13 and B11 B12 B13 and *B2 *G2 *O2 and *B8 *B9 *B10 ", gs.printTable());
    }

    @Test
    public void testMeld6(){
        gameServer gs = new gameServer();
        gs.playerCount = 4;
        gs.handindex = 0;
        LinkedList<tile> hc1 = new LinkedList<tile>();
        hc1.add(new tile("O11"));
        hc1.add(new tile("O12"));
        hc1.add(new tile("O13"));
        hc1.add(new tile("B2"));
        hc1.add(new tile("G2"));
        hc1.add(new tile("O2"));
        hc1.add(new tile("B3"));
        hc1.add(new tile("G3"));
        hc1.add(new tile("O3"));
        hc1.add(new tile("B8"));
        hc1.add(new tile("B9"));
        hc1.add(new tile("B10"));
        hc1.add(new tile("B11"));
        hc1.add(new tile("B12"));
        gs.handcard.add(hc1);
        LinkedList<tile> hc2 = new LinkedList<tile>();
        hc2.add(new tile("R11"));
        hc2.add(new tile("R12"));
        hc2.add(new tile("R13"));
        gs.handcard.add(hc2);
        LinkedList<tile> hc3 = new LinkedList<tile>();
        hc3.add(new tile("B11"));
        hc3.add(new tile("B12"));
        hc3.add(new tile("B13"));
        gs.handcard.add(hc3);

        gs.addNewMeld("O11 O12 O13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("R11 R12 R13");

        gs.goNext();
        gs.refreshTable();

        gs.addNewMeld("B11 B12 B13");

        gs.goNext();
        gs.refreshTable();


        gs.addNewMeld("B2 G2 O2");
        gs.addNewMeld("B3 G3 O3");
        gs.addNewMeld("B8 B9 B10 B11 B12");

        assertEquals("Melds on the table:\n" +
                "O11 O12 O13 and R11 R12 R13 and B11 B12 B13 and *B2 *G2 *O2 and *B3 *G3 *O3 and *B8 *B9 *B10 *B11 *B12 ", gs.printTable());
    }
}