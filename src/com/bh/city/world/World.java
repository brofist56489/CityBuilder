package com.bh.city.world;

import com.bh.city.graphics.Screen;
import com.bh.city.world.generators.DesertGenerator;
import com.bh.city.world.generators.ForestGenerator;
import com.bh.city.world.tiles.Tile;

public class World {
	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;
	
	private int[] tiles;
	private int[] data;
	private int[] subTexture;
	private Object[] strongData;
	private boolean[] tileVisible;
	private Biome[] biomeData;
	
	public World() {
		tiles = new int[WIDTH * HEIGHT];
		data = new int[WIDTH * HEIGHT];
		subTexture = new int[WIDTH * HEIGHT];
		strongData = new Object[WIDTH * HEIGHT];
		tileVisible = new boolean[WIDTH * HEIGHT];
		biomeData = new Biome[WIDTH * HEIGHT];
		for(int i=0; i<HEIGHT*WIDTH; i++) {
			tileVisible[i] = false;
			biomeData[i] = Biome.VOID;
		}
		
		new ForestGenerator(this, 0, 0, WIDTH, HEIGHT / 2).generate();
		new DesertGenerator(this, 0, HEIGHT / 2, WIDTH, HEIGHT / 2).generate();
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return null;
		return Tile.tiles[tiles[x + y * WIDTH]];
	}
	
	public void setTile(int x, int y, Tile t) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return;
		tiles[x + y * WIDTH] = t.getId();
	}
	
	public int getData(int x, int y) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return 0;
		return data[x + y * WIDTH];
	}
	
	public void setData(int x, int y, int d) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return;
		data[x + y * WIDTH] = d;
	}
	
	public int getSubtexture(int x, int y) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return 0;
		return subTexture[x + y * WIDTH];
	}
	
	public void setSubtexture(int x, int y, int d) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return;
		subTexture[x + y * WIDTH] = d;
	}
	public Object getStrongData(int x, int y) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return null;
		return strongData[x + y * WIDTH];
	}
	
	public void setStrongData(int x, int y, Object d) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return;
		strongData[x + y * WIDTH] = d;
	}
	
	public boolean isTileVisible(int x, int y) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return false;
		return tileVisible[x + y * WIDTH];
	}
	
	public void setTileVisible(int x, int y, boolean b) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return;
		tileVisible[x + y * WIDTH] = b;
	}
	
	public Biome getBiome(int x, int y) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return Biome.VOID;
		return biomeData[x + y * WIDTH];	
	}
	
	public void setBiome(int x, int y, Biome b) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return;
		biomeData[x + y * WIDTH] = b;
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
}
