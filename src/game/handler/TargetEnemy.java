package game.handler;

import game.Manage.Game_Manager;
import game.tiles.units.enemies.Enemy;
import game.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class TargetEnemy {
    public static Game_Manager gameManager;
    public static List<Enemy> FindEnemy(Position p, int range){
        List<Enemy> InRangeEnemy = new ArrayList<>();
        for(Enemy enemy:gameManager.getEnemies()){
            if(p.Range(enemy.getPosition())<range)
                InRangeEnemy.add(enemy);
        }
        return InRangeEnemy;
    }
    public static Enemy findClosestEnemy(Position p, int range) {
        List<Enemy> inRangeEnemies = FindEnemy(p,range);
        if (!inRangeEnemies.isEmpty()) {
            Enemy closestEnemy = inRangeEnemies.get(0);
            double closestRange = p.Range(closestEnemy.getPosition());
            for (Enemy enemy : inRangeEnemies) {
                double currentRange = p.Range(enemy.getPosition());
                if (currentRange < closestRange) {
                    closestEnemy = enemy;
                    closestRange = currentRange;
                }
            }

            return closestEnemy;
        }
        return null;
    }
}
