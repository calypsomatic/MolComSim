/**
 * A Molecular rail on which molecules
 * can be guided to their destination
 *
 */

public class Microtubule {

	//Positions for each end of the microtubule
	//TODO: Might need more descriptive names
	private Position startPoint;
	private Position endPoint;
	private MolComSim simulation;
	private Position directionVector;
	
	public Microtubule(Position start, Position end, MolComSim sim) {
		startPoint = start;
		endPoint = end;
		this.simulation = sim;
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
			int vel = simulation.getSimParams().getVelRail();
			int x1 = startPoint.getX();
			int y1 = startPoint.getY();
			int z1 = startPoint.getZ();
			int x2 = endPoint.getX(); 
			int y2 = endPoint.getY();
			int z2 = endPoint.getZ();
			//The difference between the start and end points for each coordinate,
			//Defines the direction along the microtubule
			int delX = (x2 - x1);
			int delY = (y2 - y1);
			int delZ = (z2 - z1);
			//Normalize the direction vector so that its length
			//is that of the distance traveled in one time step
			double unitLength = Math.sqrt(delX*delX + delY*delY + delZ*delZ);
			int x = (int)(((double)delX*(double)vel/unitLength) + 0.5);
			int y = (int)(((double)delY*(double)vel/unitLength) + 0.5);
			int z = (int)(((double)delZ*(double)vel/unitLength) + 0.5);
			directionVector = new Position(x, y, z);
		}
		return directionVector;
	}
	
	public boolean isNearby(Position pos){
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
		int AdotB = dot(A, B);
		int MTLength = endPoint.getDistance(startPoint);
		//if this value is negative, the closest point from pos to the microtubule
		//is beyond the microtubule's endpoint
		if (AdotB < 0)
			return false;
		//Find the component of B that is parallel to A
		if(startPoint.getDistance(pos) == 0) 
			return true;
		double bpCoeff = (double)AdotB/((double)MTLength*startPoint.getDistance(pos));
		Position BParallel = new Position ((int)(0.5 + ((double)A.getX() * bpCoeff)), 
						(int)(0.5 + ((double)A.getY() * bpCoeff)), 
						(int)(0.5 + ((double)A.getZ() * bpCoeff)));
		//if this length is greater than MTLength, closest point from pos to microtubule
		//is beyond the microtubule's other endpoint
		int bpLength = (int)(0.5 + (Math.sqrt(BParallel.getX()*BParallel.getX() + BParallel.getY()*BParallel.getY()
				+ BParallel.getZ()*BParallel.getZ())));
		if (bpLength > MTLength)
			return false;
		//Find the perpendicular component of B
		Position BPerp = new Position(B.getX() - BParallel.getX(), B.getY() - BParallel.getY(),
				B.getZ() - BParallel.getZ());
		//Find the magnitute of BPerpendicular
		int bperpLength = (int)(0.5 + Math.sqrt(BPerp.getX()*BPerp.getX() + BPerp.getY()*BPerp.getY()
				+ BPerp.getZ()*BPerp.getZ()));
		//If this length is less than molecule's radius + microtubule's radius, they are close enough
		if (bperpLength <= 2)
			return true;
		//Note: might want to check for weird cases where pos is very close to MT ends
		return false;
	}

	//Returns the dot product of two position vectors
	private int dot(Position a, Position b){
		return a.getX()*b.getX() + a.getY()*b.getY() + a.getZ()*b.getZ();
	}

}
