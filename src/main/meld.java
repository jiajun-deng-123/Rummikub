package main;

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

    public tile moveHead(){
        return meld.removeFirst();
    }

    public tile moveTail(){
        return meld.removeLast();
    }

    public boolean moveTile(tile t){
        for (int i = 0; i < meld.size(); i++){
            if (meld.get(i).sameColor(t) && meld.get(i).samePoint(t)){
                meld.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        String string = "";
        for (int i = 0; i < meld.size(); i++){
            if (meld.get(i).moved){
                string += "!";
                meld.get(i).moved = false;
            }else if(meld.get(i).played){
                string += "*";
                meld.get(i).played = false;
            }
            string += meld.get(i).toString() + " ";
        }
        return string;
    }

    public boolean isValid(){
        if (meld.size() < 3){
            return false;
        }
        if (meld.get(0).color == meld.get(1).color){
            for (int i = 0; i < meld.size() - 1; i++){
                if ((meld.get(i).point != (meld.get(i + 1).point - 1)) || (meld.get(i).color != meld.get(i + 1).color)){
                    return false;
                }
            }
            return true;
        }else if (meld.get(0).point == meld.get(1).point){
            for (int i = 0; i < meld.size() - 1; i++){
                if ((meld.get(i).color == meld.get(i + 1).color) || (meld.get(i).point != meld.get(i + 1).point)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
