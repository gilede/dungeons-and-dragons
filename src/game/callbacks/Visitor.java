package game.callbacks;
import game.tiles.tile.Wall;
import game.tiles.tile.Empty;
import game.tiles.units.players.Player;
import game.tiles.units.enemies.Enemy;

public interface Visitor {
    void visit(Enemy e);
    void visit(Empty e);
    void visit(Wall w);
    void visit(Player p);

}
