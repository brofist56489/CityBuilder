package com.bh.city.world.generators;

import com.bh.city.world.Biome;
import com.bh.city.world.World;
import com.bh.city.world.tiles.Tile;

public class ForestGenerator extends Generator {

	public ForestGenerator(World world, int x, int y, int w, int h) {
		super(world, x, y, w, h);
	}

	public void generate() {
		for (int yy = y; yy < y + h; yy++) {
			for (int xx = x; xx < x + w; xx++) {
				world.setTile(xx, yy, Tile.GRASS);
				world.setBiome(xx, yy, Biome.FOREST);
				if(rand.nextBoolean()) {
					world.setSubtexture(xx, yy, rand.nextInt(3));
				}
			}
		}
		
		for(int i = 0; i < 20; i++) {
			treeClump(rand.nextInt(w) + x, rand.nextInt(h) + y, rand.nextInt(20) + 10);
		}
	}

	private void treeClump(int x, int y, int r) {
		for (int yy = -r; yy < r; yy++) {
			for (int xx = -r; xx < r; xx++) {
				int d = (xx * xx) + (yy * yy);

				if (d >= r * r)
					continue;
				if(rand.nextInt(10) >= 5)
					world.setTile(xx + x, yy + y, Tile.TREE);
			}
		}
	}
}
