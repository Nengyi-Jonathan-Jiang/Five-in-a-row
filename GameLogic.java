import java.awt.event.KeyEvent;
import java.util.Arrays;

public class GameLogic {
    private GameDisplay display;

    public static final int BOARD_SIZE = 13;

    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

    private ComputerOpponent opponent;

    public GameLogic(GameDisplay disp){
        opponent = new ComputerOpponent(this);
        display = disp;
    }


    public void onKey(int keycode){
        if(keycode == KeyEvent.VK_R){
            resetBoard();
            display.repaint();
        }
    }

    public void click(int x, int y) {
        if(x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE || board[x][y] > 0) return;
        playerMove(x, y);
    }

    private void playerMove(int x, int y) {
        board[x][y] = 1;
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
        for(int[] i : board) java.util.Arrays.fill(i,0);
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

    public int getPieceAt(int x, int y){return board[x][y];}
    public boolean hasPieceAt(int x, int y){return board[x][y] > 0;}
    public void setPieceAt(ComputerOpponent o,int x, int y, int val){if(o == opponent) board[x][y] = val;}
    public boolean isEmpty(){return Arrays.stream(board).flatMapToInt(Arrays::stream).sum() == 0;}

    private static final char[] m = {'_','X','O'};
	public String getDiagonals(boolean direction){
		StringBuilder res = new StringBuilder();
		int i,j,x,y,l = BOARD_SIZE;
		for (i = l - 1; i > 0; i--) {
			for (j = 0, x = i; x < l; j++, x++)
				res.append(m[board[direction ? l - x - 1 : x][j]]);
			res.append('|');
		}
		for (i = 0; i < l; i++) {
			for (j = 0, y = i; y < l; j++, y++)
				res.append(m[board[direction ? l - j - 1 : j][y]]);
			res.append('|');
		}
		return res.toString();
	}
	public String getRows(){
		StringBuilder res = new StringBuilder();
		int i,j,l = BOARD_SIZE;
		for(i = 0; i < l; i++){
			for(j = 0; j < l; j++) res.append(m[board[i][j]]);
			res.append('|');
		}
		return res.toString();
	}
	public String getCols(){
		StringBuilder res = new StringBuilder();
		int i,j,l = BOARD_SIZE;
		for(i = 0; i < l; i++){
			for(j = 0; j < l; j++) res.append(m[board[j][i]]);
			res.append('|');
		}
		return res.toString();
	}
}
