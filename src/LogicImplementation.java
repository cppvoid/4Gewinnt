public class LogicImplementation implements FourWinsLogic {

    private Player lastPlayer;
    private static final int MAX_COLUMN = 7;
    private static final int MAX_ROW = 6;
    private Chip[][] playField = new Chip[MAX_COLUMN][MAX_ROW];
    private boolean gameIsFinished = false;

    LogicImplementation() {
        for(int i = 0; i < 7; ++i) {
            for(int j = 0; j < 6; ++j) {
                playField[i][j] = Chip.EMPTY;
            }
        }
    }

    public boolean isDraw() {
        for(int i = 0; i < MAX_COLUMN; ++i) {
            if(!isFull(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkLine(int startColumn, int startRow, int columnDirection, int rowDirection, Player p){
        while(isInBounds(startColumn - columnDirection, startRow - rowDirection)){
            startColumn -= columnDirection;
            startRow -= rowDirection;
        }

        Chip chip = (p == Player.P1)? Chip.RED : Chip.YELLOW;
        int chipCounter = 0;
        while(isInBounds(startColumn, startRow)){
            if(playField[startColumn][startRow] == chip) {
                chipCounter++;
            }
            else{
                chipCounter = 0;
            }

            if(chipCounter == 4){
                return true;
            }

            startColumn += columnDirection;
            startRow += rowDirection;
        }
        return false;
    }



    private boolean isInBounds(int column, int row) {
        return column >= 0 && column < MAX_COLUMN && row >= 0 && row < MAX_ROW;
    }

    public boolean hasWon(int column, int row, Player p) {
        boolean hasWon = false;
        hasWon |= checkLine(column, row, 1, 0, p);
        hasWon |= checkLine(column, row, 0, 1, p);
        hasWon |= checkLine(column, row, 1, 1, p);
        hasWon |= checkLine(column, row, -1, 1, p);

        return hasWon;
    }

    public boolean isFull(int column) {
        return playField[column][MAX_ROW - 1] != Chip.EMPTY;
    }


    @Override
    public Result throwChip(Player p, int column) {
        Result result = new Result();

        if(gameIsFinished) {
            result.errorstate = Errorstate.ALREADY_FINISHED;
            return result;
        }

        if(lastPlayer == p) {
            result.errorstate = Errorstate.WRONG_PLAYER;
            return result;
        }
        lastPlayer = p;

        if (column < 0 || column > 6) {
            result.errorstate = Errorstate.WRONG_COLUMN_INPUT;
            return result;
        }

        if(isFull(column)) {
            result.errorstate = Errorstate.FULL_COLUMN;
            return result;
        }



        int row = 0;
        while(playField[column][row] != Chip.EMPTY) {
            row++;
        }
        playField[column][row] = p == Player.P1 ? Chip.RED : Chip.YELLOW;


        if(hasWon(column, row, p)) {
            result.errorstate = Errorstate.NO_ERROR;

            if(p == Player.P1) {
                result.gamestate = Gamestate.P1_WON;
            } else {
                result.gamestate = Gamestate.P2_WON;
            }
            gameIsFinished = true;

            return result;
        }

        if(isDraw()) {
            result.gamestate = Gamestate.DRAW;

            return result;
        }

        result.errorstate = Errorstate.NO_ERROR;
        result.gamestate = Gamestate.RUNNING;
        return result;
    }
}
