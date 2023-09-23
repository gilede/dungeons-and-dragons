package game.callbacks;

import game.tiles.units.Unit;
import game.tiles.units.enemies.Enemy;
import game.utils.Position;

public interface MoveCallBack {
    public void Move(Unit u, Position p);
}
