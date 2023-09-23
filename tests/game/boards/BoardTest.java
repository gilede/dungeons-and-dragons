package game.boards;

import game.tiles.units.enemies.Monster;
import game.tiles.units.players.Warrior;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



import game.tiles.Tile;
import game.tiles.tile.Empty;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.players.Player;
import game.utils.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
class BoardTest {
    private Board board;
    private Player player;
    private Enemy enemy;
    @BeforeEach
    void setUp() {
        Tile[][] tiles = {
                {new Empty(Position.at(0, 0)), new Empty(Position.at(0, 2))},
                {new Empty(Position.at(1, 0)), new Empty(Position.at(1, 1))}
        };
        board = new Board(tiles);
        player = new Warrior("Jon Snow", 300, 30, 4, 3);
        enemy = new Monster('s', "Lannister Solider", 80, 8, 3, 25, 3);
    }

    @Test
    void testGetExistingTile() throws Exception {
        Tile tile = board.get(1, 1);
        assertNotNull(tile);
        assertEquals(Position.at(1, 1).getX(), tile.getPosition().getX());
        assertEquals(Position.at(1, 1).getY(), tile.getPosition().getY());
    }

    @Test
    void testGetNonExistingTile() {
        assertThrows(Exception.class, () -> board.get(2, 2));
    }

    @Test
    void testRemoveEnemy() throws Exception {
        enemy.init(new Position(0,1));
        board.remove(enemy);
        assertEquals(board.get(0, 1).getTile(),'.');
    }

    @Test
    void testRemovePlayer() throws Exception {

        player.init(new Position(1,2));
        board.remove(player);
        assertEquals(board.get(1, 2).getTile(),'X');
    }

    @Test
    void testToString() {
        String expected = "..\n" +
                "..";
        assertEquals(expected, board.toString());
    }
}