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
	
	public boolean isNearby(Position pos, double rad){
		/*
		 * Considering the microtubule to be a vector A,
		 * and creating another vector B from the start of the microtubule
		 * to pos, find the projection of B onto A 
		 */
		Position A = new Position(minusEndCenter.getX() - plusEndCenter.getX(),
				minusEndCenter.getY() - plusEndCenter.getY(),
				minusEndCenter.getZ() - plusEndCenter.getZ());

		Position B = new Position(pos.getX() - plusEndCenter.getX(),
				pos.getY() - plusEndCenter.getY(),
				pos.getZ() - plusEndCenter.getZ());
		double AdotB = dot(A, B);
		double MTLength = minusEndCenter.getDistance(plusEndCenter);
		//if this value is negative, the closest point from pos to the microtubule
		//is beyond the microtubule's endpoint
		if (AdotB < 0)
			return false;
		//Find the component of B that is parallel to A
		double bpCoeff = AdotB/(MTLength*plusEndCenter.getDistance(pos));
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
	
	/**
	 * 
	 * @param pos The position being tested for nearness to microtubule
	 * @param radius The radius defining how far away is "near"
	 * @return True if <position> is with <radius> of the microtubule
	 */
	//TODO: Account for radius of microtubule
	//TODO: Account for microtubule not having infinite length
	//TODO: Or replace it with finding the orthogonal between sphere and line
	public boolean isNearby2(Position pos, double radius){
		/* The direction vector for a line L perpendicular to the microtubule 
		 *  and passing through pos is:
		 *  (Ax - posX, Ay - posY, Az - posZ) 
		 *  where A is any point on the microtubule.  We can use one of the known 
		 *  end points to determine this:
		 */
		 
		/*Position orthogVect = new Position(plusEndCenter.getX() - pos.getX(), 
				plusEndCenter.getY() - pos.getY(), plusEndCenter.getZ() - pos.getZ());
		
		 /* The unit length normal line toward pos can be represented parametrically by:
		 *  orthogVectX*t + posX, orthogVectY*t + posY, orthogVectZ*t + posZ)
		 *  We then solve for this parameter t:
		 */
		
		double parameter = ((plusEndCenter.getX() - pos.getX())*(getDirectionVector().getX())
				+ (plusEndCenter.getX() - pos.getX())*(getDirectionVector().getX())
				+ (plusEndCenter.getX() - pos.getX())*(getDirectionVector().getX()))/
				(getDirectionVector().getX()*getDirectionVector().getX() +
						getDirectionVector().getY()*getDirectionVector().getY() +
						getDirectionVector().getZ()*getDirectionVector().getZ());
		
		
		/* For this line to be orthogonal to the microtubule, the dot product of
		 * their vectors must be zero:
		 *  getDirectionVector().getX()*Lx + getDirectionVector().getY()*Ly + getDirectionVector().getZ()*Lz = 0
		 */
		
		
		//Find the position of intersection of this line
		Position perpIntersect = new Position (pos.getX() + getDirectionVector().getX()*parameter,
				pos.getY() + getDirectionVector().getY()*parameter,
				pos.getZ() + getDirectionVector().getZ()*parameter);
		//Determine if distance is less than the radius of the microtubule + radius of molecule
		double molToMT = perpIntersect.getDistance(pos);
		if (molToMT <= radius + this.radius)
			return true;
		else
			return false;
		
		
		
		/*a = dot(P2-P3,P2-P1)
				b = -dot(P1-P3,P2-P1)
		//Solve for points a,b where line from p1 to p2 intersects orthogonally with a line from p3
		//p2 - p3/
		Position plusEndToCenter = new Position(plusEndCenter.getX() - pos.getX(), 
				plusEndCenter.getY() - pos.getY(),
				plusEndCenter.getZ() - pos.getZ());
		//p2 - p1
		Position plusToMinus = new Position(plusEndCenter.getX() - minusEndCenter.getX(), 
				plusEndCenter.getY() - minusEndCenter.getY(),
				plusEndCenter.getZ() - minusEndCenter.getZ());
		//p1 - p3
		Position minusEndToCenter = new Position(minusEndCenter.getX() - pos.getX(), 
				minusEndCenter.getY() - pos.getY(),
				minusEndCenter.getZ() - pos.getZ());
		//Find x
		double xValue = dot(plusEndToCenter, plusToMinus);
		double yValue = -dot(minusEndToCenter, plusToMinus);
				
		
		
		
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
		double sqterm = dirDiffDot*dirDiffDot - dist*dist + radius*radius;
		//If this value is negative, there is no solution for this intersection
		if (sqterm < 0)
			return false;
		//Else, the line and position intersect
		else
			return true;	*/
	}
	
	//Returns the dot product of two position vectors
	private double dot(Position a, Position b){
		return a.getX()*b.getX() + a.getY()*b.getY() + a.getZ()*b.getZ();
	}

}
