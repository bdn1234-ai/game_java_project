package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10];

        getTileImage();
        loadMap("/maps/mapTmp.txt");
    }

    public void getTileImage(){
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/new_version/grass01.png"));
            tile[0].collision = false;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/new_version/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/new_version/water00.png"));
            tile[2].collision = true;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // Đọc toàn bộ file vào list
            List<String[]> mapData = new ArrayList<>();
            String line;
            while((line = br.readLine()) != null){
                String[] numbers = line.split(" ");
                mapData.add(numbers);
            }
            br.close();

            gp.maxWorldRow = mapData.size();
            gp.maxWorldCol = mapData.get(0).length;

            // Khởi tạo mảng map
            mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

            // Gán dữ liệu
            for(int row = 0; row < gp.maxWorldRow; row++){
                String[] numbers = mapData.get(row);
                for(int col = 0; col < gp.maxWorldCol; col++){
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        for(int row = 0; row < gp.maxWorldRow; row++){
            for(int col = 0; col < gp.maxWorldCol; col++){
                int tileNum = mapTileNum[col][row];

                int worldX = col * gp.tileSize;
                int worldY = row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;


                if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}