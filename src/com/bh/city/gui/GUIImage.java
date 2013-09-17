package com.bh.city.gui;

import com.bh.city.graphics.Screen;
import com.bh.city.sprites.Sprite;

public class GUIImage extends GUIObject {

	protected Sprite sprite;
	
	public GUIImage(GUIManager p, int x, int y, Sprite s) {
		super(p, x, y, s.texture.width, s.texture.height);
		sprite = s;
	}
	
	public void render() {
		Screen.render(sprite, x, y, 0);
	}
	
	public void render(int x, int y) {
		Screen.render(sprite, this.x + x, this.y + y, 0);
	}
}
