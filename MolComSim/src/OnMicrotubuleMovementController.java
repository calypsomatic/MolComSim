//package MComSim.OnMicrotubuleMovementController;

public class OnMicrotubuleMovementController extends MovementController{

	private Microtubule microtubule;

	public OnMicrotubuleMovementController(CollisionHandler collHandle, MolComSim sim, Molecule mol, Microtubule tubule) {
		super(collHandle, sim, mol);
		microtubule = tubule;
	}

	/* UNFINISHED METHOD*/
	protected Position decideNextPosition(Molecule molecule) {
		/*decide next position based on microtubule, getMolecule().getPosition() and getSimulation().getSimParams.getVelRail().  Use algorithm similar to current code.
		 
	if(molecule gets derailed) // check using getSimulation().getSimParams().getProbDRail() and random number generator, as per current code.
	{
		molecule.setMoleculeMovementController(new DiffusiveRandomMovementController(new StandardCollisionHandler(), getSimulation(), getMolecule()));		
	} 
	return the next position*/
		Position nextPosition;
		Position plusend = microtubule.getPlusEndCenter();
		Position minusend = microtubule.getMinusEndCenter();
		Position currentPosition = getMolecule().getPosition();
		double velocity = getSimulation().getSimParams().getVelRail();
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
