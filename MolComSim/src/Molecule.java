/**
 *  Molecule class represents a generic molecule
 *  in the molecular communication simulation
 *  Contains information necessary for moving
 *  the molecule through the medium
 *
 */

public abstract class Molecule {

	private Position position;
	private double radius;
	private MovementController movementController;
	private MolComSim simulation;
	private MoleculeMovementType moleculeMovementType;

	protected Molecule(MovementController mc, Position psn, double r, MolComSim sim, MoleculeMovementType molMvType) {
		this.movementController = mc;
		this.position = psn;
		this.radius = r;
		this.simulation = sim;
		this.moleculeMovementType = molMvType;
	}
	
	//Moves the molecule as defined by its movementController
	public abstract void move();

	public Position getPosition() {
		return position;
	}

	public double getRadius() {
		return radius;
	}

	public void setMovementController(MovementController mc) {
		this.movementController = mc;
	}

	public MolComSim getSimulation() {
		return simulation;
	}

	public MovementController getMovementController() {
		return movementController;
	}

	protected void setPosition(Position p) {
		this.position = p;
	}

	public MoleculeMovementType getMoleculeMovementType() {
		return moleculeMovementType;
	}

}
