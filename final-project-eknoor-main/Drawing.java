import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

import javax.swing.Timer;

public class Drawing extends JPanel{
  // Timer for the game loop
  Timer gameTimer;
  Timer ghostTimer;
  
  Keyboard keys = new Keyboard();
  Mouse mouse = new Mouse();

  
  // How fast do you want your game to run? Frames Per Second  
  final int FPS = 60;
  // What suze of screen do you want?
  final int WIDTH = 420;
  final int HEIGHT = 500;

  // Other variables for your project can go on the next lines

  // create pacman
  private Pacman pacman;  
  // create the ghosts
  private Blinky blinky; 
  private Pinky pinky;
  private Claud claud;
  private Inky inky;
  BufferedImage blinkyImage = loadImage("GameImages/Blinky.png");
  BufferedImage pinkyImage = loadImage("GameImages/Pinky.png");
  BufferedImage claudImage = loadImage("GameImages/Claud.png");
  BufferedImage inkyImage = loadImage("GameImages/Inky.png");
  // array to store all the ghosts
  private PacmanGhost[] ghosts = new PacmanGhost[4];
  // create the walls
  private Walls wall; 
  // create the pellets
  private Pellets pellets;
  // create screen switcher
  private int screen;
  private final int START_SCREEN = 0;
  private final int MAPSELECTION_SCREEN = 1;
  private final int GAME_SCREEN = 2;
  private final int END_SCREEN = 3;
  // images for each screen
  BufferedImage startScreen = loadImage("GameImages/Start_screen.png");
  BufferedImage winEndScreen = loadImage("GameImages/Winning_Screen.png");
  BufferedImage looseEndScreen = loadImage("GameImages/Losing_screen.png");
  // rectangles/images to display the different map options
  Rectangle map1 = new Rectangle(30, 30, 165, 205);
  Rectangle map2 = new Rectangle(225, 30, 165, 205);
  Rectangle map3 = new Rectangle(127, 265, 165, 205);
  BufferedImage map1Image = loadImage("GameImages/map1.png");
  BufferedImage map2Image = loadImage("GameImages/map2.png");
  BufferedImage map3Image = loadImage("GameImages/map3.png");
  // font for the player score
  Font playerFont = new Font("courier", Font.BOLD, 10);
  // how many pellets pacman has eaten and scored
  private int pacmanScore;
  // did pacman win or not
  private boolean won;

  // Initialize things BEFORE the game starts
  public void setup(){
    // are there any variables that need initialized BEFORE the game starts?
    // Do that here!
    // create the coordinates and size for pacman/ghost/pellets
    this.pacman = new Pacman();
    this.blinky = new Blinky();
    this.pinky = new Pinky();
    this.claud = new Claud();
    this.inky = new Inky();
    this.ghosts[0] = this.blinky;
    this.ghosts[1] = this.pinky;
    this.ghosts[2] = this.claud;
    this.ghosts[3] = this.inky;
    this.pellets = new Pellets();
  }

  // method to help use images 
  public BufferedImage loadImage(String filename) {
    // creating a holding variable
    BufferedImage image = null;
    // loading files may have errors
    try {
        // try to load in image
        image = ImageIO.read(new File(filename));
    } catch (Exception e) {
        // print out red error message
        e.printStackTrace();
    }
    return image;
}

