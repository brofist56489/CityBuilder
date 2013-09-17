package com.bh.city.gui;

import com.bh.city.graphics.Font;

public class GUIText extends GUIObject {

	protected String text;
	
	public GUIText(GUIManager p, int x, int y, String s) {
		super(p, x, y, s.length() * 8, 10);
		text = s;
	}
	
	public GUIText(GUIManager p, int x, int y, String s, GUIWindow w) {
		super(p, x, y, s.length() * 8, 10, w);
		text = s;
	}
	
	public void setText(String text) {
		this.text = text;
		w = text.length() * 8;
	}
	
	public void render() {
		Font.render(text, x, y);
	}
	
	public void render(int x, int y) {
		Font.render(text, this.x + x, this.y + y);
	}
}
