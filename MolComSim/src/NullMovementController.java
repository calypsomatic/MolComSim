/**
 * A MovementController for a molecule that doesn't move
 *
 */

public class NullMovementController extends MovementController{
	
	public NullMovementController(CollisionHandler collHandle, MolComSim sim, Molecule mol) {
		super(collHandle, sim, mol);
	}
		
	protected Position decideNextPosition() {
		return getMolecule().getPosition();
	}



} 
