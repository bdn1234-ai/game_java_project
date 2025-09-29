package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public final int originalTileSize = 16; // 16x16 tile
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    public int maxWorldCol = 50;
    public int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    private KeyHandler keyH = new KeyHandler();
    private Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);
    public TileManager tileM = new TileManager(this);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval = 1000000000.0 / 60; // 60 FPS
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        player.update();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
