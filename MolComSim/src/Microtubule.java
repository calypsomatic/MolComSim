//package MComSim.Microtubule;

public class Microtubule {

	private double radius;
	private Position plusEndCenter;
	private Position minusEndCenter;

	public double getRadius() {
		return radius;
	}

	public Microtubule(Position pEndCntr, Position mEndCntr, double r) {
		this.plusEndCenter = pEndCntr;
		this.minusEndCenter = mEndCntr;
		this.radius = r;
	}

	public Position getPlusEndCenter() {
		return plusEndCenter;
	}

	public Position getMinusEndCenter() {
		return minusEndCenter;
	}

}
