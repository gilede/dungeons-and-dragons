package game.tiles.units.enemies;

import game.tiles.units.enemies.Enemy;
import game.tiles.units.players.Player;
import game.utils.Health;
import game.utils.Position;

public class Trap extends Enemy {
    private char visiblechar;
    private int visibility_time;
    private int invisibility_time;
    private int ticks_count;
    private boolean visible;
    public Trap(char t, String name,int heal_pool, int Attack_points, int Defense_points, int Experience_value, int visibility_time, int invisibility_time) {
        super(t, name, heal_pool, Attack_points, Defense_points,Experience_value);
        this.visibility_time = visibility_time;
        this.invisibility_time = invisibility_time;
        ticks_count =0;
        visible =true;
        visiblechar=t;
    }
    public void tick(Player player){
        visible();
        attack(player);
    }
    public void visible(){
        visible = (ticks_count<visibility_time);
        if(!visible)
            setChar('.');
        else
            setChar(visiblechar);
        if(ticks_count == visibility_time+invisibility_time){
                ticks_count=0;
            }
        else{
            ticks_count++;
        }
    }
    public void attack(Player player){
        if(this.p.Range(player.getPosition())<2)
            super.visit(player);
    }
    public void Action(Player player){
        tick(player);
    }

}
