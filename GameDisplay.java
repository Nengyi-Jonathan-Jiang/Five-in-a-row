import java.awt.*;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameDisplay extends JPanel implements Listeners.MouseListener.MouseObserver, Listeners.KeyListener.KeyObserver{

    public GameDisplay(){
        super();
        setSize(800,800);
        setBackground(Color.BLACK);
        setVisible(true);

        new Timer(10, e -> {repaint();}).start();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D)graphics;

        final int WIDTH = getWidth();
        final int HEIGHT = getHeight();
        final int SIZE = Math.min(WIDTH,HEIGHT);
        final int LEFT_OFFSET = (WIDTH - SIZE) / 2;
        final int TOP_OFFSET = (HEIGHT - SIZE) / 2;

        g.setColor(Color.WHITE);
        g.fillRect(LEFT_OFFSET,TOP_OFFSET,SIZE,SIZE);

        
    }

    @Override
    public void onKeyPressed(int keyCode) {
        
    }

    @Override
    public void onKeyReleased(int keyCode) {
        
    }

    @Override
    public void onKeyTyped(int keyCode) {
                
    }

    @Override
    public void onMouseClick(int x, int y, Listeners.MouseListener.Button b) {
                
    }

    @Override
    public void onMouseWheel(int wheelRotation) {
                
    }

    @Override
    public void onMouseMove(int x, int y) {
                
    }

    @Override
    public void onMouseDown(int x, int y, Listeners.MouseListener.Button b) {
                
    }

    @Override
    public void onMouseUp(int x, int y, Listeners.MouseListener.Button b) {
                
    }

    @Override
    public void onMouseEntered() {
                
    }

    @Override
    public void onMouseExited() {
                
    }
}
