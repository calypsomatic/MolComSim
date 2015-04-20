/**
 * When a molecule on a microtubule collides
 * It gets knocked off the microtubule
 */

public class OnTubuleCollisionHandler extends CollisionDecorator{
	
	public OnTubuleCollisionHandler(CollisionHandler cH){
		super(cH);
	}

	/**
	 * @param mol The molecule trying to move
	 * @param nextPosition Where the molecule is trying to move to
	 * @param simulation The simulation in which this is taking place
	 * 
	 * @return nextPosition if unoccupied, otherwise stay in place but
	 *   get knocked off rail
	 */
	public Position handlePotentialCollisions(Molecule mol, Position nextPos, MolComSim simulation) {
		Position nextPosition = collH.handlePotentialCollisions(mol, nextPos, simulation);
		if (!simulation.getMedium().hasMolecule(nextPos)){
		//		if(!simulation.isOccupied(nextPosition)){
			return nextPosition;
		}
		else {
			new DiffusiveRandomMovementController(new StandardCollisionHandler(new SimpleCollisionHandler()), simulation, mol);
			return mol.getPosition();
		}
	}

} 
