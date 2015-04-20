import java.util.Scanner;

public class Position {

	private int x;
	private int y;
	private int z;
	public Position(int x0, int y0, int z0) {
		this.x = x0;
		this.y = y0;
		this.z = z0;
	}
	
	//Reads the three coordinates of a Position with input Scanner
	//Assumes written as (x, y, z)
	public Position(Scanner readParams) {
		readParams.useDelimiter("[,()\\s]+");
		x = readParams.nextInt();
		y = readParams.nextInt();
		z = readParams.nextInt();
		readParams.useDelimiter("[\\s]+");
		// read closing parens, throw it away.
		readParams.next();
	}

	public int getDistance(Position other) {
		return (int) (0.5 + Math.sqrt(Math.pow(x-other.getX(), 2) + Math.pow(y-other.getY(),2) 
				+ Math.pow(z-other.getZ(),2)));
	} 

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
	
	//TODO: Does this method belong here?
	//Should it also have a molecule passed in?  Does it need the radius, to check for equality?
	/*public boolean isOccupied(MolComSim simulation){
		for(Molecule m : simulation.getMolecules()){
			 //TODO: Should we implement our own equals for molecule?
		 	//if(!m.equals(mol)){ // && m not at dest or nextPosition in mol's dest or something
		 	if(m.getPosition().getDistance(this) < 1){//+mol.getRadius())){
		 		return true;
		 	}
		 }
		return false;
	}*/
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	@Override
	public boolean equals(Object other){
		if (other instanceof Position){
			Position nextother = (Position) other;
			if (x == nextother.getX() && y == nextother.getY()
				&& z == nextother.getZ())
				return true;
		}
		return false;
	}
	
	 @Override
	 public int hashCode() {
	        int hash = 1;
	        hash = hash * 17 + x;
	        hash = hash * 31 + y;
	        hash = hash * 13 + z;
	        return hash;
	    }

}
