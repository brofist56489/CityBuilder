package com.bh.city.world.tiles;

import com.bh.city.graphics.Screen;
import com.bh.city.sprites.Sprite;
import com.bh.city.world.World;

public class GrassTile extends MeshTile implements ForestTile {

	public GrassTile(int id) {
		super(id, 0, false);
	}
	
	public void render(int x, int y, World world) {
		
		boolean u = (world.getTile(x, y - 1) instanceof ForestTile);
		boolean d = (world.getTile(x, y + 1) instanceof ForestTile);
		boolean l = (world.getTile(x - 1, y) instanceof ForestTile);
		boolean r = (world.getTile(x + 1, y) instanceof ForestTile);

		boolean ul = (world.getTile(x - 1, y - 1) instanceof ForestTile);
		boolean ur = (world.getTile(x + 1, y - 1) instanceof ForestTile);
		boolean dl = (world.getTile(x - 1, y + 1) instanceof ForestTile);
		boolean dr = (world.getTile(x + 1, y + 1) instanceof ForestTile);

		render(x, y, u, d, l, r, ul, ur, dl, dr);
		
		if(world.getSubtexture(x, y) > 0) {
			Screen.render(Sprite.tilemap, 48 + world.getSubtexture(x, y), x * TILE_WIDTH + 2, y * TILE_HEIGHT + 2, 0);
		}
	}
}
