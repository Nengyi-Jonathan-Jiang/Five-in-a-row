import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {
        super("Five in a row");

        setSize(800,800);

        GameDisplay g = new GameDisplay();
        Listeners.KeyListener kl = new Listeners.KeyListener(this);
        kl.addObserver(g);
        new Listeners.MouseListener(this, g);

        getContentPane().add(g);

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args){new Main();}
}