package game.tiles.tile;
import game.tiles.Tile;
import game.utils.Position;
import game.callbacks.Visitor;
import game.tiles.units.players.Player;
import game.tiles.units.enemies.Enemy;

public class Empty extends Tile {
    public Empty(Position p){
        super('.');
        super.init(p);
    }
    //constructor to player lost
    public Empty(Position p,char t){
        super(t);
        super.init(p);
    }
    public void visit(Player p) {
        swapPosition(this,p);
    }
    public void visit(Enemy e) {
        swapPosition(this,e);
    }
    public void visit(Wall w) {}
    public void visit(Empty e) {}

    public void accept(Visitor v){
        v.visit(this);
    }

}
