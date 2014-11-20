/**
 * When a molecule on a microtubule collides
 * It gets knocked off the microtubule
 */

public class OnTubuleCollisionHandler extends CollisionHandler{

	/**
	 * @param mol The molecule trying to move
	 * @param nextPosition Where the molecule is trying to move to
	 * @param simulation The simulation in which this is taking place
	 * 
	 * @return nextPosition if unoccupied, otherwise stay in place but
	 *   get knocked off rail
	 */
	public Position handlePotentialCollisions(Molecule mol, Position nextPosition, MolComSim simulation) {
		if(!nextPosition.isOccupied(simulation)){
			return nextPosition;
		}
		else {
			mol.setMovementController(new DiffusiveRandomMovementController(new StandardCollisionHandler(), simulation, mol));
			return mol.getPosition();
		}
	}

}
