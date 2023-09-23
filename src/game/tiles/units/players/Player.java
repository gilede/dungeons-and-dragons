package game.tiles.units.players;
import game.callbacks.*;
import game.tiles.tile.Empty;
import game.utils.Health;
import game.utils.Position;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.Unit;
import view.InputQuery;

import java.util.List;
import java.util.Random;

public abstract class Player extends Unit implements HeroicUnit {
    protected int Experience;
    protected int Player_Level;
    protected PlayerDeathCallBack playerDeathCallBack;

    public Player(String name,int heal_pool, int Attack_points, int Defense_points) {
        super('@',name, heal_pool, Attack_points, Defense_points);
        this.Experience =0;
        this.Player_Level=1;
    }
    public Player init(Position p, MessageCallback messageCallback, PlayerDeathCallBack playerDeathCallBack, MoveCallBack move){
        super.init(p,messageCallback,move);
        this.playerDeathCallBack = playerDeathCallBack;
        return this;
    }
    public void level_up(){
        this.Player_Level+=1;
        health.setPool(health.getPool()+10*this.Player_Level);
        health.setAmount(health.getPool());
        this.Attack_points+= 4*this.Player_Level;
        this.Defense_points+= this.Player_Level;
    }
    public void accept(Visitor v){
        v.visit(this);
    }
    public void visit(Enemy e){
        super.combat(e);
        if(!e.IsAlive()){
            swapPosition(e,this);
            onKill(e);
        }
    }
    public void visit(Player p){}
    public void visit(Empty E){
        swapPosition(this,E);
    }
    public void onKill(Enemy e){
        int expgain =  e.getexp();
        messageCallback.send(String.format("%s died. %s gained %d experience",e.getName(),getName(),e.getExperience()));
        addexp(expgain);
        e.onDeath();
    }
    public void onDeath(){
        playerDeathCallBack.call();
    }
    public void addexp(int experience){
        this.Experience+=experience;
        int levelup_exp = 50*this.Player_Level;
        while(this.Experience>=levelup_exp){
            this.Experience -= 50*this.Player_Level;
            level_up();
            levelup_exp = 50*this.Player_Level;
        }
        if(this.Experience<0)
            this.Experience=0;
    }
    public Enemy selectRandomEnemy(List<Enemy> enemyList) {
        if (enemyList.isEmpty()) {
            return null;  // Return null if the enemy list is empty
        }
        Random random = new Random();
        int randomIndex = random.nextInt(enemyList.size());
        return enemyList.get(randomIndex);
    }
    public abstract void tick();
    public abstract void ability();
    public int getLevel() {
        return this.Player_Level;
    }
    public int getExperience() {
        return this.Experience;
    }
    public int LevelExperience() {
        return this.Player_Level*50;
    }
    public void Move(String c){
        moveMap.get(c).Move(this,super.p);
    }
    @Override
    public String describe(){
        return String.format("%s\tLevel: %d\t\tExperience: %d/%d",super.describe(),getLevel(),getExperience(),LevelExperience());
    }
}
