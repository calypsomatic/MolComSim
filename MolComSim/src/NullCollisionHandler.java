/** Collisionless collision handler -
 * a molecule can always move to its intended next position
 * 
 */

public class NullCollisionHandler extends CollisionDecorator{
	
	public NullCollisionHandler(CollisionHandler cH){
		super(cH);
	}

	/**
	 * @param mol The molecule trying to move
	 * @param nextPosition Where the molecule is trying to move to
	 * @param simulation The simulation in which this is taking place
	 * 
	 * @return nextPosition There are no collisions
	 */
	public Position handlePotentialCollisions(Molecule mol, Position nextPosition, MolComSim simulation) {
		simulation.moveObject(mol, mol.getPosition(), nextPosition);
		return nextPosition;
	}

} 
