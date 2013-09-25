package com.bh.city.town.buildings;

import java.awt.Rectangle;

import com.bh.city.Game;
import com.bh.city.gui.windows.BuildingWindow;
import com.bh.city.town.Town;
import com.bh.city.town.buildings.Output.Outs;
import com.bh.city.world.World;
import com.bh.city.world.tiles.Tile;

public class Building {
	public int tileX;
	public int tileY;
	protected Town town;
	
	public String strRep;
	
	public boolean enabled = true;
	
	public Output output;

	public Building(int tileX, int tileY, Town t) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.town = t;
		output = new Output();
		
		if(t.getWorld().getTile(tileX, tileY).isSolid()) {
			t.getWorld().setTile(tileX, tileY, t.getWorld().getBiome(tileX, tileY).baseTile);
		}
	}
	
	public void tick() {
		for(Outs o : output.getOuts()) {
			if(requirementsMet()) {
				town.getRes(o.rep).val += o.output;
				town.getRes(o.rep).val -= o.input;
				town.getRes(o.rep).persec += o.output - o.input;
			}
			town.getRes(o.rep).max += o.storage;
		}
	}

	public void onLeftClick() {
		Game.gui.addObject(new BuildingWindow(this, Game.gui, 0, 0, 100, 80));
	}

	public void onMiddleClick() {
	}

	public void onRightClick() {
	}
	
	public void render(World w) {	
	}
	
	public Rectangle getRect() {
		return new Rectangle(tileX * Tile.TILE_WIDTH, tileY * Tile.TILE_HEIGHT, 16, 16);
	}

	public boolean requirementsMet() {
		boolean yes = true;
		for(Outs o : output.getOuts()) {
			if(o.input > town.getRes(o.rep).val) {
				yes = false;
			}
		}
		return yes;
	}
}
