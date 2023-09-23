package game.tiles.units.players;
import game.handler.TargetEnemy;
import game.tiles.units.enemies.Enemy;
import game.utils.Health;
import game.utils.Resource;
import game.utils.Stats;

import java.util.List;

public class Rogue extends Player {
    private Resource energy;
    public Rogue(String name,int heal_pool, int Attack_points, int Defense_points, int energy_cost){
        super( name, heal_pool, Attack_points, Defense_points);
        energy = new Resource(100,100,energy_cost);
    }
    @Override
    public void level_up() {
        Stats rs = new Stats(this.health.getPool(),this.Attack_points,this.Defense_points);
        super.level_up();
        energy.setAmount(100);
        Attack_points += 3*super.Player_Level;
        rs.setAfterStats(this.health.getPool(),this.Attack_points,this.Defense_points);
        messageCallback.send(String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defense",getName(),getLevel(),rs.getint1(),rs.getint2(),rs.getint3()));
    }
    public void tick(){
        energy.increase(10);
    }
    public void ability(){
        if(!energy.AbleToCast()) {
            messageCallback.send(String.format("%s tried to cast Fan of Knives, but there was not enough energy: %d/%d", getName(), energy.getAmount(),energy.getPool()));
            tick();
        }
        else {
            messageCallback.send(String.format("%s cast Fan of Knives", getName()));
            energy.reduce();
            List<Enemy> enemies = TargetEnemy.FindEnemy(this.p, 2);
            for (Enemy e : enemies) {
                Roguecombat(Attack_points, e);
                if (!e.IsAlive()) {
                    onKill(e);
                }
            }
        }
    }
    public void Roguecombat(int attack,Enemy e){
        int damage = Math.max(attack - e.Defense(),0);
        messageCallback.send(String.format("%s hit %s for %d ability damage",getName(),e.getName(),damage));
        e.getHealth().reduce(damage);
    }
    public Resource getEnergy(){
        return energy;
    }
    @Override
    public String describe(){
        return String.format("%s\tEnergy: %d/%d",super.describe(),getEnergy().getAmount(),getEnergy().getPool());
    }
}
