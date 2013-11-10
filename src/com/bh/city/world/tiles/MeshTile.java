package com.bh.city.world.tiles;

import com.bh.city.graphics.Screen;
import com.bh.city.sprites.Sprite;
import com.bh.city.world.World;

public class MeshTile extends Tile {
	
	public MeshTile(int id, int tid, boolean s) {
		super(id, tid, s);
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
		
		render(x, y, u, d, l, r, ul, ur, dl, dr);
	}
	
	protected void render(int x, int y, boolean u, boolean d, boolean l, boolean r, boolean ul, boolean ur, boolean dl, boolean dr) {
		x *= TILE_WIDTH;
		y *= TILE_HEIGHT;
		
		if(u && l) {
			if(ul)
				Screen.render(Sprite.tilemap, 4 + textPos, x, y, 0);
			else
				Screen.render(Sprite.tilemap, 3 + textPos, x, y, 0);
		} else
				Screen.render(Sprite.tilemap, (u ? 2 : 0) + (l ? 1 : 0) + textPos, x, y, 0);
		
		if(u && r) {
			if(ur)
				Screen.render(Sprite.tilemap, 4 + textPos, x + 8, y, 0);
			else
				Screen.render(Sprite.tilemap, 3 + textPos, x + 8, y, 1);
		} else
			Screen.render(Sprite.tilemap, (u ? 2 : 0) + (r ? 1 : 0) + textPos, x + 8, y, (r ? 0 : 1));
		
		if(d && l) {
			if(dl)
				Screen.render(Sprite.tilemap, 4 + textPos, x, y + 8, 0);
			else
				Screen.render(Sprite.tilemap, 3 + textPos, x, y + 8, 2);
		} else
			Screen.render(Sprite.tilemap, (d ? 2 : 0) + (l ? 1 : 0) + textPos, x, y + 8, (d ? 0 : 2));
		
		if(d && r) {
			if(dr)
				Screen.render(Sprite.tilemap, 4 + textPos, x + 8, y + 8, 0);
			else
				Screen.render(Sprite.tilemap, 3 + textPos, x + 8, y + 8, 3);
		} else
			Screen.render(Sprite.tilemap, (d ? 2 : 0) + (r ? 1 : 0) + textPos, x + 8, y + 8, 3 - (d ? 2 : 0) - (r ? 1 : 0));
	}
}
