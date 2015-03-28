import java.util.Scanner;

//package MComSim.Position;

public class Position {

	private double x;
	private double y;
	private double z;
	protected static final double THRESHOLD = 1;

	public Position(double x0, double y0, double z0) {
		this.x = closestInt(x0);
		this.y = closestInt(y0);
		this.z = closestInt(z0);
	}
	
	public Position(Scanner readParams) {
		readParams.useDelimiter("[,()\\s]+");
		x = readParams.nextDouble();
		y = readParams.nextDouble();
		z = readParams.nextDouble();
		readParams.useDelimiter("[\\s]+");
		// read closing parens, throw it away.
		readParams.next();
	}

	public double getDistance(Position other) {
		return Math.sqrt(Math.pow(x-other.getX(), 2) + Math.pow(y-other.getY(),2) 
				+ Math.pow(z-other.getZ(),2));
	}
	
	public double closestInt(double n){
		if (n - (int) n > 0.5)
			return (int) n + 1.0;
		return (int) n;
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
	
	//TODO: Does this method belong here?
	//Should it also have a molecule passed in?  Does it need the radius, to check for equality?
	public boolean isOccupied(MolComSim simulation){
		for(Molecule m : simulation.getMolecules()){
			 //TODO: Should we implement our own equals for molecule?
		 	//if(!m.equals(mol)){ // && m not at dest or nextPosition in mol's dest or something
		 	if(m.getPosition().getDistance(this)<(m.getRadius())){//+mol.getRadius())){
		 		return true;
		 	}
		 }
		return false;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
	public boolean equals(Position other){
		if (Math.abs(x - other.getX()) < THRESHOLD &&
				Math.abs(y - other.getY()) < THRESHOLD &&
				Math.abs(z - other.getZ()) < THRESHOLD)
			return true;
		return false;
	}

}
