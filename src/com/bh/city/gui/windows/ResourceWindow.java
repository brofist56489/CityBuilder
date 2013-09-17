package com.bh.city.gui.windows;

import com.bh.city.gui.GUIImage;
import com.bh.city.gui.GUIManager;
import com.bh.city.gui.GUIText;
import com.bh.city.gui.GUIWindow;
import com.bh.city.town.Resource;

public class ResourceWindow extends GUIWindow {

	private Resource resource;
	private GUIText valdisp;
	private GUIText maxdisp;
	private GUIText lossdisp;
	private GUIText psdisp;
	
	public ResourceWindow(Resource r, GUIManager p, int x, int y, int w, int h) {
		super(r.strRep, p, x, y, w, h);
		resource = r;
		valdisp = new GUIText(p, w - 50, 0, r.val+"");
		maxdisp = new GUIText(p, w - 50, 16, r.max+"");
		lossdisp = new GUIText(p, w - 50, 32, r.loss+"");
		psdisp = new GUIText(p, w - 50, 48, r.persec+"");
		
		addObject(valdisp);
		addObject(maxdisp);
		addObject(lossdisp);
		addObject(psdisp);
		addObject(new GUIText(p, 0, 0, "AMM."));
		addObject(new GUIText(p, 0, 16, "MAX"));
		addObject(new GUIText(p, 0, 32, "LOSS"));
		addObject(new GUIText(p, 0, 48, "GAIN"));
		addObject(new GUIImage(p, w / 2 - 8, 70, r.sprite));
	}
	
	public void tick() {
		valdisp.setText(resource.val+"");
		maxdisp.setText(resource.max+"");
		lossdisp.setText(resource.loss+"");
		psdisp.setText(resource.persec+"");
	}
}
