
public class SimpleCollisionHandler implements CollisionHandler {

	@Override
	public Position handlePotentialCollisions(Molecule mol, Position nextPosition, MolComSim simulation){
		return nextPosition;
	}

}
