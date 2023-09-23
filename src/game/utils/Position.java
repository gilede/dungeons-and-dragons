package game.utils;

public class Position implements Comparable<Position>{
    private int x;
    private int y;
    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }
    public double Range(Position other){
        double new_x =Math.pow((this.x- other.x),2); // (x1-x2)^2
        double new_y =Math.pow((this.y- other.y),2); // (y1-y2)^2
        return Math.sqrt((new_x+new_y));
    }
    public boolean equals(Position other){
        if((this.x==other.x) && (this.y==other.y))
            return true;
        return false;
    }
    public static Position at(int x, int y) {
        return new Position(x, y);
    }
    @Override
    public int compareTo(Position other) {
        int result =Integer.compare(this.x,other.x);
        if(result ==0){
            return Integer.compare(this.y,other.y);
        }
        return result;
    }
    public int getY(){
        return this.y;
    }
    public int getX(){
        return this.x;
    }

}
