package com.bh.city.graphics;

public abstract class Bitmap {
	public int width;
	public int height;
	public int[] pixels;
	
	public Bitmap(int w, int h) {
		width = w;
		height = h;
		pixels = new int[w * h];
	}
}
