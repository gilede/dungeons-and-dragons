package game.tiles.units.enemies;

import game.callbacks.HeroicUnit;
import game.callbacks.MoveCallBack;
import game.tiles.units.players.Player;
import game.utils.Position;

import java.util.Map;
import java.util.Random;

public class Boss extends Enemy implements HeroicUnit {
    private int vision_range;
    private int ability_frequency;
    private int combat_ticks;

    public Boss(char t , String name,int heal_pool, int Attack_points, int Defense_points, int Experience_value, int vision_range,int ability_frequency) {
        super(t,name, heal_pool, Attack_points, Defense_points,Experience_value);
        this.vision_range = vision_range;
        this.ability_frequency=ability_frequency;
        this.combat_ticks=0;

    }
    public void ability(){
        combat_ticks = 0;
    }
    public int getvision(){
        return this.vision_range;
    }
    @Override
    public String describe(){
        return String.format("%s\t\tVision Range: %d",super.describe(),getvision());
    }
    public void Action(Player Player){
        Position Player_pos = Player.getPosition();
        int x = Player_pos.getX();
        int y = Player_pos.getY();
        int dx = this.p.getY()-y;
        int dy = this.p.getX()-x;
        if(Player_pos.Range(this.p)<vision_range) {
            if(combat_ticks==ability_frequency) {
                ability();
                Bosscombat(this.Attack_points,Player);
            }
            else{
                combat_ticks++;
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0)
                        //left
                        moveMap.get("a").Move(this,this.p);
                    else
                        //right
                        moveMap.get("d").Move(this,this.p);
                } else {
                    if (dy > 0)
                        //up
                        moveMap.get("w").Move(this,this.p);
                    else
                        //down
                        moveMap.get("s").Move(this,this.p);
                }
            }
        }
        else {
            combat_ticks = 0;
            moveMap.get(getRandomKey(moveMap)).Move(this, this.p);
        }
    }
    public void Bosscombat(int attack,Player player){
        int damage = Math.max(attack - player.Defense(),0);
        messageCallback.send(String.format("%s hit %s for %d ability damage",getName(),player.getName(),damage));
        player.getHealth().reduce(damage);
    }
    public static String getRandomKey(Map<String, MoveCallBack> moveMap) {
        Random random = new Random();
        Object[] keysArray = moveMap.keySet().toArray();
        int randomIndex = random.nextInt(keysArray.length);
        return (String) keysArray[randomIndex];
    }
}
