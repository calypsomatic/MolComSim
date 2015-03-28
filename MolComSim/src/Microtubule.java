/**
 * A Molecular rail on which molecules
 * can be guided to their destination
 *
 */

public class Microtubule {

	private double radius;
	private double length = 0;
	//Positions for each end of the microtubule
	//TODO: Might need more descriptive names
	private Position startPoint;
	private Position endPoint;
	private MolComSim simulation;
	private Position directionVector;
	
	public Microtubule(Position start, Position end, double r, MolComSim sim) {
		startPoint = start;
		endPoint = end;
		this.radius = r;
		this.simulation = sim;
	}
	
	public double getRadius() {
		return radius;
	}

	public Position getStartPoint() {
		return startPoint;
	}

	public Position getEndPoint() {
		return endPoint;
	}
	
	public MolComSim getSimulation(){
		return simulation;
	}
	
	//Not a position exactly, but holds the x, y, z values of a vector pointed
	//from MinusEndPoint to PlusEndPoint
	public Position getDirectionVector(){
		if (directionVector == null){
			double vel = simulation.getSimParams().getVelRail();
			double x1 = startPoint.getX();
			double y1 = startPoint.getY();
			double z1 = startPoint.getZ();
			double x2 = endPoint.getX();
			double y2 = endPoint.getY();
			double z2 = endPoint.getZ();
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
	
	public boolean isNearby(Position pos, double rad){
		/*
		 * Considering the microtubule to be a vector A,
		 * and creating another vector B from the start of the microtubule
		 * to pos, find the projection of B onto A 
		 */
		Position A = new Position(endPoint.getX() - startPoint.getX(),
				endPoint.getY() - startPoint.getY(),
				endPoint.getZ() - startPoint.getZ());

		Position B = new Position(pos.getX() - startPoint.getX(),
				pos.getY() - startPoint.getY(),
				pos.getZ() - startPoint.getZ());
		double AdotB = dot(A, B);
		double MTLength = endPoint.getDistance(startPoint);
		//if this value is negative, the closest point from pos to the microtubule
		//is beyond the microtubule's endpoint
		if (AdotB < 0)
			return false;
		//Find the component of B that is parallel to A
		if(startPoint.getDistance(pos) == 0.0) 
			return true;
		double bpCoeff = AdotB/(MTLength*startPoint.getDistance(pos));
		Position BParallel = new Position (A.getX() * bpCoeff, A.getY()*bpCoeff, A.getZ()*bpCoeff);
		//if this length is greater than MTLength, closest point from pos to microtubule
		//is beyond the microtubule's other endpoint
		double bpLength = Math.sqrt(BParallel.getX()*BParallel.getX() + BParallel.getY()*BParallel.getY()
				+ BParallel.getZ()*BParallel.getZ());
		if (bpLength > MTLength)
			return false;
		//Find the perpendicular component of B
		Position BPerp = new Position(B.getX() - BParallel.getX(), B.getY() - BParallel.getY(),
				B.getZ() - BParallel.getZ());
		//Find the magnitute of BPerpendicular
		double bperpLength = Math.sqrt(BPerp.getX()*BPerp.getX() + BPerp.getY()*BPerp.getY()
				+ BPerp.getZ()*BPerp.getZ());
		//If this length is less than molecule's radius + microtubule's radius, they are close enough
		if (bperpLength <= rad + radius)
			return true;
		//Note: might want to check for weird cases where pos is very close to MT ends
		return false;
	}
	

	//Returns the dot product of two position vectors
	private double dot(Position a, Position b){
		return a.getX()*b.getX() + a.getY()*b.getY() + a.getZ()*b.getZ();
	}

}
