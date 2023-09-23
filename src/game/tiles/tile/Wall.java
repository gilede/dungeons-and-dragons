package game.tiles.tile;
import game.tiles.Tile;
import game.utils.Position;
import game.callbacks.Visitor;
import game.tiles.units.players.Player;
import game.tiles.units.enemies.Enemy;

public class Wall extends Tile {
    public Wall(Position p){
        super('#');
        super.init(p);
    }

    public void visit(Enemy e) {}
    public void visit(Player p) {}
    public void visit(Wall w) {}
    public void visit(Empty e) {}

    public void accept(Visitor v){
        v.visit(this);
    }
}
