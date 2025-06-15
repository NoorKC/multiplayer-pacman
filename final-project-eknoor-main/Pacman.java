import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Pacman {

    // make a rectangle var for pacman
    private Rectangle pacman = new Rectangle(195, 255, 15, 15);
    // pacman speed
    int speed = 1;
    // the direction pacman is going in:
    // '_' = stop
    // N = up
    // E = right
    // S = down
    // W = left
    char direction;
    // color for pacman
    Color pacmanColor = new Color(255, 255, 0);
    // var for tracking lives
    private int lives = 3;

    /**
     * retrieves the x coordinate of pacman
     * 
     * @return the x coordinate
     */
    public int getX() {
        return this.pacman.x;
    }

    /**
     * retrieves the y coordinate of pacman
     * 
     * @return the y coordinate
     */
    public int getY() {
        return this.pacman.y;
    }

    /**
     * retrieves the rectangle pacman
     * 
     * @return the rectangle
     */
    public Rectangle getRec() {
        return this.pacman;
    }

    /**
     * get the direction pacman is facing and moving towards
     * 
     * @return the direction
     */
    public char getDirection() {
        return this.direction;
    }

    /**
     * updates the direction and uses speed to change the coordinates
     */
    public void update() {
        // if the direction pacman facing is up or down, change the y value
        // if its left or right, change the x value
        if (this.direction == 'N') {
            this.pacman.y -= speed;
        } else if (this.direction == 'S') {
            this.pacman.y += speed;
        } else if (this.direction == 'W') {
            this.pacman.x -= speed;
        } else if (this.direction == 'E') {
            this.pacman.x += speed;
        }
    }

    /**
     * checks if pacman can move in the direction that is needed by the user through
     * the keys
     * 
     * @param wall use the board to find where the 0s on the grid are
     * @param dir  the direction char needed by the user
     */
    public void collisionAhead(Walls wall, char dir) {
        int[][] board = wall.getBoard();
        // since each square is 15 big, divide it by that amount to find its position in
        // the 2d array
        int x = this.pacman.x / 15;
        int y = this.pacman.y / 15;
        // if the direction is being requested to change to dir, first find out whether
        // pacman can take that path
        if (dir == 'N') {
            // the x coordinate should be at the edge of its box
            if (this.pacman.x % 15 == 0) {
                // if the box ahead has no wall, return true indicating the change can be made
                if (board[y - 1][x] != 1) {
                    this.direction = 'N';
                }
            }
            // repeat for all other scenarios
        } else if (dir == 'E') {
            if (this.pacman.y % 15 == 0) {
                if (x + 1 > 27 || board[y][x + 1] != 1) {
                    this.direction = 'E';
                }
            }
        } else if (dir == 'S') {
            if (this.pacman.x % 15 == 0) {
                if (board[y + 1][x] != 1) {
                    this.direction = 'S';
                }
            }
        } else if (dir == 'W') {
            if (this.pacman.y % 15 == 0) {
                if (x - 1 < 0 || board[y][x - 1] != 1) {
                    this.direction = 'W';
                }
            }
        }
        // if it cannot move in any of those directions, set it to '_' to stop it from
        // going through walls
        else {
            this.direction = ' ';
        }
    }

    /**
     * checks for walls close to pacman
     * 
     * @param wall needed to retrieve the grid/board which has the numbers for paths
     *             and walls
     */
    public void hitWall(Walls wall) {
        int[][] board = wall.getBoard();
        // since each square is 15 big, divide it by that amount to find its position in
        // the 2d array
        int x = this.pacman.x / 15;
        int y = this.pacman.y / 15;
        // depending on the direction it is facing, check the box infront
        if (this.direction == 'N') {
            // make sure it is at the edge of the box, or else it will pass through walls
            if (this.pacman.y % 15 == 0) {
                // if the box ahead is a wall
                if (board[y - 1][x] == 1) {
                    // change direction to 0 which means no movement
                    this.direction = ' ';
                }
            }
        }
        // repeat for all other scenarios
        else if (this.direction == 'E') {
            // exception for if pacman is on the grid with number 3
            if (x + 1 > 27 || board[y][x + 1] == 3) {
                this.pacman.x = 0;
            } else if (board[y][x + 1] == 1) {
                this.direction = ' ';
            }
        } else if (this.direction == 'S') {
            if (board[y + 1][x] == 1) {
                this.direction = ' ';
            }
        } else if (this.direction == 'W') {
            if (this.pacman.x % 15 == 0) {
                // exception for if pacman is on the grid with number 3
                if (x - 1 < 0 || board[y][x - 1] == 3) {
                    this.pacman.x = 405;
                } else if (board[y][x - 1] == 1) {
                    this.direction = ' ';
                }
            }
        }
    }

    /**
     * did one of the ghosts intersect pacman?
     * 
     * @param ghosts the array goes through all ghosts to check for intersections
     *               and resets them
     */
    public void pacmanDie(PacmanGhost[] ghosts) {
        // if pacman intersects any ghost
        if (pacman.intersects(ghosts[0].getRec()) || pacman.intersects(ghosts[1].getRec())
                || pacman.intersects(ghosts[2].getRec()) || pacman.intersects(ghosts[3].getRec())) {
            // remove a live
            this.lives--;
            // reset pacman
            reset();
            // go through a array and reset all ghosts as well as thier chase timers
        for (int i = 0; i < ghosts.length; i++) {
            ghosts[i].reset();
            ghosts[i].setNextTime(7000);
        }
        }
    }

    /**
     * gets the number of lives left of pacman
     * 
     * @return the lives remaining of pacman
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * sets the lives to a specific value
     * 
     * @param lives the value you want to set it to
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * resets pacman to its starting locations and sets direction as '_' to stop it
     * from automatically moving
     */
    public void reset() {
        this.pacman.x = 195;
        this.pacman.y = 255;
        this.direction = ' ';
    }

    /**
     * draw pacman
     * 
     * @param g the graphics needed
     */
    public void drawPacman(Graphics g) {
        // set the color
        g.setColor(pacmanColor);
        // make the circle
        g.fillArc(this.pacman.x, this.pacman.y, this.pacman.width, this.pacman.height, 0, 360);
    }

}
