package com.bh.city.town;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bh.city.input.MouseHandler;
import com.bh.city.town.buildings.Building;
import com.bh.city.town.buildings.PumpBuilding;
import com.bh.city.town.buildings.SolarPanel;
import com.bh.city.world.World;

public class Town {
	private Set<Building> buildings = new HashSet<Building>();
	public List<Resource> resources = new ArrayList<Resource>();
	private World world;
	
	public Resource ENERGY = new EnergyResource(this, 0, 0);
	public Resource WATER = new WaterResource(this, 0, 0);

	public Town(World world) {
		this.world = world;
	}
	
	public void addBuilding(Building b) {
		if(world.getTile(b.tileX, b.tileY).isSolid()) return;
		for(Building bu : buildings) {
			if(bu.tileX == b.tileX && bu.tileY == b.tileY) return;
		}
		buildings.add(b);
	}
	
	public Set<Building> getBuildings() {
		return buildings;
	}
	
	public void tick() {
		ENERGY.preTick();
		WATER.preTick();
		
		for(Building b : buildings) {
			b.tick();
		}
		
		ENERGY.tick();
		WATER.tick();
	}
	
	public Resource getRes(String rep) {
		for(Resource r : resources) {
			if(r.strRep.equals(rep)) {
				return r;
			}
		}
		return null;
	}
	
	public World getWorld() {
		return world;
	}

	public void render() {
		for(Building b : buildings) {
			b.render(world);
		}
	}
	
	public void onclick() {
		for(Building b : buildings) {
			if(MouseHandler.getTranslatedRect().intersects(b.getRect())) {
				if(MouseHandler.getButton() == 0) {
					b.onLeftClick();
				}
				if(MouseHandler.getButton() == 1) {
					b.onMiddleClick();
				}
				if(MouseHandler.getButton() == 2) {
					b.onRightClick();
				}
				break;
			}
		}
		if(MouseHandler.buttonDownOnce(3)) {
			int x = (int) MouseHandler.getTranslatedRect().getX() >> 4;
			int y = (int) MouseHandler.getTranslatedRect().getY() >> 4;
			addBuilding(new PumpBuilding(x, y, this));
		}
		if(MouseHandler.buttonDownOnce(2)) {
			int x = (int) MouseHandler.getTranslatedRect().getX() >> 4;
			int y = (int) MouseHandler.getTranslatedRect().getY() >> 4;
			addBuilding(new SolarPanel(x, y, this));
		}
	}
}
