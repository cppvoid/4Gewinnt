import org.junit.*;
import static org.junit.Assert.*;


public class LogicImplementationTest {
    @Test
    public void otherPlayersTurn() {
        LogicImplementation logic = new LogicImplementation();
        logic.throwChip(Player.P1, 0);
        Result res = logic.throwChip(Player.P1,1);
        assertEquals(Errorstate.WRONG_PLAYER, res.errorstate);
    }

    @Test
    public void columnInputSmaller0() {
        LogicImplementation logic = new LogicImplementation();
        Result result = logic.throwChip(Player.P1, -1);
        assertEquals(Errorstate.WRONG_COLUMN_INPUT, result.errorstate);
    }

    @Test
    public void columnInputBigger6() {
        LogicImplementation logic = new LogicImplementation();
        Result result = logic.throwChip(Player.P1, 7);
        assertEquals(Errorstate.WRONG_COLUMN_INPUT, result.errorstate);
    }

    @Test
    public void gameIsOverAlready() {
        LogicImplementation logic = new LogicImplementation();
        logic.throwChip(Player.P1, 0);
        logic.throwChip(Player.P2, 1);
        logic.throwChip(Player.P1, 0);
        logic.throwChip(Player.P2, 1);
        logic.throwChip(Player.P1, 0);
        logic.throwChip(Player.P2, 1);
        logic.throwChip(Player.P1, 0);
        Result result = logic.throwChip(Player.P2, 1);

        assertEquals( Errorstate.ALREADY_FINISHED, result.errorstate);
    }

    @Test
    public void HasWonHorizontal() {
        LogicImplementation logic = new LogicImplementation();
        logic.throwChip(Player.P1, 0);
        logic.throwChip(Player.P2, 0);
        logic.throwChip(Player.P1, 1);
        logic.throwChip(Player.P2, 1);
        logic.throwChip(Player.P1, 2);
        logic.throwChip(Player.P2, 2);
        Result result = logic.throwChip(Player.P1, 3);


        assertEquals(Gamestate.P1_WON, result.gamestate);
    }

    @Test
    public void HasWonVertical() {
        LogicImplementation logic = new LogicImplementation();
        logic.throwChip(Player.P2, 5);
        logic.throwChip(Player.P1, 1);
        logic.throwChip(Player.P2, 5);
        logic.throwChip(Player.P1, 2);
        logic.throwChip(Player.P2, 5);
        logic.throwChip(Player.P1, 3);
        Result result = logic.throwChip(Player.P2, 5);


        assertEquals(Gamestate.P2_WON, result.gamestate);
    }

    @Test
    public void draw() {
        LogicImplementation logic = new LogicImplementation();
        logic.throwChip(Player.P2, 0);
        logic.throwChip(Player.P1, 1);
        logic.throwChip(Player.P2, 2);
        logic.throwChip(Player.P1, 3);
        logic.throwChip(Player.P2, 4);
        logic.throwChip(Player.P1, 5);
        logic.throwChip(Player.P2, 6);

        logic.throwChip(Player.P1, 0);
        logic.throwChip(Player.P2, 1);
        logic.throwChip(Player.P1, 2);
        logic.throwChip(Player.P2, 3);
        logic.throwChip(Player.P1, 4);
        logic.throwChip(Player.P2, 5);
        logic.throwChip(Player.P1, 6);

        logic.throwChip(Player.P2, 0);
        logic.throwChip(Player.P1, 2);
        logic.throwChip(Player.P2, 1);
        logic.throwChip(Player.P1, 4);
        logic.throwChip(Player.P2, 3);
        logic.throwChip(Player.P1, 6);
        logic.throwChip(Player.P2, 5);

        logic.throwChip(Player.P1, 1);
        logic.throwChip(Player.P2, 0);
        logic.throwChip(Player.P1, 3);
        logic.throwChip(Player.P2, 2);
        logic.throwChip(Player.P1, 5);
        logic.throwChip(Player.P2, 4);
        logic.throwChip(Player.P1, 6);


        logic.throwChip(Player.P2, 0);
        logic.throwChip(Player.P1, 1);
        logic.throwChip(Player.P2, 2);
        logic.throwChip(Player.P1, 3);
        logic.throwChip(Player.P2, 4);
        logic.throwChip(Player.P1, 5);
        logic.throwChip(Player.P2, 6);

        logic.throwChip(Player.P1, 0);
        logic.throwChip(Player.P2, 1);
        logic.throwChip(Player.P1, 2);
        logic.throwChip(Player.P2, 3);
        logic.throwChip(Player.P1, 4);
        logic.throwChip(Player.P2, 5);
        Result result = logic.throwChip(Player.P1, 6);


        assertEquals(Gamestate.DRAW, result.gamestate);
    }

    @Test
    public void ColumnFull() {
        LogicImplementation logic = new LogicImplementation();
        logic.throwChip(Player.P1, 0);
        logic.throwChip(Player.P2, 0);
        logic.throwChip(Player.P1, 0);
        logic.throwChip(Player.P2, 0);
        logic.throwChip(Player.P1, 0);
        logic.throwChip(Player.P2, 0);
        Result result = logic.throwChip(Player.P1, 0);

        assertEquals(Errorstate.FULL_COLUMN, result.errorstate);
    }
}
