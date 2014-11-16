//package MComSim.Position;

import java.io.*;
import java.util.*;

public class Position {

	private double x;
	private double y;
	private double z;

	public Position(double x0, double y0, double z0) {
		this.x = x0;
		this.y = y0;
		this.z = z0;
	}
	
	public double getDistance(Position other) {
		return Math.sqrt(Math.pow(x-other.getX(), 2) + Math.pow(y-other.getY(),2) 
				+ Math.pow(z-other.getZ(),2));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

}
