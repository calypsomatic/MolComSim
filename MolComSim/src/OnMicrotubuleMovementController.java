/**
 * MovementController for molecules in active transport
 * 
 *
 */
		

public class OnMicrotubuleMovementController extends MovementController{

	private Microtubule microtubule;

	public OnMicrotubuleMovementController(CollisionHandler collHandle, MolComSim sim, Molecule mol, Microtubule tubule) {
		super(collHandle, sim, mol);
		microtubule = tubule;
	}

	/**
	 * Determines the next position for a molecule moving along a microtubule
	 * @param molecule The molecule whose position is being decided
	 * @return the Position it should go to
	 */
	protected Position decideNextPosition(Molecule molecule) {
		Position currentPosition = getMolecule().getPosition();
		Position direction = microtubule.getDirectionVector();
		Position nextPosition = new Position(currentPosition.getX() + direction.getX(), currentPosition.getY() + direction.getY(), currentPosition.getZ() + direction.getZ());
		//If the molecule gets derailed, it moves to the same spot, but switches to passive movement off the microtubule
		if (Math.random() < this.simulation.getSimParams().getProbDRail()){
			CollisionHandler collh;
			if (simulation.isUsingCollisions())
				collh = new StandardCollisionHandler();
			else
				collh = new NullCollisionHandler();
			molecule.setMovementController(new DiffusiveRandomMovementController(collh, getSimulation(), getMolecule()));
		}
		return nextPosition;
	}

}
