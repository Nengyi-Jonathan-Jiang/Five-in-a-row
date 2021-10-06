import java.util.*;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Listeners {
    private Listeners(){}
    
    public static class KeyListener{
        private int i;
        private TreeMap<Integer,Boolean> keys;
        private TreeMap<Integer,KeyObserver> observers;

        public static interface KeyObserver{
            public void onKeyPressed(int keyCode);
            public void onKeyReleased(int keyCode);
            public void onKeyTyped(int keyCode);
        }

        public KeyListener(Component c){
            keys = new TreeMap<>();
            observers = new TreeMap<>();

            c.addKeyListener(new java.awt.event.KeyListener(){
                public void keyPressed(KeyEvent e){
                    int keyCode = e.getKeyCode();
                    keys.put(keyCode,true);
                    for(KeyObserver o : observers.values()){
                        o.onKeyPressed(keyCode);
                    }
                }
                public void keyReleased(KeyEvent e){
                    int keyCode = e.getKeyCode();
                    keys.put(keyCode,false);
                    for(KeyObserver o : observers.values()){
                        o.onKeyReleased(keyCode);
                    }
                }
                public void keyTyped(KeyEvent e){
                    int keyCode = e.getKeyCode();
                    for(KeyObserver o : observers.values()){
                        o.onKeyTyped(keyCode);
                    }
                }
            });
        }

        public int addObserver(KeyObserver o){
            observers.put(i,o);
            return i++;
        }

        public boolean isKeyPressed(int c){
            return keys.containsKey(c) && keys.get(c);
        }
    }

    public static class MouseListener {
        public enum Button{
            LEFT_CLICK,
            RIGHT_CLICK,
            MIDDLE_CLICK,
            NO_CLICK
        }
        private Button getButton(MouseEvent event){
            switch(event.getButton()){
                case 1: return Button.LEFT_CLICK;
                case 2: return Button.MIDDLE_CLICK;
                case 3: return Button.RIGHT_CLICK;
                default: return Button.NO_CLICK;
            }
        }
    
        public static interface MouseObserver{
            public void onMouseClick(int x, int y, Button b);
            public void onMouseWheel(int wheelRotation);
            public void onMouseMove(int x, int y);
            public void onMouseDown(int x, int y, Button b);
            public void onMouseUp(int x, int y, Button b);
            public void onMouseEntered();
            public void onMouseExited();
        }
    
        public MouseListener(Component c, MouseObserver m){
            c.addMouseListener(new java.awt.event.MouseListener(){
                @Override
                public void mouseClicked(MouseEvent event) {
                    m.onMouseClick(event.getX(), event.getY(), getButton(event));
                }
                @Override
                public void mousePressed(MouseEvent event){
                    m.onMouseDown(event.getX(), event.getY(), getButton(event));
                }
                @Override
                public void mouseReleased(MouseEvent event){
                    m.onMouseUp(event.getX(), event.getY(), getButton(event));
                }
    
                @Override
                public void mouseEntered(MouseEvent e) {m.onMouseEntered();}
                @Override
                public void mouseExited(MouseEvent e) {m.onMouseExited();}
            });
            c.addMouseMotionListener(new MouseMotionListener(){
                @Override
                public void mouseMoved(MouseEvent event){
                    m.onMouseMove(event.getX(), event.getY());
                }
                @Override
                public void mouseDragged(MouseEvent event){
                    m.onMouseMove(event.getX(), event.getY());
                }
            });
            c.addMouseWheelListener(event -> m.onMouseWheel(event.getWheelRotation()));
        }
    }
}
