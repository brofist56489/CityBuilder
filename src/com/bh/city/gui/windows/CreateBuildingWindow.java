package com.bh.city.gui.windows;

import com.bh.city.gui.GUIImage;
import com.bh.city.gui.GUIManager;
import com.bh.city.gui.GUIWindow;
import com.bh.city.sprites.Sprite;

public class CreateBuildingWindow extends GUIWindow {

	public CreateBuildingWindow(GUIManager p, int x, int y) {
		super("Bulidings", p, x, y, 100, 75);
		
		addObject(new GUIImage(p, 0, 0, Sprite.buildingmap.getSprite(0)) {
			public void onLeftClick() {
				
			}
		});
	}
}
