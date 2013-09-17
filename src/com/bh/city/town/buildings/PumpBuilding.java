package com.bh.city.town.buildings;

import com.bh.city.graphics.Screen;
import com.bh.city.sprites.Sprite;
import com.bh.city.town.Town;
import com.bh.city.world.World;
import com.bh.city.world.tiles.Liquid;
import com.bh.city.world.tiles.Tile;

public class PumpBuilding extends Building {

	public PumpBuilding(int tileX, int tileY, Town t) {
		super(tileX, tileY, t);
		switch(t.getWorld().getTile(tileX, tileY).getId()) {
		case 4:
			output.WATER.output = 1;
			output.WATER.storage = 100;
			break;
		default:
			break;
		}
		strRep = "Pump";
	}
	
	public boolean requirementsMet() {
		boolean a = super.requirementsMet();
		boolean b = (town.getWorld().getTile(tileX, tileY) instanceof Liquid);
		return a && b;
	}
	
	public void render(World w) {
		int x = tileX * Tile.TILE_WIDTH;
		int y = tileY * Tile.TILE_HEIGHT;
		
		Screen.render(Sprite.buildingmap, 1, x, y, 0);
	}
}
