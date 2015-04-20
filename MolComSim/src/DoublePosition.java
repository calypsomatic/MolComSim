/**
 * A Position class that uses doubles instead of Ints
 * For use only in growing microtubules
 */

import java.util.HashSet;

public class DoublePosition {
	private double x;
	private double y;
	private double z;
	
	public DoublePosition(double x0, double y0, double z0){
		x = x0; y = y0; z = z0;
	}
	
	//Convert a regular int Position to doubles
	public DoublePosition toDouble(Position pos){
		return new DoublePosition(pos.getX(), pos.getY(), pos.getZ());
	}
	
	//Add two double positions together
	public DoublePosition addDouble(DoublePosition other){
		return new DoublePosition(x + other.getX(), y + other.getY(), z + other.getZ());
	}
	
	//Turns a DoublePosition into an int Position
	public Position toInt(){
		return new Position((int) x, (int) y, (int) z);
	}
	
	//Find all int Positions within the direction of a double vector
	public HashSet<Position> add(DoublePosition other){
		HashSet<Position> poses = new HashSet<Position>();
		double newX = x + other.getX();
		double newY = y + other.getY();
		double newZ = z + other.getZ();
		int Xdown = (int) newX;
		int Xup = (int)(newX + 0.5);
		int Ydown = (int) newY;
		int Yup = (int)(newY + 0.5);
		int Zdown = (int) newZ;
		int Zup = (int)(newZ + 0.5);
		poses.add(new Position(Xdown, Ydown, Zdown));
		poses.add(new Position(Xdown, Ydown, Zup));
		poses.add(new Position(Xdown, Yup, Zdown));
		poses.add(new Position(Xdown, Yup, Zup));
		poses.add(new Position(Xup, Ydown, Zdown));
		poses.add(new Position(Xup, Ydown, Zup));
		poses.add(new Position(Xup, Yup, Zdown));
		poses.add(new Position(Xup, Yup, Zup));
		return poses;
	}
	
	public double getX(){ return x;}
	public double getY(){ return y;}
	public double getZ(){ return z;}

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
}
