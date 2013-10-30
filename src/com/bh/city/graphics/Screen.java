package com.bh.city.graphics;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.bh.city.Game;
import com.bh.city.sprites.Sprite;
import com.bh.city.sprites.Spritesheet;

public class Screen {

	public static int WIDTH = Game.WIDTH;
	public static int HEIGHT = Game.HEIGHT;

	public static boolean LIGHTING_ENABLED = true;

	public static int xoff = 0;
	public static int yoff = 0;

	public static int[] pixels;
	public static int[] red_light;
	public static int[] green_light;
	public static int[] blue_light;
	
	public static List<Light> lights = new ArrayList<Light>();

	public static void init() {
		pixels = new int[WIDTH * HEIGHT];
		red_light = new int[WIDTH * HEIGHT];
		green_light = new int[WIDTH * HEIGHT];
		blue_light = new int[WIDTH * HEIGHT];
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			red_light[i] = 0xff;
			green_light[i] = 0xff;
			blue_light[i] = 0xff;
		}
	}

	public static void clearLighting(int r, int g, int b) {
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			red_light[i] = r;
			green_light[i] = g;
			blue_light[i] = b;
		}
	}
	
	public static void clear(int color) {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
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
				if(!LIGHTING_ENABLED) {
					red_light[i] = 0xff;
					green_light[i] = 0xff;
					blue_light[i] = 0xff;
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

				if(!LIGHTING_ENABLED) {
					red_light[i] = 0xff;
					green_light[i] = 0xff;
					blue_light[i] = 0xff;
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
			
			if(!LIGHTING_ENABLED) {
				if(i1 != -1) {
					red_light[i1] = 0xff;
					green_light[i1] = 0xff;
					blue_light[i1] = 0xff;
				}

				if(i2 != -1) {
					red_light[i2] = 0xff;
					green_light[i2] = 0xff;
					blue_light[i2] = 0xff;
				}
			}
			if(i1 !=  -1)
				pixels[i1] = col1;
			if(i2 !=  -1)
				pixels[i2] = col2;
		}
		for(int xx = 0; xx<=w; xx++) {
			if(xx + x < 0|| xx + x >= WIDTH) continue;
			if(y < 0 || y >= HEIGHT) i1 =  -1; 
			else i1 = (xx + x) + (y) * WIDTH;
			if(y + h < 0 || y + h >= HEIGHT) i2 = -1;
			else i2 = (xx + x) + (h + y) * WIDTH;
			col1 = c;
			col2 = c;

			if(!LIGHTING_ENABLED) {
				if(i1 != -1) {
					red_light[i1] = 0xff;
					green_light[i1] = 0xff;
					blue_light[i1] = 0xff;
				}

				if(i2 != -1) {
					red_light[i2] = 0xff;
					green_light[i2] = 0xff;
					blue_light[i2] = 0xff;
				}
			}
			if(i1 !=  -1)
				pixels[i1] = col1;
			if(i2 !=  -1)
				pixels[i2] = col2;
		}
	}

	private static int applyLighting(int col, int x, int y) {
		int i = x + y * WIDTH;
		return applyLighting(col, red_light[i], green_light[i], blue_light[i]);
	}

	private static int applyLighting(int col, int lr, int lg, int lb) {
		if (!LIGHTING_ENABLED || (lr == 255 && lg == 255 && lg == 255)) {
			return col;
		}
		if(lr + lg + lb <= 0) {
			return 0;
		}
		int r = (col >> 16) & 255;
		int g = (col >> 8)  & 255;
		int b = col & 255;
		
		r = r * lr / 255;
		g = g * lg / 255;
		b = b * lb / 255;

		col = r << 16 | g << 8 | b;
		
		return col;
	}

	public static void addLightSource(Light l) {
		if(!LIGHTING_ENABLED)
			return;
		
		lights.add(l);
	}

	public static void centerOn(int x, int y) {
		xoff = x - (WIDTH / 2);
		yoff = y - (HEIGHT / 2);
	}

	public static void setLighting(boolean on) {
		LIGHTING_ENABLED = on;
	}
	
	public static int[] getPostPixels(int[] p) {
		
		for(Light l : lights) {
			l.apply();
		}
		
		for(int y = 0; y < HEIGHT; y++) {
			for(int x = 0; x < WIDTH; x++) {
				if(!LIGHTING_ENABLED)
					p[x + y * WIDTH] = pixels[x + y * WIDTH];
				else
					p[x + y * WIDTH] = applyLighting(pixels[x + y * WIDTH], x, y);
			}
		}
		
		return p;
	}
	
	public static void makeFullScreen() {
		int w = (int)(Toolkit.getDefaultToolkit().getScreenSize()).getWidth();
		int h = (int)(Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
		
		if(Game.frame != null)
			Game.frame.dispose();
		Game.frame = new JFrame(Game.NAME);
		Game.frame.setMaximumSize(new Dimension(w, h));
		Game.frame.setMinimumSize(new Dimension(w, h));
		Game.frame.setPreferredSize(new Dimension(w, h));
		Game.frame.setResizable(false);
		Game.frame.setUndecorated(true);
		Game.frame.add(Game.instance);
		Game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game.frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null cursor"));
		Game.frame.setVisible(true);
		Game.frame.pack();
	}
	
	public static void makeNormalScreen() {
		Game.frame = new JFrame(Game.NAME);
		Game.frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		Game.frame.setSize(WIDTH * 3, HEIGHT * 3);
		Game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game.frame.setLocationRelativeTo(null);
		Game.frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null cursor"));
		Game.frame.add(Game.instance);
		Game.frame.setVisible(true);
	}
}
