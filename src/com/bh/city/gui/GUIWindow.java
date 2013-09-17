package com.bh.city.gui;

import java.util.ArrayList;
import java.util.List;

import com.bh.city.graphics.Font;
import com.bh.city.graphics.Screen;
import com.bh.city.input.MouseHandler;
import com.bh.city.sprites.Sprite;

public class GUIWindow extends GUIObject {

	protected List<GUIObject> objects = new ArrayList<GUIObject>();
	protected final int TOP_BORDER = 14;
	protected final int BACKGROUND_COLOR = 0x3f3f3f;
	protected String title;

	public GUIWindow(String title, GUIManager p, int x, int y, int w, int h) {
		super(p, x, y, w, h);
		this.title = title;
		int minWid = 20 + title.length() * 8;
		if (w < minWid) {
			this.w = minWid;
		}
	}

	public void addObject(GUIObject o) {
		objects.add(o);
	}
	
	public void addObjects(GUIObject... os) {
		for(GUIObject o : os) {
			addObject(o);
		}
	}

	public void onLeftClick() {
		if (MouseHandler.hasMoved() && MouseHandler.y - y <= TOP_BORDER) {
			x += MouseHandler.rx;
			y += MouseHandler.ry;
			return;
		}
		if (MouseHandler.x - x >= w - 13 && MouseHandler.y - y <= TOP_BORDER) {
			destroy();
			return;
		}
		if (MouseHandler.buttonDownOnce(1)) {
			for (GUIObject o : objects) {
				if (MouseHandler.getRect().intersects(o.getRect(x, y + TOP_BORDER))) {
					o.onLeftClick();
					break;
				}
			}
		}
	}

	public void render() {
		Screen.renderRect(x, y, w, h, BACKGROUND_COLOR);
		Screen.renderRectBorder(x, y + TOP_BORDER, w, h - TOP_BORDER, 0x7f7f7f);
		Screen.renderRectBorder(x, y, w, TOP_BORDER, 0xafafaf);
		Screen.render(Sprite.guimap, 0, x + w - 13, y + 2, 0);
		Font.render(title, x + 2, y + 2);
		for (GUIObject o : objects) {
			o.render(x, y + TOP_BORDER + 1);
		}
	}
}
