/**
 *  Molecule class represents a generic molecule
 *  in the molecular communication simulation
 *  Contains information necessary for moving
 *  the molecule through the medium
 *
 */

public abstract class Molecule {

	protected Position position;
	private double radius;
	private MovementController movementController;
	private MolComSim simulation;
	private MoleculeMovementType moleculeMovementType;
	
	//Id of the message a molecule carries - null for noise molecules
	protected Integer msgId;

	protected Molecule(Position psn, double r, MolComSim sim, MoleculeMovementType molMvType) {
		this.movementController = null;
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
	
	public Integer getMsgId(){
		return this.msgId;
	}

}
