import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public interface PacmanGhost{
    public int getX();

    public int getY();

    public Rectangle getRec();

    public void setDirection(char direction);

    public void update();

    public void setNextTime(int time);

    public void drawGhost(Graphics g, BufferedImage image);

    public void ghostLogic(Walls wall, Pacman pacman, Blinky blinky);

    public void pacmanPathFinder(double targetX, double targetY);

    public void randomPath();

    public void wallCollision(int[][] board);

    public void reset();
}
