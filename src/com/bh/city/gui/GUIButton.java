package com.bh.city.gui;

import com.bh.city.graphics.Screen;

public class GUIButton extends GUIText {

	public GUIButton(GUIManager p, int x, int y, String text) {
		super(p, x, y, text);
	}
	
	public GUIButton(GUIManager p, int x, int y, String text, GUIWindow w) {
		super(p, x, y, text, w);
	}
	
	public void render() {
		render(0, 0);
	}
	
	public void render(int x, int y) {
		super.render(x, y);
		Screen.renderRectBorder(this.x + x - 1, this.y + y - 1, w + 2, h + 2, 0xff0000);
	}
}
