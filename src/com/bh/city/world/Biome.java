package com.bh.city.world;

import com.bh.city.world.tiles.Tile;

public enum Biome {
	DESERT(Tile.SAND), FOREST(Tile.GRASS), VOID(null);
	
	public Tile baseTile;
	Biome(Tile bt) {
		baseTile = bt;
	}
}
