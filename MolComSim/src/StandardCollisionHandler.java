/**
 * General-case collisions handled:
 * Molecules do not move if a potential
 * collision is detected
 */

public class StandardCollisionHandler extends CollisionHandler{

	/** 
	 * 
	 *  @param mol The molecule trying to move
	 * @param nextPosition where the molecule is trying to move to
	 * @param simulation The simulation instance in which this is occurring
	 * 
	 *  @return nextPosition if unoccupied, or the molecule's current position
	 *   if moving to nextPosition would result in a collision
	 */
	public Position handlePotentialCollisions(Molecule mol, Position nextPosition, MolComSim simulation) {
		if (simulation.getMedium().isOccupied(nextPosition)){
			return mol.getPosition();
		}
		 simulation.moveObject(mol, mol.getPosition(), nextPosition);
		 return nextPosition;
	}

}
