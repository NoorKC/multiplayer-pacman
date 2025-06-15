import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener{

    private HashMap<Integer, Boolean> buttons = new HashMap<>();
    private int mouseX = 0;
    private int mouseY = 0;


    public boolean buttonPressed(int button){
        Boolean pressed = this.buttons.get(button);
        if(pressed == null){
            return false;
        }
        return pressed;
    }

    public int getX(){
        return this.mouseX;
    }

    public int getY(){
        return this.mouseY;
    }
 
    // mouse events
    public void mousePressed(MouseEvent e){
        this.buttons.put(e.getButton(), true);
    }

    public void mouseReleased(MouseEvent e){
        this.buttons.put(e.getButton(), false);
    }

    public void mouseMoved(MouseEvent e){
    this.mouseX = e.getX();
    this.mouseY = e.getY();
    }


    public void mouseDragged(MouseEvent e){
        
    }

    public void mouseEntered(MouseEvent e){
        
    }

    public void mouseExited(MouseEvent e){
        
    }
    
    public void mouseClicked(MouseEvent e){
        
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        
    }
}
