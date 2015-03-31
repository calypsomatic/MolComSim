/**
 * Moves molecules according to passive transport,
 * using a random walk
 *
 */

import java.util.*;

public class DiffusiveRandomMovementController extends MovementController{
	private static Random random = new Random();
	
	public DiffusiveRandomMovementController(CollisionHandler collHandle, MolComSim sim, Molecule mol) {
		super(collHandle, sim, mol);
	}
	
	/** Randomly selects where to move molecule based on simulation step length parameters
	 *  @param molecule The molecule to move
	 *  @return the position to move to
	 * 
	 */
	protected Position decideNextPosition() {
		//Randomly decide the next position based on current position + some delta.
		Position currentPosition = getMolecule().getPosition();
		double currentX = currentPosition.getX();
		double currentY = currentPosition.getY(); 
		double currentZ = currentPosition.getZ();
		// The idea behind the getMolRandMove~ methods are that the molecule moves a random amount in one step
		// , and that will, for each dimension, be a random amount between -getMolRandMove~ and +getMolRandMove~
		double maxXDelta = getSimulation().getSimParams().getMolRandMoveX();
		double maxYDelta = getSimulation().getSimParams().getMolRandMoveY();;
		double maxZDelta = getSimulation().getSimParams().getMolRandMoveZ();;
		double nextX = currentX + (2 * random.nextDouble() * maxXDelta) - maxXDelta;
		double nextY = currentY + (2 * random.nextDouble() * maxYDelta) - maxYDelta;
		double nextZ = currentZ + (2 * random.nextDouble() * maxZDelta) - maxZDelta;
		Position nextPosition = new Position(nextX, nextY, nextZ);
		
		//If the molecule has ACTIVE movement type, it looks for a nearby microtubule to reattach to
		if (getMolecule().getMoleculeMovementType() == MoleculeMovementType.ACTIVE){
			for (Microtubule mt : getSimulation().getMicrotubules()){
				//If a microtubule is found nearby, the molecule attaches to it
				if (mt.isNearby(getMolecule().getPosition(), getMolecule().getRadius())){
					CollisionHandler collH = simulation.isUsingCollisions() ? new OnTubuleCollisionHandler() : new NullCollisionHandler();
					new OnMicrotubuleMovementController(collH, getSimulation(), getMolecule(), mt);
					break;
				}
			}
		}
		return nextPosition;
	}
}

