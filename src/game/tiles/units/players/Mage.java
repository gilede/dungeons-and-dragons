package game.tiles.units.players;
import game.handler.TargetEnemy;
import game.tiles.units.enemies.Enemy;
import game.utils.Resource;
import game.utils.Health;
import game.utils.Stats;

import java.util.List;

public class Mage extends Player {
    private Resource mana;
    private int spell_power;
    private int hits_count;
    private int ability_range;
    public Mage(String name,int heal_pool, int Attack_points, int Defense_points, int mana_pool, int mana_cost, int spell_power, int hits_count, int ability_range){
        super(name, heal_pool, Attack_points, Defense_points);
        mana = new Resource(mana_pool,mana_pool/4,mana_cost);
        this.spell_power=spell_power;
        this.hits_count=hits_count;
        this.ability_range=ability_range;
    }
    @Override
    public void level_up() {
        //save all the stats before level up
        Stats ms = new Stats(this.health.getPool(),this.Attack_points,this.Defense_points,this.mana.getPool(),this.spell_power);
        super.level_up();
        mana.setPool(mana.getPool() + 25*super.Player_Level);
        mana.setAmount(Math.min(mana.getAmount() + (mana.getPool() /4),mana.getPool()));
        spell_power += 10*super.Player_Level;
        //calculate the difference before and after level up
        ms.setAfterStats(this.health.getPool(),this.Attack_points,this.Defense_points,this.mana.getPool(),this.spell_power);
        messageCallback.send(String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defense , maximum mana +%d , spell power +%d",getName(),getLevel(),ms.getint1(),ms.getint2(),ms.getint3(),ms.getint4(),ms.getint5()));
    }
    public void tick(){
        mana.increase(super.Player_Level);
    }
    public void ability(){
        if(!mana.AbleToCast()) {
            messageCallback.send(String.format("%s tried to cast Blizzard, but there was not enough mana: %d/%d", getName(), mana.getAmount(),mana.getPool()));
            tick();
        }
        else {
            messageCallback.send(String.format("%s cast Blizzard", getName()));
            mana.reduce();
            int hits = 0;
            List<Enemy> enemies = TargetEnemy.FindEnemy(this.p, ability_range);
            while (hits < hits_count && enemies.size() > 0) {
                Enemy e = super.selectRandomEnemy(enemies);
                Magecombat(spell_power, e);
                if (!e.IsAlive()) {
                    onKill(e);
                    enemies.remove(e);
                }
                hits += 1;
            }
        }
        //attack enemy based on hits count
    }
    public void Magecombat(int attack,Enemy e){
        int damage = Math.max(attack - e.Defense(),0);
        messageCallback.send(String.format("%s hit %s for %d ability damage",getName(),e.getName(),damage));
        e.getHealth().reduce(damage);
    }
    public Resource getMana(){
        return mana;
    }
    public int getSpell_power(){
        return spell_power;
    }
    @Override
    public String describe(){
        return String.format("%s\tMana: %d/%d\t\tSpell Power: %d ",super.describe(),getMana().getAmount(),getMana().getPool(),getSpell_power());
    }

}
