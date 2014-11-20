//package MComSim.MovementController;


public abstract class MovementController {

	private CollisionHandler collisionHandler;
	private MolComSim simulation;
	private Molecule molecule;

	protected MovementController(CollisionHandler collHandle, MolComSim sim, Molecule mol) {
		collisionHandler = collHandle;
		simulation = sim;
		molecule = mol;
	}

	public Position getNextPosition(Molecule molecule, MolComSim sim) {
		Position nextPosition = decideNextPosition(molecule);
		return collisionHandler.handlePotentialCollisions(molecule, nextPosition, simulation);
	}

	protected abstract Position decideNextPosition(Molecule molecule);

	protected MolComSim getSimulation() {
		return simulation;
	}

	protected Molecule getMolecule() {
		return molecule;
	}

}
