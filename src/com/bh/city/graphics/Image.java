package com.bh.city.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image extends Bitmap {

	public Image(int w, int h) {
		super(w, h);
	}
	
	public Image(int[] pixels, int w, int h) {
		super(w, h);
		this.pixels = pixels;
	}
	
	public Image(String path) {
		super(0, 0);
		
		BufferedImage i = null;
		try {
			i = ImageIO.read(Image.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		width = i.getWidth();
		height = i.getHeight();
		
		pixels = i.getRGB(0, 0, width, height, null, 0, width);
		for(int a=0; a<pixels.length; a++) {
			if(pixels[a] < 0) {
				pixels[a] += 0x1000000;
			}
		}
	}
}
