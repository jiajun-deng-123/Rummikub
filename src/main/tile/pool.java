package main.tile;

import java.util.Collections;
import java.util.LinkedList;

public class pool {
    public LinkedList<tile> pool;
    public pool(){
        pool = new LinkedList<tile>();
        char color;
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 4; j++){
                for (int k = 1; k <= 13; k++){
                    if (j == 0){
                        color = 'B';
                    }else if (j == 1){
                        color = 'R';
                    }else if (j == 2){
                        color = 'G';
                    }else {
                        color = 'O';
                    }
                    pool.add(new tile(color, k));
                    Collections.shuffle(pool);
                }
            }
        }
    }

    public LinkedList<tile> handCard(){
        LinkedList<tile> res = new LinkedList();
        for (int i = 0; i < 14; i++) {
            res.add(pool.remove(0));
        }
        return res;
    }

    public tile draw(){
        return pool.remove(0);
    }
}

