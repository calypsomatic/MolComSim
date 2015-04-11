/**
 * General-case collisions handled:
 * Molecules do not move if a potential
 * collision is detected
 */

public class StandardCollisionHandler implements CollisionHandler{

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
		/*Legacy code,
		 *TODO: make isOccupied a method belonging to Position
		 *or maybe MolComSim,
		 *improve efficiency
		 *if(!nextPosition.isOccupied(simulation))	
		 *	return nextPosition;
		 *else
		 *	return mol.getPosition() 
		 */
		 /*for(Molecule m : simulation.getMolecules()){
			 //TODO: Should we implement our own equals for molecule?
		 	if(!m.equals(mol)){ // && m not at dest or nextPosition in mol's dest or something
		 		if(m.getPosition().getDistance(nextPosition)<(m.getRadius()+mol.getRadius())){
		 			return mol.getPosition();
		 		}
	 		}
		 }*/
		if (simulation.getMedium().isOccupied(nextPosition)){
			return mol.getPosition();
		}

		 simulation.moveObject(mol, mol.getPosition(), nextPosition);

		 return nextPosition;
	}

}
