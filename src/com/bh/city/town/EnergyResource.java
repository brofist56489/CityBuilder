package com.bh.city.town;

import com.bh.city.sprites.Sprite;

public class EnergyResource extends Resource {

	public EnergyResource(Town t, int val, int max) {
		super(t);
		this.val = val;
		this.max = max;
		this.strRep = "Energy";
		sprite = Sprite.guimap.getSprite(8);
	}
	
	
}
