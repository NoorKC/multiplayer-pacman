import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Inky implements PacmanGhost {
    // make a rectangle var for inky
    private Rectangle inky = new Rectangle(390, 435, 15, 15);
    // inky speed
    int speed = 1;
    // the direction inky is going in:
    // N = up
    // E = right
    // S = down
    // W = left
    char direction;
    // var to track which path ghost can take
    int north;
    int south;
    int west;
    int east;
    // color of ghost
    Color inkyColor = new Color(70, 191, 238);
    // timer for switching between chase and scatter
    private long nextTime = 0;
    // boolean to switch between chase and scatter
    private boolean chase = false;

    /**
     * get X coordinate for the ghost
     * 
     * @return returns the x coordinate of inky
     */
    public int getX() {
        return this.inky.x;
    }

    /**
     * get Y coordinate for the ghost
     * 
     * @return returns the y coordinate of inky
     */
    public int getY() {
        return this.inky.y;
    }

    /**
     * get rectangle for the ghost
     * 
     * @return returns the rectangle of inky
     */
    public Rectangle getRec() {
        return this.inky;
    }

    /**
     * Sets new direction for ghost
     * 
     * @param direction the char for which direction ghost will go in
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }

    /**
     * update that actually changes the x and y coordinates
     */
    public void update() {
        // if the direction inky facing is up or down, change the y value
        // if its left or right, change the x value
        if (this.direction == 'N') {
            this.inky.y -= speed;
        } else if (this.direction == 'S') {
            this.inky.y += speed;
        } else if (this.direction == 'W') {
            this.inky.x -= speed;
        } else if (this.direction == 'E') {
            this.inky.x += speed;
        }

        // use the timer to see how long ghost must stay in chase or scatter mode
        // the current time
        long now = System.currentTimeMillis();
        // if the timer is 0 or the current time is greater/equal than the timer
        if (nextTime != 0 && now >= nextTime) {
            // if it isnt chase mode, set chase to true and set timer to 20 seconds
            if (!chase) {
                setNextTime(20000);
                this.chase = true;
            }
            // if it is chase mode, set chase to false and set timer to 7 seconds
            else {
                setNextTime(7000);
                this.chase = false;
            }
        }
    }

    /**
     * sets the time for the timer
     * 
     * @param time is the time in milliseconds for what you want the timer to use
     */
    public void setNextTime(int time) {
        this.nextTime = System.currentTimeMillis() + time;
    }

    /**
     * draw the ghost
     * 
     * @param g     is the graphics library import needed
     * @param image is the image used for the specific ghost
     */
    public void drawGhost(Graphics g, BufferedImage image) {
        // set the color
        g.setColor(inkyColor);
        // make the circle
        g.drawImage(image, this.inky.x, this.inky.y, this.inky.width, this.inky.height, null);
    }

    /**
     * the logic ghost performs to see where to go next
     * 
     * @param wall   is used to get the grid of the map
     * @param pacman is used to find pacmans coordinates for ghost to follow
     * @param blinky is used by certain ghost behaviours
     */
    public void ghostLogic(Walls wall, Pacman pacman, Blinky blinky) {
        // find the distance between pacman and blinky and flip it by multiplying by -1
        double xDistance = (blinky.getX() - pacman.getX()) * -1;
        double yDistance = (blinky.getY() - pacman.getY()) * -1;
        // add that distance to pacmans coordinates and this is the place inky will
        // target
        double xTarget = pacman.getX() + xDistance;
        double yTarget = pacman.getY() + yDistance;

        // check everytime ghost is percectly in a 15x15 box on the map
        if (this.inky.x % 15 == 0 && this.inky.y % 15 == 0) {
            // check for wall collision and which path it cannot take
            wallCollision(wall.getBoard());
            // if it is not chase mode
            if (!chase) {
                // ghost will target the top left corner of the map
                pacmanPathFinder(420, 500);
            } else {
                // for chase mode, ghost follows pacman directly
                pacmanPathFinder(xTarget, yTarget);
            }
        }
    }

    /**
     * uses a target to formulate the best path to take
     * 
     * @param targetX the x coordinate of where ghost needs to go
     * @param targetY the y coordinate of where ghost needs to go
     */
    public void pacmanPathFinder(double targetX, double targetY) {
        // the coordinates of ghost
        double x = this.inky.x;
        double y = this.inky.y;
        // vars for finding distance between ghost and target
        double xDis;
        double yDis;
        double northDis;
        double southDis;
        double eastDis;
        double westDis;

        // if one of the available paths is north (up), find the distance between target
        // and ghost moving up
        if (north != 1) {
            // calculate the distance between the target and ghost
            xDis = targetX - x;
            yDis = targetY - (y - 15);
            northDis = Math.sqrt(Math.pow(xDis, 2) + Math.pow(yDis, 2));
        } else {
            // else set it to a high number which makes it impossible to choose that path
            northDis = 10000;
        }
        // repeat for all other directions
        if (south != 1) {
            xDis = targetX - x;
            yDis = targetY - (y + 15);
            southDis = Math.sqrt(Math.pow(xDis, 2) + Math.pow(yDis, 2));
        } else {
            southDis = 10000;
        }

        if (east != 1) {
            xDis = targetX - (x + 15);
            yDis = targetY - y;
            eastDis = Math.sqrt(Math.pow(xDis, 2) + Math.pow(yDis, 2));
        } else {
            eastDis = 10000;
        }

        if (west != 1) {
            xDis = targetX - (x - 15);
            yDis = targetY - y;
            westDis = Math.sqrt(Math.pow(xDis, 2) + Math.pow(yDis, 2));
        } else {
            westDis = 10000;
        }

        // compare all paths to see which the shortest one is and set direction to face
        // that way
        // if east or west is equal to 3, set the coordinates of the ghost to telepor to
        // other side
        if (east == 3) {
            this.inky.x = 0;
        } else if (west == 3) {
            this.inky.x = 405;
        } else {
            // else compare all distances to find the shortest distance to take
            if (northDis < southDis && northDis < eastDis && northDis < westDis) {
                this.direction = 'N';
            } else if (southDis < northDis && southDis < eastDis && southDis < westDis) {
                this.direction = 'S';
            } else if (eastDis < northDis && eastDis < southDis && eastDis < westDis) {
                this.direction = 'E';
            } else if (westDis < northDis && westDis < southDis && westDis < eastDis) {
                this.direction = 'W';
            } else {
                // if two distances are the same length or ghost cannot choose, go in a random
                // direction
                randomPath();
            }
        }
    }

    /**
     * chooses a random path to take for the ghost
     */
    public void randomPath() {
        // choose a random number between 0 - 3 (there are 4 directions)
        int randomDirection = (int) (Math.random() * (3 + 1));
        // if the random number is 0, try moving up
        if (randomDirection == 0) {
            if (north != 1) {
                this.direction = 'N';
            } else {
                // if you cannot move up, call the method again and generate a new random number
                randomPath();
            }
        }
        // try repeating the same for all other random numbers
        else if (randomDirection == 1) {
            if (west != 1) {
                this.direction = 'W';
            } else {
                randomPath();
            }
        } else if (randomDirection == 2) {
            if (south != 1) {
                this.direction = 'S';
            } else {
                randomPath();
            }
        } else if (randomDirection == 3) {
            if (east != 1) {
                this.direction = 'E';
            } else {
                randomPath();
            }
        }
    }

    /**
     * check for walls close to ghost
     * 
     * @param board is the grid used to determine where the 1s (walls) are located
     */
    public void wallCollision(int[][] board) {
        // divide the coordinates by 15 to find on grid
        int x = this.inky.x / 15;
        int y = this.inky.y / 15;
        // the grid has 1s for walls, set any direction facing a wall as a 1
        north = board[y - 1][x];
        south = board[y + 1][x];
        // if east or west is equal to 3, set it as 0 because it is basically a path
        // if it isn't, just do the same as north and south above
        if (east != 3) {
            east = board[y][x + 1];
        } else {
            east = 0;
        }
        if (west != 3) {
            west = board[y][x - 1];
        } else {
            west = 0;
        }

        // set the opposite direction to the one its moving in as 1 as well
        // ghosts cannot turn around and move in the direction opposite to the pervious
        // one
        if (this.direction == 'N') {
            south = 1;
        }
        if (this.direction == 'S') {
            north = 1;
        }
        if (this.direction == 'E') {
            west = 1;
        }
        if (this.direction == 'W') {
            east = 1;
        }
    }

    /**
     * resets the location, direction, and mode for ghost
     */
    public void reset() {
        this.inky.x = 390;
        this.inky.y = 435;
        this.direction = ' ';
        setNextTime(7000);
        this.chase = false;
    }
}
