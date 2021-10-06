import java.awt.event.KeyEvent;

public class GameLogic {
    private GameDisplay display;

    public enum STATES{PLAYER_MOVE, PLAYER_WIN, PLAYER_LOSE}
    private STATES state = STATES.PLAYER_MOVE;

    public static final int BOARD_SIZE = 13;

    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

    private ComputerOpponent opponent;

    public GameLogic(GameDisplay disp){
        opponent = new ComputerOpponent(this);
        display = disp;
    }


    public void onKey(int keycode){
        switch(state){
            case PLAYER_MOVE:
                if(keycode == KeyEvent.VK_R){
                    resetBoard();
                    awaitPlayerMove();
                }
                break;

            case PLAYER_WIN:
                resetBoard();
                opponentMove();
                break;
            case PLAYER_LOSE:
                resetBoard();
                awaitPlayerMove();
                break;
        }    
    }

    public void click(int x, int y) {
        switch(state){
            case PLAYER_MOVE:
                if(x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) return;
                playerMove(x, y);
                break;

            case PLAYER_WIN:
                resetBoard();
                opponentMove();
                break;
            case PLAYER_LOSE:
                resetBoard();
                awaitPlayerMove();
                break;
        }
    }

    private void awaitPlayerMove(){
        this.state = STATES.PLAYER_MOVE;
        display.repaint();
    }

    private void playerMove(int x, int y) {
        board[x][y] = 1 - board[x][y];
        switch(gameWon()){
            case 1:
                this.state = STATES.PLAYER_WIN;
                display.alertWin();
                break;
            default: break;
        }
        display.repaint();
        opponentMove();
    }

    private void opponentMove(){
        opponent.move(board);
        switch(gameWon()){
            case 2:
                this.state = STATES.PLAYER_LOSE;
                display.alertLose();
                break;
            default:
                this.state = STATES.PLAYER_MOVE;
                break;
        }
        display.repaint();
    }

    private void resetBoard() {
        for(int[] i : board) java.util.Arrays.fill(i,0);
    }

    public int gameWon(){
        return ( getRows().matches(".*XXXXX.*")
              || getCols().matches(".*XXXXX.*")
              || getDiagonals(false).matches(".*XXXXX.*")
              || getDiagonals(true ).matches(".*XXXXX.*")
        ) ? 1 : ( getRows().matches(".*OOOOO.*")
               || getCols().matches(".*OOOOO.*")
               || getDiagonals(false).matches(".*OOOOO.*")
               || getDiagonals(true ).matches(".*OOOOO.*")
        ) ? 2: 0;
    }

    public STATES getState(){return state;}
    public int getPieceAt(int x, int y){return board[x][y];}
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
