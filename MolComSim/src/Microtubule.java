/**
 * A Molecular rail on which molecules
 * can be guided to their destination
 *
 */

public class Microtubule {

	private double radius;
	//Positions for each end of the microtubule
	//TODO: Might need more descriptive names
	private Position plusEndCenter;
	private Position minusEndCenter;
	private MolComSim simulation;
	private Position directionVector;
	
	public Microtubule(Position pEndCntr, Position mEndCntr, double r, MolComSim sim) {
		this.plusEndCenter = pEndCntr;
		this.minusEndCenter = mEndCntr;
		this.radius = r;
		this.simulation = sim;
	}
	
	public double getRadius() {
		return radius;
	}

	public Position getPlusEndCenter() {
		return plusEndCenter;
	}

	public Position getMinusEndCenter() {
		return minusEndCenter;
	}
	
	public MolComSim getSimulation(){
		return simulation;
	}
	
	//Not a position exactly, but holds the x, y, z values of a vector pointed
	//from MinusEndPoint to PlusEndPoint
	public Position getDirectionVector(){
		if (directionVector == null){
			double vel = simulation.getSimParams().getVelRail();
			double x1 = minusEndCenter.getX();
			double y1 = minusEndCenter.getY();
			double z1 = minusEndCenter.getZ();
			double x2 = plusEndCenter.getX();
			double y2 = plusEndCenter.getY();
			double z2 = plusEndCenter.getZ();
			//The difference between the start and end points for each coordinate,
			//Defines the direction along the microtubule
			double delX = (x2 - x1);
			double delY = (y2 - y1);
			double delZ = (z2 - z1);
			//Normalize the direction vector so that its length
			//is that of the distance traveled in one time step
			double unitLength = Math.sqrt(delX*delX + delY*delY + delZ*delZ);
			double x = delX*vel/unitLength;
			double y = delY*vel/unitLength;
			double z = delZ*vel/unitLength;
			directionVector = new Position(x, y, z);
		}
		return directionVector;
	}
	
	/**
	 * 
	 * @param pos The position being tested for nearness to microtubule
	 * @param radius The radius defining how far away is "near"
	 * @return True if <position> is with <radius> of the microtubule
	 */
	public boolean isNearby(Position pos, double radius){
		//Distance between the starting end of the microtubule and the position
		double dist = minusEndCenter.getDistance(pos);
		//Vector difference between minusEndCenter and position
		Position diff = new Position(minusEndCenter.getX() - pos.getX(), 
				minusEndCenter.getY() - pos.getY(),
				minusEndCenter.getZ() - pos.getZ());
		//The dot product of the direction vector of the microtubule
		//and the difference vector from the position to the minusEndPoint
		double dirDiffDot = dot(getDirectionVector(), diff);
		//Solving for the intersection of the line from minusEndPoint to plusEndPoint
		//And a sphere of radius <radius> centered as <pos>
		double sqterm = Math.sqrt(dirDiffDot*dirDiffDot - dist*dist + radius*radius);
		//If this value is negative, there is no solution for this intersection
		if (sqterm < 0)
			return false;
		//Else, the line and position intersect
		else
			return true;	
	}
	
	//Returns the dot product of two position vectors
	private double dot(Position a, Position b){
		return a.getX()*b.getX() + a.getY()*b.getY() + a.getZ()*b.getZ();
	}

}
