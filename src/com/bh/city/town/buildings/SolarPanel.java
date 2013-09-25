package com.bh.city.town.buildings;

import com.bh.city.graphics.Screen;
import com.bh.city.sprites.Sprite;
import com.bh.city.town.Town;
import com.bh.city.world.Biome;
import com.bh.city.world.World;
import com.bh.city.world.tiles.Tile;

public class SolarPanel extends Building {

	public SolarPanel(int tileX, int tileY, Town t) {
		super(tileX, tileY, t);
		
		output.ENERGY.storage = 10000;
		output.ENERGY.output = (t.getWorld().getBiome(tileX, tileY)==Biome.DESERT) ? 2.5 : .5;
		output.ENERGY.input = 0;
		output.WATER.input = 3;
		
		strRep = "Solar Panel";
	}
	
	public void render(World w) {
		int x = tileX * Tile.TILE_WIDTH;
		int y = tileY * Tile.TILE_HEIGHT;
		
		Screen.render(Sprite.buildingmap, 0, x, y, 0);
	}
}
