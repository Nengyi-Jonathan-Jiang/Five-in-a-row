import java.awt.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameDisplay extends JPanel implements Listeners.MouseListener.MouseObserver, Listeners.KeyListener.KeyObserver{

    private transient GameLogic logic;
    private int SIZE = 800;
    private int LEFT_OFFSET = 0;
    private int TOP_OFFSET = 0;

    public GameDisplay(){
        super();

        logic = new GameLogic(this);

        setSize(800,800);
        setBackground(Color.BLACK);
        setVisible(true);

        new Timer(10, e -> repaint()).start();
    }

    @Override
    public void paint(Graphics graphics) {
        //Call super.paint and cast graphics to Graphics2D
        super.paint(graphics);
        Graphics2D g = (Graphics2D)graphics;
        g.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        //Recalculate dimensions
        final int WIDTH = getWidth();
        final int HEIGHT = getHeight();
        SIZE = Math.min(WIDTH,HEIGHT);
        LEFT_OFFSET = (WIDTH - SIZE) / 2;
        TOP_OFFSET = (HEIGHT - SIZE) / 2;

        final int BOARD_SIZE = GameLogic.BOARD_SIZE;
        final int PIECE_WIDTH = SIZE / BOARD_SIZE;

        //Paint board background
        g.setColor(new Color(200,200,200));
        g.fillRect(LEFT_OFFSET,TOP_OFFSET,SIZE,SIZE);

        g.setColor(Color.BLACK);

        //Draw grid
        for(int i = 1; i < BOARD_SIZE; i++){
            int offset =  i * SIZE / BOARD_SIZE;
            g.drawLine(LEFT_OFFSET, TOP_OFFSET + offset, WIDTH - LEFT_OFFSET, TOP_OFFSET + offset);
            g.drawLine(LEFT_OFFSET + offset, TOP_OFFSET, LEFT_OFFSET + offset, HEIGHT - TOP_OFFSET);
        }

        final int PADDING = SIZE / BOARD_SIZE / 9;
        //Draw pieces
        for(int i = 0; i < GameLogic.BOARD_SIZE; i++){
            for(int j = 0; j < GameLogic.BOARD_SIZE; j++){
                int circleX = LEFT_OFFSET + i * SIZE / BOARD_SIZE;
                int circleY = TOP_OFFSET  + j * SIZE / BOARD_SIZE;
                
                switch(logic.getPieceAt(i, j)){
                    case 1:
                        g.drawOval(circleX + PADDING, circleY + PADDING, PIECE_WIDTH - 2 * PADDING, PIECE_WIDTH - 2 * PADDING);
                        break;
                    case 2:
                        g.fillOval(circleX + PADDING, circleY + PADDING, PIECE_WIDTH - 2 * PADDING, PIECE_WIDTH - 2 * PADDING);
                        break;
                    default:break;
                }
            }
        }
    }

    public void alertWin(){
        JOptionPane.showMessageDialog(getParent(), "You Win!");
    }
    public void alertLose(){
        JOptionPane.showMessageDialog(getParent(), "You Lose!");
    }

    @Override
    public void onKeyPressed(int keyCode) {
        logic.onKey(keyCode);
    }

    @Override
    public void onMouseDown(int x, int y, Listeners.MouseListener.Button b) {
        logic.click(
            GameLogic.BOARD_SIZE * (x - LEFT_OFFSET) / SIZE,
            GameLogic.BOARD_SIZE * (y - TOP_OFFSET) / SIZE
        );
    }

    //#region unused methods from Mouse/Key Observer

    @Override
    public void onKeyTyped(int keyCode) {/*Empty*/}

    @Override
    public void onKeyReleased(int keyCode) {/*Empty*/}


    @Override
    public void onMouseWheel(int wheelRotation) {/*Empty*/}

    @Override
    public void onMouseMove(int x, int y) {/*Empty*/}

    @Override
    public void onMouseClick(int x, int y, Listeners.MouseListener.Button b) {/*Empty*/}

    @Override
    public void onMouseUp(int x, int y, Listeners.MouseListener.Button b) {/*Empty*/}

    @Override
    public void onMouseEntered() {/*Empty*/}

    @Override
    public void onMouseExited() {/*Empty*/}

    //#endregion
}
