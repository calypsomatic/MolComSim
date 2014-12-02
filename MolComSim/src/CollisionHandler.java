/**
 * Processes collisions based on what type of molecules
 * are colliding and the needs of the particular
 * simulation
 *
 */

public abstract class CollisionHandler {

	public abstract Position handlePotentialCollisions(Molecule mol, Position nextPosition, MolComSim simulation);

}
