package com.dvoragames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.dvoragames.main.Game;
import com.dvoragames.world.AStar;
import com.dvoragames.world.Camera;
import com.dvoragames.world.Vector2i;
import com.dvoragames.world.World;


public class Enemy extends Entity{
	
	public static boolean ghost = false;
	public int ghostFrames = 0;
		
	public static int time = 0, maxTime = 500;
	
	public static int ix1,iy1,ix2,iy2,ix3,iy3,ix4,iy4;
	public static Entity enemy1,enemy2,enemy3,enemy4;
	public static boolean dead1,dead2,dead3,dead4;
	
	public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}
	
	public void tick(){
		depth = 1;
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
				Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
				path = AStar.findPath(Game.world, start, end);
			}
			
			if(new Random().nextInt(100) < 50)
				followPath(path);
				
			if(x % 16 == 0 && y % 16 == 0) {
				if(new Random().nextInt(100) < 100) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
					path = AStar.findPath(Game.world, start, end);
				}
			}
			if(dead1 == true) {
				if(path == null || path.size() == 0) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(ix1/16)),((int)(iy1/16)));
					path = AStar.findPath(Game.world, start, end);
				}
				
				if(new Random().nextInt(100) < 50)
					followPath(path);
					
				if(x % 16 == 0 && y % 16 == 0) {
					if(new Random().nextInt(100) < 100) {
						Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
						Vector2i end = new Vector2i(((int)(ix1/16)),((int)(iy1/16)));
						path = AStar.findPath(Game.world, start, end);
					}
				}
				
				if(ix1 == x && iy1 == y) {
					dead1 = false;
				}
			}
		
		if(ghost) {
			ghostFrames++;
			if(ghostFrames == 500) {
				ghostFrames = 0;
				ghost = false;

			}
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
	}
}
