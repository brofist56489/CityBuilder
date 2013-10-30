package com.bh.city.gui.windows;

import com.bh.city.Game;
import com.bh.city.gui.GUIImage;
import com.bh.city.gui.GUIManager;
import com.bh.city.gui.GUIWindow;
import com.bh.city.input.MouseHandler;
import com.bh.city.sprites.Sprite;

public class ResourceListWindow extends GUIWindow {

	public ResourceListWindow(GUIManager p, int x, int y) {
		super("Resources", p, x, y, 50, 50);
		addObject(new GUIImage(p, 2, 2, Sprite.guimap.getSprite(8)) {
			public void onLeftClick() {
				if(!MouseHandler.buttonDownOnce(1)) return;
				parent.addObject(new ResourceWindow(Game.town.ENERGY, parent, 10, 10, 100, 100));
			}
		});
		addObject(new GUIImage(p, 14, 2, Sprite.guimap.getSprite(9)) {
			public void onLeftClick() {
				if(!MouseHandler.buttonDownOnce(1)) return;
				parent.addObject(new ResourceWindow(Game.town.WATER, parent, 10, 10, 100, 100));
			}
		});
		addObject(new GUIImage(p, 26, 2, Sprite.guimap.getSprite(10)) {
			public void onLeftClick() {
				if(!MouseHandler.buttonDownOnce(1)) return;
				parent.addObject(new ResourceWindow(Game.town.MONEY, parent, 10, 10, 100, 100));
			}
		});
	}

}
