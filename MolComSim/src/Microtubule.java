/**
 * A Molecular rail on which molecules
 * can be guided to their destination
 *
 */

public class Microtubule {

	//Positions for each end of the microtubule
	private Position startPoint;
	private Position endPoint;
	private MolComSim simulation;
	private DoublePosition directionVector;
	
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
	
	// Not a position exactly, but holds the x, y, z values of a vector pointed
	// from MinusEndPoint to PlusEndPoint of size velRail, so this will be the 
	// distance an individual molecule travels along a microtubule in a single step.
	public DoublePosition getDirectionVector(){
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
			double x = ((double)delX*(double)vel/unitLength);
			double y = ((double)delY*(double)vel/unitLength);
			double z = ((double)delZ*(double)vel/unitLength);
			directionVector = new DoublePosition(x, y, z);
		}
		return directionVector;
	}
	
	// Not a position exactly, but holds the x, y, z values of a vector pointed
	// from MinusEndPoint to PlusEndPoint of size 1 (the unit vector)
	public DoublePosition getUnitVector(){
		DoublePosition unitVector;
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
		double x = ((double)delX/unitLength);
		double y = ((double)delY/unitLength);
		double z = ((double)delZ/unitLength);
		unitVector = new DoublePosition(x, y, z);
		return unitVector;
	}

}
