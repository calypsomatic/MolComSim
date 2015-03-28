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
	protected Position decideNextPosition() {
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
			System.out.println("step = " + getSimulation().getSimStep());
			if(getMolecule() instanceof AcknowledgementMolecule) {
				System.out.println("acknowledgement Molecule going from active to passive on microtubule with params: ");
				System.out.println("start: " + microtubule.getStartPoint() + " end " + microtubule.getEndPoint());
				System.out.println("molecule position " + getMolecule().getPosition());
			} else {
				System.out.println("information Molecule going from active to passive on microtubule with params: ");
				System.out.println("start: " + microtubule.getStartPoint() + " end " + microtubule.getEndPoint());						
				System.out.println("molecule position " + getMolecule().getPosition());
			}
			new DiffusiveRandomMovementController(collh, getSimulation(), getMolecule());
		}
		return nextPosition;
	}

}
