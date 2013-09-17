package com.bh.city.world.generators;

import com.bh.city.world.Biome;
import com.bh.city.world.World;
import com.bh.city.world.tiles.Tile;

public class DesertGenerator extends Generator {

	public DesertGenerator(World world, int x, int y, int w, int h) {
		super(world, x, y, w, h);
	}

	public void generate() {
		for(int yy=y; yy<y+h; yy++) {
			for(int xx=x; xx<x+w; xx++) {
				world.setTile(xx, yy, Tile.SAND);
				world.setBiome(xx, yy, Biome.DESERT);
				if(rand.nextInt(5) == 0) {
					world.setSubtexture(xx, yy, 1);
				}
			}
		}
		for(int i=0; i < w / 2; i+=2) {
			if(rand.nextInt(4) == 0) {
				world.setTile(i, y-1, Tile.SAND);
				world.setTile(i+1, y-1, Tile.SAND);
			}
			if(rand.nextInt(4) == 0) {
				world.setTile(i, y + h + 1, Tile.SAND);
				world.setTile(i+1, y + h + 1, Tile.SAND);
			}
		}
		genOasi();
	}
	
	private void genOasi() {
		for(int i=0; i<5; i++) {
			int x = this.x + rand.nextInt(w - 20) + 10;
			int y = this.y + rand.nextInt(h - 20) + 10;
			int r = rand.nextInt(10) + 3;
			for (int yy = -r; yy < r; yy++) {
				for (int xx = -r; xx < r; xx++) {
					int d = (xx * xx) + (yy * yy);

					if (d >= r * r)
						continue;
					world.setTile(xx + x, yy + y, Tile.WATER);
				}
			}
		}
	}
}
