package game.tiles.units.players;
import game.handler.TargetEnemy;
import game.tiles.units.Unit;
import game.tiles.units.enemies.Enemy;
import game.utils.Health;
import game.utils.Resource;
import game.utils.Stats;

import java.util.Random;
import java.util.List;

public class Hunter extends Player {
    private Resource arrows;
    private int range;
    private int tick_count;
    public Hunter(String name,int heal_pool, int Attack_points, int Defense_points,int range){
        super(name, heal_pool, Attack_points, Defense_points);
        arrows = new Resource(super.Player_Level*10,super.Player_Level*10,1);
        this.range=range;
        this.tick_count =0;
    }

    @Override
    public void level_up() {
        Stats hs = new Stats(this.health.getPool(),this.Attack_points,this.Defense_points);
        super.level_up();
        arrows.setPool(arrows.getAmount()+10*super.Player_Level);
        Attack_points += 2*super.Player_Level;
        Defense_points += super.Player_Level;
        hs.setAfterStats(this.health.getPool(),this.Attack_points,this.Defense_points);
        messageCallback.send(String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defense",getName(),getLevel(),hs.getint1(),hs.getint2(),hs.getint3()));
    }
    @Override
    public void tick(){
        if(tick_count==10){
            arrows.increase(super.Player_Level);
            tick_count=0;
        }
        else{
            tick_count++;
        }
    }
    public void ability(){
        if(!arrows.AbleToCast()) {
            messageCallback.send(String.format("%s tried to shoot an arrow but there were no more arrow", getName()));
            tick();
        }
        else {
            Enemy e  = TargetEnemy.findClosestEnemy(this.p, range);
            if(e!=null) {
                arrows.reduce();
                Huntercombat(getAttack(), e);
                if (!e.IsAlive()) {
                    onKill(e);
                }
            }
            else{
                messageCallback.send(String.format("%s tried to shoot an arrow but there were no enemies in range", getName()));
            }

        }

    }
    public void Huntercombat(int attack,Enemy e){
        int damage = Math.max(attack - e.Defense(),0);
        messageCallback.send(String.format("%s hit %s for %d ability damage",getName(),e.getName(),damage));
        e.getHealth().reduce(damage);
    }

    public Resource getAbility(){
        return arrows;
    }
    @Override
    public String describe(){
        return String.format("%s\tArrows: %d\t Range: %d",super.describe(),this.arrows.getAmount(),this.range);
    }

}
