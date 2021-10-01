package main.tile;

public class tile {
    public char color;
    public int point;
    public tile(char color, int point){
        this.color = color;
        this.point = point;
    }

    public int getPoint(){
        return this.point;
    }

    public boolean sameColor(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        tile other = (tile) obj;
        if (color != other.color) {
            return false;
        }
        return true;
    }

    public boolean samePoint(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        tile other = (tile) obj;
        if (point != other.point) {
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return color + "" + point;
    }
}
