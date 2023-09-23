package game.tiles.units.players;
import game.handler.TargetEnemy;
import game.tiles.units.Unit;
import game.tiles.units.enemies.Enemy;
import game.utils.Health;
import game.utils.Resource;
import game.utils.Stats;

import java.util.Random;
import java.util.List;

public class Warrior extends Player {
    private Resource ability;
    public Warrior(String name,int heal_pool, int Attack_points, int Defense_points, int ability_cooldown){
        super(name, heal_pool, Attack_points, Defense_points);
        ability = new Resource(ability_cooldown,0,1);
    }

    @Override
    public void level_up() {
        Stats ws = new Stats(this.health.getPool(),this.Attack_points,this.Defense_points);
        super.level_up();
        ability.setAmount(0);
        health.setPool(health.getPool()+5*super.Player_Level);
        this.Attack_points+= 2*super.Player_Level;
        this.Defense_points+= super.Player_Level;
        ws.setAfterStats(this.health.getPool(),this.Attack_points,this.Defense_points);
        messageCallback.send(String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defense",getName(),getLevel(),ws.getint1(),ws.getint2(),ws.getint3()));
    }
    @Override
    public void tick(){
        ability.reduce();
    }
    public void ability(){
        if(!ability.WAbleToCast()) {
            messageCallback.send(String.format("%s tried to cast Avenger's Shield, but there is a cooldown: %d", getName(), ability.getAmount()));
            tick();
        }
        else {
            int heal = heal();
            messageCallback.send(String.format("%s used Avenger's Shield, healing for %d",getName(),heal));
            ability.increase(ability.getPool());
            List<Enemy> enemies = TargetEnemy.FindEnemy(this.p, 3);
            Enemy e = super.selectRandomEnemy(enemies);
            if(e!=null) {
                Warriorcombat(getHealth().getAmount() / 10, e);
                if (!e.IsAlive()) {
                    onKill(e);
                }
            }

        }
        //attack enemy range<3

    }
    public void Warriorcombat(int attack,Enemy e){
        int damage = Math.max(attack - e.Defense(),0);
        messageCallback.send(String.format("%s hit %s for %d ability damage",getName(),e.getName(),damage));
        e.getHealth().reduce(damage);
    }


    public Resource getAbility(){
        return ability;
    }
    public int heal(){
        int cur_heal = this.health.getAmount();
        this.health.setAmount(Math.min(this.health.getAmount()+10*this.Defense_points,this.health.getPool()));
        return this.health.getAmount()-cur_heal;
    }
    @Override
    public String describe(){
        return String.format("%s\tCooldown: %d/%d",super.describe(),getAbility().getAmount(),getAbility().getPool());
    }

}
