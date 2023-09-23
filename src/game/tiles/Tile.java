package game.tiles;

import game.utils.Position;
import game.callbacks.Visitor;
import game.callbacks.Visited;
public abstract class Tile implements Visitor, Visited,Comparable<Tile> {
    protected Position p;
    protected char tile;
    public Tile(char tile){
        this.tile=tile;
    }


    public Position getPosition(){
        return p;
    }
    public void setPosition(Position p){
        this.p=p;
    }
    public void swapPosition(Tile tile1,Tile tile2){
        Position new_p = tile1.getPosition();
        tile1.setPosition(tile2.getPosition());
        tile2.setPosition(new_p);
    }
    protected void setChar(char t){
        this.tile=t;
    }
    public char getTile(){return tile;}
    public void init(Position p){
        this.p=p;
    }
    public String toString(){
        return Character.toString(tile);
    }
    @Override
    public int compareTo(Tile tile) {
        return getPosition().compareTo(tile.getPosition());
    }
}
