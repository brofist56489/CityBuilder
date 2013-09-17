package com.bh.city.input;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.bh.city.Game;
import com.bh.city.graphics.Screen;

public class MouseHandler implements MouseListener, MouseMotionListener {

	private static final int BUTTON_COUNT = 3;
	public static int x;
	public static int y;
	public static int rx;
	public static int ry;
	public static int heldFor;
	private static int currentPosX;
	private static int currentPosY;
	private static int ax = 0;
	private static int ay = 0;
	private static boolean[] state = new boolean[BUTTON_COUNT];
	private static MouseState[] poll = new MouseState[] { MouseState.RELEASED, MouseState.RELEASED, MouseState.RELEASED };

	private enum MouseState {
		RELEASED, PRESSED, ONCE
	}

	public static void init(Game game) {
		MouseHandler m = new MouseHandler();
		game.addMouseListener(m);
		game.addMouseMotionListener(m);
	}
	
	public static Rectangle getRect() {
		return new Rectangle(x, y, 1, 1);
	}
	
	public static Rectangle getTranslatedRect() {
		return new Rectangle(x+Screen.xoff, y+Screen.yoff,1,1);
	}

	public static void poll() {
		x += rx;
		y += ry;
		
		if(heldFor >= -1) {
			heldFor++;
		}
		
		for (int i = 0; i < BUTTON_COUNT; ++i) {
			if (state[i]) {
				if (poll[i] == MouseState.RELEASED)
					poll[i] = MouseState.ONCE;
				else
					poll[i] = MouseState.PRESSED;
			} else {
				poll[i] = MouseState.RELEASED;
			}
		}
	}
	
	public static void postTick() {
		rx = 0;
		ry = 0;
		x = ax;
		y = ay;
	}
	
	public static int getButton() {
		for(int i=0; i<3; i++) {
			if(poll[i] == MouseState.ONCE) return i;
		}
		return -1;
	}

	public static boolean buttonDownOnce(int button) {
		return poll[button - 1] == MouseState.ONCE;
	}

	public static boolean buttonDown(int button) {
		return poll[button - 1] == MouseState.ONCE || poll[button - 1] == MouseState.PRESSED;
	}

	public synchronized void mousePressed(MouseEvent e) {
		state[e.getButton() - 1] = true;
		heldFor = -1;
	}

	public synchronized void mouseReleased(MouseEvent e) {
		state[e.getButton() - 1] = false;
		heldFor = -2;
	}

	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseMoved(MouseEvent e) {
		currentPosX = e.getX() * Game.WIDTH / Game.instance.getWidth();
		currentPosY = e.getY() * Game.HEIGHT / Game.instance.getHeight();
		rx += currentPosX - ax;
		ry += currentPosY - ay;
		ax = currentPosX;
		ay = currentPosY;
	}

	public void mouseClicked(MouseEvent e) {

	}
	
	public static boolean hasMoved() {
		return (rx != 0 || ry != 0);
	}
}