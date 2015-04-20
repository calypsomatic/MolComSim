/*
 * The simplest collision handler ignores all possibility of a collision
 * 
 */
public class SimpleCollisionHandler implements CollisionHandler {

	@Override
	public Position handlePotentialCollisions(Molecule mol, Position nextPosition, MolComSim simulation){
		return nextPosition;
	}

}
