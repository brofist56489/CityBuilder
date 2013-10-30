package com.bh.city.graphics;

public class Light {
	public float color_r;
	public float color_g;
	public float color_b;
	
	public int r;
	private float r2;
	
	public int x, y;
	
	public Light(int x, int y, int r, int cr, int cg, int cb) {
		this.r = r;
		this.r2 = r * r;
		this.color_r = cr / r2;
		this.color_g = cg / r2;
		this.color_b = cb / r2;
		this.x = x;
		this.y = y;
	}
	
	public void apply() {
		if(!Screen.LIGHTING_ENABLED)
			return;
		
		int w = Screen.WIDTH;
		int h = Screen.HEIGHT;
		
		int xp = x - Screen.xoff;
		int yp = y - Screen.yoff;

		if (xp + r < 0 || xp - r >= w || yp + r < 0 || yp - r >= h)
			return;
		
		int[] red_light = Screen.red_light;
		int[] green_light = Screen.green_light;
		int[] blue_light = Screen.blue_light;

		int r2 = r * r;
		int i, d;
		for (int y = -r; y < r; y++) {
			if (y + yp < 0 || y + yp >= h)
				continue;
			for (int x = -r; x < r; x++) {
				if (x + xp < 0 || x + xp >= w)
					continue;
				d = (x * x) + (y * y);
				if (d >= r2)
					continue;

				d = r2 - d;
				
				i = (xp + x) + (yp + y) * w;
				red_light[i] += d * color_r;
				green_light[i] += d * color_g;
				blue_light[i] += d * color_b;
				if(red_light[i] > 255) red_light[i] = 255;
				if(green_light[i] > 255) green_light[i] = 255;
				if(blue_light[i] > 255) blue_light[i] = 255;
			}
		}
	}
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
