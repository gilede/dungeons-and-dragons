package game.tiles.units.enemies;
import game.callbacks.EnemyDeathCallBack;
import game.callbacks.MoveCallBack;
import game.callbacks.MessageCallback;
import game.tiles.units.players.Player;
import game.tiles.units.Unit;
import game.utils.Position;
import game.utils.Health;
import game.callbacks.Visitor;



public abstract class Enemy extends Unit {
    private int Experience_value;
    private EnemyDeathCallBack edc;
    public Enemy(char t , String name,int heal_pool, int Attack_points, int Defense_points, int Experience_value) {
        super(t, name,heal_pool, Attack_points, Defense_points);
        this.Experience_value =Experience_value;
    }
    public Enemy init(Position p, MessageCallback messageCallback, EnemyDeathCallBack edc, MoveCallBack emc){
        super.init(p,messageCallback,emc);
        this.edc =edc;
        return this;
    }
    public void accept(Visitor v){
        v.visit(this);
    }
    public void visit(Player p){
        super.combat(p);
        if(!p.IsAlive()){
            messageCallback.send(String.format("%s was killed by %s",p.getName(),getName()));
            p.onDeath();
        }
    }
    public void visit(Enemy e){}
    public int getexp(){
        return this.Experience_value;
    }
    public void onDeath(){
        edc.call();
    }
    public int getExperience() {
        return this.Experience_value;
    }
    @Override
    public String describe(){
        return String.format("%s\t\tExperience Value: %d",super.describe(),getExperience());
    }
    public abstract void Action(Player player);

}
