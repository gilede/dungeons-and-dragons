package Fronted;

import game.Manage.Game_Manager;
import game.handler.TargetEnemy;
import game.tiles.TileFactory;
import game.tiles.units.players.Player;

import java.util.Scanner;

public class CLI {
    private Game_Manager gameManager;
    private static boolean GameEnd=true;
    public CLI(Game_Manager gameManager) {
        this.gameManager = gameManager;
        TargetEnemy.gameManager=gameManager;
        this.gameManager.setMessageCallBack((msg -> System.out.println(msg)));
        this.gameManager.setInputQuery(() -> readLine());

    }
    public void startgame(){
        while (!gameManager.isstoped()){
            gameManager.tick();
            gameManager.shouldstop();
        }
        if(!gameManager.getPlayer().IsAlive())
            GameEnd=false;
    }
    public boolean endgame(){
        return GameEnd;
    }
    public String readLine(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static Player ChoosePlayer(){
        while(true){
            System.out.println("choose player:");
            System.out.println(TileFactory.GetAllPlayer());
            Scanner sc = new Scanner(System.in);
            String c = sc.nextLine();
            try {
                int index = Integer.parseInt(c);
                if(index>0 && index<8)
                    return TileFactory.getPlayerByIndex(index);
            }
            catch (Exception e){
                System.out.println("not a number");
            }
        }
    }
}
