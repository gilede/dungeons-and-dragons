package game.Manage;
import game.boards.Board;
import game.callbacks.MessageCallback;
import game.tiles.Tile;
import game.tiles.tile.Empty;
import game.tiles.tile.Wall;
import game.tiles.units.Unit;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.players.Player;
import game.utils.Position;
import view.InputQuery;

import java.util.ArrayList;
import java.util.List;
public class Game_Manager {
    //game.boards.Board
    //player enemies
    protected boolean shouldstop;
    protected Player player;
    protected List<Enemy> enemies;
    protected Board board;
    protected MessageCallback messageCallback;
    protected InputQuery inputQuery;

    public Game_Manager() {
        shouldstop = false;
        enemies = new ArrayList<Enemy>();
    }

    public void setMessageCallBack(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }

    public void setInputQuery(InputQuery inputQuery) {
        this.inputQuery = inputQuery;
    }

    public void send(String msg) {
        if (this.messageCallback != null)
            this.messageCallback.send(msg);
    }

    public void stop() {
        shouldstop = true;
    }

    public boolean isstoped() {
        return shouldstop;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    public void SetPlayer(Player player) {
        this.player = player;
    }
    public List<Enemy> getEnemies(){return this.enemies;}
    public void removePlayer(Player player) {
        board.remove(player);
        messageCallback.send(board.toString());
        endgame();
    }

    public void endgame() {
        messageCallback.send("You Lost");
        stop();
    }

    public void removeEnemy(Enemy e) {
        enemies.remove(e);
        board.remove(e);
    }

    public void tick() {
        messageCallback.send(player.describe());
        messageCallback.send(board.toString());
        PlayerAction();
        EnemyAction();


    }

    public void PlayerAction() {
        boolean stop_input=true;
        int y = this.player.getPosition().getY();
        int x = this.player.getPosition().getX();
        String c = inputQuery.getInput();
        while (stop_input) {
           if(c.equals("a")||c.equals("w")||c.equals("s")||c.equals("d")||c.equals("q")){
               this.player.Move(c);
               this.player.tick();
                stop_input=false;
           }
           else
           if(c.equals("e")){
               this.player.ability();
               stop_input=false;
           }
           else
                c = inputQuery.getInput();

        }
    }
    public void EnemyAction(){
        for (Enemy enemy : enemies) {
            enemy.Action(this.player);
        }
    }
    public void Move(Unit u, Position p){
        try {
            Tile tile = board.get(p.getX(), p.getY());
            u.interact(tile);
        }
        catch (Exception ex){
            send(ex.getMessage());
        }
    }
    public Player getPlayer(){return this.player;}
    public void shouldstop(){
        if(enemies.size()==0 || !player.IsAlive())
            shouldstop = true;
    }
}
