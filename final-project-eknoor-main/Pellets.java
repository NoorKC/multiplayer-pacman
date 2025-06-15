import java.awt.Color;
import java.awt.Graphics;

public class Pellets {
    // use an adt to store the pellets
    PelletsADT pellets = new PelletsADT();
    // color of the pellets
    Color pelletColor = new Color(222, 161, 133);
    // point system for eating pellets
    private int points;

    // create the coordinates for each pellet using the grid
    public void createPellets(Walls wall){
        // coordinates of the grid
        int x = 0;
        int y = 0;
        int[][] board = wall.getBoard();
        // size of each box in grid
        int boardSize = 15;
        // go through the grid and add a pellet for every 0
        for(int r = 0; r < 28; r++){
            for(int c = 0; c < 31; c++){
                if(board[c][r] == 0){
                    // add to the array list
                    pellets.add(x+5,y+5,5);
                }
                y+=boardSize;
            }
            y = 0;
            x+=boardSize;
        }
    }

    public int eatPellet(Pacman pacman){
        for(int i = 0; i < this.pellets.size(); i++){
            if(pacman.getRec().intersects(this.pellets.get(i))){
                this.points+=1;
                this.pellets.remove(i);
            }
        }
        return this.points;
    }

    // draw the pellets
    public void drawPellets(Graphics g){
        // set the color
        g.setColor(pelletColor);
        // go through the pellet array and draw each circle
        for(int i = 0; i < pellets.size(); i++){
            if(this.pellets.get(i) != null)
                g.fillArc(this.pellets.get(i).x, this.pellets.get(i).y, this.pellets.get(i).width, this.pellets.get(i).height,0,360);
        }
    }

    public boolean pelletsGone(){
        if(pellets.isEmpty()){
            return true;
        }
        return false;
    }

    public void startAgain(){
        this.points = 0;
    }
}
