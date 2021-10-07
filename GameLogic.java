import java.awt.event.KeyEvent;
import java.util.*;

public class GameLogic {
    private final GameDisplay display;

    public static final int BOARD_SIZE = 13;

    public enum BOARD_CELL{EMPTY,PLAYER,OPPONENT}
    private BOARD_CELL[][] board = new BOARD_CELL[BOARD_SIZE][BOARD_SIZE];

    private final ComputerOpponent opponent;

    public GameLogic(GameDisplay disp){
        opponent = new ComputerOpponent(this);
        display = disp;
        resetBoard();
    }


    public void onKey(int keycode){
        if(keycode == KeyEvent.VK_R){
            resetBoard();
            display.repaint();
        }
    }

    public void click(int x, int y) {
        if(x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE || board[x][y] != BOARD_CELL.EMPTY) return;
        playerMove(x, y);
    }

    private void playerMove(int x, int y) {
        board[x][y] = BOARD_CELL.PLAYER;
        if(gameWon() == 1){
            display.alertWin();
            resetBoard();
        }
        display.repaint();
        opponentMove();
    }

    private void opponentMove(){
        opponent.move();
        if(gameWon() == 2){
            display.alertLose();
            resetBoard();
        }
        display.repaint();
    }

    private void resetBoard() {
        for(var i : board) java.util.Arrays.fill(i,BOARD_CELL.EMPTY);
    }

    public int gameWon(){
        return getRows().matches(".*XXXXX.*")
            || getCols().matches(".*XXXXX.*")
            || getDiagonals(false).matches(".*XXXXX.*")
            || getDiagonals(true ).matches(".*XXXXX.*")
        ? 1 :  getRows().matches(".*OOOOO.*")
            || getCols().matches(".*OOOOO.*")
            || getDiagonals(false).matches(".*OOOOO.*")
            || getDiagonals(true ).matches(".*OOOOO.*")
        ? 2: 0;
    }

    public BOARD_CELL getPieceAt(int x, int y){return board[x][y];}
    public boolean hasPieceAt(int x, int y){return board[x][y] != BOARD_CELL.EMPTY;}
    public void setPieceAt(ComputerOpponent o,int x, int y, BOARD_CELL val){if(o == opponent) board[x][y] = val;}
    public boolean isEmpty(){
        for(var i : board) for(var j : i) if(j !=  BOARD_CELL.EMPTY) return false;
        return true;
    }

    private static final Map<BOARD_CELL,Character> m = Util.toMap(new Object[][] {{BOARD_CELL.EMPTY,"_"},{BOARD_CELL.PLAYER,"X"},{BOARD_CELL.OPPONENT,"O"},});
	public String getDiagonals(boolean direction){
		StringBuilder res = new StringBuilder();
		int i,j,x,y,l = BOARD_SIZE;
		for (i = l - 1; i > 0; i--) {
			for (j = 0, x = i; x < l; j++, x++)
				res.append(m.get(board[direction ? l - x - 1 : x][j]));
			res.append('|');
		}
		for (i = 0; i < l; i++) {
			for (j = 0, y = i; y < l; j++, y++)
				res.append(m.get(board[direction ? l - j - 1 : j][y]));
			res.append('|');
		}
		return res.toString();
	}
	public String getRows(){
		var res = new StringBuilder();
		int i,j,l = BOARD_SIZE;
		for(i = 0; i < l; i++){
			for(j = 0; j < l; j++) res.append(m.get(board[i][j]));
			res.append('|');
		}
		return res.toString();
	}
	public String getCols(){
		var res = new StringBuilder();
		int i,j,l = BOARD_SIZE;
		for(i = 0; i < l; i++){
			for(j = 0; j < l; j++) res.append(m.get(board[j][i]));
			res.append('|');
		}
		return res.toString();
	}
}
