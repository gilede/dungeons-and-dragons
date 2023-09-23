package game.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    @Test
    void testReduce() {
        int pool = 10;
        int amount = 5;
        int cost = 3;
        Resource resource = new Resource(pool, amount, cost);

        resource.reduce();

        assertEquals(amount - cost, resource.getAmount());
    }

    @Test
    void testIncrease() {
        int pool = 10;
        int amount = 5;
        int cost = 3;
        Resource resource = new Resource(pool, amount, cost);

        int inc = 4;
        resource.increase(inc);

        assertEquals(amount + inc, resource.getAmount());
    }

    @Test
    void testSetPool() {
        int pool = 10;
        int amount = 5;
        int cost = 3;
        Resource resource = new Resource(pool, amount, cost);

        int newPool = 15;
        resource.setPool(newPool);

        assertEquals(newPool, resource.getPool());
        assertEquals(newPool, resource.getAmount());
    }

    @Test
    void testAbleToCast_EnoughAmount() {
        int pool = 10;
        int amount = 5;
        int cost = 3;
        Resource resource = new Resource(pool, amount, cost);

        assertTrue(resource.AbleToCast());
    }

    @Test
    void testAbleToCast_NotEnoughAmount() {
        int pool = 10;
        int amount = 2;
        int cost = 5;
        Resource resource = new Resource(pool, amount, cost);

        assertFalse(resource.AbleToCast());
    }

    @Test
    void testWAbleToCast_EnoughAmount() {
        int pool = 10;
        int amount = 5;
        int cost = 3;
        Resource resource = new Resource(pool, amount, cost);

        assertFalse(resource.WAbleToCast());
    }

    @Test
    void testWAbleToCast_NotEnoughAmount() {
        int pool = 10;
        int amount = 2;
        int cost = 5;
        Resource resource = new Resource(pool, amount, cost);

        assertTrue(resource.WAbleToCast());
    }
}
