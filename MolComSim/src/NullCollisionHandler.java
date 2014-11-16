//package MComSim.NullCollisionHandler;

public class NullCollisionHandler extends CollisionHandler{

	public Position handlePotentialCollisions(Molecule mol, Position nextPosition, MolComSim simulation) {
		return nextPosition;
	}

}
