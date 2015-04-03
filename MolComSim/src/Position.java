import java.util.Scanner;

//package MComSim.Position;

public class Position {

	private double x;
	private double y;
	private double z;
	protected static final double THRESHOLD = 1.0;

	public Position(double x0, double y0, double z0) {
		int tempX = (int) x0;
		int tempY = (int) y0;
		int tempZ = (int) z0;
		this.x = x0 - tempX > 0.5 ? tempX + 1.0 : tempX;
		this.y = y0 - tempY > 0.5 ? tempY + 1.0 : tempY;
		this.z = z0 - tempZ > 0.5 ? tempZ + 1.0 : tempZ;
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

	@Override
	public boolean equals(Object other){
		if (other instanceof Position){
			Position nextother = (Position) other;
			if ((int) x == (int) nextother.getX() && (int) y == (int) nextother.getY()
				&& (int) z == (int) nextother.getZ())
				return true;
		/*if (Math.abs(x - other.getX()) <= THRESHOLD &&
				Math.abs(y - other.getY()) <= THRESHOLD &&
				Math.abs(z - other.getZ()) <= THRESHOLD)
			return true;*/
		}
		return false;
	}
	
	 @Override
	    public int hashCode() {
	        int hash = 1;
	        hash = hash * 17 + (int) x;
	        hash = hash * 31 + (int) y;
	        hash = hash * 13 + (int) z;
	        return hash;
	    }

}
