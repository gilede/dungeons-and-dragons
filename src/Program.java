import Fronted.CLI;
import game.Manage.GameInitializer;
import game.Manage.Game_Manager;
import game.tiles.units.players.Player;

import java.io.File;

public class Program {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Invalid directory");
        }
        else {
            String filePath = "C:\\Users\\gilpl\\Desktop\\קורסים שסיימתי\\levels_dir";
            File directory = new File(filePath);
            File[] files = directory.listFiles();
            Player player = CLI.ChoosePlayer();
            for (File file : files) {
                Game_Manager gameManager = GameInitializer.open_file(file.getPath(), player);
                CLI cli = new CLI(gameManager);
                cli.startgame();
                if (!cli.endgame())
                    break;
            }
        }
    }
}