    // Paint the game components here
    @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // clear the screen
    g.clearRect(0, 0, WIDTH, HEIGHT);
    // You can add more drawing here
    // draw a black background
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WIDTH, HEIGHT);
    // if its the start screen
    if(screen == START_SCREEN){
      // display the start screen
      g.drawImage(startScreen, 0, 0, WIDTH, HEIGHT ,null);
    }
    // if map selection screen
    else if(screen == MAPSELECTION_SCREEN){
      // display all the maps
      g.drawImage(map1Image, map1.x,map1.y,map1.width,map1.height, null);
      g.drawImage(map2Image, map2.x,map2.y,map2.width,map2.height, null);
      g.drawImage(map3Image, map3.x,map3.y,map3.width,map3.height, null);
    }
    // if game screen, draw the walls, pacman, ghost, pellets
    else if(screen == GAME_SCREEN){
      this.pellets.drawPellets(g);
      this.pacman.drawPacman(g);  
      this.wall.drawWall(g);
      this.ghosts[0].drawGhost(g,blinkyImage);
      this.ghosts[1].drawGhost(g,pinkyImage);
      this.ghosts[2].drawGhost(g,claudImage);
      this.ghosts[3].drawGhost(g,inkyImage);
    
      g.setColor(Color.WHITE);
      g.setFont(playerFont);
      g.drawString("Player Score:" + this.pacmanScore, 10, 460);
      g.drawString("Lives:" + this.pacman.getLives(), 200, 460);
    }
    // if end screen, display the ending image
    else if(screen == END_SCREEN){
      // if pacman won, display the winning screen
      if(won){
        g.drawImage(winEndScreen, 0, 0, WIDTH, HEIGHT ,null);
      }
      // if lost, display the loosing screen
      else{
        g.drawImage(looseEndScreen, 0, 0, WIDTH, HEIGHT ,null);
      }
    }
  }

  

  // Update game logic here
  public void loop() {
    // This method is called by the game loop
    // This is where your game logic goes
    // display the start screen
    if(screen == START_SCREEN){
      // if enter is pressed, add to the screen count so we move to the next screen
      if(keys.isClicked(KeyEvent.VK_ENTER)){
        screen = 1;
      }
    }
    // for the map selection screen
    else if(screen == MAPSELECTION_SCREEN){
      // get the coordinates of the mouse
      int x = mouse.getX();
      int y = mouse.getY();
      // create a rectangle using those coordinates
      Rectangle mouseRec = new Rectangle(x,y,1,1);
      // if the mouse button is pressed
      if(mouse.buttonPressed(MouseEvent.BUTTON1)){
        // check if the mouse is rested on one of the map rectangles
        // if so, use that map file to generate the walls and start the game screen
        for(int i = 0; i < ghosts.length; i++){
      if(mouseRec.intersects(map1)){
        this.wall = new Walls("Map1.txt");
        this.pellets.createPellets(this.wall);
        this.screen = 2;
        this.ghosts[i].setNextTime(7000);
      }else if(mouseRec.intersects(map2)){
        this.wall = new Walls("Map2.txt");
        this.pellets.createPellets(this.wall);
        this.screen = 2;
        this.ghosts[i].setNextTime(7000);
      }else if(mouseRec.intersects(map3)){
        this.wall = new Walls("Map3.txt");
        this.pellets.createPellets(this.wall);
        this.screen = 2;
        this.ghosts[i].setNextTime(7000);
      }
    }
      }
    // if they wanna go back to the start screen
    if(keys.isClicked(KeyEvent.VK_BACK_SPACE)){
      // go to the previous screen
      this.screen = 0;
    }
    // everytime at the map selection screen, reset all these values so a new game begins when map is selected
      this.pacman.reset();
      this.pacman.setLives(3);
      for(int i = 0; i < this.ghosts.length; i++){
      this.ghosts[i].reset();
      }
      this.pellets.startAgain(); 
    // if its the game screen
    }else if(screen == GAME_SCREEN){
    // if this key is pressed
    if(keys.isPressed(KeyEvent.VK_LEFT)){
      // check if pacman can move in this direction 
      this.pacman.collisionAhead(this.wall,'W');
      // repeat for all other scenarios
    }else if(keys.isPressed(KeyEvent.VK_RIGHT)){
      this.pacman.collisionAhead(this.wall,'E');
    }else if(keys.isPressed(KeyEvent.VK_UP)){
      this.pacman.collisionAhead(this.wall,'N');
    }else if(keys.isPressed(KeyEvent.VK_DOWN)){
      this.pacman.collisionAhead(this.wall,'S');
    }
    // update pacman to make the changes to coordinates
    this.pacman.update();
    // does pacman collide with the wall infront of the direction it is moving in
    // if it does, stop its movement
    this.pacman.hitWall(this.wall);
    // if they don't want to play anymore and press back
    if(keys.isClicked(KeyEvent.VK_BACK_SPACE)){
      // go to the previous screen
      this.screen = 1;
    }
    // removes pellets and adds points for the ones eaten
    this.pacmanScore = this.pellets.eatPellet(this.pacman);
    // update the ghost movement
    for(int i = 0; i < this.ghosts.length; i++){
      this.ghosts[i].update();
      this.ghosts[i].ghostLogic(this.wall, this.pacman, this.blinky);
    }
    // how many lives does pacman have
    this.pacman.pacmanDie(this.ghosts);
    // if all the pellets are gone, show the end screen and set won as true
    if(this.pellets.pelletsGone()){
      this.screen = 3;
      this.won = true;
    }
    // if there are 0 lives left, show the end screen and set won as false
    else if(this.pacman.getLives() == 0){
      this.screen = 3;
      this.won = false;
    }
  }
  // to go back to the start screen, click anywhere on the screen
  else if(this.screen == END_SCREEN){
    if(mouse.buttonPressed(MouseEvent.BUTTON1)){
      screen = 0;
    }
  }
}

  // YOU SHOULDN'T NEED TO MODIFY ANYTHING AFTER THIS POINT
  // Feel free to have a look to see what is happening but don't touch the code down here!
  // This is what makes the window and all of the keyboard and mouse stuff work

  // creates the game window and sets everything up to run properly
  public Drawing() {
    // Initialize the game window
    JFrame frame = new JFrame("the yellow block is racist");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(WIDTH, HEIGHT);
    frame.setVisible(true);
    frame.add(this);
   
    frame.addKeyListener(keys);
    this.addMouseListener(mouse);
    this.addMouseMotionListener(mouse);
    this.addMouseWheelListener(mouse);
    // call the setup method for parts that need initialized before the game starts
    setup();

    // Initialize game timer to run at a constant FPS
    gameTimer = new Timer(1000/FPS, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            loop();
            repaint();
        }
    });
    gameTimer.start();
  }

  public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
          public void run() {
              new Drawing();
          }
      });
  }
}
