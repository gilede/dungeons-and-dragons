package game.Manage;

import game.boards.Board;
import game.callbacks.MessageCallback;
import game.tiles.Tile;
import game.tiles.tile.Empty;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.players.Player;
import game.tiles.units.players.Warrior;
import game.utils.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.InputQuery;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class Game_ManagerTest {
    private Game_Manager gameManager;
    private Player player;
    private Enemy enemy;
    private Board board;
    private MessageCallback messageCallback;
    private InputQuery inputQuery;

    @BeforeEach
    void setUp() {
        gameManager = new Game_Manager();
        player = new Warrior("Jon Snow", 300, 30, 4, 3);
        enemy = new Monster('s', "Lannister Solider", 80, 8, 3, 25, 3);
        Tile[][] tiles = {
                {new Empty(Position.at(0, 0)),player, new Empty(Position.at(0, 2))},
                {new Empty(Position.at(1, 0)),enemy, new Empty(Position.at(1, 2))}
        };
        player.init(new Position(0,1));
        enemy.init(new Position(1,1));
        gameManager.SetPlayer(player);
        board = new Board(tiles);
        messageCallback = (msg -> System.out.println(msg));
        enemy = new Monster('s', "Lannister Solider", 80, 8, 3, 25, 3);
        gameManager.setBoard(board);
        gameManager.setMessageCallBack(messageCallback);
        gameManager.setInputQuery(inputQuery);
    }

    @Test
    void testStop() {
        gameManager.stop();
        assertTrue(gameManager.isstoped());
    }

    @Test
    void testAddEnemy() {
        gameManager.addEnemy(enemy);
        List<Enemy> enemies = gameManager.getEnemies();
        assertEquals(1, enemies.size());
        assertTrue(enemies.contains(enemy));
    }

    @Test
    void testRemovePlayer() {

        gameManager.removePlayer(player);
        assertEquals(gameManager.isstoped(),true);

    }

    @Test
    void testRemoveEnemy() {
        gameManager.removeEnemy(enemy);
        List<Enemy> enemies = gameManager.getEnemies();
        assertFalse(enemies.contains(enemy));
    }


    @Test
    void testMove_Success() throws Exception {
        Position position = Position.at(1, 1);
        gameManager.Move(player, position);
        assertEquals(player.getPosition().getX(),0);
        assertEquals(player.getPosition().getY(),1);
    }

    @Test
    void testShouldStop_EnemiesAlive() {
        gameManager.addEnemy(enemy);
        assertFalse(gameManager.isstoped());
        gameManager.shouldstop();
        assertFalse(gameManager.isstoped());
    }

    @Test
    void testShouldStop_NoEnemies() {
        assertTrue(gameManager.getEnemies().isEmpty());
        gameManager.shouldstop();
        assertTrue(gameManager.isstoped());
    }


}
