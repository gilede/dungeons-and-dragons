package game.tiles.units.players;

import game.callbacks.MessageCallback;
import game.callbacks.MoveCallBack;
import game.callbacks.PlayerDeathCallBack;
import game.tiles.units.enemies.Enemy;
import game.utils.Position;
import game.utils.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarriorTest {

    private Warrior warrior;
    private MessageCallback messageCallback;
    private PlayerDeathCallBack Playercallback;
    private MoveCallBack moveCallBack;

    @BeforeEach
    void setUp() {
        warrior = new Warrior("Warrior", 100, 10, 5, 3);
        messageCallback = (msg -> System.out.println(msg));
        warrior.init(new Position(0,1),messageCallback,Playercallback,moveCallBack);
    }

    @Test
    void testLevelUp() {
        warrior.level_up();
        assertEquals(130, warrior.getHealth().getPool());
        assertEquals(22, warrior.getAttack());
        assertEquals(9, warrior.getDefense());
        assertEquals(0, warrior.getAbility().getAmount());
    }

    @Test
    void testTick() {
        warrior.getAbility().setAmount(2);
        warrior.tick();

        assertEquals(1, warrior.getAbility().getAmount());
    }


    @Test
    void testHeal() {
        warrior.getHealth().setAmount(90);

        int healAmount = warrior.heal();

        assertEquals(100, warrior.getHealth().getAmount());
        assertEquals(10, healAmount);
    }

    @Test
    void testDescribe() {
        warrior.getAbility().setPool(5);
        warrior.getAbility().setAmount(2);
        String description = warrior.describe();

        assertEquals("Warrior\t\tHealth: 100/100\t\tAttack: 10\t\tDefense: 5\tLevel: 1\t\tExperience: 0/50\tCooldown: 2/5", description);
    }
}
