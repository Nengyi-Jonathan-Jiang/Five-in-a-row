import java.awt.*;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameDisplay extends JPanel implements Listeners.MouseListener.MouseObserver, Listeners.KeyListener.KeyObserver{

    private GameLogic logic;
    private int WIDTH = 800;
    private int HEIGHT = 800;
    private int SIZE = 800;
    private int LEFT_OFFSET = 0;
    private int TOP_OFFSET = 0;

    private static final int BOARD_SIZE = 13;

    public GameDisplay(){
        super();
        setSize(800,800);
        setBackground(Color.BLACK);
        setVisible(true);

        new Timer(10, e -> {repaint();}).start();
    }

    @Override
    public void paint(Graphics graphics) {
        //Call super.paint and cast graphics to Graphics2D
        super.paint(graphics);
        Graphics2D g = (Graphics2D)graphics;

        //Recalculate dimensions
        WIDTH = getWidth();
        HEIGHT = getHeight();
        SIZE = Math.min(WIDTH,HEIGHT);
        LEFT_OFFSET = (WIDTH - SIZE) / 2;
        TOP_OFFSET = (HEIGHT - SIZE) / 2;

        //Paint board background
        g.setColor(Color.WHITE);
        g.fillRect(LEFT_OFFSET,TOP_OFFSET,SIZE,SIZE);


    }
    
    @Override
    public void onKeyTyped(int keyCode) {
        logic.onKey(keyCode);
    }

    @Override
    public void onMouseClick(int x, int y, Listeners.MouseListener.Button b) {
        logic.click(
            BOARD_SIZE * (x - LEFT_OFFSET) / SIZE,
            BOARD_SIZE * (y - TOP_OFFSET ) / SIZE
        );
    }

    //#region unused methods from Mouse/Key Observer

    @Override
    public void onKeyPressed(int keyCode) {/*Empty*/}

    @Override
    public void onKeyReleased(int keyCode) {/*Empty*/}


    @Override
    public void onMouseWheel(int wheelRotation) {/*Empty*/}

    @Override
    public void onMouseMove(int x, int y) {/*Empty*/}

    @Override
    public void onMouseDown(int x, int y, Listeners.MouseListener.Button b) {/*Empty*/}

    @Override
    public void onMouseUp(int x, int y, Listeners.MouseListener.Button b) {/*Empty*/}

    @Override
    public void onMouseEntered() {/*Empty*/}

    @Override
    public void onMouseExited() {/*Empty*/}

    //#endregion
}
