package com.bh.city;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.bh.city.graphics.Light;
import com.bh.city.graphics.Mouse;
import com.bh.city.graphics.Screen;
import com.bh.city.gui.GUIImage;
import com.bh.city.gui.GUIManager;
import com.bh.city.gui.windows.ResourceListWindow;
import com.bh.city.input.MouseHandler;
import com.bh.city.sprites.Sprite;
import com.bh.city.town.Town;
import com.bh.city.town.buildings.SolarPanel;
import com.bh.city.world.World;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public static JFrame frame;
	private static Thread game_thread;
	private static boolean running = false;
	
	public static final int WIDTH = 256;
	public static final int HEIGHT = WIDTH * 3 / 4;
	public static final String NAME = "City Builder";
	
	public static Game instance;

	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public static World world;
	public static int tickCount = 0;
	
	public static Town town;
	
	public static GUIManager gui;
	
	public int x = 0;
	public int y = 0;
	private Light mouseLight;
	
	public Game() {
		instance = this;
	}
	
	public void start() {
		if(running)
			return;
		running = true;
		game_thread = new Thread(this, "MAIN_GAME");
		game_thread.start();
	}
	
	public void stop() {
		running = false;
	}
	
	public void init() {
		
		Screen.init();
		MouseHandler.init(this);
		
		mouseLight = new Light(0, 0, 50, 255, 255, 255);
		Screen.addLightSource(mouseLight);
		
		world = World.loadFromImage("/testLevel.png");
		town = new Town(world);
		town.addBuilding(new SolarPanel(0, 150, town));
		
		gui = new GUIManager();
		gui.addObject(new GUIImage(gui, 10, HEIGHT - 10, Sprite.guimap.getSprite(0)) {
			public void onLeftClick() {
				if(!MouseHandler.buttonDownOnce(1)) return;
				
				parent.addObject(new ResourceListWindow(parent, 10, 10));
			}
		});
		gui.addObject(new GUIImage(gui, 25, HEIGHT - 11, Sprite.guimap.getSprite(1)) {
			public void onLeftClick() {
				if(!MouseHandler.buttonDownOnce(1)) return;
				System.out.println("FUNCTION NOT IMPLEMENTED YET");
			}
		});
	}
	
	public void run() {
		init();
		
		long lt = System.nanoTime();
		double delta = 0.0;
		double nsPt = 1000000000.0/30.0;
		long now;
		int ticks = 0, frames = 0;
		long ltr = System.currentTimeMillis();
		boolean shouldRender = false;
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lt) / nsPt;
			lt = now;
			shouldRender = false;
			
			while(delta >= 1) {
				tick();
				ticks++;
				shouldRender = true;
				delta--;
			}
			
			if(shouldRender) {
				render();
				frames++;
			}
			
			if(System.currentTimeMillis() - ltr >= 1000) {
				ltr += 1000;
				System.out.println(ticks + " ticks, " + frames + " frames");
				ticks = frames = 0;
			}
		}
	}
	
	public void tick() {
		tickCount++;
		MouseHandler.poll();
		
		
		if(MouseHandler.buttonDown(1)) {
			if(gui.onLeftClick()) {}
			else if(MouseHandler.hasMoved()) {
				x -= MouseHandler.rx;
				y -= MouseHandler.ry;
			}
		}
		if(MouseHandler.getButton() != -1) {
			town.onclick();
		}
		
		world.tick();
		if(tickCount % 3 == 0)
			town.tick();
		
		gui.tick();
		
		mouseLight.moveTo(MouseHandler.transX(), MouseHandler.transY());
		
		MouseHandler.postTick();
	}
	
	public void render() {
		Screen.LIGHTING_ENABLED = true;
		Screen.clear(0);
		Screen.centerOn(x, y);
		Screen.clearLighting(50, 50, 50);
		
		world.render();
		town.render();

		renderGui();
		
		Screen.LIGHTING_ENABLED = true;
		pixels = Screen.getPostPixels(pixels);		
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	private void renderGui() {
		Screen.LIGHTING_ENABLED = false;
		Screen.centerOn(WIDTH / 2, HEIGHT / 2);
		Screen.renderRect(0, HEIGHT - 13, WIDTH, 13, 0x3f3f3f);
		gui.render();
		Mouse.render();
		Screen.centerOn(x, y);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		
		Screen.makeNormalScreen();
		game.start();
	}
}
