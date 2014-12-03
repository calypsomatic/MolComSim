//package MComSim.Position;

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
	
	//TODO: Does this method belong here?
	//Should it also have a molecule passed in?  Does it need the radius, to check for equality?
	public boolean isOccupied(MolComSim simulation){
		for(Molecule m : simulation.getMolecules()){
			 //TODO: Should we implement our own equals for molecule?
		 	//if(!m.equals(mol)){ // && m not at dest or nextPosition in mol's dest or something
		 	if(m.getPosition().getDistance(this) < m.getRadius()) {
		 		return true;
		 	}
		 }
		return false;
	}
	
	public String toString(){
		return "(" + x + ", " + y + ", " + z + ")";
	}

}
