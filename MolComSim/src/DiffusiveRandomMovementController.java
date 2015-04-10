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
		int currentX = currentPosition.getX();
		int currentY = currentPosition.getY(); 
		int currentZ = currentPosition.getZ();
		// The idea behind the getMolRandMove~ methods are that the molecule moves a random amount in one step
		// , and that will, for each dimension, be a random amount between -getMolRandMove~ and +getMolRandMove~
		int maxXDelta = getSimulation().getSimParams().getMolRandMoveX();
		int maxYDelta = getSimulation().getSimParams().getMolRandMoveY();;
		int maxZDelta = getSimulation().getSimParams().getMolRandMoveZ();;
		int nextX = currentX + random.nextInt(1 + (2 * maxXDelta)) - maxXDelta;
		int nextY = currentY + random.nextInt(1 + (2 * maxYDelta)) - maxYDelta;
		int nextZ = currentZ + random.nextInt(1 + (2 * maxZDelta)) - maxZDelta;
		Position nextPosition = new Position(nextX, nextY, nextZ);
		
		//If the molecule has ACTIVE movement type, it looks for a nearby microtubule to reattach to
		if (getMolecule().getMoleculeMovementType() == MoleculeMovementType.ACTIVE){
			for (Microtubule mt : getSimulation().getMicrotubules()){
				//If a microtubule is found nearby, the molecule attaches to it
				if (mt.isNearby(getMolecule().getPosition())){
					CollisionHandler collH = simulation.isUsingCollisions() ? 
							new OnTubuleCollisionHandler() : new NullCollisionHandler();
					new OnMicrotubuleMovementController(collH, getSimulation(), getMolecule(), mt);
					break;
				}
			}
		}
		return nextPosition;
	}
}

