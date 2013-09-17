package com.bh.city.sprites;

import com.bh.city.graphics.Image;

public class Spritesheet {
	
	public Image texture;
	
	private Sprite[] sprites;
	public int tiles_width;
	public int tiles_height;
	
	public int sub_width;
	public int sub_height;
	
	public Spritesheet(String path, int w, int h) {
		texture = new Image(path);
		
		tiles_width = w;
		tiles_height = h;
		
		sub_width = texture.width / w;
		sub_height = texture.height / h;
		
		sprites = new Sprite[w * h];
		
		for(int yy=0; yy<h; yy++) {
			for(int xx=0; xx<w; xx++) {
				int[] p = new int[sub_width * sub_height];
				for(int y=0; y<sub_height; y++) {
					for(int x=0; x<sub_width; x++) {
						p[x + y * sub_width] = texture.pixels[(x + xx * sub_width) + (y + yy * sub_height) * texture.width];
					}
				}
				sprites[xx + yy * tiles_width] = new Sprite(p, sub_width, sub_height);
			}
		}
	}
	
	public Sprite getSprite(int index) {
		return sprites[index];
	}
}
