package com.bh.city.world.tiles;

import com.bh.city.graphics.Screen;
import com.bh.city.sprites.Sprite;
import com.bh.city.world.World;

public class Tile {

	public static final int TILE_WIDTH = 16;
	public static final int TILE_HEIGHT = 16;
	public static Tile[] tiles = new Tile[128];
	
	public static final Tile GRASS = new GrassTile(0);
	public static final Tile SAND = new SandTile(1);
	public static final Tile ROCK = new RockTile(2);
	public static final Tile TREE = new TreeTile(3);
	public static final Tile WATER = new WaterTile(4);
	
	protected final int id;
	protected int textPos;
	protected boolean solid;
	public Tile(int id, int tid, boolean s) {
		if(tiles[id] != null)
			throw new RuntimeException("Duplicate Tiles: " + id);
		tiles[id] = this;
		this.textPos = tid;
		this.id = id;
		this.solid = s;
	}
	
	public void render(int x, int y, World world) {
		Screen.render(Sprite.tilemap, textPos, x * TILE_WIDTH, y * TILE_HEIGHT, 0);
	}
	
	public void tick() {	}
	
	public void onClick() { 	}

	public final int getId() {
		return id;
	}

	public int getTextPos() {
		return textPos;
	}
	public boolean isSolid() {
		return solid;
	}
}
