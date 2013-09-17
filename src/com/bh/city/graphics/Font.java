package com.bh.city.graphics;

import com.bh.city.sprites.Sprite;

public class Font {
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz{}|~!#%')+-/\"$&(*,.0123456789;=?:<>[] ";
	
	public static void render(String msg, int x, int y) {
		for(int i=0; i<msg.length(); i++) {
			int index = chars.indexOf(msg.charAt(i));
			
			if(index >= 0) {
				Screen.render(Sprite.font, index, x + i * 8, y, 0);
			}
		}
	}
}
