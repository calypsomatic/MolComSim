/**
 * A Molecular rail on which molecules
 * can be guided to their destination
 *
 */

public class Microtubule {

	private double radius;
	//Positions for each end of the microtubule
	private Position plusEndCenter;
	private Position minusEndCenter;
	private MolComSim simulation;

	
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

}
