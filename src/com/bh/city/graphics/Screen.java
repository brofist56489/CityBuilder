package com.bh.city.graphics;

import com.bh.city.Game;
import com.bh.city.sprites.Sprite;
import com.bh.city.sprites.Spritesheet;

public class Screen {

	public static int WIDTH = Game.WIDTH;
	public static int HEIGHT = Game.HEIGHT;

	public static boolean LIGHTING_ENABLED = true;
	public static boolean POST_REDRAW_PIXELS = true;

	public static int xoff = 0;
	public static int yoff = 0;

	public static int[] pixels;
	private static int[] lighting;
	private static int[] preLightPixels;

	public static void init() {
		pixels = new int[WIDTH * HEIGHT];
		lighting = new int[WIDTH * HEIGHT];
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			lighting[i] = 255;
		}
		preLightPixels = new int[WIDTH * HEIGHT];
	}

	public static void clearLighting(int l) {
		for (int i = 0; i < lighting.length; i++) {
			lighting[i] = l;
			pixels[i] = 0;
		}
	}

	public static void render(Sprite s, int x, int y, int flip) {
		x -= xoff;
		y -= yoff;

		boolean flipx = ((flip & 0x01) == 0x01);
		boolean flipy = ((flip & 0x02) == 0x02);

		int ys, xs, col, h = s.texture.height, w = s.texture.width, i;
		for (int yy = 0; yy < h; yy++) {
			ys = yy;
			if (flipy)
				ys = h - 1 - yy;
			if (ys + y < 0 || ys + y >= HEIGHT)
				continue;
			for (int xx = 0; xx < w; xx++) {
				xs = xx;
				if (flipx)
					xs = w - 1 - xx;
				if (xs + x < 0 || xs + x >= WIDTH)
					continue;

				i = (xs + x) + (ys + y) * WIDTH;
				col = s.texture.pixels[xx + yy * w];
				if (col == 0x7f007f)
					continue;
				preLightPixels[i] = col;
				if (LIGHTING_ENABLED) {
					col = applyLighting(col, xs + x, ys + y);
				}
				pixels[i] = col;
			}
		}
	}

	public static void render(Spritesheet sheet, int t, int x, int y, int flip) {
		render(sheet.getSprite(t), x, y, flip);
	}

	public static void renderRect(int x, int y, int w, int h, int c) {
		x -= xoff;
		y -= yoff;
		
		for(int yy = 0; yy<h; yy++) {
			if(yy + y < 0|| yy + y >= HEIGHT) continue;
			for(int xx = 0; xx<w; xx++) {
				if(xx + x < 0|| xx + x >= WIDTH) continue;
				
				int i = (xx + x) + (yy + y) * WIDTH;
				int col = c;
				if(LIGHTING_ENABLED) {
					col = applyLighting(col, i);
				}
				
				pixels[i] = col;
			}
		}	
	}
	
	public static void renderRectBorder(int x, int y, int w, int h, int c) {
		x -= xoff;
		y -= yoff;
		
		int i1, i2, col1, col2;
		for(int yy = 0; yy<=h; yy++) {
			if(yy + y < 0|| yy + y >= HEIGHT) continue;
			if(x < 0 || x >= WIDTH) i1 =  -1; 
			else i1 = (x) + (yy + y) * WIDTH;
			if(x + w < 0 || x + w >= WIDTH) i2 = -1;
			else i2 = (x + w) + (yy + y) * WIDTH;
			col1 = c;
			col2 = c;
			if(LIGHTING_ENABLED) {
				if(i1 != -1)
					col1 = applyLighting(col1, i1);
				if(i2 != -1)
					col2 = applyLighting(col2, i2);
			}
			if(i1 !=  -1)
				pixels[i1] = col1;
			if(i2 !=  -1)
				pixels[i2] = col2;
		}
		for(int xx = 0; xx<=w; xx++) {
			if(xx + x < 0|| xx + x >= WIDTH) continue;
			if(y < 0 || y >=HEIGHT) i1 =  -1; 
			else i1 = (xx + x) + (y) * WIDTH;
			if(y + h < 0 || y + h >= HEIGHT) i2 = -1;
			else i2 = (xx + x) + (h + y) * WIDTH;
			col1 = c;
			col2 = c;
			if(LIGHTING_ENABLED) {
				if(i1 != -1)
					col1 = applyLighting(col1, i1);
				if(i2 != -1)
					col2 = applyLighting(col2, i2);
			}
			if(i1 !=  -1)
				pixels[i1] = col1;
			if(i2 !=  -1)
				pixels[i2] = col2;
		}
	}

	private static int applyLighting(int col, int x, int y) {
		return applyLighting(col, lighting[x + y * WIDTH]);
	}

	private static int applyLighting(int col, int br) {
		if (!LIGHTING_ENABLED || br >= 255) {
			return col;
		}
		int r = (col >> 16) & 0xff;
		int g = (col >> 8) & 0xff;
		int b = col & 0xff;

		r = r * br / 255;
		g = g * br / 255;
		b = b * br / 255;

		col = r << 16 | g << 8 | b;
		return col;
	}

	public static void addLightSource(int xp, int yp, int r, int br) {
		xp -= xoff;
		yp -= yoff;

		if (xp + r < 0 || xp - r >= WIDTH || yp + r < 0 || yp - r >= HEIGHT)
			return;

		for (int y = -r; y < r; y++) {
			if (y + yp < 0 || y + yp >= HEIGHT)
				continue;
			for (int x = -r; x < r; x++) {
				if (x + xp < 0 || x + xp >= WIDTH)
					continue;
				int d = (x * x) + (y * y);
				if (d >= r * r)
					continue;

				int i = (xp + x) + (yp + y) * WIDTH;
				lighting[i] += br - d * br / (r * r);
				lighting[i] = (lighting[i] > 255) ? 255 : lighting[i];

				if (POST_REDRAW_PIXELS) {
					int col = preLightPixels[i];
					pixels[i] = applyLighting(col, lighting[i]);
				}
			}
		}
	}

	public static void centerOn(int x, int y) {
		xoff = x - (WIDTH / 2);
		yoff = y - (HEIGHT / 2);
	}

	public static void setLighting(boolean on) {
		LIGHTING_ENABLED = on;
	}
}
