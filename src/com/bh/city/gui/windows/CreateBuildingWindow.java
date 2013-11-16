package com.bh.city.gui.windows;

import com.bh.city.graphics.Font;
import com.bh.city.gui.GUIImage;
import com.bh.city.gui.GUIManager;
import com.bh.city.gui.GUIWindow;
import com.bh.city.input.MouseHandler;
import com.bh.city.sprites.Sprite;

public class CreateBuildingWindow extends GUIWindow {

	private static class CreateBuildingImage extends GUIImage {

		private String bname;
		public CreateBuildingImage(GUIManager p, int x, int y, Sprite s, String bname) {
			super(p, x, y, s);
			this.bname = bname;
		}
		
		public void render(int x, int y) {
			super.render(x, y);
			if(MouseHandler.getRect().intersects(this.getRect(x, y))) {
				Font.render(bname, MouseHandler.x - 10, MouseHandler.y - 20);
			}
		}
	}
	
	public CreateBuildingWindow(GUIManager p, int x, int y) {
		super("Buildings", p, x, y, 100, 75);
		
		addObject(new CreateBuildingImage(p, 0, 0, Sprite.buildingmap.getSprite(0), "Solar Panel") {
			public void onLeftClick() {
				
			}
		});
		addObject(new CreateBuildingImage(p, 16, 0, Sprite.buildingmap.getSprite(1), "Pump") {
			public void onLeftClick() {
				
			}
		});
	}
	
	public void tick() {
		super.tick();
	}
}
