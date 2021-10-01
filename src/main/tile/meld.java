package main.tile;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class meld {
    public LinkedList<tile> meld;
    public meld(){
        meld = new LinkedList<tile>();
    }

    public meld addTile(tile t){
        meld.add(t);
        Collections.sort(meld, new Comparator<tile>() {
            @Override
            public int compare(tile t1, tile t2) {
                if (t1.point > t2.point){
                    return 1;
                }else if (t1.point < t2.point){
                    return -1;
                }else{
                    if (t1.color > t2.color){
                        return 1;
                    }else if (t1.color < t2.color){
                        return -1;
                    }
                }
                return 0;
            }
        });
        return this;
    }

    public tile removeHead(){
        return meld.removeFirst();
    }

    public tile removeTail(){
        return meld.removeLast();
    }

    public boolean removeTile(tile t){
        if (meld.contains(t)){
            meld.remove(t);
            return true;
        }
        return false;
    }
}
