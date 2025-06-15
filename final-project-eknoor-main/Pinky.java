import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Pinky implements PacmanGhost {
    // make a rectangle var for pinky
    private Rectangle pinky = new Rectangle(390, 15, 15, 15);
    // pinky speed
    int speed = 1;
    // the direction pinky is going in:
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
    Color pinkyColor = new Color(234, 130, 229);
    // timer for switching between chase and scatter
    private long nextTime = 0;
    // boolean to switch between chase and scatter
    private boolean chase = false;

    /**
     * get X coordinate for the ghost
     * 
     * @return returns the x coordinate of pinky
     */
    public int getX() {
        return this.pinky.x;
    }

    /**
     * get Y coordinate for the ghost
     * 
     * @return returns the y coordinate of pinky
     */
    public int getY() {
        return this.pinky.y;
    }

    /**
     * get rectangle for the ghost
     * 
     * @return returns the rectangle of pinky
     */
    public Rectangle getRec() {
        return this.pinky;
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
        // if the direction pinky facing is up or down, change the y value
        // if its left or right, change the x value
        if (this.direction == 'N') {
            this.pinky.y -= speed;
        } else if (this.direction == 'S') {
            this.pinky.y += speed;
        } else if (this.direction == 'W') {
            this.pinky.x -= speed;
        } else if (this.direction == 'E') {
            this.pinky.x += speed;
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
        g.setColor(pinkyColor);
        // draw the image
        g.drawImage(image, this.pinky.x, this.pinky.y, this.pinky.width, this.pinky.height, null);
    }

    /**
     * the logic ghost performs to see where to go next
     * 
     * @param wall   is used to get the grid of the map
     * @param pacman is used to find pacmans coordinates for ghost to follow
     * @param blinky is used by certain ghost behaviours
     */
    public void ghostLogic(Walls wall, Pacman pacman, Blinky blinky) {
        // check everytime ghost is percectly in a 15x15 box on the map
        if (this.pinky.x % 15 == 0 && this.pinky.y % 15 == 0) {
            // check for wall collision and which path it cannot take
            wallCollision(wall.getBoard());
            // if it is not chase mode
            if (!chase) {
                // ghost will target the top right corner of the map
                pacmanPathFinder(420, 15);
            } else {
                // for chase mode, ghost follows 4 blocks ahead of pacman
                // if pacman is facing up, it targets four blocks up and four blocks left
                if (pacman.getDirection() == 'S') {
                    pacmanPathFinder(pacman.getX(), pacman.getY() + 60);
                } else if (pacman.getDirection() == 'E') {
                    pacmanPathFinder(pacman.getX() + 60, pacman.getY());
                } else if (pacman.getDirection() == 'W') {
                    pacmanPathFinder(pacman.getX() - 60, pacman.getY());
                } else {
                    pacmanPathFinder(pacman.getX() - 60, pacman.getY() - 60);
                }
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
        double x = this.pinky.x;
        double y = this.pinky.y;
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
            this.pinky.x = 0;
        } else if (west == 3) {
            this.pinky.x = 405;
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
        int x = this.pinky.x / 15;
        int y = this.pinky.y / 15;

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
        this.pinky.x = 390;
        this.pinky.y = 15;
        this.direction = ' ';
        setNextTime(7000);
        this.chase = false;
    }

}
