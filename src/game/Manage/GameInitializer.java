package game.Manage;
import game.callbacks.EnemyDeathCallBack;
import game.callbacks.MoveCallBack;
import game.callbacks.MessageCallback;
import game.tiles.TileFactory;
import game.tiles.tile.Wall;
import game.boards.Board;
import game.tiles.tile.Empty;
import game.tiles.Tile;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.players.Mage;
import game.tiles.units.players.Player;
import game.tiles.units.players.Rogue;
import game.tiles.units.players.Warrior;
import game.utils.Health;
import game.utils.Position;
import game.tiles.units.enemies.Trap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameInitializer {
    public static Game_Manager init(char[][] arr,Player player) {
        TileFactory tileFactory = new TileFactory();
        Game_Manager gameManager = new Game_Manager();
        MessageCallback MessageBack = (msg -> gameManager.send(msg));
        MoveCallBack move = (unit, position) -> gameManager.Move(unit, position);
        int arr_rows = arr.length;
        int arr_columns = arr[0].length;
        int x;
        Tile[][] tiles = new Tile[arr_rows][arr_columns];
        for (int i = 0; i < arr_rows; i++) {
            for (int j = 0; j < arr_columns; j++) {
                Position p = new Position(i, j);
                char tileValue = arr[i][j];
                switch (tileValue) {
                    case '#':
                        tiles[i][j] = new Wall(p);
                        break;
                    case '.':
                        tiles[i][j] = new Empty(p);
                        break;
                    case '@':
                        player.init(p, MessageBack, () -> gameManager.removePlayer(player),move);
                        tiles[i][j] = player;
                        gameManager.SetPlayer(player);
                        tiles[i][j].init(p);
                        break;
                    default:
                        Enemy e = tileFactory.produceEnemy(tileValue);
                        EnemyDeathCallBack enemyDeath = ()->gameManager.removeEnemy(e);
                        e.init(p, MessageBack, enemyDeath,move);
                        tiles[i][j] = e;
                        gameManager.addEnemy(e);
                        break;
                }
            }
        }
        Board b = new Board(tiles);
        gameManager.setBoard(b);
        // Print the array

        return gameManager;
    }

    public static Game_Manager open_file(String filePath,Player player) {
        try {
            // Open the file
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Determine the dimensions of the array
            int rows = 0;
            int columns = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                rows++;
                columns = line.length();
            }

            // Reset the scanner to read from the beginning of the file
            scanner = new Scanner(file);

            // Create the 2D char array
            char[][] array = new char[rows][columns];

            // Populate the array with file contents
            int rowIndex = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int colIndex = 0; colIndex < columns; colIndex++) {
                    array[rowIndex][colIndex] = line.charAt(colIndex);
                }
                rowIndex++;
            }

            Game_Manager gameManager = GameInitializer.init(array,player);
            // Close the scanner
            scanner.close();
            return gameManager;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Game_Manager();
    }

}
