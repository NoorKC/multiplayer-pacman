import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Keyboard implements KeyListener {

    private HashMap<Integer, Boolean> keyPressed = new HashMap<>();
    private HashMap<Integer, Boolean> keyReleased = new HashMap<>();

    public boolean isPressed(int keyCode){
        Boolean pressed = this.keyPressed.get(keyCode);
        if(pressed == null){
            return false;
        }
        return pressed;
    }

    public boolean isClicked(int keyCode){
        Boolean pressed = this.keyPressed.get(keyCode);
        Boolean released = this.keyReleased.get(keyCode);
        if(pressed == null){
            return false;
        }else if(pressed && (released == null || released)){
            this.keyReleased.put(keyCode, false);
            return true;
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.keyPressed.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.keyPressed.put(e.getKeyCode(), false);
        this.keyReleased.put(e.getKeyCode(), true);
    }
    
}
