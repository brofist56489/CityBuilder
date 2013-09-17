package com.bh.city.world.tiles;

import com.bh.city.graphics.Screen;
import com.bh.city.sprites.Sprite;
import com.bh.city.world.World;

public class TreeTile extends MeshTile implements ForestTile {

	public TreeTile(int id) {
		super(id, 32, true);
	}
	
	public void render(int x, int y, World world) {
		
		boolean u = (world.getTile(x, y - 1) == this);
		boolean d = (world.getTile(x, y + 1) == this);
		boolean l = (world.getTile(x - 1, y) == this);
		boolean r = (world.getTile(x + 1, y) == this);

		boolean ul = (world.getTile(x - 1, y - 1) == this);
		boolean ur = (world.getTile(x + 1, y - 1) == this);
		boolean dl = (world.getTile(x - 1, y + 1) == this);
		boolean dr = (world.getTile(x + 1, y + 1) == this);

		Tile.GRASS.render(x, y, world);
		
		x *= TILE_WIDTH;
		y *= TILE_HEIGHT;
		
		if(u && l && ul)
			Screen.render(Sprite.tilemap, 3 + textPos, x, y, 1);
		else
			Screen.render(Sprite.tilemap, 0 + textPos, x, y, 0);
			
		if(u && r && ur)
			Screen.render(Sprite.tilemap, 3 + textPos, x + 8, y, 0);
		else
			Screen.render(Sprite.tilemap, 0 + textPos, x + 8, y, 1);
		
		if(d && l && dl)
			Screen.render(Sprite.tilemap, 2 + textPos, x, y + 8, 1);
		else
			Screen.render(Sprite.tilemap, 1 + textPos, x, y + 8, 0);
		
		if(d && r && dr)
			Screen.render(Sprite.tilemap, 2 + textPos, x + 8, y + 8, 0);
		else
			Screen.render(Sprite.tilemap, 1 + textPos, x + 8, y + 8, 1);
	}
}