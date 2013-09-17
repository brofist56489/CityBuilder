package com.bh.city.town.buildings;

import java.util.ArrayList;
import java.util.List;

public class Output {
	
	public static final int OUTPUT_COUNT = 2;
	
	private List<Outs> outputs = new ArrayList<Outs>();
	
	public Outs ENERGY = new Outs(0, 0, 0, "Energy");
	public Outs WATER = new Outs(0, 0, 0, "Water");
	
	public class Outs {
		public double input;
		public double output;
		public double storage;
		public String rep;
		
		public Outs(int in, int out, int storage, String rep) {
			input = in;
			output = out;
			this.storage = storage;
			this.rep = rep;
			outputs.add(this);
		}
	}
	
	public Outs getOut(String rep) {
		for(Outs o : outputs) {
			if(o.rep.equals(rep)) {
				return o;
			}
		}
		return null;
	}
	
	public List<Outs> getOuts() {
		return this.outputs;
	}
}
