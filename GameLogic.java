import java.awt.event.KeyEvent;

public class GameLogic {
    private GameDisplay display;

    public enum STATES{PLAYER_MOVE, PLAYER_WIN, PLAYER_LOSE}
    private STATES state = STATES.PLAYER_MOVE;

    public static final int BOARD_SIZE = 13;

    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

    private ComputerOpponent opponent;

    public GameLogic(GameDisplay disp){
        opponent = new ComputerOpponent();
        display = disp;
    }


    public void onKey(int keycode){
        switch(state){
            case PLAYER_MOVE:
                if(keycode == KeyEvent.VK_R) restartGame();
                break;

            case PLAYER_WIN:
            case PLAYER_LOSE:
                restartGame();
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
            case PLAYER_LOSE:
                restartGame();
                break;
        }
    }

    private void playerMove(int x, int y) {
        board[x][y] = 1 - board[x][y];
        switch(gameWon()){
            case 1:
                this.state = STATES.PLAYER_WIN;
                break;
            case 2:
                this.state = STATES.PLAYER_LOSE;
                break;
            default: break;
        }
        display.repaint();
        opponentMove();
    }

    private void opponentMove(){
        opponent.move(board);
        switch(gameWon()){
            case 1:
                this.state = STATES.PLAYER_WIN;
                break;
            case 2:
                this.state = STATES.PLAYER_LOSE;
                break;
            default:
                this.state = STATES.PLAYER_MOVE;
                break;
        }
        display.repaint();
    }

    private void restartGame() {
        for(int[] i : board) java.util.Arrays.fill(i,0);
        this.state = STATES.PLAYER_MOVE;
        display.repaint();
    }

    public int gameWon(){

        return 0;
    }

    public STATES getState(){return state;}
    public int getPieceAt(int x, int y){return board[x][y];}
}
