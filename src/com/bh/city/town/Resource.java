package com.bh.city.town;

import com.bh.city.graphics.Screen;
import com.bh.city.sprites.Sprite;
import com.bh.city.world.World;

public class Resource {
	public String strRep;
	
	public double val;
	public int max;
	public double loss;
	public double persec;
	
	protected Town town;
	
	public Sprite sprite;
	
	public Resource(Town t, String rep) {
		this.town = t;
		this.town.resources.add(this);
		
		val = 0.0;
		max = 0;
		loss = 0;
		persec = 0;
		
		this.strRep = rep;
	}
	
	public Resource(Town t, String rep, int spriteIndex) {
		this(t, rep);
		this.sprite = Sprite.guimap.getSprite(spriteIndex);
	}
	
	public void preTick() {
		max = 0;
		persec = 0;
		loss = 0;
	}
	public void tick() {
		if(val > max) {
			loss = (int)val - max;
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
