package game.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testRange() {
        Position position1 = new Position(1, 1);
        Position position2 = new Position(4, 5);

        double expectedRange = 5.0;
        double actualRange = position1.Range(position2);

        assertEquals(expectedRange, actualRange, 0.0001);
    }

    @Test
    void testEquals_SamePosition() {
        Position position1 = new Position(1, 1);
        Position position2 = new Position(1, 1);

        assertTrue(position1.equals(position2));
    }

    @Test
    void testEquals_DifferentPositions() {
        Position position1 = new Position(1, 1);
        Position position2 = new Position(2, 2);

        assertFalse(position1.equals(position2));
    }

    @Test
    void testAt() {
        int x = 1;
        int y = 2;
        Position position = Position.at(x, y);

        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
    }

    @Test
    void testCompareTo_LessThan() {
        Position position1 = new Position(1, 1);
        Position position2 = new Position(2, 2);

        assertTrue(position1.compareTo(position2) < 0);
    }

    @Test
    void testCompareTo_GreaterThan() {
        Position position1 = new Position(2, 2);
        Position position2 = new Position(1, 1);

        assertTrue(position1.compareTo(position2) > 0);
    }

    @Test
    void testCompareTo_Equal() {
        Position position1 = new Position(1, 1);
        Position position2 = new Position(1, 1);

        assertEquals(0, position1.compareTo(position2));
    }
}
