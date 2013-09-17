package com.bh.city.town;

import com.bh.city.graphics.Screen;
import com.bh.city.sprites.Sprite;
import com.bh.city.world.World;

public abstract class Resource {
	public String strRep;
	
	public int val;
	public int max;
	public int loss;
	public int persec;
	
	protected Town town;
	
	public Sprite sprite;
	
	public Resource(Town t) {
		this.town = t;
		this.town.resources.add(this);
	}
	
	public void preTick() {
		max = 0;
		persec = 0;
		loss = 0;
	}
	public void tick() {
		if(val > max) {
			loss = val - max;
			val = max;
		}
		if(val < 0) {
			val = 0;
		}
		if(persec < 0) {
			loss -= persec;
			persec = 0;
		}
		persec *= 10;
		loss *= 10;
	}
	
	public void render(int x, int y, Town t, World w) {
		Screen.render(sprite, x, y, 0);
	}
	
	public String toString() {
		return strRep;
	}
}
