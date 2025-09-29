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
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_right_2.png"));
        }catch (IOException e){
            System.out.println("Không thể load sprite cho Player!");
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
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
                else spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        switch(direction){
            case "up":
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2;
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

}
