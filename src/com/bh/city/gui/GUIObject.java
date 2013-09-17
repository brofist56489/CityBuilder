package com.bh.city.gui;

import java.awt.Rectangle;

public class GUIObject {
	public int x;
	public int y;
	public int w;
	public int h;
	
	protected GUIManager parent;
	protected GUIWindow window;
	
	public GUIObject(GUIManager p, int x, int y, int w, int h) {
		parent = p;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.window = null;
	}
	
	public GUIObject(GUIManager p, int x, int y, int w, int h, GUIWindow window) {
		this(p, x, y, w, h);
		this.window = window;
	}
	
	public void tick() {
		
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	public Rectangle getRect(int x, int y) {
		return new Rectangle(this.x + x, this.y + y, w, h);
	}
	
	public void onLeftClick() {
	}
	public void onMiddleClick() {
	}
	public void onRightClick() {
	}
	
	public void render() {
		
	}
	
	public void render(int x, int y) {
		
	}
	
	public void destroy() {
		parent.removeObject(this);
	}
}
