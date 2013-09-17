package com.bh.city.gui.windows;

import com.bh.city.graphics.Screen;
import com.bh.city.gui.GUIButton;
import com.bh.city.gui.GUIManager;
import com.bh.city.gui.GUIText;
import com.bh.city.gui.GUIWindow;
import com.bh.city.sprites.Sprite;
import com.bh.city.town.buildings.Building;
import com.bh.city.town.buildings.Output;
import com.bh.city.town.buildings.Output.Outs;

public class BuildingWindow extends GUIWindow {

	protected Building build;
	
	protected int currentResource;
	
	protected GUIText psdisp;
	protected GUIText storagedisp;
	protected GUIText costdisp;
	
	public BuildingWindow(Building b, GUIManager p, int x, int y, int w, int h) {
		super(b.strRep, p, x, y, w, h);
		currentResource = 0;
		
		psdisp = new GUIText(p, w - 50, 0, (int)(b.output.ENERGY.output * 10) + "");
		storagedisp = new GUIText(p, w - 50, 16, (int)(b.output.ENERGY.storage) + "");
		costdisp = new GUIText(p, w - 50, 32, (int)(b.output.ENERGY.input * 10) + "");
		
		addObjects(psdisp);
		addObjects(storagedisp);
		addObjects(costdisp);
		addObject(new GUIText(p, 0, 0, "OUT"));
		addObject(new GUIText(p, 0, 16, "STORE"));
		addObject(new GUIText(p, 0, 32, "COST"));
		addObject(new GUIButton(p, 5, 48, "<<", this) {
			public void onLeftClick() {
				((BuildingWindow)window).currentResource -= 1;
			}
		});
		addObject(new GUIButton(p, w - 17, 48, ">>", this) {
			public void onLeftClick() {
				((BuildingWindow)window).currentResource += 1;
			}
		});
		
		build = b;
	}
	
	public void tick() {
		if(currentResource >= Output.OUTPUT_COUNT) {
			currentResource = 0;
		}
		if(currentResource < 0) {
			currentResource = Output.OUTPUT_COUNT - 1;
		}
		switch(currentResource) {
		case 0:
			setDisps(build.output.ENERGY);
			break;
		case 1:
			setDisps(build.output.WATER);
			break;
		default:
			break;
		}
	}
	protected void setDisps(Outs out) {
		psdisp.setText((int)out.output * 10 + "");
		storagedisp.setText((int)out.storage + "");
		costdisp.setText((int)out.input * 10 + "");
	}
	
	public void render() {
		super.render();
		Screen.render(Sprite.guimap, currentResource + 8, w / 2  - 4 + x, y + 50 + TOP_BORDER, 0);
	}
}
