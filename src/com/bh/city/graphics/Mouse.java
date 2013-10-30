package com.bh.city.graphics;

import com.bh.city.input.MouseHandler;
import com.bh.city.sprites.Sprite;

public class Mouse {
	public static Sprite sprite = Sprite.guimap.getSprite(7 * 8);
	
	public static void render() {
		Screen.render(sprite, MouseHandler.x, MouseHandler.y, 0);
	}
}
