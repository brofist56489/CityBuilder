package com.bh.city.world.generators;

import java.util.Random;

import com.bh.city.world.World;

public abstract class Generator {
	protected Random rand;
	
	protected World world;
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	
	public Generator(World world, int x, int y, int w, int h) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.w = w; 
		this.h = h;
		
		rand = new Random();
	}
	public abstract void generate();
}
