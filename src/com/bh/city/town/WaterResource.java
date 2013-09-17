package com.bh.city.town;

import com.bh.city.sprites.Sprite;

public class WaterResource extends Resource {

	public WaterResource(Town t, int val, int max) {
		super(t);
		this.val = val;
		this.max = max;
		this.strRep = "Water";
		sprite = Sprite.guimap.getSprite(9);
	}
}
