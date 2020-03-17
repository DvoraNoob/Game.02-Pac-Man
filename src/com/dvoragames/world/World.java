package com.dvoragames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.dvoragames.entities.Cacho;
import com.dvoragames.entities.Enemy;
import com.dvoragames.entities.Entity;
import com.dvoragames.entities.Player;
import com.dvoragames.entities.Uva;
import com.dvoragames.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
					
	public World(String path){
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++){
				for(int yy = 0; yy < map.getHeight(); yy++){
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					if(pixelAtual == 0xFFFFFFFF){
						//Floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					}else if(pixelAtual == 0xFF000000){
						//Parede
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_WALL);
					}else if(pixelAtual == 0xFF0026FF) {
						//Player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if(pixelAtual == 0xFFFF0000) {
						//Instanciar inimigo e adicionar a lista das entities
					}else if(pixelAtual == 0xFFB6FF00) {
						//Frutinhas uvas
						Uva uva = new Uva(xx*16,yy*16,16,16,0,Entity.UVA);
						Game.entities.add(uva);
						Game.totalFruit++;
					}else if(pixelAtual == 0xFF57007F) {
						//Cacho de uvas
						Cacho cacho = new Cacho(xx*16,yy*16,16,16,0,Entity.UVA_CACHO);
						Game.entities.add(cacho);
						Game.totalFruit++;
					}else if(pixelAtual == 0xFF7F0000) {
						//Inimigo 1
						Enemy enemy1 = new Enemy(xx*16,yy*16,16,16,0,Entity.ENEMY_1);
						Game.entities.add(enemy1);
						Enemy.enemy1 = enemy1;
						Enemy.ix1 = enemy1.getX();
						Enemy.iy1 = enemy1.getY();
					}else if(pixelAtual == 0xFF00FFFF) {
						//Inimigo 2
						Enemy enemy2 = new Enemy(xx*16,yy*16,16,16,0,Entity.ENEMY_2);
						Game.entities.add(enemy2);
						Enemy.enemy2 = enemy2;
						Enemy.ix2 = enemy2.getX();
						Enemy.iy2 = enemy2.getY();
					}else if(pixelAtual == 0xFFFFD800) {
						//Inimigo 3
						Enemy enemy3 = new Enemy(xx*16,yy*16,16,16,0,Entity.ENEMY_3);
						Game.entities.add(enemy3);
						Enemy.enemy3 = enemy3;
						Enemy.ix3 = enemy3.getX();
						Enemy.iy3 = enemy3.getY();
					}else if(pixelAtual == 0xFFB200FF) {
						//Inimigo 3
						Enemy enemy4 = new Enemy(xx*16,yy*16,16,16,0,Entity.ENEMY_4);
						Game.entities.add(enemy4);
						Enemy.enemy4 = enemy4;
						Enemy.ix4 = enemy4.getX();
						Enemy.iy4 = enemy4.getY();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	public static void restartGame(){
		Game.entities.clear();
		Game.player = new Player(0,0,16,16,1,Game.spritesheet.getSprite(32, 0,16,16));
		Game.totalFruit = 0;
		Game.countFruit = 0;
		Game.world = new World("/level1.png");
		Game.entities.add(Game.player);
		return;
	}
	
	public void render(Graphics g){
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
}