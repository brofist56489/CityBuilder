package com.bh.city.sprites;

import java.util.List;
import java.util.ArrayList;

import com.bh.city.graphics.Image;

public class Sprite {
	public static List<Sprite> sprites = new ArrayList<Sprite>();
	
	public static Spritesheet tilemap = new Spritesheet("/tilemap.png", 16, 16);
	public static Spritesheet buildingmap = new Spritesheet("/buildings.png", 8, 8);
	public static Spritesheet guimap = new Spritesheet("/guimap.png", 8, 8);
	public static Spritesheet font = new Spritesheet("/font.png", 90, 1);
	
	public Image texture = null;
	
	public Sprite(String imgPath) {
		texture = new Image(imgPath);
		sprites.add(this);
	}
	
	public Sprite(int[] pixels, int w, int h) {
		texture = new Image(pixels, w, h);
	}
}
