# 👾 Multiplayer Pac-Man (Final Project by Eknoor)

A modern, multiplayer twist on the iconic **Pac-Man** arcade game — developed in Java with love, pixels, and ghost-chasing chaos.

## 🎮 Features

- 💥 Multiplayer Mode: Challenge your friends and survive the maze!
- 👻 Multiple Ghosts: Blinky, Pinky, Inky, and Claud — each with their own behavior
- 🗺️ Map Variations: Choose between 3 unique game maps (`Map1`, `Map2`, `Map3`)
- 🍒 Pellets & Power-Ups: Classic mechanics with a modern visual twist
- 🖼️ Custom UI: Start, win, and lose screens built with original art

## 🧠 How It Works

The game is structured with clean object-oriented design in Java:
- `Pacman.java`: Handles player logic  
- `PacmanGhost.java` & subclasses (`Blinky.java`, `Pinky.java`, etc.): Ghost AI  
- `Drawing.java`: Manages rendering and game loop  
- `Keyboard.java` & `Mouse.java`: Handle input  
- `Pellets`, `Walls`, and map `.txt` files create dynamic gameplay areas  

## 🧩 File Structure

```
├── Pacman.java
├── Blinky.java / Pinky.java / Inky.java / Claud.java
├── Drawing.java
├── Keyboard.java / Mouse.java
├── Pellets.java / PelletsADT.java
├── Walls.java
├── Map1.txt / Map2.txt / Map3.txt
└── GameImages/
    ├── Ghosts, maps, start/win/lose screens
```

## 🛠️ How to Run

> **Requirements:** Java 8+ and an IDE like IntelliJ or Eclipse

1. Clone the repo:
   ```bash
   git clone https://github.com/yourusername/multiplayer-pacman.git
   ```
2. Open the project in your IDE  
3. Run `Drawing.java` as the main file  
4. Use keyboard controls to start and play  

## 🕹️ Controls

- **Arrow Keys**: Player 1 movement  
- **WASD**: Player 2 movement  
- **Mouse Clicks**: Start the game from the menu  

## 🚀 Credits

Made by Eknoor for a final course project — bringing retro gaming to life with modern multiplayer magic ✨
