/**
 * A MovementController for a molecule that doesn't move
 *
 */

public class NullMovementController extends MovementController{
	
	public NullMovementController(CollisionHandler collHandle, MolComSim sim, Molecule mol) {
		super(collHandle, sim, mol);
	}
	
	//TODO: Does this need a molecule at all?
	/*public NullMovementController(CollisionHandler collHandle, MolComSim sim) {
		super(collHandle, sim);
	}*/
	
	protected Position decideNextPosition() {
		return getMolecule().getPosition();
	}



} 
