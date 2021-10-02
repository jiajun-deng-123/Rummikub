package main;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class poolTest {
    @Test
    public void testPool(){
        pool p1 = new pool();
        pool p2 = p1;

        for (int i = 0; i < 14; i++) {
            p2.draw();
        }

        p1.handCard();

        assertEquals(p2, p1);
    }
}