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

    public boolean hasWon() {
        for(int i = 0; i < MAX_COLUMN; ++i) {
            int chipCounter = 0;
            Chip chipColor = null;
            for(int j = 0; j < MAX_ROW; ++j) {
                if(playField[i][j] == Chip.EMPTY) {
                    chipCounter = 0;
                } else if(playField[i][j] == chipColor) {
                    chipCounter++;
                } else {
                    chipCounter = 0;
                    chipColor = playField[i][j];
                }
                if(chipCounter == 3) {
                    return true;
                }
            }
        }

        for(int i = 0; i < MAX_ROW; ++i) {
            int chipCounter = 0;
            Chip chipColor = null;
            for(int j = 0; j < MAX_COLUMN; ++j) {
                if(playField[j][i] == Chip.EMPTY) {
                    chipCounter = 0;
                } else if(playField[j][i] == chipColor) {
                    chipCounter++;
                } else {
                    chipCounter = 0;
                    chipColor = playField[j][i];
                }
                if(chipCounter == 3) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isFull(int column) {
        return playField[column][MAX_ROW - 1] != Chip.EMPTY;
    }

    private void updatePlayfield(int column, Player p){
        int i = 0;
        while(playField[column][i] != Chip.EMPTY) {
            i++;
        }
        playField[column][i] = p == Player.P1 ? Chip.RED : Chip.YELLOW;
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
        updatePlayfield(column, p);

        if(hasWon()) {
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
