/**
 * Class that determines how a molecule moves to its 
 * next position and how it deals with collisions
 */

public abstract class MovementController {

	private CollisionHandler collisionHandler;
	protected MolComSim simulation;
	private Molecule molecule;

	protected MovementController(CollisionHandler collHandle, MolComSim sim, Molecule mol) {
		collisionHandler = collHandle;
		simulation = sim;
		molecule = mol;
		mol.setMovementController(this);
	}

	/** 
	 * 
	 * @param molecule The molecule trying to move
	 * @param sim The simulation in which this is taking place
	 * @return the position the molecule should move to next
	 */
	public Position getNextPosition(Molecule molecule, MolComSim sim) {
		Position nextPosition = simulation.getMedium().getClosestPosition(decideNextPosition());
		return collisionHandler.handlePotentialCollisions(molecule, nextPosition, simulation);
	}

	protected abstract Position decideNextPosition();

	protected MolComSim getSimulation() {
		return simulation;
	}

	protected Molecule getMolecule() {
		return molecule;
	}

}
