package game.tiles.units;
import game.tiles.Tile;
import game.tiles.tile.Empty;
import game.utils.Position;
import game.utils.Health;
import game.tiles.units.enemies.Enemy;
import game.callbacks.*;

import game.tiles.tile.Wall;
import game.tiles.units.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public abstract class Unit extends Tile {
    protected String name;
    protected Health health;
    protected int Attack_points;
    protected int Defense_points;
    protected MessageCallback messageCallback;
    protected static MoveCallBack move;
    public Unit(char t, String name, int heal_pool, int Attack_points , int Defense_points){
        super(t);
        Health health = new Health(heal_pool);
        this.name = name;
        this.health = health;
        this.Attack_points = Attack_points;
        this.Defense_points = Defense_points;
    }

    public Unit init(Position p, MessageCallback messageCallback,MoveCallBack move){
        super.init(p);
        this.messageCallback = messageCallback;
        this.move=move;
        return this;
    }
    protected static Map<String, MoveCallBack> moveMap = new HashMap<>();

    static {
        moveMap.put("a", (unit,position) -> move.Move(unit, new Position( position.getX() , position.getY() - 1)));
        moveMap.put("d", (unit,position) -> move.Move(unit, new Position(unit.p.getX(), unit.p.getY() + 1)));
        moveMap.put("w", (unit,position) -> move.Move(unit, new Position(position.getX() - 1, position.getY())));
        moveMap.put("s", (unit,position) -> move.Move(unit, new Position(position.getX() + 1, position.getY())));
        moveMap.put("q", (unit,position) -> move.Move(unit, new Position(position.getX(), position.getY())));
    }
    public void interact(Tile tile){
        tile.accept(this);
    }

    public void visit(Empty e){
        swapPosition(e,this);
    }
    public void combat(Unit u){
        messageCallback.send(String.format("%s engaged in combat with %s.\n%s\n%s",getName(),u.getName(),describe(),u.describe()));
        int damage = Math.max(Attack() - u.Defense(),0);
        messageCallback.send(String.format("%s dealt %d damage to %s.",getName(),damage,u.getName()));
        u.health.reduce(damage);
    }

    public boolean IsAlive(){
        return this.health.getAmount()>0;
    }
    public void visit(Wall w){}

    public abstract void visit(Enemy e);
    public abstract void visit(Player p);
    public int Attack(){
        Random random = new Random();
        int randomNumber = random.nextInt(this.Attack_points + 1);
        return randomNumber;
    }
    public int Defense(){
        Random random = new Random();
        int randomNumber = random.nextInt(this.Defense_points + 1);
        return randomNumber;
    }
    public String getName() {
        return name;
    }
    public String getHealthString() {
        return health.toString();
    }
    public Health getHealth() {
        return health;
    }
    public int getAttack() {
        return this.Attack_points;
    }
    public int getDefense() {
        return this.Defense_points;
    }
    public String describe(){
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d",getName(),getHealthString(),getAttack(),getDefense());
    }
}
