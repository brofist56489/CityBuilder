package com.bh.city.gui;

import java.util.ArrayList;
import java.util.List;

import com.bh.city.input.MouseHandler;

public class GUIManager {
	protected List<GUIObject> objects = new ArrayList<GUIObject>();
	protected GUIObject activeObject = null;
	
	public GUIManager() {
		
	}
	
	public void tick() {
		for(GUIObject o : objects) {
			o.tick();
		}
	}
	
	public final void addObject(GUIObject o) {
		objects.add(o);
	}
	
	public void removeObject(GUIObject o) {
		if(activeObject.equals(o)) {
			activeObject = null;
		}
		objects.remove(o);
	}
	
	public boolean onLeftClick() {
		boolean r = false;
		if(MouseHandler.heldFor <= 0) {
			activeObject = null;
			for(GUIObject o : objects) {
				if(o.getRect().intersects(MouseHandler.getRect())) {
					activeObject = o;
					r = true;
				}
			}
		} else {
			if(activeObject != null)
				r = true;
		}
		if(activeObject != null)
			activeObject.onLeftClick();
		return r;
	}
	
	public void render() {
		for(GUIObject o : objects) {
			if(!o.equals(activeObject))
				o.render();
		}
		if(activeObject != null)
			activeObject.render();
	}
}
