import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Scanner;
import java.io.File;

public class Walls {
    
    // rectangle array to store all the walls
    private Rectangle[] wall;
    // the coordinates for where the wall goes
    private int x;
    private int y;
    // size of the wall
    private int size;
    // color of the walls
    Color wallColor = new Color(33, 33, 222);
    // the grid for the map
    private int[][] board = new int[31][28];
    // the file from which map is extracted
    private String mapFile;

    // constructor to set the dimensions of the walls 
    public Walls(String mapFile){
        this.x = 0;
        this.y = 0;
        this.size = 15;
        this.wall = new Rectangle[31*28];
        // call the map generator to generate a map
        this.mapFile = mapFile;
        mapGenerator();
        // using the grid, make the walls
        createWalls();
    }

    // return the board, used for grid movements of pacman
    public int[][] getBoard(){
        return this.board;
    }

    // uses a txt file to generate the map
    private void mapGenerator(){
        // counter for which row it is
        int r = 0;
        // the file
        // reading in the text file
        Scanner input = null;
        // open the file with the scanner
        try{
            input = new Scanner(new File(this.mapFile));
        }catch(Exception e){
            // if errors, print them out
            e.printStackTrace();
        }
        // get rid of the header in the file
        input.nextLine();
        // loop until we run out of lines
        while(input.hasNext()){
            // scan an entire line
            String rowInfo = input.nextLine();
            // break up the info
            String[] row = rowInfo.split(",");
            // fill in the numbers for every column
            for(int c = 0; c < row.length; c++){
                this.board[r][c] = Integer.parseInt(row[c]);
            }
            // move to the next row
            r++;
        }
        // close off the Scanner
        input.close();
    }
    
    // make the walls using the grid
    public void createWalls(){
        // counter for keepign track of walls 
        int i = 0;
        // go through the rows
        for(int r = 0; r < 31; r++){
            // go through the columns
            for(int c = 0; c < 28; c++){
                // check the integer at the specific location
                // if 1
                if(this.board[r][c] == 1){
                    // make a rectangle
                    this.wall[i] = new Rectangle(this.x , this.y , this.size , this.size);
                    // move to the next place in the wall array
                    i++;
                }
                // add to the x and y coordinates to move to the next spot
                this.x += this.size;
            }
            // set x to 0 to start from the beginning of each row
            this.x = 0;
            this.y += this.size;
        }
        
    }

    // draw the walls
    public void drawWall(Graphics g){
        // set the color
        g.setColor(wallColor);
        // go through the wall array and draw each rectangle
        for(int i = 0; i < wall.length; i++){
            if(this.wall[i] != null)
                g.fillRect(this.wall[i].x, this.wall[i].y, this.wall[i].width, this.wall[i].height);
        }
    }
    
}
