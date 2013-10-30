package com.bh.city.world;

import com.bh.city.graphics.Image;
import com.bh.city.graphics.Screen;
import com.bh.city.world.tiles.Tile;

public class World {
	public int width = 200;
	public int height = 200;
	
	private int[] tiles;
	private int[] data;
	private int[] subTexture;
	//private Object[] strongData;
	private boolean[] tileVisible;
	private Biome[] biomeData;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new int[width * height];
		data = new int[width * height];
		subTexture = new int[width * height];
		//strongData = new Object[WIDTH * HEIGHT];
		tileVisible = new boolean[width * height];
		biomeData = new Biome[width * height];
//		for(int i=0; i<HEIGHT*WIDTH; i++) {
//			tileVisible[i] = false;
//			biomeData[i] = Biome.VOID;
//		}
//		
//		new ForestGenerator(this, 0, 0, WIDTH, HEIGHT / 2).generate();
//		new DesertGenerator(this, 0, HEIGHT / 2, WIDTH, HEIGHT / 2).generate();
//		
//		for(int y=0; y<5; y++) {
//			setTile(WIDTH / 2 + y - 2, HEIGHT / 2, Tile.ROAD);
//			setTile(WIDTH / 2, HEIGHT / 2 - 3 + y, Tile.ROAD);
//		}
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height) return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];
	}
	
	public void setTile(int x, int y, Tile t) {
		if(x < 0 || x >= width || y < 0 || y >= height) return;
		tiles[x + y * width] = t.getId();
	}
	
	public int getData(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height) return 0;
		return data[x + y * width];
	}
	
	public void setData(int x, int y, int d) {
		if(x < 0 || x >= width || y < 0 || y >= height) return;
		data[x + y * width] = d;
	}
	
	public int getSubtexture(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height) return 0;
		return subTexture[x + y * width];
	}
	
	public void setSubtexture(int x, int y, int d) {
		if(x < 0 || x >= width || y < 0 || y >= height) return;
		subTexture[x + y * width] = d;
	}
	
//	public Object getStrongData(int x, int y) {
//		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return null;
//		return strongData[x + y * WIDTH];
//	}
//	
//	public void setStrongData(int x, int y, Object d) {
//		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return;
//		strongData[x + y * WIDTH] = d;
//	}
	
	public boolean isTileVisible(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height) return false;
		return tileVisible[x + y * width];
	}
	
	public void setTileVisible(int x, int y, boolean b) {
		if(x < 0 || x >= width || y < 0 || y >= height) return;
		tileVisible[x + y * width] = b;
	}
	
	public Biome getBiome(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height) return Biome.VOID;
		return biomeData[x + y * width];	
	}
	
	public void setBiome(int x, int y, Biome b) {
		if(x < 0 || x >= width || y < 0 || y >= height) return;
		biomeData[x + y * width] = b;
	}
	
	public void tick() {
		
	}
	
	public void render() {
		int cx =  Screen.xoff >> 4;
		int cy = Screen.yoff >> 4;
		int ry = (Screen.HEIGHT + 32) >> 4;
		int rx = (Screen.WIDTH + 24) >> 4;
		for(int y=cy; y<cy+ry; y++) {
			for(int x=cx; x<cx+rx; x++) {
				boolean vis = isTileVisible(x, y);
				Tile t = getTile(x, y);
				if(t != null && vis) {
					t.render(x, y, this);
				}
			}
		}
	}
	
	public static World loadFromImage(String path) {
		Image level = new Image(path);
		World world = new World(level.width, level.height);
		for(int y = 0; y < level.height; y++) {
			for(int x = 0; x < level.width; x++) {
				world.setTile(x, y, getTileByColor(level.pixels[x + y * level.width]));
			}
		}
		return world;
	}
	
	private static Tile getTileByColor(int color) {
		Tile t = Tile.VOID;
		switch(color) {
		case 0x00ff00:
			t = Tile.GRASS;
			break;
		case 0x7f7f7f:
			t = Tile.ROCK;
			break;
		case 0x007f00:
			t = Tile.ROCK;
			break;
		default:
			break;
		}
		return t;
	}
}
