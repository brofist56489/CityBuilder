package com.bh.city;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

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
	
	public int x = World.WIDTH / 2 * 16;
	public int y = World.HEIGHT / 2 * 16;
	
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
		instance = this;
		
		Screen.init();
		MouseHandler.init(this);
		
		world = new World();
		town = new Town(world);
		town.addBuilding(new SolarPanel(0, 150, town));
		
		gui = new GUIManager();
		gui.addObject(new GUIImage(gui, 10, HEIGHT - 10, Sprite.guimap.getSprite(0)) {
			public void onLeftClick() {
				if(!MouseHandler.buttonDownOnce(1)) return;
				parent.addObject(new ResourceListWindow(parent, 10, 10));
			}
		});
	}
	
	public void run() {
		init();
		
		long lt = System.nanoTime();
		double delta = 0.0;
		double nsPt = 1000000000.0/60.0;
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
		if(MouseHandler.getButton() !=  -1) {
			town.onclick();
		}
		
		world.tick();
		if(tickCount % 6 == 0)
			town.tick();
		
		gui.tick();
		
		MouseHandler.postTick();
	}
	
	public void render() {
		Screen.LIGHTING_ENABLED = false;
		Screen.clearLighting(tickCount % 255);
		Screen.centerOn(x, y);
		
		world.render();
		town.render();
		
		renderGui();
		
		for(int y=0; y<HEIGHT; y++) {
			for(int x=0; x<WIDTH; x++) {
				pixels[x + y * WIDTH] = Screen.pixels[x + y * WIDTH];
			}
		}
		
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
		Screen.centerOn(WIDTH / 2, HEIGHT / 2);
		Screen.renderRect(0, HEIGHT - 13, WIDTH, 13, 0x3f3f3f);
		gui.render();
		Screen.centerOn(x, y);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		
		JFrame frame = new JFrame(NAME);
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setSize(WIDTH * 3, HEIGHT * 3);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		
		game.start();
	}
}
