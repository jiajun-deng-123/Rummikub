package main;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class meld {
    public LinkedList<tile> meld;
    public int n  = 0;
    public meld(){
        meld = new LinkedList<tile>();
    }

    public meld addTile(tile t){
        meld.add(t);
        Collections.sort(meld, new Comparator<tile>() {
            @Override
            public int compare(tile t1, tile t2) {
                if (t1.isJoker){
                    return 1;
                }
                if (t2.isJoker){
                    return -1;
                }
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
            }else if(meld.get(i).played){
                string += "*";
            }
            string += meld.get(i).toString() + " ";
        }
        return string;
    }

    public boolean isValid(){
        if (meld.size() < 3){
            return false;
        }
        int count = countJR();
        if ((meld.size() == 3) && (count == 2)){
            for (int i = 1; i < 3; i++){
                meld.get(i).point = meld.get(0).point + i;
            }
            return true;
        }
        int max = meld.size() - 1 - count;
        if (meld.get(0).color == meld.get(1).color){
            for (int i = 0; i < max; i++){
                if (meld.get(i).color != meld.get(i + 1).color){
                    return false;
                }else if (meld.get(i).point != (meld.get(i + 1).point - 1)){
                    if (meld.get(i).point == meld.get(i + 1).point){
                        return false;
                    }
                    if (meld.get(i).point >= (meld.get(i + 1).point - 1 - count)){
                        int c = 1;
                        for (int j = meld.size() - count; j < meld.size() - count + (meld.get(i + 1).point - meld.get(i).point - 1); j++){
                            meld.get(j).point = meld.get(i).point + c;
                            c++;
                        }
                        count -= meld.get(i + 1).point - meld.get(i).point - 1;
                    }else{
                        return false;
                    }
                }
            }
            for (int i = meld.size() - count; i < meld.size(); i++){
                meld.get(i).point = meld.get(0).point + i;
            }
            return true;
        }else if (meld.get(0).point == meld.get(1).point){
            if (meld.size() > 4){
                return false;
            }
            for (int i = 0; i < max; i++){
                if ((meld.get(i).color == meld.get(i + 1).color) || (meld.get(i).point != meld.get(i + 1).point)){
                    return false;
                }
            }
            for (int i = meld.size() - countJR(); i < meld.size(); i++){
                meld.get(i).point = meld.get(0).point;
            }
            return true;
        }
        return false;
    }

    public int countJR(){
        int sum = 0;
        for (int i = 0; i < meld.size(); i++){
            if (meld.get(i).isJoker){
                sum ++;
            }else{
                continue;
            }
        }
        return sum;
    }

    public meld clone(){
        meld m = new meld();
        for (int i = 0; i < meld.size(); i++){
            m.addTile(meld.get(i));
        }
        return m;
    }

    public int getSize(){
        return meld.size();
    }

    public tile getTile(int i){
        return meld.get(i);
    }
}
