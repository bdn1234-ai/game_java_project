package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;
    public String direction;

    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, right1, right2;
    public int spriteCounter = 0;
    public int spriteNum = 3;

    public Rectangle solidArea;
    public boolean collisionOn = false;
}
