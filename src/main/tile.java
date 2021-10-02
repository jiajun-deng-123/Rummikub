package main;

public class tile {
    public char color;
    public int point;
    public boolean moved = false;
    public boolean played = false;

    public tile(char color, int point){
        this.color = color;
        this.point = point;
    }

    public tile(String t){
        this.color = t.charAt(0);
        String string = "";
        for (int i = 1; i < t.length(); i++){
            string += t.charAt(i);
        }
        try {
            this.point = Integer.parseInt (string);
        } catch (NumberFormatException npe) {
            npe.printStackTrace();
        }
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

