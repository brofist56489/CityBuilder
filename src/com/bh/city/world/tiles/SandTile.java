package com.bh.city.world.tiles;

import com.bh.city.graphics.Screen;
import com.bh.city.sprites.Sprite;
import com.bh.city.world.World;

public class SandTile extends MeshTile {

	public SandTile(int id) {
		super(id, 16, false);
	}
	
	public void render(int x, int y, World world) {
		super.render(x, y, world);
		
		if(world.getSubtexture(x, y) > 0) {
			Screen.render(Sprite.tilemap, 51, x * TILE_WIDTH + 4, y * TILE_HEIGHT + 4, 0);
		}
	}
}
