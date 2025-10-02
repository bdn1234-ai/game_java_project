package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;



    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea  = new Rectangle(8, 16, 32, 32);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 4;
        worldY = gp.tileSize * 5;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Huster_walking/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Huster_walking/up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/Huster_walking/up3.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/Huster_walking/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/Huster_walking/down2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/Huster_walking/down3.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Huster_walking/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Huster_walking/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Huster_walking/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Huster_walking/right2.png"));
        }catch (IOException e){
            System.out.println("Không thể load sprite cho Player!");
            e.printStackTrace();
        }
    }

    public void update(){
        if(!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) {
            spriteNum = 3;
            spriteCounter = 0;
        }
        
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){

            if(spriteNum == 3) spriteNum = 1;

            if(keyH.upPressed){
                direction = "up";
            } else if(keyH.downPressed){
                direction = "down";
            } else if(keyH.leftPressed){
                direction = "left";
            } else if(keyH.rightPressed){
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            if(collisionOn == false){
                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            // animation counter
            spriteCounter++;
            if(spriteCounter > 12){ // đổi frame sau 12 lần update
                if(spriteNum == 1) spriteNum = 2;
                else if (spriteNum == 2) spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        switch(direction){
            case "up":
                switch(spriteNum) {
                    case 1: image = up1; break;
                    case 2: image = up2; break;
                    case 3: image = up3; break;
                }
                break;
            case "down":
                switch(spriteNum) {
                    case 1: image = down1; break;
                    case 2: image = down2; break;
                    case 3: image = down3; break;
                }
                break;
            case "left":
                switch(spriteNum) {
                    case 1: image = left1; break;
                    case 2: image = left2; break;
                    case 3: image = left2; break;
                }
                break;
            case "right":
                switch(spriteNum) {
                    case 1: image = right1; break;
                    case 2: image = right2; break;
                    case 3: image = right2; break;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

}
